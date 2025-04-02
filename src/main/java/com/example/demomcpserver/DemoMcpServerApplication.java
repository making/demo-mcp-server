package com.example.demomcpserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class DemoMcpServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoMcpServerApplication.class, args);
	}

}
