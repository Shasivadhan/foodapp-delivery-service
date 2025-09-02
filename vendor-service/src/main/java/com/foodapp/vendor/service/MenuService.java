// MenuService.java
package com.foodapp.vendor.service;

import com.foodapp.vendor.entity.MenuItem;
import java.util.List;

public interface MenuService {
    MenuItem addMenuItem(MenuItem item);
    List<MenuItem> getItemsByVendor(String vendorId);
    MenuItem updateAvailability(String itemId, boolean available);
}
