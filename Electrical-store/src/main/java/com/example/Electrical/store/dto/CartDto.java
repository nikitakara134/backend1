package com.example.Electrical.store.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartDto {

    private Integer userId;

    private List<CartItemDto> cartItems;

    private Double totalCost;

    private String paymentMethod;

}
