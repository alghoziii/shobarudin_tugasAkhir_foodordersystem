package com.sinaukoding.foodordersystem.repository.master;

import com.sinaukoding.foodordersystem.entity.master.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface MenuItemRepository extends JpaRepository<MenuItem,String>, JpaSpecificationExecutor<MenuItem> {
    Optional<MenuItem> findByNamaIgnoreCase(String nama);
    Optional<MenuItem> findFirstByNamaContainingIgnoreCase(String nama);
}
