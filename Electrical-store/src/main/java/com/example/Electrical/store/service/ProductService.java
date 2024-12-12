package com.example.Electrical.store.service;

import java.util.List;
import com.example.Electrical.store.exception.CategoryException;
import com.example.Electrical.store.exception.ProductException;
import com.example.Electrical.store.exception.UserException;
import com.example.Electrical.store.model.Product;

public interface ProductService {

    void createProduct(Product product, String authenticationToken)
            throws UserException, ProductException, CategoryException;

    void updateProduct(Product product, String authenticationToken)
            throws UserException, ProductException, CategoryException;

    List<Product> listAllPrdoucts() throws UserException, ProductException;

    void deleteProduct(Integer productId, String authenticationToken) throws UserException, ProductException;

    Product getProductById(Integer id) throws UserException, ProductException;

}
