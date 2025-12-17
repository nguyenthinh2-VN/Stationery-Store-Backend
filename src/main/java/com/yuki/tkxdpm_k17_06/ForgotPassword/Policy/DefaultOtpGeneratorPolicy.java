package com.yuki.tkxdpm_k17_06.ForgotPassword.Policy;

import com.yuki.tkxdpm_k17_06.RegisterAccountUserWithEmail.Entity.Otp;

import java.security.SecureRandom;
import java.time.LocalDateTime;

/**
 * Default implementation of OtpGeneratorPolicy.
 * Generates 6-digit OTP codes with 5-minute expiration.
 */
public class DefaultOtpGeneratorPolicy implements OtpGeneratorPolicy {

    private static final int OTP_LENGTH = 6;
    private static final int EXPIRATION_MINUTES = 5;
    private final SecureRandom secureRandom = new SecureRandom();

    @Override
    public Otp generate(String email) {
        // Generate secure 6-digit code
        int code = 100000 + secureRandom.nextInt(900000);

        Otp otp = new Otp();
        otp.setEmail(email);
        otp.setCode(String.valueOf(code));
        otp.setExpiredAt(LocalDateTime.now().plusMinutes(EXPIRATION_MINUTES));
        otp.setUsed(false);

        return otp;
    }
}
