package com.dbConnectivity.dbConnectivity.repository;


import com.dbConnectivity.dbConnectivity.entities.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface OrderRepository extends JpaRepository<Orders, Long> {
    List<Orders> findByUserId(Long userId);

}
