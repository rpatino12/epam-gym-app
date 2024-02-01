package com.rpatino12.epam.gym.controller;

import com.rpatino12.epam.gym.model.Trainee;
import com.rpatino12.epam.gym.service.TraineeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/trainees")
@Tag(name = "Trainee Controller", description = "Operations for creating, updating, retrieving and deleting trainees")
public class TraineeRestController {
    private final TraineeService traineeService;

    public TraineeRestController(TraineeService traineeService) {
        this.traineeService = traineeService;
    }

    @GetMapping
    @Operation(summary = "View all trainees")
    public ResponseEntity<List<Trainee>> getAll(){
        return new ResponseEntity<>(traineeService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Retrieve specific trainee with the supplied trainee Id")
    public ResponseEntity<Trainee> getTrainee(@PathVariable("id") long traineeId){
        return traineeService.select(traineeId)
                .map(trainee -> new ResponseEntity<>(trainee, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/username/{username}")
    @Operation(summary = "Retrieve specific trainee with the supplied trainee username")
    public ResponseEntity<Trainee> getTraineeByUsername(@PathVariable("username") String username){
        return traineeService.getByUsername(username)
                .map(trainee -> new ResponseEntity<>(trainee, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/save")
    @Operation(summary = "Create a new trainee")
    public ResponseEntity<Trainee> createTrainee(@RequestBody Trainee trainee){
        return new ResponseEntity<>(traineeService.save(trainee), HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Delete specific trainee with the supplied trainee Id")
    public ResponseEntity deleteTrainee(@PathVariable("id") long traineeId){
        if (traineeService.delete(traineeId)){
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete-by-username/{username}")
    @Operation(summary = "Delete specific trainee with the supplied trainee username")
    public ResponseEntity deleteTraineeByUsername(@PathVariable(name = "username") String username){
        if (traineeService.deleteByUsername(username)){
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/update/{id}")
    @Operation(summary = "Update trainee information")
    public ResponseEntity<Trainee> updateTrainee(@RequestBody Trainee newTrainee, @PathVariable("id") long traineeId){
        return new ResponseEntity<>(traineeService.update(newTrainee, traineeId), HttpStatus.ACCEPTED);
    }

    @PostMapping("/update-password")
    @Operation(summary = "Update trainee password")
    public ResponseEntity<String> updatePassword(
            @RequestHeader(name = "username") String username,
            @RequestHeader(name = "password") String password,
            @RequestHeader(name = "newPassword") String newPassword){
        String updateStatus = traineeService.updatePassword(username, password, newPassword);
        if (updateStatus.equals("Password updated")){
            return new ResponseEntity<>(updateStatus, HttpStatus.ACCEPTED);
        } else if (updateStatus.equals("Wrong username or password")) {
            return new ResponseEntity<>(updateStatus, HttpStatus.UNAUTHORIZED);
        } else {
            return new ResponseEntity<>(updateStatus, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/activate")
    @Operation(summary = "Activate/Deactivate trainee")
    public ResponseEntity<String> updateStatus(
            @RequestHeader(name = "username") String username,
            @RequestHeader(name = "password") String password) {
        String activate = traineeService.updateActiveStatus(username, password);
        if (activate.equals("Wrong username or password")){
            return new ResponseEntity<>(activate, HttpStatus.UNAUTHORIZED);
        } else {
            return new ResponseEntity<>(activate, HttpStatus.ACCEPTED);
        }
    }
}
