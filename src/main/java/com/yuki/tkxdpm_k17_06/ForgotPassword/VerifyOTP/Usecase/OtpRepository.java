package com.yuki.tkxdpm_k17_06.ForgotPassword.VerifyOTP.Usecase;

import com.yuki.tkxdpm_k17_06.RegisterAccountUserWithEmail.Entity.Otp;

public interface OtpRepository {

    void save(Otp otp);

    Otp findValidOtp(String email, String code);

    void invalidateAllByEmail(String email);

    /**
     * Find a recently used (verified) OTP for the email.
     * Used by ResetPasswordUseCase to confirm OTP was verified.
     */
    Otp findUsedOtpByEmail(String email);
}
