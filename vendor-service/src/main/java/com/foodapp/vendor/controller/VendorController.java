package com.foodapp.vendor.controller;

import com.foodapp.vendor.entity.Vendor;
import com.foodapp.vendor.repository.VendorRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vendors")
public class VendorController {

    private final VendorRepository vendorRepository;

    public VendorController(VendorRepository vendorRepository) {
        this.vendorRepository = vendorRepository;
    }

    // ✅ Get all vendors
    @GetMapping
    public List<Vendor> getAllVendors(@RequestParam(required = false) String city) {
        if (city != null && !city.isEmpty()) {
            return vendorRepository.findByCityIgnoreCase(city);  // case-insensitive search
        }
        return vendorRepository.findAll();  // return all vendors if city not provided
    }

    // ✅ Get vendor by ID
    @GetMapping("/{id}")
    public Vendor getVendorById(@PathVariable String id) {
        return vendorRepository.findById(id).orElse(null);
    }
}
