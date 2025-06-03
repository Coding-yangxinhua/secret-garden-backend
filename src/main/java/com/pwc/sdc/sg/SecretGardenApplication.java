package com.pwc.sdc.sg;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @author Xinhua X Yang
 */
@SpringBootApplication
@EnableAsync
public class SecretGardenApplication {
    public static void main(String[] args) {
        SpringApplication.run(SecretGardenApplication.class, args);
    }

}
