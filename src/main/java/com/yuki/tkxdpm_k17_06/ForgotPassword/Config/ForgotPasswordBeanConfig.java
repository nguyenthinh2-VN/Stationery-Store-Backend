package com.yuki.tkxdpm_k17_06.ForgotPassword.Config;

import com.yuki.tkxdpm_k17_06.ForgotPassword.Policy.DefaultOtpGeneratorPolicy;
import com.yuki.tkxdpm_k17_06.ForgotPassword.Policy.OtpGeneratorPolicy;
import com.yuki.tkxdpm_k17_06.ForgotPassword.RequestResetPassword.Usecase.RequestResetPasswordControl;
import com.yuki.tkxdpm_k17_06.ForgotPassword.RequestResetPassword.Usecase.RequestResetPasswordInputBoundary;
import com.yuki.tkxdpm_k17_06.ForgotPassword.Policy.ResendEmailService;
import com.yuki.tkxdpm_k17_06.ForgotPassword.Presenter.RequestResetPasswordPresenter;
import com.yuki.tkxdpm_k17_06.ForgotPassword.ResetPassword.Usecase.ResetPasswordControl;
import com.yuki.tkxdpm_k17_06.ForgotPassword.ResetPassword.Usecase.ResetPasswordInputBoundary;
import com.yuki.tkxdpm_k17_06.ForgotPassword.Presenter.ResetPasswordPresenter;
import com.yuki.tkxdpm_k17_06.ForgotPassword.VerifyOTP.Usecase.OtpRepository;
import com.yuki.tkxdpm_k17_06.ForgotPassword.VerifyOTP.Usecase.VerifyOtpControl;
import com.yuki.tkxdpm_k17_06.ForgotPassword.VerifyOTP.Usecase.VerifyOtpInputBoundary;
import com.yuki.tkxdpm_k17_06.ForgotPassword.Presenter.VerifyOtpPresenter;
import com.yuki.tkxdpm_k17_06.LoginEmailOrUsernameAndGoogle.Usecase.UserRepository;
import com.yuki.tkxdpm_k17_06.PasswordEncryptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Bean Configuration for Forgot Password feature.
 * Wires InputBoundary, OutputBoundary, Control, and Presenter.
 */
@Configuration
public class ForgotPasswordBeanConfig {

    // ==================== Presenters ====================

    @Bean
    public RequestResetPasswordPresenter requestResetPasswordPresenter() {
        return new RequestResetPasswordPresenter();
    }

    @Bean
    public VerifyOtpPresenter verifyOtpPresenter() {
        return new VerifyOtpPresenter();
    }

    @Bean
    public ResetPasswordPresenter resetPasswordPresenter() {
        return new ResetPasswordPresenter();
    }

    // ==================== Policies ====================

    @Bean
    public OtpGeneratorPolicy otpGeneratorPolicy() {
        return new DefaultOtpGeneratorPolicy();
    }

    // ==================== InputBoundary (Controls/Interactors)
    // ====================

    @Bean
    public RequestResetPasswordInputBoundary requestResetPasswordInputBoundary(
            UserRepository userRepository,
            OtpRepository otpRepository,
            OtpGeneratorPolicy otpGeneratorPolicy,
            ResendEmailService emailService,
            RequestResetPasswordPresenter presenter) {
        return new RequestResetPasswordControl(userRepository, otpRepository, otpGeneratorPolicy, emailService,
                presenter);
    }

    @Bean
    public VerifyOtpInputBoundary verifyOtpInputBoundary(
            OtpRepository otpRepository,
            VerifyOtpPresenter presenter) {
        return new VerifyOtpControl(otpRepository, presenter);
    }

    @Bean
    public ResetPasswordInputBoundary resetPasswordInputBoundary(
            UserRepository userRepository,
            OtpRepository otpRepository,
            PasswordEncryptor passwordEncryptor,
            ResetPasswordPresenter presenter) {
        return new ResetPasswordControl(userRepository, otpRepository, passwordEncryptor, presenter);
    }
}
