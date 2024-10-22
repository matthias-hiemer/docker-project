package com.example.backend.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

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

        // WHEN
        mockMvc.perform(get("/api/auth/me")
                        .with(oidcLogin().userInfoToken(token -> token
                                .claim("login", "John"))))
                // THEN
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("John"));
    }

}