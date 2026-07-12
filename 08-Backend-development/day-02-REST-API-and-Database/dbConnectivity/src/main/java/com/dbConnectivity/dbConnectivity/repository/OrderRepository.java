package com.dbConnectivity.dbConnectivity.repository;


import com.dbConnectivity.dbConnectivity.entities.Orders;
import org.springframework.data.jpa.repository.JpaRepository;


public interface OrderRepository extends JpaRepository<Orders, Long> {


}
