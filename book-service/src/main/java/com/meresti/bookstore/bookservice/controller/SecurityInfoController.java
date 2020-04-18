package com.meresti.bookstore.bookservice.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
public class SecurityInfoController {

    @GetMapping("/me")
    public Map<String, Object> getPrincipal(final @AuthenticationPrincipal Jwt jwt) {
        final Map<String, Object> result = new LinkedHashMap<>();
        result.put("TokenValue", jwt.getTokenValue());
        result.put("Headers", jwt.getHeaders());
        result.put("Claims", jwt.getClaims());
        result.put("Audience", jwt.getAudience());
        result.put("Issuer", jwt.getIssuer());
        result.put("IssuedAt", jwt.getIssuedAt());
        result.put("ExpiresAt", jwt.getExpiresAt());
        result.put("NotBefore", jwt.getNotBefore());
        result.put("Subject", jwt.getSubject());
        result.put("Id", jwt.getId());

        return result;
    }

}
