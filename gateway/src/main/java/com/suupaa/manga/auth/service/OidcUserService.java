package com.suupaa.manga.auth.service;

import org.springframework.http.MediaType;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class OidcUserService {

    public void synchronizeUser(OidcUser oidcUser) {
        WebClient.create("http://localhost:8084/user-profiles")
                .get()
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .subscribe();
    }

}
