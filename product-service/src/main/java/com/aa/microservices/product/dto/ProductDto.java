package com.aa.microservices.product.dto;

import com.aa.microservices.product.model.Product;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record ProductDto(String id,
                         @NotBlank(message = "Name is mandatory")
                         String name,
                         String description,
                         @NotNull(message = "Price is mandatory")
                         @Positive(message = "Price must be greater than zero")
                         BigDecimal price
        ) {

    public static ProductDto fromProduct(Product product) {
        return new ProductDto(product.getId(), product.getName(), product.getDescription(), product.getPrice());
    }

    public  Product toProduct() {
        return new Product(id, name, description, price);
    }

}
