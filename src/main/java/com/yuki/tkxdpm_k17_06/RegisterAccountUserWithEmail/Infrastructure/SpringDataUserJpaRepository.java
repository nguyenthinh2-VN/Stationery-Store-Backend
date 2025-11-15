package com.yuki.tkxdpm_k17_06.RegisterAccountUserWithEmail.Infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpringDataUserJpaRepository extends JpaRepository<UserAccountEntity, Long> {
    boolean existsByEmail(String email);
}
