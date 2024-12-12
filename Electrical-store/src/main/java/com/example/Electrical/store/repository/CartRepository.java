package com.example.Electrical.store.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.Electrical.store.model.Cart;
import com.example.Electrical.store.model.Product;
import com.example.Electrical.store.model.User;

public interface CartRepository extends JpaRepository<Cart, Integer> {

    Cart findByUserAndProduct(User user, Product product);

    List<Cart> findByUserOrderByCreateDateTimeDesc(User user);

    void deleteByUser(User user);

}
