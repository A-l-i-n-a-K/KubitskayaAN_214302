package com.fitquest.controller;

import com.fitquest.controller.main.Main;
import com.fitquest.model.AppUser;
import com.fitquest.model.enums.Role;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/reg")
public class RegCont extends Main {
    @PostMapping
    public String reg(Model model, @RequestParam String username, @RequestParam String password, @RequestParam String fio) {
        if (userRepo.findByUsername(username) != null) {
            model.addAttribute("message", "Пользователь с таким логином уже существует!");
            getCurrentUserAndRole(model);
            model.addAttribute("reg", true);
            return "login";
        }

        if (userRepo.findByFio(fio) != null) {
            model.addAttribute("message", "Пользователь с таким ФИО уже существует!");
            getCurrentUserAndRole(model);
            model.addAttribute("reg", true);
            return "login";
        }

        AppUser user = new AppUser(username, password, fio);

        if (userRepo.findAll().isEmpty()) {
            user.setRole(Role.ADMIN);
            user.setEnabled(true);
        }

        userRepo.save(user);

        return "redirect:/login";
    }
}
