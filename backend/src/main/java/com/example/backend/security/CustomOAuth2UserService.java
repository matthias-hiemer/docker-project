package com.example.backend.security;

import com.example.backend.model.AppUser;
import com.example.backend.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest request) {

        // Load user
        OAuth2User oAuth2User = super.loadUser(request);

        // Check if user exist, if not create it
        AppUser appUser = userRepository.findById(oAuth2User.getName())
                .orElseGet( () -> createAndSaveUser(oAuth2User) );

        // return oauth user
        return oAuth2User;
    }

    private AppUser createAndSaveUser(OAuth2User oAuth2User) {
        AppUser newUser = AppUser.builder()
                .id(oAuth2User.getName())
                .username(oAuth2User.getAttribute("login"))
                .avatarUrl(oAuth2User.getAttribute("avatar_url"))
                .favorites(List.of())
                .build();

        return userRepository.save(newUser);
    }
}
