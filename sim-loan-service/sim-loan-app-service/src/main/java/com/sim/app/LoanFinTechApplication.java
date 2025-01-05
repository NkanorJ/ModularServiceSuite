package com.sim.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableFeignClients
@EntityScan(basePackages = "com.sim.jpa")
@EnableJpaRepositories(basePackages = "com.sim.jpa")
@ComponentScan(basePackages = {"com.sim.app", "com.sim.rest", "com.sim.jpa", "com.sim.commons",
        "com.sim.auth", "com.sim.core", "com.sim.gateway"})
public class LoanFinTechApplication {

    public static void main(String[] args) {
        SpringApplication.run(LoanFinTechApplication.class, args);
    }

}
