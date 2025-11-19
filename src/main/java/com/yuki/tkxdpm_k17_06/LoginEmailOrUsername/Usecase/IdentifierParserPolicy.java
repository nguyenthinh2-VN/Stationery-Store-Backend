package com.yuki.tkxdpm_k17_06.LoginEmailOrUsername.Usecase;

import com.yuki.tkxdpm_k17_06.LoginEmailOrUsername.LoginIdentifier;

public interface IdentifierParserPolicy {
    LoginIdentifier parse(String identifier); //Email Or Username
}
