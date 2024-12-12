package com.example.Electrical.store.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @NotBlank
    private String name;

    @NotBlank
    @Size(min = 25, message = "description should contain minimum 25 characters")
    private String description;

    @NotBlank(message = "image link is mandatory")
    private String imageUrl;


    @NotNull
    private Double price;


    @NotNull
    private Integer stock;



    @NotNull
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

}
