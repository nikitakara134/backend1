package com.example.Electrical.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.Electrical.store.model.OrderProduct;

public interface OrderItemRepository extends JpaRepository<OrderProduct, Integer> {



}