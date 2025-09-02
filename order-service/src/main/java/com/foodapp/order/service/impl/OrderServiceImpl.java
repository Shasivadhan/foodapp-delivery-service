package com.foodapp.order.service.impl;

import com.foodapp.order.dto.CartItemDTO;
import com.foodapp.order.dto.OrderRequestDTO;
import com.foodapp.order.dto.FundTransferRequest;
import com.foodapp.order.dto.FundTransferResponse;
import com.foodapp.order.entity.Cart;
import com.foodapp.order.entity.MenuItem;
import com.foodapp.order.entity.Order;
import com.foodapp.order.entity.OrderItem;
import com.foodapp.order.entity.Vendor;
import com.foodapp.order.kafka.DeliveryEvent;
import com.foodapp.order.repository.CartRepository;
import com.foodapp.order.repository.MenuItemRepository;
import com.foodapp.order.repository.OrderRepository;
import com.foodapp.order.repository.VendorRepository;
import com.foodapp.order.service.OrderService;
import com.foodapp.order.feign.BankClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepo;
    @Autowired
    private CartRepository cartRepo;
    @Autowired
    private VendorRepository vendorRepo;
    @Autowired
    private MenuItemRepository menuItemRepository;   // ✅ to check availability
    @Autowired
    private BankClient bankClient;
    @Autowired
    private KafkaTemplate<String, DeliveryEvent> kafkaTemplate;

    private static final String DELIVERY_TOPIC = "delivery-requests.v1";  // ✅ versioned topic

    @Override
    public Order placeOrder(OrderRequestDTO request) {
        System.out.println("🟢 Starting order for userId=" + request.getUserId()
                + " account=" + request.getAccountNumber());

        // 1. Fetch user cart
        Optional<Cart> cartOpt = cartRepo.findByUserId(request.getUserId());
        if (!cartOpt.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Cart not found for user " + request.getUserId());
        }
        Cart cart = cartOpt.get();

        if (cart.getItems() == null || cart.getItems().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Cart is empty for user " + request.getUserId());
        }

        System.out.println("🟢 Cart found with total=" + cart.getTotal());

        // 2. Check item availability from menu_items (synced from VendorService)
        for (CartItemDTO dto : cart.getItems()) {
            MenuItem menuItem = menuItemRepository.findById(dto.getMenuItemId())
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.NOT_FOUND, "Menu item not found: " + dto.getMenuItemId()));

            if (!menuItem.isAvailable()) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, "Item not available: " + menuItem.getName());
            }
        }

        // 3. Convert cart items to order items
        List<OrderItem> orderItems = cart.getItems().stream()
                .map(this::mapToOrderItem)
                .collect(Collectors.toList());

        // 4. Create order
        Order order = new Order();
        order.setUserId(cart.getUserId());
        order.setVendorId(cart.getVendorId());
        order.setItems(orderItems);
        order.setTotal(cart.getTotal());
        order.setCreatedAt(LocalDateTime.now());

        // 5. Call Bank Service
        Vendor vendor = vendorRepo.findById(cart.getVendorId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Vendor not found: " + cart.getVendorId()));

        FundTransferRequest transfer = new FundTransferRequest();
        transfer.setFromAccount(request.getAccountNumber());
        transfer.setToAccount(vendor.getBankAccountNumber());
        transfer.setAmount(BigDecimal.valueOf(cart.getTotal()));

        System.out.println("🟢 Sending fund transfer to bank service:");
        System.out.println("   From Account: " + transfer.getFromAccount());
        System.out.println("   To Account: " + transfer.getToAccount());
        System.out.println("   Amount: " + transfer.getAmount());

        try {
            FundTransferResponse response = bankClient.transferFunds(transfer);

            System.out.println("🟢 Received bank response:");
            System.out.println("   Status: " + (response != null ? response.getStatus() : "null"));
            System.out.println("   Transaction ID: " + (response != null ? response.getTransactionId() : "null"));
            System.out.println("   Message: " + (response != null ? response.getMessage() : "null"));

            if (response != null && "SUCCESS".equalsIgnoreCase(response.getStatus())) {
                order.setStatus("PAID");
                order.setPaymentRef(response.getTransactionId());
            } else {
                System.out.println("❌ Payment failed, bank response indicates failure.");
                order.setStatus("FAILED");
            }
        } catch (Exception e) {
            System.out.println("❌ Exception occurred during payment:");
            e.printStackTrace();
            order.setStatus("FAILED");
        }

        // 6. Save order
        Order savedOrder = orderRepo.save(order);

        // 7. Produce Kafka delivery event
        try {
            DeliveryEvent event = new DeliveryEvent();
            event.setOrderId(savedOrder.getId());
            event.setUserId(savedOrder.getUserId());
            event.setProductNames(savedOrder.getItems().stream()
                    .map(OrderItem::getName)
                    .collect(Collectors.toList()));
            event.setTotalAmount(BigDecimal.valueOf(savedOrder.getTotal()));
            event.setDeliveryDate(LocalDate.now().plusDays(1).toString()); // ✅ dynamic date

            kafkaTemplate.send(DELIVERY_TOPIC, savedOrder.getId(), event); // ✅ use orderId as key
            System.out.println("🟢 Kafka delivery event sent: " + event);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("❌ Failed to send Kafka delivery event");
        }

        // 8. Clear cart
        cartRepo.delete(cart);

        return savedOrder;
    }

    @Override
    public List<Order> getOrdersByUser(String userId) {
        if (userId == null) {
            return orderRepo.findAll();
        }
        return orderRepo.findByUserId(userId);
    }

    @Override
    public List<Order> getOrdersByVendor(String vendorId) {
        return orderRepo.findByVendorId(vendorId);
    }

    private OrderItem mapToOrderItem(CartItemDTO dto) {
        OrderItem item = new OrderItem();
        item.setMenuItemId(dto.getMenuItemId());
        item.setName(dto.getName());
        item.setPrice(dto.getPrice());
        item.setQuantity(dto.getQuantity());
        return item;
    }
}
