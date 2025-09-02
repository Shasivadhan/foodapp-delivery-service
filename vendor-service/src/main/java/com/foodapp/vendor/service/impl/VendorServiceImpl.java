// VendorServiceImpl.java
package com.foodapp.vendor.service.impl;

import com.foodapp.vendor.entity.Vendor;
import com.foodapp.vendor.repository.VendorRepository;
import com.foodapp.vendor.service.VendorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class VendorServiceImpl implements VendorService {

    @Autowired
    private VendorRepository vendorRepository;

    @Override
    public Vendor addVendor(Vendor vendor) {
        return vendorRepository.save(vendor);
    }

    @Override
    public List<Vendor> getAllVendors() {
        return vendorRepository.findAll();
    }

    @Override
    public Vendor getVendorById(String vendorId) {
        return vendorRepository.findById(vendorId).orElse(null);
    }
}
