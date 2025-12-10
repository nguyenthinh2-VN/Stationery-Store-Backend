
package com.yuki.tkxdpm_k17_06.LoginEmailOrUsernameAndGoogle.Config;

import com.yuki.tkxdpm_k17_06.LoginEmailOrUsernameAndGoogle.Infrastructure.*;
import com.yuki.tkxdpm_k17_06.LoginEmailOrUsernameAndGoogle.Presenter.GoogleAuthPolicy;
import com.yuki.tkxdpm_k17_06.LoginEmailOrUsernameAndGoogle.Presenter.GoogleOAuthService;
import com.yuki.tkxdpm_k17_06.LoginEmailOrUsernameAndGoogle.Presenter.LoginPresenter;
import com.yuki.tkxdpm_k17_06.LoginEmailOrUsernameAndGoogle.Usecase.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class LoginBeanConfig {

    @Bean
    public IdentifierParserPolicy identifierParserPolicy() {
        return new IdentifierParserPolicyImpl();
    }

    @Bean("loginPasswordEncryptor")
    public PasswordEncryptor passwordEncryptor() {
        return new BCryptPasswordEncryptor();
    }

    @Bean
    public PasswordCheckPolicy passwordCheckPolicy(@Qualifier("loginPasswordEncryptor") PasswordEncryptor encryptor) {
        return new DefaultPasswordCheckPolicy(encryptor);
    }

    @Bean
    public CredentialAuthPolicy credentialAuthPolicy(
            IdentifierParserPolicy parser,
            @Qualifier("loginPasswordEncryptor") PasswordEncryptor encryptor,
            UserRepository repo,
            TokenGenerationPolicy tokenGen) {
        return new CredentialAuthPolicy(parser, encryptor, repo, tokenGen);
    }

    @Bean
    public GoogleAuthPolicy googleAuthPolicy(
            GoogleOAuthService google,
            UserRepository repo,
            TokenGenerationPolicy tokenGen) {
        return new GoogleAuthPolicy(google, repo, tokenGen);
    }

    @Bean
    public Map<LoginMethod, AuthPolicy> loginPolicies(
            CredentialAuthPolicy credential,
            GoogleAuthPolicy google) {
        Map<LoginMethod, AuthPolicy> map = new HashMap<>();
        map.put(LoginMethod.CREDENTIAL, credential);
        map.put(LoginMethod.GOOGLE, google);
        return map;
    }

    @Bean("loginTokenGenerationPolicy")
    public TokenGenerationPolicy loginTokenGenerationPolicy(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.expiration:86400000}") long expMs) {
        SecretKey key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        return new JwtTokenGenerationPolicy(key, expMs);
    }

    @Bean
    public LoginInputBoundary loginInputBoundary(Map<LoginMethod, AuthPolicy> policies,
            LoginOutputBoundary loginPresenter) {
        return new LoginControl(policies, loginPresenter);
    }

    @Bean
    public LoginOutputBoundary loginPresenter() {
        return new LoginPresenter();
    }

    @Bean
    public GoogleOAuthService googleOAuthService() {
        return new GoogleOAuthService("940090065833-lucvnncjjmqosi7cn7d4p3bg93ap4br3.apps.googleusercontent.com");
    }
}