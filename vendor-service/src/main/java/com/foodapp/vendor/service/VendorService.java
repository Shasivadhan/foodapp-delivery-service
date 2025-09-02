// VendorService.java
package com.foodapp.vendor.service;

import com.foodapp.vendor.entity.Vendor;
import java.util.List;

public interface VendorService {
    Vendor addVendor(Vendor vendor);
    List<Vendor> getAllVendors();
    Vendor getVendorById(String vendorId);
}
