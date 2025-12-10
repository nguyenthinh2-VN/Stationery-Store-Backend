package com.yuki.tkxdpm_k17_06.LoginEmailOrUsernameAndGoogle.Usecase;

import com.yuki.tkxdpm_k17_06.LoginEmailOrUsernameAndGoogle.LoginIdentifier;

public interface IdentifierParserPolicy {
    LoginIdentifier parse(String identifier); //Email Or Username
}
