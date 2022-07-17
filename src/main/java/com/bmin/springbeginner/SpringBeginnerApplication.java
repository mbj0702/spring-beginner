package com.bmin.springbeginner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBeginnerApplication {

    public static void main(String[] args) {
        System.out.println("Spring has started?");
        SpringApplication.run(SpringBeginnerApplication.class, args);
        System.out.println("Spring has ended?");
    }

}
