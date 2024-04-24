package com.fitquest.controller;

import com.fitquest.model.AppUser;
import com.fitquest.model.enums.Gender;
import com.fitquest.model.enums.Role;
import com.fitquest.repo.UserRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserContTest {

    @InjectMocks
    private UserCont userCont;

    @Mock
    private UserRepo userRepo;

    @Mock
    private Model model;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testUsers() {

        List<AppUser> users = new ArrayList<>();
        users.add(new AppUser());
         users.add(new AppUser());

        when(userRepo.findAll()).thenReturn(users);

        String result = userCont.users(model);

        verify(userRepo).findAll();

        verify(model).addAttribute("users", users);
        verify(model).addAttribute("roles", Role.values());

        assertEquals("users", result);
    }

    @Test
    public void testEdit() {
        Long userId = 1L;
        Role newRole = Role.MANAGER;

        AppUser user = new AppUser();
        user.setId(userId);
        when(userRepo.getReferenceById(userId)).thenReturn(user);

        String result = userCont.edit(userId, newRole);

        verify(userRepo).getReferenceById(userId);

        assertEquals(newRole, user.getRole());

        verify(userRepo).save(user);

        assertEquals("redirect:/users", result);
    }

    @Test
    public void testDelete() {
        Long userId = 1L;

        String result = userCont.delete(userId);

        verify(userRepo).deleteById(userId);

        assertEquals("redirect:/users", result);
    }

    @Test
    public void testApp() {
        Long userId = 1L;

        AppUser user = new AppUser();
        user.setId(userId);

        when(userRepo.getReferenceById(userId)).thenReturn(user);

        String result = userCont.app(userId);

        assertEquals("", user.getFile());
        assertEquals(Role.MANAGER, user.getRole());

        verify(userRepo).save(user);

        assertEquals("redirect:/users", result);
    }
}