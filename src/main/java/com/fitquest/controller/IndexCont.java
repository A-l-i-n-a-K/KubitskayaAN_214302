package com.fitquest.controller;

import com.fitquest.controller.main.Main;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexCont extends Main {
    @GetMapping("/index")
    public String index1() {
        return "redirect:/about";
    }

    @GetMapping("/")
    public String index2() {
        return "redirect:/about";
    }
}