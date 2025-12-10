package com.yuki.tkxdpm_k17_06.ChangePassword;

import com.yuki.tkxdpm_k17_06.LoginEmailOrUsernameAndGoogle.Usecase.UserRepository;
import com.yuki.tkxdpm_k17_06.RegisterAccountUserWithEmail.Entity.UserAccount;

public class ChangePasswordControl implements ChangePasswordInputBoundary{
    private final UserRepository repo;
    private final ChangePasswordPolicy changePasswordPolicy;
    private final ChangePasswordOutputBoundary presenter;

    public ChangePasswordControl(UserRepository repo, ChangePasswordPolicy changePasswordPolicy, ChangePasswordOutputBoundary presenter) {
        this.repo = repo;
        this.changePasswordPolicy = changePasswordPolicy;
        this.presenter = presenter;
    }

    @Override
    public void execute(ChangePasswordInputData input) {
        // 1. Lấy user
        UserAccount user = repo.findById(input.userId());
        if (user == null) {
            presenter.present(new ChangePasswordOutputData(false, "Tài khoản không tồn tại"));
            return;
        }

        // 2. Verify mật khẩu cũ
        if (!changePasswordPolicy.verifyOldPassword(input.oldPassword(), user.getPasswordHash())) {
            presenter.present(new ChangePasswordOutputData(false, "Mật khẩu cũ không chính xác"));
            return;
        }

        // 3. Validate mật khẩu mới
        if (!changePasswordPolicy.isValidNewPassword(input.newPassword(), input.oldPassword())) {
            presenter.present(new ChangePasswordOutputData(false, "Mật khẩu mới không hợp lệ"));
            return;
        }

        // 4. Hash mật khẩu mới
        String newHash = changePasswordPolicy.hashNewPassword(input.newPassword());
        user.updatePassword(newHash);

        // 5. Lưu
        repo.save(user);

        // 6. Trả kết quả
        presenter.present(new ChangePasswordOutputData(true, "Đổi mật khẩu thành công"));
    }
}
