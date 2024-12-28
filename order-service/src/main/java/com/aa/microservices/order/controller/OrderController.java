package com.aa.microservices.order.controller;

import com.aa.microservices.order.dto.OrderRequest;
import com.aa.microservices.order.dto.OrderResponse;
import com.aa.microservices.order.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/order")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderResponse createOrder(@Valid @RequestBody OrderRequest orderRequest) {
        return orderService.createOrder(orderRequest);
    }

    @GetMapping(value="/{orderId}")
    public OrderResponse getOrder(@Valid @PathVariable Long orderId) {
        return orderService.getOrder(orderId);
    }

    @GetMapping(value="/all")
    public List<OrderResponse> getOrders() {
        return orderService.getOrders();
    }
}
