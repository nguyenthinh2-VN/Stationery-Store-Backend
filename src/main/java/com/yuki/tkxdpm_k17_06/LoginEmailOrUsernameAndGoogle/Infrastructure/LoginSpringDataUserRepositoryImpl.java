package com.yuki.tkxdpm_k17_06.LoginEmailOrUsernameAndGoogle.Infrastructure;

import com.yuki.tkxdpm_k17_06.LoginEmailOrUsernameAndGoogle.Usecase.UserRepository;
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
    public UserAccount save(UserAccount user) {
        UserAccountEntity entity;

        if (user.getId() != null) {
            // UPDATE: User đã tồn tại, lấy entity hiện tại và update
            entity = jpa.findById(user.getId()).orElse(null);
            if (entity == null) {
                throw new IllegalArgumentException("User không tồn tại với ID: " + user.getId());
            }
            // Update fields (cần thêm setters vào UserAccountEntity)
            entity.setPasswordHash(user.getPasswordHash());
            entity.setName(user.getName());
            entity.setUsername(user.getUsername());
            entity.setEmail(user.getEmail());
        } else {
            // INSERT: User mới
            entity = new UserAccountEntity(
                    user.getName(),
                    user.getUsername(),
                    user.getEmail(),
                    user.getPasswordHash());
        }

        UserAccountEntity saved = jpa.save(entity);
        return toDomain(saved);
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

    @Override
    public UserAccount findById(Long id) {
        UserAccountEntity entity = jpa.findById(id).orElse(null);
        return toDomain(entity);
    }

    private UserAccount toDomain(UserAccountEntity entity) {
        if (entity == null)
            return null;

        return new UserAccount(
                entity.getId(),
                entity.getName(),
                entity.getUsername(),
                entity.getEmail(),
                entity.getPasswordHash(),
                Role.valueOf(entity.getRole()));
    }

}
