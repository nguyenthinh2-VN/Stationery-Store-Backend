package com.yuki.tkxdpm_k17_06.UserAccountTesting;

import com.yuki.tkxdpm_k17_06.RegisterAccountUserWithEmail.Adapter.RegisterWithEmailOutputBoundary;
import com.yuki.tkxdpm_k17_06.RegisterAccountUserWithEmail.Control.EmailRegistrationPolicy;
import com.yuki.tkxdpm_k17_06.RegisterAccountUserWithEmail.Control.PasswordEncryptor;
import com.yuki.tkxdpm_k17_06.RegisterAccountUserWithEmail.Control.RegisterWithEmailOutputData;
import com.yuki.tkxdpm_k17_06.RegisterAccountUserWithEmail.Entity.UserAccount;
import com.yuki.tkxdpm_k17_06.RegisterAccountUserWithEmail.Infrastructure.UserRepository;

public class FakeEncryptor implements PasswordEncryptor {

    @Override
    public String hash(String raw) {
        return "hashed-" + raw;
    }

    @Override
    public boolean verify(String raw, String hashed) {
        return hashed.equals("hashed-" + raw);
    }
}
class FakeRepository implements UserRepository {
    public UserAccount savedUser = null;

    @Override
    public boolean existsByEmail(String email) {
        return false;
    }

    @Override
    public void save(UserAccount account) {
        this.savedUser = account;
    }
}


class FakeEmailPolicy implements EmailRegistrationPolicy {
    @Override
    public void validateEmailAvailable(String email) {
        // no error → email available
    }
}

class FakePresenter implements RegisterWithEmailOutputBoundary {

    public RegisterWithEmailOutputData output;

    @Override
    public void present(RegisterWithEmailOutputData outputData) {
        this.output = outputData;
    }
}

