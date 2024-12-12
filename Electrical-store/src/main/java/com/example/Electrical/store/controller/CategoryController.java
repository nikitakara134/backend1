package com.example.Electrical.store.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.example.Electrical.store.exception.CategoryException;
import com.example.Electrical.store.exception.UserException;
import com.example.Electrical.store.model.Category;
import com.example.Electrical.store.service.CategoryService;

@CrossOrigin(origins = "http://localhost:3000/")
@RestController
public class CategoryController {

    private final Logger logger = LoggerFactory.getLogger(CategoryController.class);

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/categories")
    public ResponseEntity<String> createCategory(@RequestParam String authenticationToken, @RequestBody Category category)
            throws CategoryException, UserException {
        logger.info("Received Token for Create: {}", authenticationToken); // Логування токена
        categoryService.createCategory(category, authenticationToken);
        logger.info("Category Created Successfully: {}", category.getName());
        return new ResponseEntity<>("Category Created", HttpStatus.ACCEPTED);
    }

    @PutMapping("/categories")
    public ResponseEntity<String> updateCategory(@RequestParam String authenticationToken, @RequestBody Category category)
            throws CategoryException, UserException {
        logger.info("Received Token for Update: {}", authenticationToken); // Логування токена
        categoryService.updateCategory(category, authenticationToken);
        logger.info("Category Updated Successfully: {}", category.getName());
        return new ResponseEntity<>("Category Updated", HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/categories/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable("id") Integer id, @RequestParam String authenticationToken)
            throws CategoryException, UserException {
        logger.info("Received Token for Delete: {}", authenticationToken); // Логування токена
        categoryService.deleteCategory(id, authenticationToken);
        logger.warn("Category Deleted: {}", id);
        return new ResponseEntity<>("Category Deleted", HttpStatus.OK);
    }

    @GetMapping("/categories")
    public ResponseEntity<List<Category>> listAllCategories(@RequestParam String authenticationToken)
            throws CategoryException, UserException {
        logger.info("Received Token for List Categories: {}", authenticationToken); // Логування токена
        List<Category> categories = categoryService.listAllCategories(authenticationToken);
        logger.info("Categories Retrieved Successfully: {} found", categories.size());
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }
}
