package com.example.Electrical.store.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.Electrical.store.exception.CategoryException;
import com.example.Electrical.store.exception.ProductException;
import com.example.Electrical.store.exception.UserException;
import com.example.Electrical.store.helper.Helper;
import com.example.Electrical.store.model.Category;
import com.example.Electrical.store.model.Product;
import com.example.Electrical.store.repository.CategoryRepository;
import com.example.Electrical.store.repository.CurrentUserSessionRepository;
import com.example.Electrical.store.repository.ProductRepository;


@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private CurrentUserSessionRepository currentUserSessionRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public void createProduct(Product product, String authenticationToken)
            throws UserException, ProductException, CategoryException {

        if (Helper.isLoggedIn(authenticationToken, currentUserSessionRepository)
                && Helper.isAdmin(authenticationToken, currentUserSessionRepository)) {

            Category category = categoryRepository.findById(product.getCategory().getId()).get();

            if (category == null) {
                throw new CategoryException("Category does not exist with given id : " + product.getCategory().getId());
            }

            Product existedProduct = productRepository.findByName(product.getName());

            if (existedProduct != null) {
                throw new ProductException("Product already exist");
            }

            productRepository.save(product);

        } else {
            throw new UserException("You don't have access to perform this operation or log in as an admin");
        }

    }

    @Override
    public void updateProduct(Product product, String authenticationToken)
            throws UserException, ProductException, CategoryException {
        if (Helper.isLoggedIn(authenticationToken, currentUserSessionRepository)
                && Helper.isAdmin(authenticationToken, currentUserSessionRepository)) {

            Category category = categoryRepository.findById(product.getCategory().getId()).get();

            if (category == null) {
                throw new CategoryException("Category does not exist with given id : " + product.getCategory().getId());
            }

            Product existedProduct = productRepository.findById(product.getId()).get();

            if (existedProduct == null) {
                throw new ProductException("Product does not exist");
            }

            productRepository.save(product);

        } else {
            throw new UserException("You don't have access to perform this operation or log in as an admin");
        }
    }

    @Override
    public List<Product> listAllPrdoucts() throws ProductException, UserException {

        List<Product> products = productRepository.findAll();

        if (products.isEmpty()) {
            throw new ProductException("No products Available");
        }

        return products;
    }

    @Override
    public void deleteProduct(Integer productId, String authenticationToken) throws ProductException, UserException {
        if (Helper.isLoggedIn(authenticationToken, currentUserSessionRepository)
                && Helper.isAdmin(authenticationToken, currentUserSessionRepository)) {

            Optional<Product> optionalProduct = productRepository.findById(productId);

            if (optionalProduct.isEmpty()) {
                throw new ProductException("Product does not exist");
            }

            Product existedProduct = optionalProduct.get();

            productRepository.delete(existedProduct);

        } else {
            throw new UserException("You don't have access to perform this operation or log in as an admin");
        }
    }

    @Override
    public Product getProductById(Integer id) throws UserException, ProductException {

        Optional<Product> optionalProduct = productRepository.findById(id);

        if (optionalProduct.isEmpty()) {
            throw new ProductException("Product does not exist");
        }

        return optionalProduct.get();

    }

}
