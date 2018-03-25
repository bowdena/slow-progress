package com.memorynotfound.integration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Date;
import java.util.concurrent.TimeUnit;

// Enable if you want to configure an embedded activeMQ server using Spring XML Configuration
// @Configuration
// @ImportResource(value = "classpath:spring-activemq-config.xml")
@SpringBootApplication
public class Run implements ApplicationRunner {

    private static Logger log = LoggerFactory.getLogger(Run.class);

    @Autowired
    private OrderSender orderSender;

    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {
        log.info("Spring Boot Embedded ActiveMQ Configuration Example");

        for (int i = 0; i < 5; i++){
            Order myMessage = new Order(i + " - Sending JMS Message using Embedded activeMQ", new Date());
            orderSender.send(myMessage);
        }

        log.info("Waiting for all ActiveMQ JMS Messages to be consumed");
        TimeUnit.SECONDS.sleep(3);
        System.exit(-1);
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Run.class, args);
    }
}
