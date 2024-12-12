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
import com.example.Electrical.store.exception.CategoryException;
import com.example.Electrical.store.exception.ProductException;
import com.example.Electrical.store.exception.UserException;
import com.example.Electrical.store.model.Product;
import com.example.Electrical.store.service.ProductService;
import org.springframework.web.bind.annotation.CrossOrigin;



@CrossOrigin("http://localhost:3000/")
@RestController
public class ProductController {

    private final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ProductService productService;

    @PostMapping("/products")
    public ResponseEntity<String> createProduct(@RequestParam String authenticationToken, @RequestBody Product product)
            throws UserException, ProductException, CategoryException {

        logger.info("called createProduct method of ProductService");
        productService.createProduct(product, authenticationToken);
        logger.info("Product Created");

        return new ResponseEntity<>("Product Created", HttpStatus.ACCEPTED);

    }


    @PutMapping("/products")
    public ResponseEntity<String> updateProduct(@RequestParam String authenticationToken, @RequestBody Product product)
            throws UserException, ProductException, CategoryException {

        logger.info("called updateProduct method of ProductService");
        productService.updateProduct(product, authenticationToken);
        logger.info("Product Updated");

        return new ResponseEntity<>("Product Updated", HttpStatus.ACCEPTED);

    }


    @GetMapping("/products")
    public ResponseEntity<List<Product>> listAllProducts()
            throws UserException, ProductException {

        logger.info("called listAllProducts method of ProductService");
        List<Product> products = productService.listAllPrdoucts();
        logger.info("All product details fetched successfully");

        return new ResponseEntity<>(products, HttpStatus.OK);

    }


    @DeleteMapping("/products/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable("id") Integer id, @RequestParam String authenticationToken)
            throws UserException, ProductException {

        logger.info("called deleteProduct method of ProductService");
        productService.deleteProduct(id, authenticationToken);
        logger.warn("Product Deleted");

        return new ResponseEntity<>("Product deleted", HttpStatus.OK);

    }


    @GetMapping("/products/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable("id") Integer id)
            throws UserException, ProductException {

        logger.info("called getProductById method of ProductService");
        Product product = productService.getProductById(id);
        logger.info("product details fetched successfully for given product id");

        return new ResponseEntity<>(product, HttpStatus.OK);

    }

}