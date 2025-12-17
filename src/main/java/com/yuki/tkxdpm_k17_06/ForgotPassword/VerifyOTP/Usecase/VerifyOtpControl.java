package com.yuki.tkxdpm_k17_06.ForgotPassword.VerifyOTP.Usecase;

import com.yuki.tkxdpm_k17_06.ForgotPassword.VerifyOTP.*;
import com.yuki.tkxdpm_k17_06.RegisterAccountUserWithEmail.Entity.Otp;

/**
 * UC2 Control/Interactor: Verify OTP.
 * Implements InputBoundary, calls OutputBoundary to present results.
 */
public class VerifyOtpControl implements VerifyOtpInputBoundary {

    private final OtpRepository otpRepo;
    private final VerifyOtpOutputBoundary presenter;

    public VerifyOtpControl(OtpRepository otpRepo, VerifyOtpOutputBoundary presenter) {
        this.otpRepo = otpRepo;
        this.presenter = presenter;
    }

    @Override
    public void execute(VerifyOtpInputData input) {
        try {
            Otp otp = otpRepo.findValidOtp(input.email(), input.code());

            if (otp == null || !otp.canUse()) {
                presenter.present(new VerifyOtpOutputData(false, "OTP không hợp lệ hoặc đã hết hạn"));
                return;
            }

            otp.markUsed();
            otpRepo.save(otp);

            presenter.present(new VerifyOtpOutputData(true, "OTP hợp lệ"));
        } catch (Exception e) {
            presenter.present(new VerifyOtpOutputData(false, e.getMessage()));
        }
    }
}
