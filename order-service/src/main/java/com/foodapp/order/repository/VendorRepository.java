package com.foodapp.order.repository;

import com.foodapp.order.entity.Vendor;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface VendorRepository extends MongoRepository<Vendor, String> {
}
