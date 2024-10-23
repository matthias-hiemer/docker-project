package com.example.backend.security;

import com.example.backend.model.AppUser;
import com.example.backend.repo.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomOAuth2UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Spy
    private CustomOAuth2UserService customOAuth2UserService;



    @Test
    void loadUser_whenExistingUser_thenLoad() {
        // GIVEN

        // Dummy OAuth2User
        Map<String, Object> attributes = Map.of("id", "existingUser", "login", "newUserLogin", "avatar_url", "newUserAvatarUrl");
        OAuth2User oAuth2User = new DefaultOAuth2User(List.of(new SimpleGrantedAuthority("USER")), attributes, "id");

        // Mock loadUser
        customOAuth2UserService = spy(new CustomOAuth2UserService(userRepository));
        OAuth2UserRequest request = mock(OAuth2UserRequest.class);
        doReturn(oAuth2User).when(customOAuth2UserService).loadUserFromDefault(request);

        // Mock existing user
        AppUser appUser = new AppUser("existingUser", "username", "avatarUrl", List.of(), "USER");
        when(userRepository.findById("existingUser")).thenReturn(Optional.of(appUser));

        // WHEN
        OAuth2User result = customOAuth2UserService.loadUser(request);

        // THEN
        assertEquals("existingUser", result.getName());
        verify(userRepository, never()).save(any(AppUser.class));
    }

    @Test
    void loadUser_whenNewUser_thenSave() {
        // GIVEN

        // Dummy OAuth2User
        Map<String, Object> attributes = Map.of("id", "newUser", "login", "newUserLogin", "avatar_url", "newUserAvatarUrl");
        OAuth2User oAuth2User = new DefaultOAuth2User(List.of(new SimpleGrantedAuthority("USER")), attributes, "id");

        // Mock loadUser
        customOAuth2UserService = spy(new CustomOAuth2UserService(userRepository));
        OAuth2UserRequest request = mock(OAuth2UserRequest.class);
        doReturn(oAuth2User).when(customOAuth2UserService).loadUserFromDefault(request);

        // Mock non-existing user
        when(userRepository.findById("newUser")).thenReturn(Optional.empty());

        // Mock newly created user
        AppUser newUser = new AppUser("newUser", "newUserLogin", "newUserAvatarUrl", List.of(), "USER");
        when(userRepository.save(any(AppUser.class))).thenReturn(newUser);

        // WHEN
        OAuth2User result = customOAuth2UserService.loadUser(request);

        // THEN
        assertEquals("newUser", result.getName());
        verify(userRepository).save(any(AppUser.class));
    }
}