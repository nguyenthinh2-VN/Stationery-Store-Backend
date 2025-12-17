package com.yuki.tkxdpm_k17_06.ForgotPassword.VerifyOTP.Usecase;

import com.yuki.tkxdpm_k17_06.ForgotPassword.VerifyOTP.VerifyOtpInputData;
import com.yuki.tkxdpm_k17_06.RegisterAccountUserWithEmail.Entity.Otp;

/**
 * UC2: Verify OTP.
 */
public class VerifyOtpUseCase {

    private final OtpRepository otpRepo;

    public VerifyOtpUseCase(OtpRepository otpRepo) {
        this.otpRepo = otpRepo;
    }

    public void execute(VerifyOtpInputData input) {
        Otp otp = otpRepo.findValidOtp(input.email(), input.code());

        if (otp == null || !otp.canUse()) {
            throw new RuntimeException("OTP không hợp lệ hoặc đã hết hạn");
        }

        otp.markUsed();
        otpRepo.save(otp);
    }
}
