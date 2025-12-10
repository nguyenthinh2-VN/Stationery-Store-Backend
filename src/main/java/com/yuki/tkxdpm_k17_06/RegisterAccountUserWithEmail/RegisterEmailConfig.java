package com.yuki.tkxdpm_k17_06.RegisterAccountUserWithEmail;

import com.yuki.tkxdpm_k17_06.RegisterAccountUserWithEmail.Adapter.RegisterWithEmailInputBoundary;
import com.yuki.tkxdpm_k17_06.RegisterAccountUserWithEmail.Adapter.RegisterWithEmailOutputBoundary;
import com.yuki.tkxdpm_k17_06.RegisterAccountUserWithEmail.Control.EmailRegistrationPolicy;
import com.yuki.tkxdpm_k17_06.RegisterAccountUserWithEmail.Control.PasswordEncryptor;
import com.yuki.tkxdpm_k17_06.RegisterAccountUserWithEmail.Control.RegisterWithEmailControl;

import com.yuki.tkxdpm_k17_06.RegisterAccountUserWithEmail.Infrastructure.UserRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RegisterEmailConfig {

    @Bean
    public RegisterWithEmailInputBoundary registerWithEmailInteractor(
            UserRepository repository,
            @Qualifier("registerPasswordEncryptor") PasswordEncryptor encryptor,
            EmailRegistrationPolicy emailPolicy,
            RegisterWithEmailOutputBoundary presenter) {
        return new RegisterWithEmailControl(repository, encryptor, presenter, emailPolicy);
    }
}
