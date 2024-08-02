package com.nareun.sbur_rest_demo.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nareun.sbur_rest_demo.config.Greeting;

@RestController
@RequestMapping("/greeting")
public class GreetingController {
    // @Value("${greeting-name: seongho}") //* greeting-name이 없으면 seongho : 띄어쓰기 주의
    // private String name;

    // @Value("${greeting-coffee: ${greeting-name} is drinking Cafe Ganador}")
    // private String coffee;
    private final Greeting greeting;

    public GreetingController(Greeting greeting){
        this.greeting = greeting;
    }

    @GetMapping
    String getString(){
        return greeting.getName(); 
    }

    @GetMapping("/coffee")
    String getNameAndCoffee(){
        return greeting.getCoffee();
    }
}
