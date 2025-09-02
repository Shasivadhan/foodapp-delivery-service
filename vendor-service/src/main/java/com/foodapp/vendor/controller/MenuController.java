package com.foodapp.vendor.controller;

import com.foodapp.vendor.entity.MenuItem;
import com.foodapp.vendor.repository.MenuItemRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/vendors/{vendorId}/menu")
public class MenuController {
    private final MenuItemRepository menuItemRepository;
    public MenuController(MenuItemRepository menuItemRepository) { this.menuItemRepository = menuItemRepository; }

    @GetMapping
    public List<MenuItem> getMenu(@PathVariable String vendorId, @RequestParam(defaultValue = "true") boolean available) {
        return menuItemRepository.findByVendorIdAndIsAvailable(vendorId, available);
    }
}
