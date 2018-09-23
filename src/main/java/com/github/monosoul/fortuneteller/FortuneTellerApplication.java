package com.github.monosoul.fortuneteller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class FortuneTellerApplication {
    public static void main(String[] args) {
        SpringApplication.run(FortuneTellerApplication.class, args);
    }
}
