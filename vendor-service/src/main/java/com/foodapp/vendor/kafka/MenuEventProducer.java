package com.foodapp.vendor.kafka;

import com.foodapp.order.event.MenuChangedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class MenuEventProducer {

    private static final String TOPIC = "menu-changes.v1";

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    public void publish(MenuChangedEvent event) {
        kafkaTemplate.send(TOPIC, event.getMenuItemId(), event);
        System.out.println("📢 Published menu event: " + event);
    }
}