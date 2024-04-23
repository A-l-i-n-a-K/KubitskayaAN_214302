package com.fitquest.controller;

import com.fitquest.controller.main.Main;
import com.fitquest.model.AppUser;
import com.fitquest.model.enums.Gender;
import com.fitquest.model.enums.Role;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Controller
@RequestMapping("/stats")
public class StatsCont extends Main {
    @GetMapping
    public String stats(Model model) {
        getCurrentUserAndRole(model);

        Gender[] genders = Gender.values();

        String[] genderString = new String[genders.length];
        int[] genderInt = new int[genders.length];

        for (int i = 0; i < genders.length; i++) {
            genderString[i] = genders[i].getName();
            genderInt[i] = userRepo.findAllByGenderAndRole(genders[i], Role.USER).size();
        }

        model.addAttribute("genderString", genderString);
        model.addAttribute("genderInt", genderInt);

        List<AppUser> users = userRepo.findAllByRole(Role.USER);

        int medExp;
        float medRatio;
        float medWin;

        if (users.isEmpty()) {
            medExp = 0;
            medRatio = 0;
            medWin = 0;
        } else {
            medExp = users.stream().reduce(0, (i, user) -> i + user.getExperience(), Integer::sum) / users.size();
            medRatio = round(users.stream().reduce(0f, (i, user) -> i + user.getRatio(), Float::sum) / users.size());
            medWin = round(users.stream().reduce(0f, (i, user) -> i + user.getRatioWin(), Float::sum) / users.size());
        }

        model.addAttribute("medExp", medExp);
        model.addAttribute("medExpMin", users.stream().reduce(0, (i, user) -> {
            if (user.getExperience() < medExp) return i + 1;
            return i;
        }, Integer::sum));
        model.addAttribute("medExpMax", users.stream().reduce(0, (i, user) -> {
            if (user.getExperience() >= medExp) return i + 1;
            return i;
        }, Integer::sum));


        model.addAttribute("medRatio", medRatio);
        model.addAttribute("medRatioMin", users.stream().reduce(0, (i, user) -> {
            if (user.getRatio() < medRatio) return i + 1;
            return i;
        }, Integer::sum));
        model.addAttribute("medRatioMax", users.stream().reduce(0, (i, user) -> {
            if (user.getRatio() >= medRatio) return i + 1;
            return i;
        }, Integer::sum));


        model.addAttribute("medWin", medWin);
        model.addAttribute("medWinMin", users.stream().reduce(0, (i, user) -> {
            if (user.getRatioWin() < medWin) return i + 1;
            return i;
        }, Integer::sum));
        model.addAttribute("medWinMax", users.stream().reduce(0, (i, user) -> {
            if (user.getRatioWin() >= medWin) return i + 1;
            return i;
        }, Integer::sum));

        users.sort(Comparator.comparing(AppUser::getRatioWin));
        Collections.reverse(users);

        String[] expString = new String[5];
        float[] expFloat = new float[5];

        for (int i = 0; i < users.size(); i++) {
            if (i == 5) break;
            expString[i] = users.get(i).getFio();
            expFloat[i] = users.get(i).getRatioWin();
        }

        model.addAttribute("expString", expString);
        model.addAttribute("expFloat", expFloat);

        return "stats";
    }
}
