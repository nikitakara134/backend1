package com.example.Electrical.store.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.Electrical.store.dto.AddItemToCartDto;
import com.example.Electrical.store.dto.CartDto;
import com.example.Electrical.store.dto.CartItemDto;
import com.example.Electrical.store.exception.CartException;
import com.example.Electrical.store.exception.CurrentUserServiceException;
import com.example.Electrical.store.exception.ProductException;
import com.example.Electrical.store.exception.UserException;
import com.example.Electrical.store.service.CartService;



@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*")  // Дозволяє запити з фронтенду
@RestController
public class CartController {

    private final Logger logger = LoggerFactory.getLogger(CartController.class);

    @Autowired
    private CartService cartService;

    @PostMapping("/addproducttocart")
    public ResponseEntity<String> addProductToCart(@RequestParam String authenticationToken,
                                                   @RequestBody AddItemToCartDto cartDto)
            throws CartException, CurrentUserServiceException, UserException, ProductException {

        cartService.addItemToCart(cartDto, authenticationToken);    // викликаємо CartService

        logger.info("Item added to cart");

        return new ResponseEntity<>("Item added to cart", HttpStatus.ACCEPTED);
    }

    @PutMapping("/cart")
    public ResponseEntity<String> updateCartItem(@RequestParam String authenticationToken,
                                                 @RequestBody CartItemDto cartDto)
            throws CartException, CurrentUserServiceException, UserException, ProductException {

        cartService.updateCartItem(cartDto, authenticationToken);

        logger.info("Cart Updated");

        return new ResponseEntity<>("Item updated to cart", HttpStatus.ACCEPTED);
    }

    @GetMapping("/carts/user")
    public ResponseEntity<CartDto> getCartByUserId(@RequestParam String authenticationToken)
            throws CartException, UserException, CurrentUserServiceException {

        CartDto userCart = cartService.getCartByUser(authenticationToken);

        return new ResponseEntity<>(userCart, HttpStatus.OK);
    }

    @DeleteMapping("/cart")
    public ResponseEntity<String> removeItemFromCart(@RequestParam String authenticationToken,
                                                     @RequestBody CartItemDto cartDto)
            throws CartException, CurrentUserServiceException, UserException, ProductException {

        cartService.removeCartItem(cartDto, authenticationToken);

        logger.info("Item removed from cart");

        return new ResponseEntity<>("Item removed from cart", HttpStatus.OK);
    }

    // Додатково обробка OPTIONS запиту (якщо потрібно):
    @RequestMapping(value = "/addproducttocart", method = RequestMethod.OPTIONS)
    public ResponseEntity<Void> handleOptions() {
        return ResponseEntity.ok().build();
    }
}
