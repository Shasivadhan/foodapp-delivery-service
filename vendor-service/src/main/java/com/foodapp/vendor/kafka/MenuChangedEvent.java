package com.foodapp.vendor.event;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuChangedEvent {
    private String menuItemId;
    private String vendorId;
    private String type;        // ADD, REMOVE, UPDATE
    private boolean isAvailable;
}
