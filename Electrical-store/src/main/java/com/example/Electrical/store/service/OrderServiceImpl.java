package com.example.Electrical.store.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Electrical.store.dto.OrderDto;
import com.example.Electrical.store.exception.CartException;
import com.example.Electrical.store.exception.CurrentUserServiceException;
import com.example.Electrical.store.exception.OrderException;
import com.example.Electrical.store.exception.UserException;
import com.example.Electrical.store.helper.Helper;
import com.example.Electrical.store.model.Cart;
import com.example.Electrical.store.model.CurrentUserSession;
import com.example.Electrical.store.model.Order;
import com.example.Electrical.store.model.OrderProduct;
import com.example.Electrical.store.model.Payment;
import com.example.Electrical.store.model.User;
import com.example.Electrical.store.repository.*;


@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CurrentUserSessionRepository currentUserSessionRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Transactional
    @Override
    public void makeOrder(OrderDto orderDto, String authenticationToken)
            throws UserException, CurrentUserServiceException, CartException {

        if (!Helper.isLoggedIn(authenticationToken, currentUserSessionRepository)) {
            throw new CurrentUserServiceException("login required");

        }

        CurrentUserSession session = currentUserSessionRepository.findByAuthenticationToken(authenticationToken);

        Optional<User> optionalUser = userRepository.findById(session.getUserId());

        if (optionalUser.isEmpty()) {
            throw new UserException("No users exists with the given id" + orderDto.getUserId());
        }

        User user = optionalUser.get();

        List<Cart> cart = cartRepository.findByUserOrderByCreateDateTimeDesc(user);

        if (cart.isEmpty()) {
            throw new CartException("Cannot Process on empty cart");
        }

        Payment payment = new Payment();

        payment.setPaymentMethod(orderDto.getPaymentMethod());

        String paymentStatus = "pending";

        payment.setPaymentStatus(paymentStatus);

        if (orderDto.getPaymentMethod().equals("online-payment")) {
            paymentStatus = "success";
            payment.setPaymentStatus(paymentStatus);
        }

        Order newOrder = new Order();

        newOrder.setCreateDateTime(LocalDateTime.now());
        newOrder.setUser(user);
        newOrder.setOrderStatus("pending");
        newOrder.setPayment(payment);

        orderRepository.save(newOrder);

        Double totalAmount = 0.0;

        for (Cart c : cart) {

            OrderProduct orderProduct = new OrderProduct();

            orderProduct.setCreateDateTime(LocalDateTime.now());
            orderProduct.setOrder(newOrder);
            orderProduct.setProduct(c.getProduct());
            orderProduct.setQuantity(c.getQuantity());
            orderProduct.setTotalPrice(c.getProduct().getPrice() * c.getQuantity());

            totalAmount += c.getQuantity() * c.getProduct().getPrice();

            orderItemRepository.save(orderProduct);
        }
        payment.setAmount(totalAmount);
        orderRepository.save(newOrder);

        cartRepository.deleteByUser(user);
    }

    @Override
    public List<Order> listOrdersByUserId(Integer userId, String authenticationToken)
            throws OrderException, UserException, CurrentUserServiceException {

        if (!Helper.isLoggedIn(authenticationToken, currentUserSessionRepository)) {
            throw new CurrentUserServiceException("login required");

        }
        Optional<User> optionalUser;
        if (!Helper.isAdmin(authenticationToken, currentUserSessionRepository)) {
            CurrentUserSession session = currentUserSessionRepository.findByAuthenticationToken(authenticationToken);
            optionalUser = userRepository.findById(session.getUserId());
        } else {
            optionalUser = userRepository.findById(userId);
        }

        if (optionalUser.isEmpty()) {
            throw new UserException("No users exists with the given id" + userId);
        }

        User user = optionalUser.get();

        List<Order> orders = user.getOrders();

        if (orders.isEmpty()) {
            throw new OrderException("No orders available");
        }

        return orders;
    }

    @Override
    public List<Order> listAllOrders(String authenticationToken)
            throws OrderException, UserException, CurrentUserServiceException {

        if (!Helper.isLoggedIn(authenticationToken, currentUserSessionRepository)) {
            throw new CurrentUserServiceException("login required");

        }

        if (!Helper.isAdmin(authenticationToken, currentUserSessionRepository)) {
            throw new CurrentUserServiceException("you are not allowed to perform this action");

        }

        List<Order> allOrders = orderRepository.findAll();

        if (allOrders.isEmpty()) {
            throw new OrderException("no orders avalilable");
        }

        return allOrders;
    }

    @Override
    public void updateOrder(OrderDto orderDto, String authenticationToken) throws CurrentUserServiceException, OrderException {
        if (!Helper.isLoggedIn(authenticationToken, currentUserSessionRepository)) {
            throw new CurrentUserServiceException("login required");

        }

        if (!Helper.isAdmin(authenticationToken, currentUserSessionRepository)) {
            throw new CurrentUserServiceException("you are not allowed to perform this action");

        }

        Optional<Order> optionalOrder = orderRepository.findById(orderDto.getOrderId());

        if (optionalOrder.isEmpty()) {
            throw new OrderException("Order does not exists with given order id :" + orderDto.getOrderId());
        }

        Order order = optionalOrder.get();

        order.setOrderStatus(orderDto.getOrderStatus());


        orderRepository.save(order);

    }

}
