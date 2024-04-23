package com.fitquest.controller;

import com.fitquest.controller.main.Main;
import com.fitquest.model.AppUser;
import com.fitquest.model.enums.Role;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users")
public class UserCont extends Main {

    @GetMapping
    public String users(Model model) {
        getCurrentUserAndRole(model);
        model.addAttribute("users", userRepo.findAll());
        model.addAttribute("roles", Role.values());
        return "users";
    }

    @PostMapping("/{id}/edit")
    public String edit(@PathVariable Long id, @RequestParam Role role) {
        AppUser user = userRepo.getReferenceById(id);
        user.setRole(role);
        userRepo.save(user);
        return "redirect:/users";
    }

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        userRepo.deleteById(id);
        return "redirect:/users";
    }

    @GetMapping("/{id}/app")
    public String app(@PathVariable Long id) {
        AppUser user = userRepo.getReferenceById(id);
        user.setFile("");
        user.setRole(Role.MANAGER);
        userRepo.save(user);
        return "redirect:/users";
    }

    @GetMapping("/{id}/enabled")
    public String enabled(@PathVariable Long id) {
        AppUser user = userRepo.getReferenceById(id);
        user.setEnabled(true);
        userRepo.save(user);
        return "redirect:/users";
    }
}