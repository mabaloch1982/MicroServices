package com.aa.microservices.product.service;

import com.aa.microservices.product.dto.ProductDto;
import com.aa.microservices.product.model.Product;
import com.aa.microservices.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public ProductDto saveProduct(ProductDto productDto) {
        log.info("Creating product: {}", productDto);
       Product product = productRepository.save(productDto.toProduct());
        log.info("Product created: {}", product);
        return productDto.fromProduct(product);

    }

    public List<ProductDto> getProducts() {
        return productRepository.findAll().stream()
                .map(ProductDto::fromProduct)
                .collect(Collectors.toList());
    }





}
