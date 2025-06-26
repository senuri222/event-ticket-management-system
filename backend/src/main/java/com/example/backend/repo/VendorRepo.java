package com.example.backend.repo;

import com.example.backend.entity.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface VendorRepo extends JpaRepository<Vendor, Integer> {
    Optional<Vendor> findByEmail(String email);
}
