package com.fitquest.controller;

import com.fitquest.model.AppUser;
import com.fitquest.model.enums.Gender;
import com.fitquest.repo.UserRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
class DataContTest {
        @Mock
        private UserRepo userRepo;
        private UserRepo userDataService;
        @BeforeEach
        void setUp() {
                MockitoAnnotations.openMocks(this);
        }
        @Test
        void testUpdate() {
                AppUser testUser = getUser();
                testUser.setGender(Gender.M);
                testUser.setExperience(3);
                testUser.setAge(2);
                testUser.setWin(5);
                testUser.setLose(2);
                testUser.setWeight(60);
                testUser.setHeight(160);
                assertDoesNotThrow(() -> userDataService.save(testUser));
                assertEquals(Gender.M, testUser.getGender());
                assertEquals(3, testUser.getExperience());
                assertEquals(2, testUser.getAge());
                assertEquals(5, testUser.getWin());
                assertEquals(2, testUser.getLose());
                assertEquals(60, testUser.getWeight());
                assertEquals(160, testUser.getHeight());
                verify(userRepo, times(1)).save(testUser);
        }
}