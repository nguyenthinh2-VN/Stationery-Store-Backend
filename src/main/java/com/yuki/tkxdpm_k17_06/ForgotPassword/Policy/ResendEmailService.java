package com.yuki.tkxdpm_k17_06.ForgotPassword.Policy;


public interface ResendEmailService {

    /**
     * Sends an OTP code to the specified email address.
     * 
     * @param toEmail recipient email address
     * @param otpCode the OTP code to send
     */
    void sendOtp(String toEmail, String otpCode);
}
