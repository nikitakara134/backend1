package com.example.Electrical.store.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyResponseDto {

    private String message;
    private Boolean authorized;
    private String authenticationToken;

}
