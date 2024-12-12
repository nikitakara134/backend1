package com.example.Electrical.store.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.example.Electrical.store.dto.AddItemToCartDto;
import com.example.Electrical.store.dto.CartDto;
import com.example.Electrical.store.dto.CartItemDto;
import com.example.Electrical.store.dto.ProductDto;
import com.example.Electrical.store.exception.CartException;
import com.example.Electrical.store.exception.CurrentUserServiceException;
import com.example.Electrical.store.exception.ProductException;
import com.example.Electrical.store.exception.UserException;
import com.example.Electrical.store.helper.Helper;
import com.example.Electrical.store.model.Cart;
import com.example.Electrical.store.model.CurrentUserSession;
import com.example.Electrical.store.model.Product;
import com.example.Electrical.store.model.User;
import com.example.Electrical.store.repository.CartRepository;
import com.example.Electrical.store.repository.CurrentUserSessionRepository;
import com.example.Electrical.store.repository.ProductRepository;
import com.example.Electrical.store.repository.UserRepository;

@Service
public class CartServiceImpl implements CartService {

    private static final Logger logger = LoggerFactory.getLogger(CartServiceImpl.class);

    private final CurrentUserSessionRepository currentUserSessionRepository;
    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    @Autowired
    public CartServiceImpl(CurrentUserSessionRepository currentUserSessionRepository,
                           UserRepository userRepository,
                           CartRepository cartRepository,
                           ProductRepository productRepository) {
        this.currentUserSessionRepository = currentUserSessionRepository;
        this.userRepository = userRepository;
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
    }

    private User getUserFromSession(String authenticationToken) throws CurrentUserServiceException, UserException {
        if (!Helper.isLoggedIn(authenticationToken, currentUserSessionRepository)) {
            logger.error("Неправильный токен или пользователь не вошел в систему");
            throw new CurrentUserServiceException("Требуется вход в систему");
        }

        CurrentUserSession session = currentUserSessionRepository.findByAuthenticationToken(authenticationToken);
        return userRepository.findById(session.getUserId())
                .orElseThrow(() -> new UserException("Пользователь с данным ID не найден: " + session.getUserId()));
    }

    @Override
    public void addItemToCart(AddItemToCartDto cartDto, String authenticationToken)
            throws CartException, CurrentUserServiceException, UserException, ProductException {

        User user = getUserFromSession(authenticationToken);

        Product product = productRepository.findById(cartDto.getProductId())
                .orElseThrow(() -> new ProductException("Продукт с данным ID не найден"));

        if (product.getStock() < cartDto.getQuantity()) {
            throw new ProductException("Продукт закончился на складе");
        }

        Cart existingCart = cartRepository.findByUserAndProduct(user, product);
        if (existingCart != null) {
            throw new CartException("Продукт уже добавлен в корзину");
        }

        product.setStock(product.getStock() - cartDto.getQuantity());

        Cart newCart = new Cart();
        newCart.setCreateDateTime(LocalDateTime.now());
        newCart.setQuantity(cartDto.getQuantity());
        newCart.setProduct(product);
        newCart.setUser(user);

        cartRepository.save(newCart);
        logger.info("Продукт добавлен в корзину");
    }

    @Override
    public void removeCartItem(CartItemDto cartDto, String authenticationToken)
            throws CartException, CurrentUserServiceException, UserException, ProductException {

        User user = getUserFromSession(authenticationToken);

        Product product = productRepository.findById(cartDto.getProductDto().getId())
                .orElseThrow(() -> new ProductException("Продукт с данным ID не найден"));

        Cart existingCart = cartRepository.findByUserAndProduct(user, product);
        if (existingCart == null) {
            throw new CartException("Продукт не найден в корзине");
        }

        cartRepository.delete(existingCart);
        logger.info("Продукт удален из корзины");
    }

    @Override
    public CartDto getCartByUser(String authenticationToken)
            throws CartException, UserException, CurrentUserServiceException {

        User user = getUserFromSession(authenticationToken);

        List<Cart> carts = cartRepository.findByUserOrderByCreateDateTimeDesc(user);
        if (carts.isEmpty()) {
            throw new CartException("Корзина пуста");
        }

        CartDto cartDto = new CartDto();
        List<CartItemDto> cartItems = new ArrayList<>();
        double totalAmount = 0.0;

        for (Cart c : carts) {
            Product p = c.getProduct();
            ProductDto pDto = new ProductDto();
            BeanUtils.copyProperties(p, pDto);

            CartItemDto cartItemDto = new CartItemDto();
            cartItemDto.setId(c.getId());
            cartItemDto.setUserId(user.getId());
            cartItemDto.setQuantity(c.getQuantity());
            cartItemDto.setProductDto(pDto);
            cartItemDto.setTotal(p.getPrice() * c.getQuantity());

            cartItems.add(cartItemDto);
            totalAmount += cartItemDto.getTotal();
        }

        cartDto.setUserId(user.getId());
        cartDto.setCartItems(cartItems);
        cartDto.setTotalCost(totalAmount);

        logger.info("Корзина успешно загружена");
        return cartDto;
    }

    @Override
    public void updateCartItem(CartItemDto cartDto, String authenticationToken)
            throws CartException, CurrentUserServiceException, UserException, ProductException {

        User user = getUserFromSession(authenticationToken);

        Product product = productRepository.findById(cartDto.getProductDto().getId())
                .orElseThrow(() -> new ProductException("Продукт с данным ID не найден"));

        Cart existingCart = cartRepository.findByUserAndProduct(user, product);
        if (existingCart == null) {
            throw new CartException("Продукт не найден в корзине");
        }

        existingCart.setQuantity(cartDto.getQuantity());
        cartRepository.save(existingCart);
        logger.info("Продукт в корзине успешно обновлен");
    }
}
