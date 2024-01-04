package com.example.demo1;


import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = {"com.example.demo1.vo"})
public class Demo1Application {
	private static final Logger logger = LogManager.getLogger(Demo1Application.class);
	
	public static void main(String[] args) {
		logger.log(Level.INFO,"Running Main Function...");
		SpringApplication.run(Demo1Application.class, args);
		logger.log(Level.INFO, "Exiting the Main Function");
	}
}