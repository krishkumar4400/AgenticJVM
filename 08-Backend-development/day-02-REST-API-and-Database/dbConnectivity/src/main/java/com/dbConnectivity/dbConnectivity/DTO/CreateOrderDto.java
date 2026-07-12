package com.dbConnectivity.dbConnectivity.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CreateOrderDto {
    private String productName;
    private double orderPrice;
    private String orderStatus;
}
