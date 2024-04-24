package com.fitquest.controller;

import com.fitquest.model.AppUser;
import com.fitquest.model.enums.Role;
import com.fitquest.repo.UserRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.junit.jupiter.api.Assertions.*;

class StatsContTest {

    @InjectMocks
    private StatsCont statsCont;

    @Mock
    private Model model;

    @Mock
    private UserRepo userRepo;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testStats() {
        AppUser user1 = new AppUser();
        user1.setRole(Role.USER);
        AppUser user2 = new AppUser();
        user2.setRole(Role.USER);

        userRepo.save(user1);
        userRepo.save(user2);

        String result = statsCont.stats(model);

        verify(userRepo).findAllByRole(Role.USER);

        verify(model).addAttribute(eq("medExp"), anyInt());
        verify(model).addAttribute(eq("medRatio"), anyFloat());
        verify(model).addAttribute(eq("medWin"), anyFloat());
        verify(model).addAttribute(eq("expString"), any());
        verify(model).addAttribute(eq("expFloat"), any());
        verify(model).addAttribute(eq("genderString"), any());
        verify(model).addAttribute(eq("genderInt"), any());

        assertEquals("stats", result);
    }
}
