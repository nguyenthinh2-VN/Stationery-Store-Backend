package com.yuki.tkxdpm_k17_06.LoginEmailOrUsernameAndGoogle.Usecase;

import com.yuki.tkxdpm_k17_06.LoginEmailOrUsernameAndGoogle.LoginIdentifier;
import com.yuki.tkxdpm_k17_06.LoginEmailOrUsernameAndGoogle.LoginIdentifierType;
import com.yuki.tkxdpm_k17_06.RegisterAccountUserWithEmail.Entity.UserAccount;

public class CredentialAuthPolicy implements AuthPolicy {
    private final IdentifierParserPolicy parser;
    private final PasswordEncryptor encryptor;
    private final UserRepository repo;
    private final TokenGenerationPolicy tokenGen;

    public CredentialAuthPolicy(
            IdentifierParserPolicy parser,
            PasswordEncryptor encryptor,
            UserRepository repo,
            TokenGenerationPolicy tokenGen
    ) {
        this.parser = parser;
        this.encryptor = encryptor;
        this.repo = repo;
        this.tokenGen = tokenGen;
    }

    @Override
    public AuthResult authenticate(LoginInputData input) {

        LoginIdentifier id = parser.parse(input.getIdentifier());

        UserAccount user;

        // ⭐ Dùng type() thay vì instanceof
        if (id.type() == LoginIdentifierType.EMAIL) {
            user = repo.findByEmail(id.value());
        } else {
            user = repo.findByUsername(id.value());
        }

        if (user == null) {
            return AuthResult.fail("User không tồn tại");
        }

        if (!encryptor.verify(input.getPassword(), user.getPasswordHash())) {
            return AuthResult.fail("Sai mật khẩu");
        }

        String token = tokenGen.createToken(user);

        return AuthResult.success("Đăng nhập thành công", token);
    }
}
