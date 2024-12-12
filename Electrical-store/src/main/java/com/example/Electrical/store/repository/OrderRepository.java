package com.example.Electrical.store.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.Electrical.store.model.Order;
import com.example.Electrical.store.model.User;

public interface OrderRepository extends JpaRepository<Order, Integer> {

    List<Order> findByUser(User user);

}

