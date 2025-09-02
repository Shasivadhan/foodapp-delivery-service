package com.foodapp.vendor.kafka;

import com.foodapp.order.event.MenuChangedEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class MenuEventConsumer {

    @KafkaListener(topics = "menu-changes.v1", groupId = "vendor-service")
    public void consume(MenuChangedEvent event) {
        System.out.println("📥 Received menu change event: " + event);
    }
}