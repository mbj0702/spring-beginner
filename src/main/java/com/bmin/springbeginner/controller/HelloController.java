package com.bmin.springbeginner.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {
   private int helloCount = 0;
    private int byeCount = 0;

    @RequestMapping("/hello")
    public String hello() {
        helloCount++;
        System.out.println("Hello In !!");
        return "hello";
    }

    @RequestMapping(value = "/bye")
    public String bye() {
        byeCount++;
        System.out.println("Bye In !!");
        return "bye";
    }
}
