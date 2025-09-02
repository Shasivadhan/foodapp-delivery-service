package com.foodapp.vendor.repository;

import com.foodapp.vendor.entity.Vendor;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface VendorRepository extends MongoRepository<Vendor, String> {
    List<Vendor> findByCityIgnoreCase(String city);
}
