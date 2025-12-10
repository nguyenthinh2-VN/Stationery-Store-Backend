package com.yuki.tkxdpm_k17_06.ChangePassword;

import com.yuki.tkxdpm_k17_06.LoginEmailOrUsernameAndGoogle.Usecase.UserRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChangePasswordBeanConfig {

    @Bean
    public ChangePasswordPolicy changePasswordPolicy(
            @Qualifier("registerPasswordEncryptor") com.yuki.tkxdpm_k17_06.RegisterAccountUserWithEmail.Control.PasswordEncryptor encryptor) {
        return new ChangePasswordPolicy(encryptor);
    }

    @Bean
    public ChangePasswordPresenter changePasswordPresenter() {
        return new ChangePasswordPresenter();
    }

    @Bean
    public ChangePasswordInputBoundary changePasswordInteractor(
            UserRepository repo,
            ChangePasswordPolicy policy,
            ChangePasswordPresenter presenter) {
        return new ChangePasswordControl(repo, policy, presenter);
    }
}
