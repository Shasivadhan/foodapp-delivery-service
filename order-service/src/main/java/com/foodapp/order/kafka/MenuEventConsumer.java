package com.foodapp.order.kafka;

import com.foodapp.order.event.MenuChangedEvent;
import com.foodapp.order.entity.MenuItem;
import com.foodapp.order.repository.MenuItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class MenuEventConsumer {

    @Autowired
    private MenuItemRepository menuItemRepository;

    @KafkaListener(topics = "menu-changes.v1", groupId = "order-service")
    public void consume(MenuChangedEvent event) {
        System.out.println("📥 Order Service received menu change event: " + event);

        // Try to find existing menu item
        MenuItem item = menuItemRepository.findById(event.getMenuItemId()).orElse(null);

        if (item != null) {
            // Update availability
            item.setAvailable(event.isAvailable());
            menuItemRepository.save(item);
            System.out.println("✅ Updated availability: " + item.getName() +
                    " (id=" + item.getId() + ") -> " + item.isAvailable());
        } else {
            // If item doesn’t exist, create placeholder entry
            MenuItem newItem = new MenuItem();
            newItem.setId(event.getMenuItemId());
            newItem.setVendorId(event.getVendorId());
            newItem.setName("Unknown Item"); // Vendor sync can update later
            newItem.setDescription("Placeholder from Vendor event");
            newItem.setPrice(0.0);
            newItem.setAvailable(event.isAvailable());

            menuItemRepository.save(newItem);
            System.out.println("ℹ️ Created new placeholder menu item from event: " + newItem.getId());
        }
    }
}
