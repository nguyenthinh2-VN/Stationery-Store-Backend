package com.yuki.tkxdpm_k17_06.ForgotPassword.ResetPassword.Usecase;

import com.yuki.tkxdpm_k17_06.ForgotPassword.ResetPassword.ResetPasswordInputData;
import com.yuki.tkxdpm_k17_06.ForgotPassword.ResetPassword.ResetPasswordOutputData;
import com.yuki.tkxdpm_k17_06.LoginEmailOrUsernameAndGoogle.Usecase.UserRepository;
import com.yuki.tkxdpm_k17_06.PasswordEncryptor;
import com.yuki.tkxdpm_k17_06.RegisterAccountUserWithEmail.Entity.Otp;
import com.yuki.tkxdpm_k17_06.RegisterAccountUserWithEmail.Entity.UserAccount;
import com.yuki.tkxdpm_k17_06.ForgotPassword.VerifyOTP.Usecase.OtpRepository;

/**
 * UC3 Control/Interactor: Reset Password.
 * Implements InputBoundary, calls OutputBoundary to present results.
 * Only allows reset if OTP was verified first.
 */
public class ResetPasswordControl implements ResetPasswordInputBoundary {

    private final UserRepository userRepo;
    private final OtpRepository otpRepo;
    private final PasswordEncryptor encryptor;
    private final ResetPasswordOutputBoundary presenter;

    public ResetPasswordControl(
            UserRepository userRepo,
            OtpRepository otpRepo,
            PasswordEncryptor encryptor,
            ResetPasswordOutputBoundary presenter) {
        this.userRepo = userRepo;
        this.otpRepo = otpRepo;
        this.encryptor = encryptor;
        this.presenter = presenter;
    }

    @Override
    public void execute(ResetPasswordInputData input) {
        try {
            // SECURITY CHECK: Verify OTP was validated first
            Otp verifiedOtp = otpRepo.findUsedOtpByEmail(input.email());
            if (verifiedOtp == null) {
                presenter
                        .present(new ResetPasswordOutputData(false, "Chưa xác thực OTP. Vui lòng xác thực OTP trước."));
                return;
            }

            // Find user
            UserAccount user = userRepo.findByEmail(input.email());
            if (user == null) {
                presenter.present(new ResetPasswordOutputData(false, "User không tồn tại"));
                return;
            }

            // Hash and update password
            String newHash = encryptor.hash(input.newPassword());
            user.updatePassword(newHash);
            userRepo.save(user);

            // Invalidate all OTPs after successful password reset
            otpRepo.invalidateAllByEmail(input.email());

            presenter.present(new ResetPasswordOutputData(true, "Đổi mật khẩu thành công"));
        } catch (Exception e) {
            presenter.present(new ResetPasswordOutputData(false, e.getMessage()));
        }
    }
}
