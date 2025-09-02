// MenuServiceImpl.java
package com.foodapp.vendor.service.impl;

import com.foodapp.vendor.entity.MenuItem;
import com.foodapp.vendor.repository.MenuItemRepository;
import com.foodapp.vendor.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuItemRepository menuItemRepository;

    @Override
    public MenuItem addMenuItem(MenuItem item) {
        return menuItemRepository.save(item);
    }

    @Override
    public List<MenuItem> getItemsByVendor(String vendorId) {
        return menuItemRepository.findByVendorId(vendorId);
    }

    @Override
    public MenuItem updateAvailability(String itemId, boolean available) {
        MenuItem item = menuItemRepository.findById(itemId).orElse(null);
        if (item != null) {
            item.setAvailable(available);
            return menuItemRepository.save(item);
        }
        return null;
    }
}
