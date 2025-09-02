package com.foodapp.order.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data   // Generates getters, setters, toString, equals, hashCode
@Document(collection = "users")
public class User {

    @Id
    private String id;        // e.g., "user-123"
    private String name;      // User's full name
    private String email;     // User's email
    private String phone;     // User's phone number
}
