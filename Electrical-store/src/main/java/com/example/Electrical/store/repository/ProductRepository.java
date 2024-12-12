package com.example.Electrical.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.Electrical.store.model.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    Product findByName(String name);

}
