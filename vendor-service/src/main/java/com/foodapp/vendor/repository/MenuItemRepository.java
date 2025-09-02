package com.foodapp.vendor.repository;

import com.foodapp.vendor.entity.MenuItem;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface MenuItemRepository extends MongoRepository<MenuItem, String> {

    // Fetch all items for a vendor
    List<MenuItem> findByVendorId(String vendorId);

    // Fetch only available items for a vendor
    List<MenuItem> findByVendorIdAndIsAvailable(String vendorId, boolean isAvailable);
}
