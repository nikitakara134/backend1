package com.example.Electrical.store.service;

import java.util.List;
import com.example.Electrical.store.exception.CategoryException;
import com.example.Electrical.store.exception.UserException;
import com.example.Electrical.store.model.Category;


public interface CategoryService {

    void createCategory(Category category, String authenticationToken) throws CategoryException, UserException;

    void updateCategory(Category category, String authenticationToken) throws CategoryException, UserException;

    void deleteCategory(Integer categoryId, String authenticationToken) throws CategoryException, UserException;

    List<Category> listAllCategories(String authenticationToken) throws CategoryException, UserException;

}