package com.dbConnectivity.dbConnectivity.DTO;

import com.dbConnectivity.dbConnectivity.entities.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class OrderDto {
    private Long orderId;
    private String orderStatus;
    private String productName;
    private double orderPrice;

    private User user;
}
