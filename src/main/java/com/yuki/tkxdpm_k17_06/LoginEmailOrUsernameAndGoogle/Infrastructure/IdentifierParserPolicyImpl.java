package com.yuki.tkxdpm_k17_06.LoginEmailOrUsernameAndGoogle.Infrastructure;

import com.yuki.tkxdpm_k17_06.LoginEmailOrUsernameAndGoogle.LoginIdentifier;
import com.yuki.tkxdpm_k17_06.LoginEmailOrUsernameAndGoogle.LoginIdentifierType;
import com.yuki.tkxdpm_k17_06.LoginEmailOrUsernameAndGoogle.Usecase.IdentifierParserPolicy;

public class IdentifierParserPolicyImpl implements IdentifierParserPolicy {

    private static final String EMAIL_REGEX =
            "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";

    @Override
    public LoginIdentifier parse(String identifier) {

        if (identifier == null || identifier.isBlank()) {
            throw new IllegalArgumentException("Identifier rỗng");
        }

        // Nếu giống email → xem là email
        if (identifier.matches(EMAIL_REGEX)) {
            return new LoginIdentifier(LoginIdentifierType.EMAIL, identifier);
        }

        // Còn lại → username
        return new LoginIdentifier(LoginIdentifierType.USERNAME, identifier);
    }
}

