package com.foodapp.vendor.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import java.util.List;

@Data
@Document(collection = "vendors")
public class Vendor {
    @Id
    @Field("_id")   // 👈 Explicitly map MongoDB "_id" to this field
    private String id;

    private String name;
    private List<String> cuisineTypes;
    private String city;
    private String area;
    private double rating;
    private boolean isActive;
}
