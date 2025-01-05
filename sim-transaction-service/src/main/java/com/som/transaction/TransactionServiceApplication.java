package com.som.transaction;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@EnableDiscoveryClient
//@EnableFeignClients
//@ImportAutoConfiguration({FeignAutoConfiguration.class})
//@Import({AkuCommons.class, SecurityConfiguration.class, SchedulerLockConfiguration.class})
public class TransactionServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(TransactionServiceApplication.class, args);
    }
}
