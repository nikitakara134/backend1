package com.example.Electrical.store.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddItemToCartDto {

    private Integer productId;
    private Integer quantity;
    private Double total;
}
