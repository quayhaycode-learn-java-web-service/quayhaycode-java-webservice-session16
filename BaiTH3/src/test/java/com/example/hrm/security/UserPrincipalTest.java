package com.example.hrm.security;

import com.example.hrm.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class UserPrincipalTest {

    private User mockUser;

    @BeforeEach
    void setUp() {
        mockUser = new User();
        mockUser.setId(1L);
        mockUser.setUsername("testuser");
        mockUser.setPassword("$2a$12$abcdefghijklmnopqrstuvwxyz");
        mockUser.setRole("ADMIN");
        mockUser.setEnabled(true);
    }

    @Test
    @DisplayName("Kiểm tra UserPrincipal chuyển đổi dữ liệu từ User Entity chính xác")
    void shouldMapUserToUserPrincipalCorrectly() {
        UserPrincipal userPrincipal = new UserPrincipal(mockUser);

        assertEquals("testuser", userPrincipal.getUsername());
        assertEquals("$2a$12$abcdefghijklmnopqrstuvwxyz", userPrincipal.getPassword());
        assertTrue(userPrincipal.isEnabled());
        assertTrue(userPrincipal.isAccountNonExpired());
        assertTrue(userPrincipal.isAccountNonLocked());
        assertTrue(userPrincipal.isCredentialsNonExpired());

        Collection<? extends GrantedAuthority> authorities = userPrincipal.getAuthorities();
        assertNotNull(authorities);
        assertEquals(1, authorities.size());

        String actualRole = authorities.iterator().next().getAuthority();
        assertEquals("ROLE_ADMIN", actualRole);
    }
}