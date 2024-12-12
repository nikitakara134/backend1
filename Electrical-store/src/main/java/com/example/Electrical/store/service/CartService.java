package com.example.Electrical.store.service;

import com.example.Electrical.store.dto.AddItemToCartDto;
import com.example.Electrical.store.dto.CartDto;
import com.example.Electrical.store.dto.CartItemDto;
import com.example.Electrical.store.exception.CartException;
import com.example.Electrical.store.exception.CurrentUserServiceException;
import com.example.Electrical.store.exception.ProductException;
import com.example.Electrical.store.exception.UserException;

public interface CartService {

    void addItemToCart(AddItemToCartDto cartDto, String authenticationToken)
            throws CartException, CurrentUserServiceException, UserException, ProductException;

    void removeCartItem(CartItemDto cartDto, String authenticationToken)
            throws CartException, CurrentUserServiceException, UserException, ProductException;

    CartDto getCartByUser(String authenticationToken)
            throws CartException, UserException, CurrentUserServiceException;

    void updateCartItem(CartItemDto cartDto, String authenticationToken)
            throws CartException, CurrentUserServiceException, UserException, ProductException;

}
