package com.example.backend.model;

import lombok.Builder;

import java.util.List;

@Builder
public record AppUser(
        String id,
        String username,
        String avatarUrl,
        List<String> favorites,
        String role
) {
}
