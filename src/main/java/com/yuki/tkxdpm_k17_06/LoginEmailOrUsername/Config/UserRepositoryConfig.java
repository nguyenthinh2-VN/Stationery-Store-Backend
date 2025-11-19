package com.yuki.tkxdpm_k17_06.LoginEmailOrUsername.Config;

import com.yuki.tkxdpm_k17_06.LoginEmailOrUsername.Infrastructure.LoginSpringDataUserJpaRepository;
import com.yuki.tkxdpm_k17_06.LoginEmailOrUsername.Infrastructure.LoginSpringDataUserRepositoryImpl;
import com.yuki.tkxdpm_k17_06.LoginEmailOrUsername.Usecase.UserRepository;
import org.springframework.context.annotation.Bean;

public class UserRepositoryConfig {
    @Bean
    public UserRepository userRepository(LoginSpringDataUserJpaRepository jpa) {
        return new LoginSpringDataUserRepositoryImpl(jpa);
    }
}
