package com.yuki.tkxdpm_k17_06.RegisterAccountUserWithEmail.Control;


import com.yuki.tkxdpm_k17_06.RegisterAccountUserWithEmail.Infrastructure.UserRepository;
import org.springframework.stereotype.Component;

@Component
public class EmailRegistrationPolicyImpl implements EmailRegistrationPolicy{
    private final UserRepository repository;

    public EmailRegistrationPolicyImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public void validateEmailAvailable(String email) {
        if (repository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email đã được sử dụng");
        }
    }
}
