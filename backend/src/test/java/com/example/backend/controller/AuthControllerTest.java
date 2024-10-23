package com.example.backend.controller;

import com.example.backend.model.AppUser;
import com.example.backend.repo.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.oidcLogin;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    private UserRepository userRepository;

    @Test
    void getMe_whenNotLoggedIn_return401() throws Exception {

        // WHEN
        mockMvc.perform(get("/api/auth/me"))
            // THEN
            .andDo(print())
            .andExpect(status().isUnauthorized());
    }

    @Test
    void getMe_whenLoggedIn_returnUsername() throws Exception {

        // GIVEN
        userRepository.save(AppUser.builder()
                .id("12345")
                .username("John")
                .avatarUrl("https://domain.test/image")
                .favorites(List.of())
                .role("USER")
                .build());

        // WHEN
        mockMvc.perform(get("/api/auth/me")
                        .with(oidcLogin()
                                .idToken(builder -> builder.subject("12345"))
                                .userInfoToken(token -> token
                                        .claim("login", "John")
                                        .claim("avatar_url", "https://domain.test/image")
                                )))
                // THEN
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        {
                            "id": "12345",
                            "username": "John",
                            "avatarUrl": "https://domain.test/image",
                            "favorites": [],
                            "role": "USER"
                        }
                        """));
    }

    @Test
    void logout_whenLoggingOut_returnOk() throws Exception {

        // WHEN
        mockMvc.perform(get("/api/auth/logout")
                .with(oidcLogin().userInfoToken(token -> token
                        .claim("login", "John"))))

            // THEN
            .andExpect(status().isOk());
    }

    @Test
    void logout_whenNotLoggedIn_return401() throws Exception {

        // WHEN
        mockMvc.perform(get("/api/auth/logout"))

                // THEN
                .andExpect(status().isOk());
    }

}
