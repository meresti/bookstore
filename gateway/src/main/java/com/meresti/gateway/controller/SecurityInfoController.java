package com.meresti.gateway.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
public class SecurityInfoController {

    @GetMapping("/me")
    public Mono<Map<String, Object>> getPrincipal(final @RegisteredOAuth2AuthorizedClient OAuth2AuthorizedClient authorizedClient,
                                                  final @AuthenticationPrincipal OAuth2User oauth2User,
                                                  final @AuthenticationPrincipal OidcUser oidcUser) {
        final Map<String, Object> authorizedClientFields = new LinkedHashMap<>();
        authorizedClientFields.put("PrincipalName", authorizedClient.getPrincipalName());
        authorizedClientFields.put("AccessToken", authorizedClient.getAccessToken());
        authorizedClientFields.put("RefreshToken", authorizedClient.getRefreshToken());
        authorizedClientFields.put("ClientRegistration", authorizedClient.getClientRegistration());

        final Map<String, Object> oauth2UserFields = new LinkedHashMap<>();
        oauth2UserFields.put("Name", oauth2User.getName());
        oauth2UserFields.put("Attributes", oauth2User.getAttributes());
        oauth2UserFields.put("Authorities", oauth2User.getAuthorities());

        final Map<String, Object> oidcUserFields = new LinkedHashMap<>();
        oidcUserFields.put("Name", oidcUser.getName());
        oidcUserFields.put("Attributes", oidcUser.getAttributes());
        oidcUserFields.put("Authorities", oidcUser.getAuthorities());
        oidcUserFields.put("IdToken", oidcUser.getIdToken());
        oidcUserFields.put("UserInfo", oidcUser.getUserInfo());
        oidcUserFields.put("Claims", oidcUser.getClaims());

        final Map<String, Object> result = new LinkedHashMap<>();
        result.put("AuthorizedClient", authorizedClientFields);
        result.put("Oauth2User", oauth2UserFields);
        result.put("OidcUser", oidcUserFields);

        return Mono.just(result);
    }

}
