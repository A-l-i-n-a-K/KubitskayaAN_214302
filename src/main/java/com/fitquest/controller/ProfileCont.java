package com.fitquest.controller;

import com.fitquest.controller.main.Main;
import com.fitquest.model.AppUser;
import com.fitquest.model.Category;
import com.fitquest.model.enums.Gender;
import com.fitquest.model.enums.Role;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

@Controller
@RequestMapping("/profile")
public class ProfileCont extends Main {
    @GetMapping
    public String profile(Model model) {
        if (getUser().getRole() == Role.ADMIN) {
            return "redirect:/users";
        }
        getCurrentUserAndRole(model);
        model.addAttribute("genders", Gender.values());
        model.addAttribute("categories", categoryRepo.findAll());
        if (getUser().getCategory() != null) {
            model.addAttribute("trainers", userRepo.findAllByRoleAndCategory_Id(Role.MANAGER, getUser().getCategory().getId()));
        }
        return "profile";
    }

    @PostMapping("/app")
    public String app(Model model, @RequestParam MultipartFile file) {
        try {
            if (file != null && !Objects.requireNonNull(file.getOriginalFilename()).isEmpty()) {
                String uuidFile = UUID.randomUUID().toString();
                File uploadDir = new File(uploadImg);
                if (!uploadDir.exists()) uploadDir.mkdir();
                String result = "user/" + uuidFile + "_" + file.getOriginalFilename();
                file.transferTo(new File(uploadImg + "/" + result));

                AppUser user = getUser();
                user.setFile(result);
                userRepo.save(user);
            }
        } catch (IOException e) {
            model.addAttribute("message", "Некорректные данные!");
            getCurrentUserAndRole(model);
            return "profile";
        }

        model.addAttribute("message", "Ваша заявка на рассмотрении, ожидайте");
        getCurrentUserAndRole(model);
        return "profile";
    }

    @PostMapping("/photo")
    public String photo(Model model, @RequestParam MultipartFile photo) {
        try {
            if (photo != null && !Objects.requireNonNull(photo.getOriginalFilename()).isEmpty()) {
                String uuidFile = UUID.randomUUID().toString();
                File uploadDir = new File(uploadImg);
                if (!uploadDir.exists()) uploadDir.mkdir();
                String result = "user/" + uuidFile + "_" + photo.getOriginalFilename();
                photo.transferTo(new File(uploadImg + "/" + result));

                AppUser user = getUser();
                user.setPhoto(result);
                userRepo.save(user);
            }
        } catch (IOException e) {
            model.addAttribute("message", "Некорректные данные!");
            getCurrentUserAndRole(model);
            return "profile";
        }

        return "redirect:/profile";
    }

    @PostMapping("/fio")
    public String fio(@RequestParam String fio) {
        AppUser user = getUser();
        user.setFio(fio);
        userRepo.save(user);
        return "redirect:/profile";
    }

    @PostMapping("/category")
    public String category(@RequestParam Long categoryId) {
        if (categoryId == 0) {
            return "redirect:/profile";
        }
        AppUser user = getUser();
        user.setCategory(categoryRepo.getReferenceById(categoryId));
        userRepo.save(user);
        return "redirect:/profile";
    }

    @PostMapping("/trainer")
    public String trainer(@RequestParam Long trainerId) {
        if (trainerId == 0) {
            return "redirect:/profile";
        }
        AppUser user = getUser();
        user.setTrainer(userRepo.getReferenceById(trainerId));
        userRepo.save(user);
        return "redirect:/profile";
    }

    @PostMapping("/edit")
    public String edit(@RequestParam int height,@RequestParam float weight,@RequestParam Gender gender, @RequestParam String tel, @RequestParam String email, @RequestParam int age, @RequestParam int experience, @RequestParam int win, @RequestParam int lose) {
        AppUser user = getUser();
        user.setGender(gender);
        user.setExperience(experience);
        user.setAge(age);
        user.setTel(tel);
        user.setEmail(email);
        user.setWin(win);
        user.setLose(lose);
        user.setWeight(weight);
        user.setHeight(height);
        userRepo.save(user);
        return "redirect:/profile";
    }
}
