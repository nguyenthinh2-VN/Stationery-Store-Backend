
    package com.yuki.tkxdpm_k17_06.LoginEmailOrUsername.Config;

    import com.yuki.tkxdpm_k17_06.LoginEmailOrUsername.Infrastructure.*;
    import com.yuki.tkxdpm_k17_06.LoginEmailOrUsername.Presenter.LoginPresenter;
    import com.yuki.tkxdpm_k17_06.LoginEmailOrUsername.Usecase.*;
    import io.jsonwebtoken.security.Keys;
    import org.springframework.beans.factory.annotation.Value;
    import org.springframework.beans.factory.annotation.Qualifier;
    import org.springframework.context.annotation.Bean;
    import org.springframework.context.annotation.Configuration;

    import javax.crypto.SecretKey;
    import java.nio.charset.StandardCharsets;

    @Configuration
    public class LoginBeanConfig {

        @Bean
        public IdentifierParserPolicy identifierParserPolicy() {
            return new IdentifierParserPolicyImpl();
        }

        @Bean
        public PasswordEncryptor passwordEncryptor() {
            return new BCryptPasswordEncryptor();
        }

        @Bean
        public PasswordCheckPolicy passwordCheckPolicy(PasswordEncryptor encryptor) {
            return new DefaultPasswordCheckPolicy(encryptor);
        }

        @Bean
        public UserFinderPolicy userFinderPolicy(UserRepository repo) {
            return new UserFinderPolicyImpl(repo);
        }

        @Bean("loginTokenGenerationPolicy")
        public TokenGenerationPolicy loginTokenGenerationPolicy(
                @Value("${jwt.secret}") String secret,
                @Value("${jwt.expiration:86400000}") long expMs
        ) {
            SecretKey key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
            return new JwtTokenGenerationPolicy(key, expMs);
        }


        @Bean
        public LoginOutputBoundary loginPresenter() {
            return new LoginPresenter();
        }

        @Bean
        public LoginInputBoundary loginUseCase(
                IdentifierParserPolicy parser,
                UserFinderPolicy finder,
                PasswordCheckPolicy passwordPolicy,
                @Qualifier("loginTokenGenerationPolicy") TokenGenerationPolicy tokenPolicy,
                LoginOutputBoundary presenter
        ) {
            return new LoginControl(parser, finder, passwordPolicy, tokenPolicy, presenter);
        }
    }