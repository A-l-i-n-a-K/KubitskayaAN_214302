package com.fitquest.controller;

import com.fitquest.controller.main.Main;
import com.fitquest.model.AppUser;
import com.fitquest.model.Sign;
import com.fitquest.model.Training;
import com.fitquest.model.enums.Role;
import com.fitquest.model.enums.SignStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Controller
@RequestMapping("/trainings")
public class TrainingCont extends Main {
    @GetMapping
    public String trainings(Model model) {
        getCurrentUserAndRole(model);
        List<Training> trainings = new ArrayList<>();
        AppUser user = getUser();
        if (user.getRole() == Role.MANAGER) {
            trainings.addAll(user.getTrainings());
        } else {
            if (user.getTrainer() != null) {
                trainings.addAll(user.getTrainer().getTrainings());
            }
            List<Sign> signs = signRepo.findAllByOwner_IdAndStatus(user.getId(), SignStatus.PASSED);

            signs.sort(Comparator.comparing(Sign::getDateAndTime));

            String[] signString = new String[signs.size() + 1];
            Float[] signFloat = new Float[signs.size() + 1];

            signString[0] = "Вес";
            signFloat[0] = user.getWeight();

            for (int i = 0; i < signs.size(); i++) {
                signString[i + 1] = signs.get(i).getTraining().getDateAndTime();
                signFloat[i + 1] = signs.get(i).getWeight();
            }

            model.addAttribute("signString", signString);
            model.addAttribute("signFloat", signFloat);
        }
        model.addAttribute("trainings", trainings);
        return "trainings";
    }

    @GetMapping("/search")
    public String search(Model model, @RequestParam(defaultValue = "") String date, @RequestParam(defaultValue = "true") boolean filter) {
        getCurrentUserAndRole(model);
        model.addAttribute("date", date);
        model.addAttribute("filter", filter);
        List<Training> trainings = new ArrayList<>();
        AppUser user = getUser();

        if (user.getRole() == Role.MANAGER) {
            trainings.addAll(user.getTrainings());
            trainings.sort(Comparator.comparing(Training::getDateAndTime));
            if (!filter) {
                Collections.reverse(trainings);
            }
            trainings = trainings.stream().filter(training -> training.getDateAndTime().contains(date)).toList();
        } else {
            if (user.getTrainer() != null) {
                trainings.addAll(user.getTrainer().getTrainings());
                trainings.sort(Comparator.comparing(Training::getDateAndTime));
                if (!filter) {
                    Collections.reverse(trainings);
                }
                trainings = trainings.stream().filter(training -> training.getDateAndTime().contains(date)).toList();
            }
            List<Sign> signs = signRepo.findAllByOwner_IdAndStatus(user.getId(), SignStatus.PASSED);
            signs.sort(Comparator.comparing(Sign::getDateAndTime));


            String[] signString = new String[signs.size() + 1];
            Float[] signFloat = new Float[signs.size() + 1];

            signString[0] = "Вес";
            signFloat[0] = user.getWeight();

            for (int i = 0; i < signs.size(); i++) {
                signString[i + 1] = signs.get(i).getTraining().getDateAndTime();
                signFloat[i + 1] = signs.get(i).getWeight();
            }

            model.addAttribute("signString", signString);
            model.addAttribute("signFloat", signFloat);
        }
        model.addAttribute("trainings", trainings);
        return "trainings";
    }

    @GetMapping("/{trainingId}")
    public String training(Model model, @PathVariable Long trainingId) {
        getCurrentUserAndRole(model);
        model.addAttribute("training", trainingRepo.getReferenceById(trainingId));
        return "training";
    }

    @PostMapping("/add")
    public String add(@RequestParam String dateAndTime) {
        trainingRepo.save(new Training(dateAndTime, getUser()));
        return "redirect:/trainings";
    }

    @GetMapping("/{trainingId}/sign")
    public String sign(@PathVariable Long trainingId) {
        signRepo.save(new Sign(getUser(), trainingRepo.getReferenceById(trainingId)));
        return "redirect:/trainings";
    }

    @GetMapping("/{trainingId}/user/{userId}")
    public String user(Model model, @PathVariable Long trainingId, @PathVariable Long userId) {
        getCurrentUserAndRole(model);
        model.addAttribute("info", userRepo.getReferenceById(userId));
        return "info";
    }

    @PostMapping("/{trainingId}/signs/{signId}/passed")
    public String passed(@PathVariable Long trainingId, @PathVariable Long signId, @RequestParam float weight, @RequestParam int pulse) {
        Sign sign = signRepo.getReferenceById(signId);
        sign.setStatus(SignStatus.PASSED);
        sign.setWeight(weight);
        sign.setPulse(pulse);
        signRepo.save(sign);
        return "redirect:/trainings/{trainingId}";
    }

    @GetMapping("/{trainingId}/signs/{signId}/not")
    public String not(@PathVariable Long trainingId, @PathVariable Long signId) {
        Sign sign = signRepo.getReferenceById(signId);
        sign.setStatus(SignStatus.NOT_PASSED);
        signRepo.save(sign);
        return "redirect:/trainings/{trainingId}";
    }
}
