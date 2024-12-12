package com.example.Electrical.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.Electrical.store.model.User;

public interface UserRepository extends JpaRepository<User, Integer>{

    User findByEmail(String email);

}
