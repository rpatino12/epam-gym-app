package com.rpatino12.epam.gym.web;

import com.rpatino12.epam.gym.model.Trainee;
import com.rpatino12.epam.gym.model.Trainer;
import com.rpatino12.epam.gym.model.User;
import com.rpatino12.epam.gym.repo.TraineeRepository;
import com.rpatino12.epam.gym.repo.TrainerRepository;
import com.rpatino12.epam.gym.service.TraineeService;
import com.rpatino12.epam.gym.service.TrainerService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Controller
@RequestMapping("/users")
public class UserProfileViewController {

    private final TraineeService traineeService;
    private final TrainerService trainerService;
    private final TraineeRepository traineeRepository;
    private final TrainerRepository trainerRepository;

    public UserProfileViewController(TraineeService traineeService, TrainerService trainerService, TraineeRepository traineeRepository, TrainerRepository trainerRepository) {
        this.traineeService = traineeService;
        this.trainerService = trainerService;
        this.traineeRepository = traineeRepository;
        this.trainerRepository = trainerRepository;
    }

    @GetMapping("/trainee/{username}")
    public String getTraineeProfile(@PathVariable("username") String username, Model model){
        Optional<Trainee> trainee = this.traineeService.getByUsername(username);
        if (trainee.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Trainee not found");
        }
        User user = trainee.get().getUser();
        model.addAttribute("trainee", trainee.get());
        model.addAttribute("traineeUser", user);
        model.addAttribute("module", "trainees");
        return "trainee-profile";
    }

    @GetMapping("/trainer/{username}")
    public String getTrainerProfile(@PathVariable("username") String username, Model model){
        Optional<Trainer> trainer = this.trainerService.getByUsername(username);
        if (trainer.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Trainer not found");
        }
        User user = trainer.get().getUser();
        model.addAttribute("trainer", trainer.get());
        model.addAttribute("trainerUser", user);
        model.addAttribute("module", "trainers");
        return "trainer-profile";
    }

    @GetMapping("/profile")
    public String getAuthenticatedUserProfile() {
        // Get the authentication object from the SecurityContextHolder
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Extract the username of the authenticated user
        String username = authentication.getName();

        if (traineeRepository.findTraineeByUserUsername(username).isPresent()){
            // Redirect to the user profile endpoint with the username as a path variable
            return "redirect:/users/trainee/" + username;
        } else if(trainerRepository.findTrainerByUserUsername(username).isPresent()){
            // Redirect to the user profile endpoint with the username as a path variable
            return "redirect:/users/trainer/" + username;
        } else {
            return "error";
        }
    }
}
