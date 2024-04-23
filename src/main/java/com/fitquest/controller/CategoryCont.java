package com.fitquest.controller;

import com.fitquest.controller.main.Main;
import com.fitquest.model.AppUser;
import com.fitquest.model.Category;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/category")
public class CategoryCont extends Main {
    @GetMapping
    public String category(Model model) {
        getCurrentUserAndRole(model);
        model.addAttribute("categories", categoryRepo.findAll());
        return "category";
    }

    @PostMapping("/add")
    public String add(@RequestParam String name, @RequestParam int quantity) {
        categoryRepo.save(new Category(name, quantity));
        return "redirect:/category";
    }

    @PostMapping("/{id}/edit")
    public String edit(@RequestParam String name, @RequestParam int quantity, @PathVariable Long id) {
        Category category = categoryRepo.getReferenceById(id);
        category.set(name, quantity);
        categoryRepo.save(category);
        return "redirect:/category";
    }

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        Category category = categoryRepo.getReferenceById(id);
        for (AppUser i : category.getUsers()) {
            i.setCategory(null);
        }
        categoryRepo.deleteById(id);
        return "redirect:/category";
    }
}