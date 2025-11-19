package com.yuki.tkxdpm_k17_06.LoginEmailOrUsername.Infrastructure;

import com.yuki.tkxdpm_k17_06.LoginEmailOrUsername.Usecase.UserRepository;
import com.yuki.tkxdpm_k17_06.RegisterAccountUserWithEmail.Entity.Role;
import com.yuki.tkxdpm_k17_06.RegisterAccountUserWithEmail.Entity.UserAccount;
import com.yuki.tkxdpm_k17_06.UserAccountEntity;
import org.springframework.stereotype.Repository;

@Repository
public class LoginSpringDataUserRepositoryImpl implements UserRepository {
    private final LoginSpringDataUserJpaRepository jpa;

    public LoginSpringDataUserRepositoryImpl(LoginSpringDataUserJpaRepository jpa) {
        this.jpa = jpa;
    }

    @Override
    public UserAccount findByEmail(String email) {
        UserAccountEntity entity = jpa.findByEmail(email);
        return toDomain(entity);
    }

    @Override
    public UserAccount findByUsername(String username) {
        UserAccountEntity entity = jpa.findByUsername(username);
        return toDomain(entity);
    }

    private UserAccount toDomain(UserAccountEntity entity) {
        if (entity == null) return null;

        return new UserAccount(
                entity.getId(),
                entity.getName(),
                entity.getUsername(),
                entity.getEmail(),
                entity.getPasswordHash(),
                Role.valueOf(entity.getRole())
        );
    }

}

