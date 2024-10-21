package com.example.backend.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@Slf4j
public class AuthController {

    // GET /api/auth/me
    @GetMapping("/me")
    public String getMe(@AuthenticationPrincipal OAuth2User user) {
        log.info("User: {}", user);
        log.info("User.attributes: {}", user.getAttributes());
        return user.getAttributes().get("login").toString(); // This is the user's username e.g. MahmoudFawzyKhalil
    }
}