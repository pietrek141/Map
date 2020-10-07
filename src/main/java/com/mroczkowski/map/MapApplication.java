package com.mroczkowski.map;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class MapApplication {

    public static void main(String[] args) {

        //SpringApplication.run(MapApplication.class, args);

        SpringApplicationBuilder builder = new SpringApplicationBuilder(MapApplication.class);

        builder.headless(false);

        ConfigurableApplicationContext context = builder.run(args);

    }

}
