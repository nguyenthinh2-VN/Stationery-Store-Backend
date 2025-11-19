package com.yuki.tkxdpm_k17_06.LoginEmailOrUsername.Infrastructure;

import com.yuki.tkxdpm_k17_06.UserAccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginSpringDataUserJpaRepository extends JpaRepository<UserAccountEntity, Long> {
    UserAccountEntity findByEmail(String email);

    UserAccountEntity findByUsername(String username);
}
