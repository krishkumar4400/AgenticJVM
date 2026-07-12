package com.dbConnectivity.dbConnectivity.Services;

import com.dbConnectivity.dbConnectivity.DTO.CreateOrderDto;
import com.dbConnectivity.dbConnectivity.DTO.OrderDto;
import com.dbConnectivity.dbConnectivity.entities.Orders;
import com.dbConnectivity.dbConnectivity.entities.User;
import com.dbConnectivity.dbConnectivity.repository.OrderRepository;
import com.dbConnectivity.dbConnectivity.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    public OrderDto createOrder(Long userId, CreateOrderDto createOrderDto) {
        User user = userRepository.findById(userId).orElseThrow();
        Orders order = new Orders();
        order.setUser(user);
        order.setProductName(createOrderDto.getProductName());
        order.setOrderPrice(createOrderDto.getOrderPrice());
        order.setOrderStatus(createOrderDto.getOrderStatus());
        Orders savedOrder = orderRepository.save(order);
        return new OrderDto(savedOrder.getOrderId(), savedOrder.getOrderStatus(), savedOrder.getProductName(), savedOrder.getOrderPrice(), savedOrder.getUser());
    }
}

