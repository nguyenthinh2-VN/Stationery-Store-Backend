package com.yuki.tkxdpm_k17_06.ForgotPassword;

import com.yuki.tkxdpm_k17_06.LoginEmailOrUsernameAndGoogle.Usecase.UserRepository;
import com.yuki.tkxdpm_k17_06.PasswordEncryptor;
import com.yuki.tkxdpm_k17_06.RegisterAccountUserWithEmail.Entity.Otp;
import com.yuki.tkxdpm_k17_06.RegisterAccountUserWithEmail.Entity.Role;
import com.yuki.tkxdpm_k17_06.RegisterAccountUserWithEmail.Entity.UserAccount;
import com.yuki.tkxdpm_k17_06.ForgotPassword.ResetPassword.ResetPasswordInputData;
import com.yuki.tkxdpm_k17_06.ForgotPassword.ResetPassword.Usecase.ResetPasswordUseCase;
import com.yuki.tkxdpm_k17_06.ForgotPassword.VerifyOTP.Usecase.OtpRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * UC3 - Đổi mật khẩu
 * 
 * Business Rules:
 * - BR8: Không được đặt mật khẩu trùng mật khẩu cũ (chưa implement)
 * - BR9: OTP đã dùng → không dùng lại
 * - BR10: Sau khi đổi mật khẩu → invalidate tất cả session/token cũ (chưa
 * implement)
 * 
 * Flow of event:
 * 1. User nhập mật khẩu mới
 * 2. Hệ thống kiểm tra:
 * 2.1 OTP đã được xác thực chưa?
 * 3. Hệ thống kiểm tra mật khẩu:
 * 3.1 Kiểm tra độ dài
 * 3.2 Kiểm tra độ mạnh
 * 4. Hệ thống mã hoá mật khẩu mới
 * 5. Hệ thống cập nhật mật khẩu user
 * 6. Hệ thống xoá / vô hiệu OTP
 * 7. Hệ thống phản hồi cho User Thành công / Thất bại
 * 8. User thấy thông báo thành công / thất bại
 * 
 * Test Cases:
 * - KB1: Full success flow (OTP đã verify, password hợp lệ)
 * - KB2: OTP chưa xác thực → Lỗi
 */
class ResetPasswordUseCaseTest {

    private UserRepository mockUserRepo;
    private OtpRepository mockOtpRepo;
    private PasswordEncryptor mockEncryptor;
    private ResetPasswordUseCase useCase;

    // Track operations
    private List<UserAccount> savedUsers;
    private List<String> invalidatedEmails;
    private UserAccount testUser;
    private Otp verifiedOtp;

    @BeforeEach
    void setUp() {
        savedUsers = new ArrayList<>();
        invalidatedEmails = new ArrayList<>();

        // Tạo test user
        testUser = new UserAccount(1L, "Nguyen Van A", "nguyenvana",
                "user@example.com", "$2a$10$oldHashedPassword", Role.USER);

        // Tạo verified OTP
        verifiedOtp = new Otp();
        verifiedOtp.setId(1L);
        verifiedOtp.setEmail("user@example.com");
        verifiedOtp.setCode("123456");
        verifiedOtp.setExpiredAt(LocalDateTime.now().plusMinutes(5));
        verifiedOtp.setUsed(true); // Đã được verify

        // Mock UserRepository
        mockUserRepo = new UserRepository() {
            @Override
            public UserAccount save(UserAccount user) {
                savedUsers.add(user);
                return user;
            }

            @Override
            public UserAccount findByEmail(String email) {
                if (email.equals("user@example.com")) {
                    return testUser;
                }
                return null;
            }

            @Override
            public UserAccount findByUsername(String username) {
                return null;
            }

            @Override
            public UserAccount findById(Long id) {
                return null;
            }
        };

        // Mock OtpRepository
        mockOtpRepo = new OtpRepository() {
            @Override
            public void save(Otp otp) {
            }

            @Override
            public Otp findValidOtp(String email, String code) {
                return null;
            }

            @Override
            public void invalidateAllByEmail(String email) {
                invalidatedEmails.add(email);
            }

            @Override
            public Otp findUsedOtpByEmail(String email) {
                if (email.equals("user@example.com")) {
                    return verifiedOtp; // OTP đã verified
                }
                return null; // OTP chưa verified
            }
        };

        // Mock PasswordEncryptor
        mockEncryptor = new PasswordEncryptor() {
            @Override
            public String hash(String rawPassword) {
                return "$2a$10$hashed_" + rawPassword;
            }

            @Override
            public boolean verify(String rawPassword, String hashed) {
                return false;
            }
        };

        useCase = new ResetPasswordUseCase(mockUserRepo, mockOtpRepo, mockEncryptor);
    }

    @Test
    @DisplayName("KB1: Full success flow - OTP đã verify, password hợp lệ → Đổi mật khẩu thành công")
    void testResetPassword_ValidFlow_ShouldSuccess() {
        // Given: OTP đã được verify, password mới hợp lệ
        ResetPasswordInputData input = new ResetPasswordInputData("user@example.com", "newPassword123");

        // When: Thực hiện reset password
        assertDoesNotThrow(() -> useCase.execute(input));

        // Then:
        // 1. User phải được save với password mới đã hash
        assertEquals(1, savedUsers.size(), "User phải được save");
        UserAccount savedUser = savedUsers.get(0);
        assertEquals("$2a$10$hashed_newPassword123", savedUser.getPasswordHash(),
                "Password mới phải được hash và lưu");

        // 2. OTP phải được invalidate sau khi đổi mật khẩu thành công
        assertEquals(1, invalidatedEmails.size(), "OTP phải được invalidate");
        assertEquals("user@example.com", invalidatedEmails.get(0));
    }

    @Test
    @DisplayName("KB2: OTP chưa xác thực - Đổi mật khẩu thất bại")
    void testResetPassword_OtpNotVerified_ShouldFail() {
        // Given: OTP chưa được verify (email khác không có verified OTP)
        ResetPasswordInputData input = new ResetPasswordInputData("notverified@example.com", "newPassword123");

        // When & Then: Phải throw exception vì OTP chưa được xác thực
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> useCase.execute(input));

        assertEquals("Chưa xác thực OTP. Vui lòng xác thực OTP trước.", exception.getMessage());
    }

    @Test
    @DisplayName("KB2: User không tồn tại - Đổi mật khẩu thất bại")
    void testResetPassword_UserNotExist_ShouldFail() {
        // Given: Email có verified OTP nhưng user không tồn tại
        // Modify mock to return verified OTP but no user
        mockOtpRepo = new OtpRepository() {
            @Override
            public void save(Otp otp) {
            }

            @Override
            public Otp findValidOtp(String email, String code) {
                return null;
            }

            @Override
            public void invalidateAllByEmail(String email) {
            }

            @Override
            public Otp findUsedOtpByEmail(String email) {
                return verifiedOtp; // OTP đã verified
            }
        };

        mockUserRepo = new UserRepository() {
            @Override
            public UserAccount save(UserAccount user) {
                return user;
            }

            @Override
            public UserAccount findByEmail(String email) {
                return null;
            } // User không tồn tại

            @Override
            public UserAccount findByUsername(String username) {
                return null;
            }

            @Override
            public UserAccount findById(Long id) {
                return null;
            }
        };

        useCase = new ResetPasswordUseCase(mockUserRepo, mockOtpRepo, mockEncryptor);

        ResetPasswordInputData input = new ResetPasswordInputData("user@example.com", "newPassword123");

        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> useCase.execute(input));

        assertEquals("User không tồn tại", exception.getMessage());
    }
}
