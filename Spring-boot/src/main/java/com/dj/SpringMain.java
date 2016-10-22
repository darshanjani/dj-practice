package com.dj;

import com.dj.storage.StorageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class SpringMain {

    @RequestMapping("/")
    @ResponseBody
    public String home() {
        return "Hello World";
    }

    @Bean
    public StorageProperties storageProperties() {
        return new StorageProperties();
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringMain.class, args);
    }
}
