package com.yuki.tkxdpm_k17_06.LoginEmailOrUsername.Usecase;

import com.yuki.tkxdpm_k17_06.LoginEmailOrUsername.LoginIdentifier;
import com.yuki.tkxdpm_k17_06.RegisterAccountUserWithEmail.Entity.UserAccount;

public class LoginControl implements LoginInputBoundary{
    private final IdentifierParserPolicy identifierPolicy;
    private final UserFinderPolicy finderPolicy;
    private final PasswordCheckPolicy passwordCheckPolicy;
    private final TokenGenerationPolicy tokenGenerationPolicy;
    private final LoginOutputBoundary output;

    public LoginControl(IdentifierParserPolicy identifierPolicy, UserFinderPolicy finderPolicy, PasswordCheckPolicy passwordCheckPolicy, TokenGenerationPolicy tokenGenerationPolicy, LoginOutputBoundary output) {
        this.identifierPolicy = identifierPolicy;
        this.finderPolicy = finderPolicy;
        this.passwordCheckPolicy = passwordCheckPolicy;
        this.tokenGenerationPolicy = tokenGenerationPolicy;
        this.output = output;
    }


    @Override
    public void execute(LoginInputData input) {
        try {
            // 1. Phân tích identifier (email/username)
            LoginIdentifier identifier = identifierPolicy.parse(input.identifier());

            // 2. Tìm user tương ứng
            UserAccount user = finderPolicy.find(identifier);
            if (user == null) {
                output.present(new LoginOutputData(false, "Người dùng không tồn tại", null));
                return;
            }

            // 3. Kiểm tra mật khẩu
            if (!passwordCheckPolicy.check(user, input.password())) {
                output.present(new LoginOutputData(false, "Sai mật khẩu", null));
                return;
            }

            // 4. Tạo token
            String token = tokenGenerationPolicy.createToken(user);

            // 5. Trả output
            output.present(new LoginOutputData(true, "Đăng nhập thành công", token));

        } catch (Exception e) {
            e.printStackTrace();  // 🔥 IN RA LỖI
            output.present(new LoginOutputData(false, "Đăng nhập thất bại", null));
        }
    }
}
