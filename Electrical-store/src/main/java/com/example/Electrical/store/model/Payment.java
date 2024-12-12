package com.example.Electrical.store.model;

import javax.persistence.Embeddable;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "payment")
public class Payment {


    @NotBlank
    private String paymentStatus;

    @NotBlank
    private String paymentMethod;

    @NotNull
    private Double amount;

}
