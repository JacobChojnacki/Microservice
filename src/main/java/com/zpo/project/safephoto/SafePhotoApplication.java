package com.zpo.project.safephoto;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class })
public class SafePhotoApplication {
    public static void main(String[] args) {
        SpringApplication.run(SafePhotoApplication.class, args);
    }
}
