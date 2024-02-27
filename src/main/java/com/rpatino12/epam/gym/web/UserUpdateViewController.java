package com.rpatino12.epam.gym.web;

import com.rpatino12.epam.gym.model.Trainee;
import com.rpatino12.epam.gym.model.User;
import com.rpatino12.epam.gym.service.TraineeService;
import com.rpatino12.epam.gym.service.TrainerService;
import com.rpatino12.epam.gym.util.DateUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Controller
@RequestMapping("/update-users")
public class UserUpdateViewController {

    private final TraineeService traineeService;
    private final TrainerService trainerService;
    private final DateUtils dateUtils;

    public UserUpdateViewController(TraineeService traineeService, TrainerService trainerService, DateUtils dateUtils) {
        this.traineeService = traineeService;
        this.trainerService = trainerService;
        this.dateUtils = dateUtils;
    }

    @GetMapping("/trainee-form")
    public String getUpdateTraineeProfile(Model model){
        String username = getAuthenticatedUser();
        Optional<Trainee> trainee = this.traineeService.getByUsername(username);
        if (trainee.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Trainee not found");
        }
        User user = trainee.get().getUser();
        model.addAttribute("trainee", trainee.get());
        model.addAttribute("traineeUser", user);
        model.addAttribute("module", "trainees");
        return "update-trainee";
    }

    private String getAuthenticatedUser() {
        // Get the authentication object from the SecurityContextHolder
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Extract the username of the authenticated user
        return authentication.getName();
    }
}
