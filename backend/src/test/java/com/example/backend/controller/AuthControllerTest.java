package com.example.backend.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.oidcLogin;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    void getMe_whenNotLoggedIn_returnAuthError() throws Exception {

        // WHEN
        mockMvc.perform(get("/api/auth/me"))

                // THEN
                .andExpect(status().isUnauthorized());
    }


    @Test
    void getMe_whenLoggedIn_returnAuthError() throws Exception {

        // WHEN
        mockMvc.perform(get("/api/auth/me")
                        .with(oidcLogin().userInfoToken(token -> token
                                .claim("login", "joe"))))

                // THEN
                .andExpect(status().isOk())
                .andExpect(content().string("joe"));
    }
}