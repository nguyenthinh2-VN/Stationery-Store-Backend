package com.yuki.tkxdpm_k17_06;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = {
        "com.yuki.tkxdpm_k17_06.Infrastructure.JPA",
        "com.yuki.tkxdpm_k17_06.RegisterAccountUserWithEmail"
})
public class TkxdpmK1706Application {

    public static void main(String[] args) {
        SpringApplication.run(TkxdpmK1706Application.class, args);
    }

}
