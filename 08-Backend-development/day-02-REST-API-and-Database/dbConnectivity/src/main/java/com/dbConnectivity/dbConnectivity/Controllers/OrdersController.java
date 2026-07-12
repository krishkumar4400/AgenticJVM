package com.dbConnectivity.dbConnectivity.Controllers;

import com.dbConnectivity.dbConnectivity.DTO.CreateOrderDto;
import com.dbConnectivity.dbConnectivity.DTO.OrderDto;
import com.dbConnectivity.dbConnectivity.Services.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/user/{userId}/orders")
public class OrdersController {
    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderDto> createOrder(@PathVariable Long userId, @RequestBody CreateOrderDto createOrderDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.createOrder(userId, createOrderDto));
    }

    //    get all orders of a user
    @GetMapping
    public ResponseEntity<List<OrderDto>> getUserOrders(@PathVariable Long userId) {
        return ResponseEntity.status(HttpStatus.OK).body(orderService.getUserOrders(userId));
    }

}

























