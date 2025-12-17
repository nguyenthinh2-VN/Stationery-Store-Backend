package com.yuki.tkxdpm_k17_06.ForgotPassword.VerifyOTP.Usecase;

import com.yuki.tkxdpm_k17_06.ForgotPassword.Infractructure.OtpEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OtpJpaRepository extends JpaRepository<OtpEntity, Long> {

    OtpEntity findByEmailAndCodeAndUsedFalse(String email, String code);

    @Modifying
    @Query("UPDATE OtpEntity o SET o.used = true WHERE o.email = :email AND o.used = false")
    void invalidateAllByEmail(@Param("email") String email);

    // Find most recent used (verified) OTP for email
    OtpEntity findTopByEmailAndUsedTrueOrderByIdDesc(String email);
}
