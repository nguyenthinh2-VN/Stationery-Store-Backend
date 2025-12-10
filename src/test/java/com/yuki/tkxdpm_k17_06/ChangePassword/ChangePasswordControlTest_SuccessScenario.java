package com.yuki.tkxdpm_k17_06.ChangePassword;

import com.yuki.tkxdpm_k17_06.LoginEmailOrUsernameAndGoogle.Usecase.UserRepository;
import com.yuki.tkxdpm_k17_06.RegisterAccountUserWithEmail.Control.PasswordEncryptor;
import com.yuki.tkxdpm_k17_06.RegisterAccountUserWithEmail.Entity.Role;
import com.yuki.tkxdpm_k17_06.RegisterAccountUserWithEmail.Entity.UserAccount;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test case cho use case ChangePassword
 * Kịch bản 2: Thành công - Đổi mật khẩu thành công
 * 
 * Flow:
 * 1. User nhập mật khẩu cũ ĐÚNG và mật khẩu mới hợp lệ
 * 2. Hệ thống kiểm tra mật khẩu cũ → PASS
 * 3. Kiểm tra mật khẩu mới (≥ 8 ký tự, khác mật khẩu cũ) → PASS
 * 4. User không tồn tại → PASS
 * 5. Xử lý kết quả → Thành công
 * 6. Phản hồi "Đổi mật khẩu thành công"
 */
class ChangePasswordControlTest_SuccessScenario {

    private UserRepository mockRepo;
    private PasswordEncryptor mockEncryptor;
    private ChangePasswordPolicy policy;
    private ChangePasswordPresenter presenter;
    private ChangePasswordControl control;

    private UserAccount savedUser; // Để verify user đã được save

    @BeforeEach
    void setUp() {
        // Mock Repository
        mockRepo = new UserRepository() {
            private UserAccount testUser = new UserAccount(
                    1L,
                    "Nguyen Van A",
                    "nguyenvana",
                    "nguyenvana@example.com",
                    "$2a$10$hashedOldPassword", // Mật khẩu cũ đã hash
                    Role.USER);

            @Override
            public UserAccount save(UserAccount user) {
                // Lưu lại để verify
                savedUser = user;
                return user;
            }

            @Override
            public UserAccount findByEmail(String email) {
                return testUser;
            }

            @Override
            public UserAccount findByUsername(String username) {
                return testUser;
            }

            @Override
            public UserAccount findById(Long id) {
                if (id.equals(1L)) {
                    return testUser;
                }
                return null;
            }
        };

        // Mock Password Encryptor
        mockEncryptor = new PasswordEncryptor() {
            @Override
            public String hash(String rawPassword) {
                return "$2a$10$hashed_" + rawPassword;
            }

            @Override
            public boolean verify(String rawPassword, String hashed) {
                // Verify đúng nếu raw password là "oldPassword123"
                return rawPassword.equals("oldPassword123") && hashed.equals("$2a$10$hashedOldPassword");
            }
        };

        policy = new ChangePasswordPolicy(mockEncryptor);
        presenter = new ChangePasswordPresenter();
        control = new ChangePasswordControl(mockRepo, policy, presenter);
    }

    @Test
    @DisplayName("Kịch bản thành công: Đổi mật khẩu thành công với tất cả BR đúng")
    void testChangePassword_AllValidations_ShouldSuccess() {
        // Given: User nhập đầy đủ thông tin hợp lệ
        ChangePasswordInputData input = new ChangePasswordInputData(
                1L, // userId tồn tại
                "oldPassword123", // Mật khẩu cũ ĐÚNG
                "newPassword456" // Mật khẩu mới hợp lệ (≥ 8 ký tự, khác mật khẩu cũ)
        );

        // When: Thực hiện change password
        control.execute(input);

        // Then: Kết quả phải thành công
        ChangePasswordViewModel result = presenter.getViewModel();

        // 1. Verify kết quả thành công
        assertTrue(result.success(), "Change password phải thành công");
        assertEquals("Đổi mật khẩu thành công", result.message(),
                "Message phải là 'Đổi mật khẩu thành công'");

        // 2. Verify user đã được save
        assertNotNull(savedUser, "User phải được save vào repository");

        // 3. Verify mật khẩu đã được hash và update
        assertEquals("$2a$10$hashed_newPassword456", savedUser.getPasswordHash(),
                "Mật khẩu mới phải được hash và lưu vào user");

        // 4. Verify các thông tin khác không thay đổi
        assertEquals(1L, savedUser.getId(), "User ID không được thay đổi");
        assertEquals("Nguyen Van A", savedUser.getName(), "Tên không được thay đổi");
        assertEquals("nguyenvana", savedUser.getUsername(), "Username không được thay đổi");
        assertEquals("nguyenvana@example.com", savedUser.getEmail(), "Email không được thay đổi");
    }

    @Test
    @DisplayName("Kịch bản thành công: Mật khẩu mới đúng 8 ký tự (boundary test)")
    void testChangePassword_NewPasswordExactly8Chars_ShouldSuccess() {
        // Given: Mật khẩu mới đúng 8 ký tự (boundary case)
        ChangePasswordInputData input = new ChangePasswordInputData(
                1L,
                "oldPassword123",
                "12345678" // Đúng 8 ký tự
        );

        // When: Thực hiện change password
        control.execute(input);

        // Then: Kết quả phải thành công
        ChangePasswordViewModel result = presenter.getViewModel();

        assertTrue(result.success(), "Change password phải thành công với mật khẩu 8 ký tự");
        assertEquals("Đổi mật khẩu thành công", result.message());
        assertEquals("$2a$10$hashed_12345678", savedUser.getPasswordHash());
    }

    @Test
    @DisplayName("Kịch bản thành công: Mật khẩu mới rất dài (>50 ký tự)")
    void testChangePassword_VeryLongNewPassword_ShouldSuccess() {
        // Given: Mật khẩu mới rất dài
        String longPassword = "ThisIsAVeryLongPasswordWithMoreThan50CharactersToTestTheSystem123456";
        ChangePasswordInputData input = new ChangePasswordInputData(
                1L,
                "oldPassword123",
                longPassword);

        // When: Thực hiện change password
        control.execute(input);

        // Then: Kết quả phải thành công
        ChangePasswordViewModel result = presenter.getViewModel();

        assertTrue(result.success(), "Change password phải thành công với mật khẩu dài");
        assertEquals("Đổi mật khẩu thành công", result.message());
        assertEquals("$2a$10$hashed_" + longPassword, savedUser.getPasswordHash());
    }
}
