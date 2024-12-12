package com.example.Electrical.store.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.Electrical.store.exception.CategoryException;
import com.example.Electrical.store.exception.UserException;
import com.example.Electrical.store.helper.Helper;
import com.example.Electrical.store.model.Category;
import com.example.Electrical.store.repository.CategoryRepository;
import com.example.Electrical.store.repository.CurrentUserSessionRepository;


@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CurrentUserSessionRepository currentUserSessionRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public void createCategory(Category category, String authenticationToken) throws CategoryException, UserException {

        if (Helper.isLoggedIn(authenticationToken, currentUserSessionRepository)
                && Helper.isAdmin(authenticationToken, currentUserSessionRepository)) {

            Category existedCategory = categoryRepository.findByName(category.getName());

            if (existedCategory != null) {
                throw new CategoryException("Category already exists");
            }

            categoryRepository.save(category);


        } else {
            throw new UserException("You are not allowed to perform this operation");
        }

    }

    @Override
    public void updateCategory(Category category, String authenticationToken) throws CategoryException, UserException {
        if (Helper.isLoggedIn(authenticationToken, currentUserSessionRepository)
                && Helper.isAdmin(authenticationToken, currentUserSessionRepository)) {

            Category existedCategory = categoryRepository.findById(category.getId()).get();

            if (existedCategory == null) {
                throw new CategoryException("Category does not exists");
            }

            categoryRepository.save(category);


        } else {
            throw new UserException("You are not allowed to perform this operation");
        }

    }


    @Override
    public void deleteCategory(Integer categoryId, String authenticationToken) throws CategoryException, UserException {
        if (Helper.isLoggedIn(authenticationToken, currentUserSessionRepository)
                && Helper.isAdmin(authenticationToken, currentUserSessionRepository)) {

            Category existedCategory = categoryRepository.findById(categoryId).get();

            if (existedCategory == null) {
                throw new CategoryException("Category does not exists");
            }

            categoryRepository.delete(existedCategory);


        } else {
            throw new UserException("You are not allowed to perform this operation");
        }
    }

    @Override
    public List<Category> listAllCategories(String authenticationToken) throws CategoryException, UserException {
        if (Helper.isLoggedIn(authenticationToken, currentUserSessionRepository)
                && Helper.isAdmin(authenticationToken, currentUserSessionRepository)) {

            List<Category> categories = categoryRepository.findAll();

            if (categories.isEmpty()) {
                throw new CategoryException("No Categories available");
            }

            return categories;

        } else {
            throw new UserException("You are not allowed to perform this operation");
        }
    }

}
