package com.leahtcodes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@SpringBootApplication()
public class Main {
    public static void main(String[] args){
        System.out.println("Hello World!");
        SpringApplication.run(Main.class, args);
    }

    @GetMapping("/")
    public String sayHi(){
        return "Hello World";
    }
}
