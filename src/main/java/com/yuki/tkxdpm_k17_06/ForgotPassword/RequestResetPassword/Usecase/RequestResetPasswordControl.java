package com.yuki.tkxdpm_k17_06.ForgotPassword.RequestResetPassword.Usecase;

import com.yuki.tkxdpm_k17_06.ForgotPassword.Policy.OtpGeneratorPolicy;
import com.yuki.tkxdpm_k17_06.ForgotPassword.Policy.ResendEmailService;
import com.yuki.tkxdpm_k17_06.ForgotPassword.RequestResetPassword.*;
import com.yuki.tkxdpm_k17_06.LoginEmailOrUsernameAndGoogle.Usecase.UserRepository;
import com.yuki.tkxdpm_k17_06.RegisterAccountUserWithEmail.Entity.Otp;
import com.yuki.tkxdpm_k17_06.RegisterAccountUserWithEmail.Entity.UserAccount;
import com.yuki.tkxdpm_k17_06.ForgotPassword.VerifyOTP.Usecase.OtpRepository;

/**
 * UC1 Control/Interactor: Request Reset Password - sends OTP to email.
 * Implements InputBoundary, calls OutputBoundary to present results.
 * 
 * SECURITY: Always returns success message to prevent email enumeration
 * attacks.
 * Only sends OTP if email exists in system.
 */
public class RequestResetPasswordControl implements RequestResetPasswordInputBoundary {

    private final UserRepository userRepo;
    private final OtpRepository otpRepo;
    private final OtpGeneratorPolicy otpGenerator;
    private final ResendEmailService emailService;
    private final RequestResetPasswordOutputBoundary presenter;

    private static final String SUCCESS_MESSAGE = "Nếu email này đã đăng ký, bạn sẽ nhận được mã OTP qua email.";

    public RequestResetPasswordControl(
            UserRepository userRepo,
            OtpRepository otpRepo,
            OtpGeneratorPolicy otpGenerator,
            ResendEmailService emailService,
            RequestResetPasswordOutputBoundary presenter) {
        this.userRepo = userRepo;
        this.otpRepo = otpRepo;
        this.otpGenerator = otpGenerator;
        this.emailService = emailService;
        this.presenter = presenter;
    }

    @Override
    public void execute(RequestResetPasswordInputData input) {
        try {
            // Check if user exists (silently - don't reveal to attacker)
            UserAccount user = userRepo.findByEmail(input.email());

            if (user != null) {
                // User exists - send OTP
                otpRepo.invalidateAllByEmail(input.email());
                Otp otp = otpGenerator.generate(input.email());
                otpRepo.save(otp);
                emailService.sendOtp(input.email(), otp.getCode());
            }
            // If user doesn't exist, do nothing but still return success

            // Always return same message (prevent email enumeration)
            presenter.present(new RequestResetPasswordOutputData(true, SUCCESS_MESSAGE));
        } catch (Exception e) {
            // Still return success message to not reveal any information
            presenter.present(new RequestResetPasswordOutputData(true, SUCCESS_MESSAGE));
        }
    }
}
