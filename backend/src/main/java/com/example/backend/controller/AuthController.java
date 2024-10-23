package com.example.backend.controller;

import com.example.backend.model.AppUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
@Slf4j
public class AuthController {

    // GET /api/auth/me
    @GetMapping("/me")
    public AppUser getMe(@AuthenticationPrincipal OAuth2User user) {

        return AppUser.builder()
                .id(user.getName())
                .username(user.getAttribute("login"))
                .avatarUrl(user.getAttribute("avatar_url"))
                .favorites(List.of())
                .build();
    }
}