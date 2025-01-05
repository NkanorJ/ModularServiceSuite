package com.sim.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableAsync
@EnableScheduling
@SpringBootApplication
@EntityScan(basePackages = "com.sim.jpa")
@EnableJpaRepositories(basePackages = "com.sim.jpa")
@ComponentScan(basePackages = {"com.sim.app", "com.sim.rest", "com.sim.jpa", "com.sim.commons",
        "com.sim.auth", "com.sim.core", "com.sim.gateway"})
public class SimFinTechApplication {

    public static void main(String[] args) {
        SpringApplication.run(SimFinTechApplication.class, args);
    }

}
