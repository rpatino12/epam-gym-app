package com.rpatino12.epam.gym.controller;

import com.rpatino12.epam.gym.dto.UserLogin;
import com.rpatino12.epam.gym.model.Trainer;
import com.rpatino12.epam.gym.model.TrainingType;
import com.rpatino12.epam.gym.model.User;
import com.rpatino12.epam.gym.service.TrainerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/trainers")
@Tag(name = "Trainer Controller", description = "Operations for creating, updating and retrieving trainers")
@Slf4j
public class TrainerRestController {
    private final TrainerService trainerService;

    public TrainerRestController(TrainerService trainerService) {
        this.trainerService = trainerService;
    }

    // Select trainers method (GET)
    @GetMapping
    @Operation(summary = "View all trainers")
    public ResponseEntity<List<Trainer>> getAll(){
        log.info("Received GET request to /api/trainers");

        return new ResponseEntity<>(trainerService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{username}")
    @Operation(summary = "Retrieve specific trainer with the supplied trainer username")
    public ResponseEntity<Trainer> getTrainerByUsername(@PathVariable("username") String username){
        log.info("Received GET request to /api/trainers/{username} with parameter: {}", username);

        return trainerService.getByUsername(username)
                .map(trainer -> new ResponseEntity<>(trainer, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Create trainer method (POST)
    @PostMapping("/save")
    @Operation(summary = "Create a new trainer")
    public ResponseEntity<UserLogin> createTrainer(
            @RequestHeader(name = "firstName") String firstName,
            @RequestHeader(name = "lastName") String lastName,
            @RequestHeader(name = "specializationId") long specializationId){
        log.info("Received POST request to /api/trainers/save");

        if (specializationId < 1 || specializationId > 5) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        User newUser = new User();
        newUser.setFirstName(firstName);
        newUser.setLastName(lastName);

        TrainingType trainingType = new TrainingType();
        trainingType.setTrainingTypeId(specializationId);

        Trainer newTrainer = new Trainer();
        newTrainer.setUser(newUser);
        newTrainer.setSpecialization(trainingType);

        return new ResponseEntity<>(trainerService.save(newTrainer), HttpStatus.CREATED);
    }

    // Update trainer method (POST)
    @PutMapping("/update")
    @Operation(summary = "Update trainer information")
    public ResponseEntity<Trainer> updateTrainer(
            @RequestHeader(name = "username") String username,
            @RequestHeader(name = "firstName") String firstName,
            @RequestHeader(name = "lastName") String lastName,
            @RequestHeader(name = "isActive") boolean isActive,
            @RequestHeader(name = "specializationId") Long specializationId){
        log.info("Received PUT request to /api/trainers/update");

        if (trainerService.getByUsername(username).isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if (specializationId < 1 || specializationId > 5) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        User updatedUser = new User();
        updatedUser.setFirstName(firstName);
        updatedUser.setLastName(lastName);
        updatedUser.setIsActive(isActive);

        TrainingType trainingType = new TrainingType();
        trainingType.setTrainingTypeId(specializationId);

        Trainer updatedTrainer = new Trainer();
        updatedTrainer.setUser(updatedUser);
        updatedTrainer.setSpecialization(trainingType);

        return new ResponseEntity<>(trainerService.update(updatedTrainer, username), HttpStatus.ACCEPTED);
    }

    @PutMapping("/update-password")
    @Operation(summary = "Update trainer password")
    public ResponseEntity<String> updatePassword(
            @RequestHeader(name = "username") String username,
            @RequestHeader(name = "password") String password,
            @RequestHeader(name = "newPassword") String newPassword){
        log.info("Received PUT request to /api/trainers/update-password");

        String updatedStatus = trainerService.updatePassword(username, password, newPassword);
        if (updatedStatus.equals("Password updated")){
            return new ResponseEntity<>(updatedStatus, HttpStatus.ACCEPTED);
        } else if (updatedStatus.equals("Wrong username or password")) {
            return new ResponseEntity<>(updatedStatus, HttpStatus.UNAUTHORIZED);
        } else {
            return new ResponseEntity<>(updatedStatus, HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("/activate")
    @Operation(summary = "Activate/Deactivate trainer")
    public ResponseEntity<String> updateStatus(
            @RequestHeader(name = "username") String username,
            @RequestHeader(name = "password") String password) {
        log.info("Received PATCH request to /api/trainers/activate");

        String activate = trainerService.updateActiveStatus(username, password);
        if (activate.equals("Wrong username or password")){
            return new ResponseEntity<>(activate, HttpStatus.UNAUTHORIZED);
        } else {
            return new ResponseEntity<>(activate, HttpStatus.ACCEPTED);
        }
    }
}
