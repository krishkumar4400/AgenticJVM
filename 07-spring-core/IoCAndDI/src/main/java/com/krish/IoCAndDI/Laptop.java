package com.krish.IoCAndDI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Laptop {

    @Autowired
    Cpu cpu;
    public void playMusic() {
        cpu.run();
//        System.out.println("Laptop play music");
    }

    public void compile() {
        cpu.run();
//        System.out.println("code has been compiled");
    }
}
