package com.example.Electrical.store.exception;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MyErrorDetails {

    private String message;
    private String description;
    private LocalDateTime time;

}
