package com.yuki.tkxdpm_k17_06.LoginEmailOrUsernameAndGoogle.Presenter;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.http.javanet.NetHttpTransport;

import java.util.Collections;

public class GoogleOAuthService {

    private final GoogleIdTokenVerifier verifier;

    public GoogleOAuthService(String googleClientId) {
        this.verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new GsonFactory())
                .setAudience(Collections.singletonList(googleClientId))
                .build();
    }

    public GoogleUserData verify(String idTokenString) {
        try {
            GoogleIdToken idToken = verifier.verify(idTokenString);

            if (idToken == null) return null;

            GoogleIdToken.Payload payload = idToken.getPayload();

            return new GoogleUserData(
                    payload.getEmail(),
                    (String) payload.get("name"),
                    (String) payload.get("picture")
            );

        } catch (Exception e) {
            return null;
        }
    }
}

