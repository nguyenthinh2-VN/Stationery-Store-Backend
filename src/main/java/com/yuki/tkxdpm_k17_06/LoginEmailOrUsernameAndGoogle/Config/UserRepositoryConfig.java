package com.yuki.tkxdpm_k17_06.LoginEmailOrUsernameAndGoogle.Config;

import com.yuki.tkxdpm_k17_06.LoginEmailOrUsernameAndGoogle.Infrastructure.LoginSpringDataUserJpaRepository;
import com.yuki.tkxdpm_k17_06.LoginEmailOrUsernameAndGoogle.Infrastructure.LoginSpringDataUserRepositoryImpl;
import com.yuki.tkxdpm_k17_06.LoginEmailOrUsernameAndGoogle.Usecase.UserRepository;
import org.springframework.context.annotation.Bean;

public class UserRepositoryConfig {
    @Bean
    public UserRepository userRepository(LoginSpringDataUserJpaRepository jpa) {
        return new LoginSpringDataUserRepositoryImpl(jpa);
    }
}
