package com.foodapp.order.controller;

import com.foodapp.order.dto.CartItemDTO;
import com.foodapp.order.entity.Cart;
import com.foodapp.order.entity.MenuItem;
import com.foodapp.order.repository.CartRepository;
import com.foodapp.order.repository.MenuItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private MenuItemRepository menuItemRepo;
    @Autowired
    private CartRepository cartRepo;

    // Add item to cart
    @PostMapping("/items")
    public ResponseEntity<String> addItem(@RequestParam String userId,
                                          @RequestParam String menuItemId,
                                          @RequestParam int quantity) {
        try {
            MenuItem menuItem = menuItemRepo.findById(menuItemId)
                    .orElseThrow(() -> new RuntimeException("Menu item not found: " + menuItemId));

            Cart cart = cartRepo.findByUserId(userId).orElse(new Cart());
            cart.setUserId(userId);
            cart.setVendorId(menuItem.getVendorId());

            CartItemDTO dto = new CartItemDTO();
            dto.setMenuItemId(menuItem.getId());
            dto.setName(menuItem.getName());
            dto.setPrice(menuItem.getPrice());
            dto.setQuantity(quantity);

            if (cart.getItems() == null) {
                cart.setItems(new ArrayList<>());
            }
            cart.getItems().add(dto);

            double total = cart.getItems().stream()
                    .mapToDouble(item -> item.getPrice() * item.getQuantity())
                    .sum();
            cart.setTotal(total);

            cartRepo.save(cart);
            return ResponseEntity.ok(menuItem.getName() + " added to cart");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // Remove item from cart
    @DeleteMapping("/items/{userId}/{menuItemId}")
    public ResponseEntity<String> removeItem(@PathVariable String userId, @PathVariable String menuItemId) {
        return cartRepo.findByUserId(userId)
                .map(cart -> {
                    cart.getItems().removeIf(item -> item.getMenuItemId().equals(menuItemId));
                    double total = cart.getItems().stream()
                            .mapToDouble(item -> item.getPrice() * item.getQuantity())
                            .sum();
                    cart.setTotal(total);
                    cartRepo.save(cart);
                    return ResponseEntity.ok("Item removed from cart");
                })
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Cart not found for user " + userId));
    }
}
