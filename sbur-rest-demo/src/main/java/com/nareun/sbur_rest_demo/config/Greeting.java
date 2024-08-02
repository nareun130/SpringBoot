package com.nareun.sbur_rest_demo.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

//* 프로퍼티를 생성하는 클래스 -> @Value대신 사용하기 위해
@Configuration
@ConfigurationProperties(prefix = "greeting")
public class Greeting {
    private String name;
    private String coffee;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCoffee() {
        return coffee;
    }

    public void setCoffee(String coffee) {
        this.coffee = coffee;
    }

}
