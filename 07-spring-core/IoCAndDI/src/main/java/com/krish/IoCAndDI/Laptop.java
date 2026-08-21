package com.krish.IoCAndDI;

import org.springframework.stereotype.Component;

@Component
public class Laptop {
    public void playMusic() {
        System.out.println("Laptop play music");
    }
}
