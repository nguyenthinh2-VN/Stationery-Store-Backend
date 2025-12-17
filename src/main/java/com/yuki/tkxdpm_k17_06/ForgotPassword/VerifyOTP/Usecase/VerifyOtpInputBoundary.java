package com.yuki.tkxdpm_k17_06.ForgotPassword.VerifyOTP.Usecase;

import com.yuki.tkxdpm_k17_06.ForgotPassword.VerifyOTP.VerifyOtpInputData;

/**
 * Input Boundary for UC2 - Verify OTP.
 * Controller calls this interface, not the UseCase directly.
 */
public interface VerifyOtpInputBoundary {
    void execute(VerifyOtpInputData input);
}
