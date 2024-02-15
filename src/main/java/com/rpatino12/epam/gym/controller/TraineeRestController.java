package com.rpatino12.epam.gym.controller;

import com.rpatino12.epam.gym.dto.UserLogin;
import com.rpatino12.epam.gym.model.Trainee;
import com.rpatino12.epam.gym.model.User;
import com.rpatino12.epam.gym.service.TraineeService;
import com.rpatino12.epam.gym.util.DateUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Date;
import java.util.List;

@RestController
@RequestMapping("/api/trainees")
@Tag(name = "Trainee Controller", description = "Operations for creating, updating, retrieving and deleting trainees")
@Slf4j
public class TraineeRestController {
    private final TraineeService traineeService;
    private final DateUtils dateUtils;

    public TraineeRestController(TraineeService traineeService, DateUtils dateUtils) {
        this.traineeService = traineeService;
        this.dateUtils = dateUtils;
    }

    @GetMapping
    @Operation(summary = "View all trainees")
    public ResponseEntity<List<Trainee>> getAll(){
        log.info("Received GET request to /api/trainees");

        return new ResponseEntity<>(traineeService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{username}")
    @Operation(summary = "Retrieve specific trainee with the supplied trainee username")
    public ResponseEntity<Trainee> getTraineeByUsername(@PathVariable("username") String username){
        log.info("Received GET request to /api/trainees/{username} with parameter: {}", username);

        return traineeService.getByUsername(username)
                .map(trainee -> new ResponseEntity<>(trainee, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/save")
    @Operation(summary = "Create a new trainee")
    public ResponseEntity<UserLogin> createTrainee(
            @RequestHeader(name = "firstName") String firstName,
            @RequestHeader(name = "lastName") String lastName,
            @RequestHeader(name = "birthdate", required = false) String dateString,
            @RequestHeader(name = "address", required = false) String address){
        log.info("Received POST request to /api/trainees/save");

        User newUser = new User();
        newUser.setFirstName(firstName);
        newUser.setLastName(lastName);

        Trainee newTrainee = new Trainee();
        newTrainee.setUser(newUser);
        if (dateString != null){
            Date birthdate = new Date(dateUtils.createDateFromDateString(dateString).getTime());
            newTrainee.setDateOfBirth(birthdate);
        }
        if (address != null){
            newTrainee.setAddress(address);
        }

        return new ResponseEntity<>(traineeService.save(newTrainee), HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{username}")
    @Operation(summary = "Delete specific trainee with the supplied trainee username")
    public ResponseEntity deleteTraineeByUsername(@PathVariable(name = "username") String username){
        log.info("Received DELETE request to /api/trainees/delete/{username} with parameter: {}", username);

        if (traineeService.deleteByUsername(username)){
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/update")
    @Operation(summary = "Update trainee information")
    public ResponseEntity<Trainee> updateTrainee(
            @RequestHeader(name = "username") String username,
            @RequestHeader(name = "firstName") String firstName,
            @RequestHeader(name = "lastName") String lastName,
            @RequestHeader(name = "isActive") boolean isActive,
            @RequestHeader(name = "birthdate", required = false) String dateString,
            @RequestHeader(name = "address", required = false) String address){
        log.info("Received PUT request to /api/trainees/update");

        if (traineeService.getByUsername(username).isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        User updatedUser = new User();
        updatedUser.setFirstName(firstName);
        updatedUser.setLastName(lastName);
        updatedUser.setIsActive(isActive);

        Trainee updatedTrainee = new Trainee();
        updatedTrainee.setUser(updatedUser);

        if (dateString != null){
            Date birthdate = new Date(dateUtils.createDateFromDateString(dateString).getTime());
            updatedTrainee.setDateOfBirth(birthdate);
        }
        if (address != null){
            updatedTrainee.setAddress(address);
        }

        return new ResponseEntity<>(traineeService.update(updatedTrainee, username), HttpStatus.ACCEPTED);
    }

    @PutMapping("/update-password")
    @Operation(summary = "Update trainee password")
    public ResponseEntity<String> updatePassword(
            @RequestHeader(name = "username") String username,
            @RequestHeader(name = "password") String password,
            @RequestHeader(name = "newPassword") String newPassword){
        log.info("Received PUT request to /api/trainees/update-password");

        String updateStatus = traineeService.updatePassword(username, password, newPassword);
        if (updateStatus.equals("Password updated")){
            return new ResponseEntity<>(updateStatus, HttpStatus.ACCEPTED);
        } else if (updateStatus.equals("Wrong username or password")) {
            return new ResponseEntity<>(updateStatus, HttpStatus.UNAUTHORIZED);
        } else {
            return new ResponseEntity<>(updateStatus, HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("/activate")
    @Operation(summary = "Activate/Deactivate trainee")
    public ResponseEntity<String> updateStatus(
            @RequestHeader(name = "username") String username,
            @RequestHeader(name = "password") String password) {
        log.info("Received PATCH request to /api/trainees/activate");

        String activate = traineeService.updateActiveStatus(username, password);
        if (activate.equals("Wrong username or password")){
            return new ResponseEntity<>(activate, HttpStatus.UNAUTHORIZED);
        } else {
            return new ResponseEntity<>(activate, HttpStatus.ACCEPTED);
        }
    }
}
