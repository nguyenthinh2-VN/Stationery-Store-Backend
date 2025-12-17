package com.yuki.tkxdpm_k17_06.ForgotPassword.ResetPassword.Usecase;

import com.yuki.tkxdpm_k17_06.ForgotPassword.ResetPassword.ResetPasswordInputData;
import com.yuki.tkxdpm_k17_06.LoginEmailOrUsernameAndGoogle.Usecase.UserRepository;
import com.yuki.tkxdpm_k17_06.PasswordEncryptor;
import com.yuki.tkxdpm_k17_06.RegisterAccountUserWithEmail.Entity.Otp;
import com.yuki.tkxdpm_k17_06.RegisterAccountUserWithEmail.Entity.UserAccount;
import com.yuki.tkxdpm_k17_06.ForgotPassword.VerifyOTP.Usecase.OtpRepository;

/**
 * UC3: Reset Password.
 * Only allows reset if OTP was verified first.
 */
public class ResetPasswordUseCase {

    private final UserRepository userRepo;
    private final OtpRepository otpRepo;
    private final PasswordEncryptor encryptor;

    public ResetPasswordUseCase(
            UserRepository userRepo,
            OtpRepository otpRepo,
            PasswordEncryptor encryptor) {
        this.userRepo = userRepo;
        this.otpRepo = otpRepo;
        this.encryptor = encryptor;
    }

    public void execute(ResetPasswordInputData input) {
        // SECURITY CHECK: Verify OTP was validated first
        Otp verifiedOtp = otpRepo.findUsedOtpByEmail(input.email());
        if (verifiedOtp == null) {
            throw new RuntimeException("Chưa xác thực OTP. Vui lòng xác thực OTP trước.");
        }

        // Find user
        UserAccount user = userRepo.findByEmail(input.email());
        if (user == null) {
            throw new RuntimeException("User không tồn tại");
        }

        // Hash and update password
        String newHash = encryptor.hash(input.newPassword());
        user.updatePassword(newHash);
        userRepo.save(user);

        // Invalidate all OTPs after successful password reset
        otpRepo.invalidateAllByEmail(input.email());
    }
}
