package com.yuki.tkxdpm_k17_06.LoginEmailOrUsernameAndGoogle.Presenter;

import com.yuki.tkxdpm_k17_06.LoginEmailOrUsernameAndGoogle.Usecase.*;
import com.yuki.tkxdpm_k17_06.RegisterAccountUserWithEmail.Entity.UserAccount;

public class GoogleAuthPolicy implements AuthPolicy {
    private final GoogleOAuthService googleService;
    private final UserRepository repo;
    private final TokenGenerationPolicy tokenGen;

    public GoogleAuthPolicy(GoogleOAuthService googleService,
                            UserRepository repo,
                            TokenGenerationPolicy tokenGen) {
        this.googleService = googleService;
        this.repo = repo;
        this.tokenGen = tokenGen;
    }

    @Override
    public AuthResult authenticate(LoginInputData input) {

        GoogleUserData data = googleService.verify(input.getOauthToken());
        if (data == null)
            return AuthResult.fail("Token Google không hợp lệ");

        UserAccount user = repo.findByEmail(data.email());

        if (user == null) {
            user = new UserAccount(
                    data.email(),
                    data.name()  // Google có thể không gửi tên
                     );
            user.setEmail(data.email());
            user.setName(data.name());
            repo.save(user);
        }

        String token = tokenGen.createToken(user);

        return AuthResult.success("Đăng nhập Google thành công", token);
    }
}
