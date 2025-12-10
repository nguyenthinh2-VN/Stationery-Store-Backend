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
 * Kịch bản 1: Thất bại - Mật khẩu cũ sai
 * 
 * Flow:
 * 1. User nhập mật khẩu cũ SAI
 * 2. Hệ thống kiểm tra mật khẩu cũ → FAIL
 * 3. Phản hồi "Mật khẩu cũ không chính xác"
 */
class ChangePasswordControlTest_FailureScenario {

    private UserRepository mockRepo;
    private PasswordEncryptor mockEncryptor;
    private ChangePasswordPolicy policy;
    private ChangePasswordPresenter presenter;
    private ChangePasswordControl control;

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
                // Chỉ verify đúng nếu raw password là "oldPassword123"
                return rawPassword.equals("oldPassword123") && hashed.equals("$2a$10$hashedOldPassword");
            }
        };

        policy = new ChangePasswordPolicy(mockEncryptor);
        presenter = new ChangePasswordPresenter();
        control = new ChangePasswordControl(mockRepo, policy, presenter);
    }

    @Test
    @DisplayName("Kịch bản thất bại: Mật khẩu cũ SAI")
    void testChangePassword_WrongOldPassword_ShouldFail() {
        // Given: User nhập mật khẩu cũ SAI
        ChangePasswordInputData input = new ChangePasswordInputData(
                1L, // userId
                "wrongOldPassword", // Mật khẩu cũ SAI
                "newPassword123" // Mật khẩu mới
        );

        // When: Thực hiện change password
        control.execute(input);

        // Then: Kết quả phải thất bại với message "Mật khẩu cũ không chính xác"
        ChangePasswordViewModel result = presenter.getViewModel();

        assertFalse(result.success(), "Change password phải thất bại khi mật khẩu cũ sai");
        assertEquals("Mật khẩu cũ không chính xác", result.message(),
                "Message phải là 'Mật khẩu cũ không chính xác'");
    }

    @Test
    @DisplayName("Kịch bản thất bại: User không tồn tại")
    void testChangePassword_UserNotFound_ShouldFail() {
        // Given: User ID không tồn tại
        ChangePasswordInputData input = new ChangePasswordInputData(
                100L, // userId không tồn tại
                "oldPassword123",
                "newPassword123");

        // When: Thực hiện change password
        control.execute(input);

        // Then: Kết quả phải thất bại
        ChangePasswordViewModel result = presenter.getViewModel();

        assertFalse(result.success(), "Change password phải thất bại khi user không tồn tại");
        assertEquals("Tài khoản không tồn tại", result.message());
    }

    @Test
    @DisplayName("Kịch bản thất bại: Mật khẩu mới quá ngắn (< 8 ký tự)")
    void testChangePassword_NewPasswordTooShort_ShouldFail() {
        // Given: Mật khẩu mới quá ngắn
        ChangePasswordInputData input = new ChangePasswordInputData(
                1L,
                "oldPassword123", // Mật khẩu cũ đúng
                "short" // Mật khẩu mới chỉ có 5 ký tự
        );

        // When: Thực hiện change password
        control.execute(input);

        // Then: Kết quả phải thất bại
        ChangePasswordViewModel result = presenter.getViewModel();

        assertFalse(result.success(), "Change password phải thất bại khi mật khẩu mới < 8 ký tự");
        assertEquals("Mật khẩu mới không hợp lệ", result.message());
    }

    @Test
    @DisplayName("Kịch bản thất bại: Mật khẩu mới trùng với mật khẩu cũ")
    void testChangePassword_NewPasswordSameAsOld_ShouldFail() {
        // Given: Mật khẩu mới giống mật khẩu cũ
        ChangePasswordInputData input = new ChangePasswordInputData(
                1L,
                "oldPassword123",
                "oldPassword123" // Mật khẩu mới trùng với cũ
        );

        // When: Thực hiện change password
        control.execute(input);

        // Then: Kết quả phải thất bại
        ChangePasswordViewModel result = presenter.getViewModel();

        assertFalse(result.success(), "Change password phải thất bại khi mật khẩu mới trùng cũ");
        assertEquals("Mật khẩu mới không hợp lệ", result.message());
    }
}
