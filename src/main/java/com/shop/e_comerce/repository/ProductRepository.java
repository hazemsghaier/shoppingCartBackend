package com.shop.e_comerce.repository;

import com.shop.e_comerce.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByBrand(String brand);
    List<Product> findByCategoryName(String category);
    List<Product> findByCategoryNameAndBrand(String brand, String category);
   Long countByBrandAndName(String brand,String name);
}
