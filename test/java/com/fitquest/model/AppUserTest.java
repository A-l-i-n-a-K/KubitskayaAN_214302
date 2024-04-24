package com.fitquest.model;

import com.fitquest.model.enums.SignStatus;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AppUserTest {
    AppUser appUser = new AppUser();
    @Test
    void testGetImt_WithValidWeightAndHeight() {
        appUser.setWeight(70);
        appUser.setHeight(170);
        float imt = appUser.getImt();
        assertEquals(24.22f, imt, 0.01f);
    }
    @Test
    void testGetImt_WithZeroWeight() {
        appUser.setWeight(0);
        appUser.setHeight(170);
        float imt = appUser.getImt();
        assertEquals(0, imt);
    }
    @Test
    void testGetImt_WithZeroHeight() {
        appUser.setWeight(70);
        appUser.setHeight(0);

        17

        float imt = appUser.getImt();
        assertEquals(0, imt);
    }
    @Test
    void testGetImt_WithZeroWeightAndHeight() {
        appUser.setWeight(0);
        appUser.setHeight(0);
        float imt = appUser.getImt();
        assertEquals(0, imt);
    }
    @Test
    void testGetQuantity_WithNoPassedSigns() {
        List<Sign> signs = Arrays.asList(
                new Sign(SignStatus.NOT_PASSED),
                new Sign(SignStatus.NOT_PASSED),
                new Sign(SignStatus.NOT_PASSED)
        );
        appUser.setSigns(signs);
        int quantity = appUser.getQuantity();
        assertEquals(0, quantity);
    }
    @Test
    void testGetQuantity_WithSomePassedSigns() {
        List<Sign> signs = Arrays.asList(
                new Sign(SignStatus.PASSED),
                new Sign(SignStatus.NOT_PASSED),
                new Sign(SignStatus.PASSED)
        );
        appUser.setSigns(signs);
        int quantity = appUser.getQuantity();
        assertEquals(2, quantity);
    }
    @Test
    void testGetQuantity_WithAllPassedSigns() {
        List<Sign> signs = Arrays.asList(
                new Sign(SignStatus.PASSED),
                new Sign(SignStatus.PASSED),
                new Sign(SignStatus.PASSED)
        );
        appUser.setSigns(signs);
        int quantity = appUser.getQuantity();
        assertEquals(3, quantity);
    }
    @Test
    void testGetRatioWin_WithZeroWin() {
        appUser.setWin(0);
        appUser.setLose(5);
        float ratioWin = appUser.getRatioWin();
        assertEquals(0, ratioWin);
    }
    @Test

18

    void testGetRatioWin_WithNonZeroWin() {
        appUser.setWin(5);
        appUser.setLose(5);
        float ratioWin = appUser.getRatioWin();
        assertEquals(50, ratioWin);
    }
}