package com.foodapp.vendor.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "menu_items")
public class MenuItem {

    @Id
    private String id;
    private String vendorId;
    private String name;
    private String description;
    private double price;
    private List<String> tags;
    private boolean isAvailable;
    private LocalDateTime lastUpdatedAt;

    // --- Constructors ---
    public MenuItem() {}

    public MenuItem(String id, String vendorId, String name, String description,
                    double price, List<String> tags, boolean isAvailable, LocalDateTime lastUpdatedAt) {
        this.id = id;
        this.vendorId = vendorId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.tags = tags;
        this.isAvailable = isAvailable;
        this.lastUpdatedAt = lastUpdatedAt;
    }

    // --- Getters and Setters ---
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getVendorId() { return vendorId; }
    public void setVendorId(String vendorId) { this.vendorId = vendorId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public List<String> getTags() { return tags; }
    public void setTags(List<String> tags) { this.tags = tags; }

    public boolean isAvailable() { return isAvailable; }
    public void setAvailable(boolean available) { isAvailable = available; }

    public LocalDateTime getLastUpdatedAt() { return lastUpdatedAt; }
    public void setLastUpdatedAt(LocalDateTime lastUpdatedAt) { this.lastUpdatedAt = lastUpdatedAt; }
}
