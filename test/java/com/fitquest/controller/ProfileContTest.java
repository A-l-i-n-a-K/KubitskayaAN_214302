package com.fitquest.controller;

import com.fitquest.model.AppUser;
import com.fitquest.repo.CategoryRepo;
import com.fitquest.repo.UserRepo;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProfileContTest {
    ProfileCont profileCont = new ProfileCont();
    @Test
    void testPhoto_WithValidPhotoAndFormat() throws IOException {
        Model modelMock = mock(Model.class);
        MultipartFile photoMock = mock(MultipartFile.class);
        AppUser userMock = mock(AppUser.class);
        UserRepo userRepoMock = mock(UserRepo.class);
        when(profileCont.getUser()).thenReturn(userMock);
        when(photoMock.getOriginalFilename()).thenReturn("testphoto.jpg");
        String result = profileCont.photo(modelMock, photoMock);
        assertEquals("redirect:/profile", result);
        verify(userMock).setPhoto(anyString());
        verify(userRepoMock).save(userMock);
        verifyNoInteractions(modelMock);
    }
    @Test
    void testPhoto_WithInvalidPhotoFormat() throws IOException {
        Model modelMock = mock(Model.class);
        MultipartFile photoMock = mock(MultipartFile.class);
        when(photoMock.getOriginalFilename()).thenReturn("testphoto.txt");
        String result = profileCont.photo(modelMock, photoMock);
        assertEquals("redirect:/profile", result);
    }
    @Test
    void testPhoto_WithIOException() throws IOException {
        Model modelMock = mock(Model.class);
        MultipartFile photoMock = mock(MultipartFile.class);
        when(photoMock.getOriginalFilename()).thenReturn("testphoto.jpg");
        doThrow(IOException.class).when(photoMock).transferTo((File) any());

        20

        String result = profileCont.photo(modelMock, photoMock);
        assertEquals("profile", result);
        verify(modelMock).addAttribute(eq("message"), eq("Некорректные
                данные!"));
        verify(profileCont).getCurrentUserAndRole(modelMock);
    }
    ////////////////////////////////////////
    @Test
    void testFio() {
        Model modelMock = mock(Model.class);
        AppUser userMock = mock(AppUser.class);
        UserRepo userRepoMock = mock(UserRepo.class);
        when(profileCont.getUser()).thenReturn(userMock);
        String testFio = "John Doe";
        String result = profileCont.fio(testFio);
        assertEquals("redirect:/profile", result);
        verify(userMock).setFio(testFio);
        verify(userRepoMock).save(userMock);
    }
    ////////////////////////////////////////
    @Test
    void testCategory() {
        Model modelMock = mock(Model.class);
        AppUser userMock = mock(AppUser.class);
        UserRepo userRepoMock = mock(UserRepo.class);
        CategoryRepo categoryRepoMock = mock(CategoryRepo.class);
        when(profileCont.getUser()).thenReturn(userMock);
        Long testCategoryId = 1L;
        String result = profileCont.category(testCategoryId);
        assertEquals("redirect:/profile", result);
        verify(userMock).setCategory(any());
        verify(userRepoMock).save(userMock);
    }
    @Test
    void testCategory_WithInvalidCategoryId() {
        Model modelMock = mock(Model.class);
        AppUser userMock = mock(AppUser.class);
        when(profileCont.getUser()).thenReturn(userMock);
        String result = profileCont.category(0L);
        assertEquals("redirect:/profile", result);
    }
    ////////////////////////////////////////
    @Test
    void testTrainer_WithValidTrainerId() {
        Model modelMock = mock(Model.class);
        AppUser userMock = mock(AppUser.class);
        UserRepo userRepoMock = mock(UserRepo.class);

        21

        when(profileCont.getUser()).thenReturn(userMock);
        Long testTrainerId = 1L;
        String result = profileCont.trainer(testTrainerId);
        assertEquals("redirect:/profile", result);
        verify(userMock).setTrainer(any());
        verify(userRepoMock).save(userMock);
    }
    @Test
    void testTrainer_WithInvalidTrainerId() {
        Model modelMock = mock(Model.class);
        AppUser userMock = mock(AppUser.class);
        when(profileCont.getUser()).thenReturn(userMock);
        String result = profileCont.trainer(0L);
        assertEquals("redirect:/profile", result);
    }
    ////////////////////////////////////////
    @Test
    void testEdit_WithValidInputs() {
        AppUser userMock = mock(AppUser.class);
        when(profileCont.getUser()).thenReturn(userMock);
        String testTel = "1234567890";
        String testEmail = "test@example.com";
        String result = profileCont.edit(testTel, testEmail);
        assertEquals("redirect:/profile", result);
        verify(userMock).setTel(testTel);
        verify(userMock).setEmail(testEmail);
    }
    @Test
    void testEdit_WithInvalidEmailFormat() {
        String testTel = "1234567890";
        String testEmail = "invalid-email";
        when(profileCont.getCurrentUserEmail()).thenReturn("john.doe@example.com");
    }
    @Test
    void testEdit_WithInvalidTelFormat() {
        String testTel = "123456789";
        String testEmail = "test@example.com";
        when(profileCont.getCurrentUserNumber()).thenReturn("+123456789123");}
}
