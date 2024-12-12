package com.example.Electrical.store.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.Electrical.store.dto.OrderDto;
import com.example.Electrical.store.exception.CartException;
import com.example.Electrical.store.exception.CurrentUserServiceException;
import com.example.Electrical.store.exception.OrderException;
import com.example.Electrical.store.exception.UserException;
import com.example.Electrical.store.model.Order;
import com.example.Electrical.store.service.OrderService;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:3000/")
@RequestMapping("/orders")
public class OrderController {

    private final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private OrderService orderService;

    @PostMapping
    public ResponseEntity<String> makeOrder(
            @RequestHeader("Authorization") String authenticationToken,
            @RequestBody OrderDto orderDto
    ) throws UserException, CurrentUserServiceException, CartException {
        logger.info("Creating new order");
        orderService.makeOrder(orderDto, authenticationToken);
        return new ResponseEntity<>("Order Created", HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<Order>> listOrdersByUserId(
            @RequestHeader("Authorization") String authenticationToken,
            @PathVariable("id") Integer userId
    ) throws OrderException, UserException, CurrentUserServiceException {
        logger.info("Fetching orders for user: {}", userId);
        List<Order> orders = orderService.listOrdersByUserId(userId, authenticationToken);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Order>> listAllOrders(
            @RequestHeader("Authorization") String authenticationToken
    ) throws OrderException, UserException, CurrentUserServiceException {
        logger.info("Fetching all orders");
        List<Order> orders = orderService.listAllOrders(authenticationToken);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }
}
