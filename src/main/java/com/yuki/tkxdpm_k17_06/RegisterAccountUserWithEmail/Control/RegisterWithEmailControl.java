package com.yuki.tkxdpm_k17_06.RegisterAccountUserWithEmail.Control;

import com.yuki.tkxdpm_k17_06.RegisterAccountUserWithEmail.Adapter.RegisterWithEmailInputBoundary;
import com.yuki.tkxdpm_k17_06.RegisterAccountUserWithEmail.Adapter.RegisterWithEmailOutputBoundary;
import com.yuki.tkxdpm_k17_06.RegisterAccountUserWithEmail.Entity.UserAccount;
import com.yuki.tkxdpm_k17_06.RegisterAccountUserWithEmail.Infrastructure.UserRepository;


public class RegisterWithEmailControl implements RegisterWithEmailInputBoundary {
    private final UserRepository repository;
    private final PasswordEncryptor encryptor;
    private final RegisterWithEmailOutputBoundary outputBoundary;
    private EmailRegistrationPolicy emailPolicy;


    public RegisterWithEmailControl(UserRepository repository, PasswordEncryptor encryptor, RegisterWithEmailOutputBoundary outputBoundary, EmailRegistrationPolicy emailPolicy) {
        this.repository = repository;
        this.encryptor = encryptor;
        this.outputBoundary = outputBoundary;
        this.emailPolicy = emailPolicy;
    }

    @Override
    public void execute(RegisterWithEmailInputData inputData) {

        try {
            emailPolicy.validateEmailAvailable(inputData.email());

            UserAccount user = new UserAccount(
                    inputData.name(),
                    inputData.username(),
                    inputData.email(),
                    inputData.password()
            );

            //mã hóa mk
            user.encryptPassword(encryptor);

            //lưu xuống repository
            repository.save(user);

            outputBoundary.present(new RegisterWithEmailOutputData(true, "Đăng ký thành công"));
        } catch (IllegalArgumentException ex) {
            outputBoundary.present(new RegisterWithEmailOutputData(false, ex.getMessage()));
        } catch (Exception ex) {
            // catch chung để không leak exception nội bộ
            outputBoundary.present(new RegisterWithEmailOutputData(false, "Đăng ký thất bại: " + ex.getMessage()));
        }
    }

}
