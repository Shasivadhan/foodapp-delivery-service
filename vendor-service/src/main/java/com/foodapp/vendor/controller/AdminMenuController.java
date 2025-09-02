package com.foodapp.vendor.controller;

import com.foodapp.vendor.entity.MenuItem;
import com.foodapp.order.event.MenuChangedEvent;
import com.foodapp.vendor.kafka.MenuEventProducer;
import com.foodapp.vendor.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/admin/menu")
public class AdminMenuController {

    @Autowired
    private MenuService menuService;

    @Autowired
    private MenuEventProducer producer;

    // Update menu availability and publish Kafka event
    @PostMapping("/change")
    public MenuItem changeAvailability(@RequestParam String itemId,
                                       @RequestParam boolean available) {
        MenuItem item = menuService.updateAvailability(itemId, available);
        if (item != null) {
            MenuChangedEvent event =
                    new MenuChangedEvent(item.getId(), item.getVendorId(), "UPDATE", available);
            producer.publish(event);
        }
        return item;
    }
}
