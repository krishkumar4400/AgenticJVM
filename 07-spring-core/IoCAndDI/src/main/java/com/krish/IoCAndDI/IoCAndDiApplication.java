package com.krish.IoCAndDI;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class IoCAndDiApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(IoCAndDiApplication.class, args);
        System.out.println("Hello Spring");
//        Alien alien = new Alien();
        Alien alien = context.getBean(Alien.class);
        Alien alien2 = context.getBean(Alien.class);
        alien.code();
        alien2.code();

        Laptop laptop1 = context.getBean(Laptop.class);
        Laptop laptop2 = context.getBean(Laptop.class);
        laptop1.playMusic();
        laptop2.playMusic();

        Car car1 = context.getBean(Car.class);
        car1.drive();
        car1.stop();
	}

}
