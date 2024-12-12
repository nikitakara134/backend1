package com.example.Electrical.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.Electrical.store.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

    Category findByName(String name);

}

