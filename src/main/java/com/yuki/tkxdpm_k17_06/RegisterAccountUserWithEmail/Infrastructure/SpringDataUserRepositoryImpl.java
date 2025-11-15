package com.yuki.tkxdpm_k17_06.RegisterAccountUserWithEmail.Infrastructure;

import com.yuki.tkxdpm_k17_06.RegisterAccountUserWithEmail.Entity.UserAccount;
import org.springframework.stereotype.Repository;

@Repository
public class SpringDataUserRepositoryImpl implements UserRepository{
    private final SpringDataUserJpaRepository jpa;

    public SpringDataUserRepositoryImpl(SpringDataUserJpaRepository jpa) {
        this.jpa = jpa;
    }

    @Override
    public boolean existsByEmail(String email) {
        return jpa.existsByEmail(email);
    }

    @Override
    public void save(UserAccount account) {
        UserAccountEntity entity = new UserAccountEntity(
                account.getName(),
                account.getUsername(),
                account.getEmail(),
                account.getPasswordHash()
        );

        jpa.save(entity); 
    }
}

