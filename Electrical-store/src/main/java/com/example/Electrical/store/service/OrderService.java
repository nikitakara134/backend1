package com.example.Electrical.store.service;

import java.util.List;
import com.example.Electrical.store.dto.OrderDto;
import com.example.Electrical.store.exception.CartException;
import com.example.Electrical.store.exception.CurrentUserServiceException;
import com.example.Electrical.store.exception.OrderException;
import com.example.Electrical.store.exception.UserException;
import com.example.Electrical.store.model.Order;



public interface OrderService {

    void makeOrder(OrderDto orderDto, String authenticationToken)
            throws  UserException, CurrentUserServiceException, CartException;

    List<Order> listOrdersByUserId(Integer userId, String authenticationToken)
            throws OrderException, UserException, CurrentUserServiceException;

    List<Order> listAllOrders(String authenticationToken)
            throws OrderException, UserException, CurrentUserServiceException;

    void updateOrder(OrderDto orderDto,String authenticationToken) throws CurrentUserServiceException, OrderException;
}
