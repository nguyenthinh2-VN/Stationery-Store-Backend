package com.yuki.tkxdpm_k17_06.ForgotPassword.Policy;

import com.yuki.tkxdpm_k17_06.RegisterAccountUserWithEmail.Entity.Otp;

/**
 * Policy interface for generating OTP codes.
 * Separates OTP generation logic from UseCase.
 */
public interface OtpGeneratorPolicy {

    /**
     * Generates a new OTP for the given email.
     * 
     * @param email the email to associate with the OTP
     * @return a new Otp object with code and expiration set
     */
    Otp generate(String email);
}
