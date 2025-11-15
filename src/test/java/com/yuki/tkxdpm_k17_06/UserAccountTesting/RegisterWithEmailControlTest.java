package com.yuki.tkxdpm_k17_06.UserAccountTesting;

import com.yuki.tkxdpm_k17_06.RegisterAccountUserWithEmail.Control.RegisterWithEmailControl;
import com.yuki.tkxdpm_k17_06.RegisterAccountUserWithEmail.Control.RegisterWithEmailInputData;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RegisterWithEmailControlTest {

    @Test
    void shouldRegisterSuccessfully_WhenValidInput() {

        // Arrange
        FakeRepository repo = new FakeRepository();
        FakeEncryptor encryptor = new FakeEncryptor();
        FakeEmailPolicy emailPolicy = new FakeEmailPolicy();
        FakePresenter presenter = new FakePresenter();

        RegisterWithEmailControl control =
                new RegisterWithEmailControl(repo, encryptor, presenter, emailPolicy);

        RegisterWithEmailInputData input = new RegisterWithEmailInputData(
                "Thinh",
                "thinh123",
                "thinh@gmail.com",
                "mypassword"
        );

        // Act
        control.execute(input);

        // Assert 1: Presenter nhận đúng dữ liệu
        assertNotNull(presenter.output);
        assertTrue(presenter.output.success());
        assertEquals("Đăng ký thành công", presenter.output.message());

        // Assert 2: Repository lưu đúng user
        assertNotNull(repo.savedUser);
        assertEquals("Thinh", repo.savedUser.getName());
        assertEquals("thinh123", repo.savedUser.getUsername());
        assertEquals("thinh@gmail.com", repo.savedUser.getEmail());

        // Assert 3: Mật khẩu phải HASHED
        assertEquals("hashed-mypassword", repo.savedUser.getPasswordHash());
    }
}
