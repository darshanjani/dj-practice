package com.example;

import com.example.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Darshan on 7/8/2017.
 */
@SpringBootApplication
@RestController
@RequestMapping("/api")
public class Example {

	@Value("${name:DJ}")
	private String name;

	@Autowired
	private Config config;

	@Value("${my.secret}")
	private String password;

	@RequestMapping("/home")
	String home() {
		return "Hello World";
	}

	@RequestMapping("/hello")
	String hello() {
		return "Hello " + name + ". Your random password is: " + password + ". Your servers are: " + config.getServers();
	}

	public static void main(String[] args) {
		SpringApplication.run(Example.class, args);
	}
}
