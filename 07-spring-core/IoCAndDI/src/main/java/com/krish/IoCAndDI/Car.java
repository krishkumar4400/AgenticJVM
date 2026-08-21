package com.krish.IoCAndDI;

import org.springframework.stereotype.Component;

@Component
public class Car {
    public void drive(){
        System.out.println("Car drive...");
    }
    public void stop(){
        System.out.println("Car stop...");
    }
}
