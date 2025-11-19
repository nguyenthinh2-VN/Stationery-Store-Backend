package com.yuki.tkxdpm_k17_06.LoginEmailOrUsername.Infrastructure;


import com.yuki.tkxdpm_k17_06.LoginEmailOrUsername.LoginIdentifier;
import com.yuki.tkxdpm_k17_06.LoginEmailOrUsername.LoginIdentifierType;
import com.yuki.tkxdpm_k17_06.LoginEmailOrUsername.Usecase.UserFinderPolicy;
import com.yuki.tkxdpm_k17_06.LoginEmailOrUsername.Usecase.UserRepository;
import com.yuki.tkxdpm_k17_06.RegisterAccountUserWithEmail.Entity.UserAccount;

public class UserFinderPolicyImpl implements UserFinderPolicy {
    private final UserRepository repository;

    public UserFinderPolicyImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserAccount find(LoginIdentifier identifier) {

        if (identifier.type() == LoginIdentifierType.EMAIL) {
            return repository.findByEmail(identifier.value());
        }

        if (identifier.type() == LoginIdentifierType.USERNAME) {
            return repository.findByUsername(identifier.value());
        }

        throw new IllegalStateException("Loại identifier không hợp lệ");
    }
}
