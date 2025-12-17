package com.yuki.tkxdpm_k17_06.ForgotPassword.VerifyOTP.Usecase;

import com.yuki.tkxdpm_k17_06.ForgotPassword.VerifyOTP.VerifyOtpOutputData;

/**
 * Output Boundary for UC2 - Verify OTP.
 * Interactor calls this interface to present results.
 */
public interface VerifyOtpOutputBoundary {
    void present(VerifyOtpOutputData output);
}
