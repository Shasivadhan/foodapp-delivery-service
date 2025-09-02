package com.foodapp.order.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data  // ✅ Generates getters, setters, toString, equals, hashCode
@Document(collection = "vendors")
public class Vendor {

    @Id
    private String id;  // e.g., ven-1001

    @NotBlank(message = "Vendor name is required")
    private String name;

    @Email(message = "Invalid email format")
    private String email;

    @Pattern(regexp = "^[0-9]{10}$", message = "Phone must be 10 digits")
    private String phone;

    @NotBlank(message = "Bank account number is required")
    private String bankAccountNumber;
}
