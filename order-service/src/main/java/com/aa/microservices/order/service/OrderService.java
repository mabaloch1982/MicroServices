package com.aa.microservices.order.service;

import com.aa.microservices.order.client.InventoryClient;
import com.aa.microservices.order.client.InventoryClientHttp;
import com.aa.microservices.order.dto.OrderRequest;
import com.aa.microservices.order.dto.OrderResponse;
import com.aa.microservices.order.model.Order;
import com.aa.microservices.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final InventoryClient inventoryClient;
    private final InventoryClientHttp inventoryClientHttp;


    public OrderResponse createOrder(OrderRequest orderRequest) {
        //call using Fein client
       // boolean isProductInStock = inventoryClient.isInStock(orderRequest.skuCode(), orderRequest.quantity());
        //Call using REST Client
        boolean isProductInStock = inventoryClientHttp.isInStock(orderRequest.skuCode(), orderRequest.quantity());
        if(!isProductInStock)
            throw new RuntimeException("Product is not in stock: SkuCode: "+orderRequest.skuCode());

         Order order = orderRepository.save(orderRequest.convertToOrder());
        return new OrderResponse(order.getId(),order.getOrderNumber(), order.getSkuCode(), order.getPrice(), order.getQuantity());
    }

    public OrderResponse getOrder(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Order not found"));
        return new OrderResponse(order.getId(),order.getOrderNumber(), order.getSkuCode(), order.getPrice(), order.getQuantity());
    }

    public List<OrderResponse> getOrders(){
        return orderRepository.findAll().stream()
                .map(order -> new OrderResponse(order.getId(),order.getOrderNumber(), order.getSkuCode(), order.getPrice(), order.getQuantity()))
                .collect(Collectors.toList());
    }
}
