package com.fitquest.controller;

import com.fitquest.controller.main.Main;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
public class LoginCont extends Main {
    @GetMapping
    public String login(Model model) {
        getCurrentUserAndRole(model);
        model.addAttribute("login", true);
        return "login";
    }
}
