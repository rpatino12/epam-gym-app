package com.rpatino12.epam.gym.controller;

import com.rpatino12.epam.gym.model.Training;
import com.rpatino12.epam.gym.service.TrainingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/trainings")
public class TrainingRestController {
    private final TrainingService trainingService;

    public TrainingRestController(TrainingService trainingService) {
        this.trainingService = trainingService;
    }

    // Select training method (GET)
    @GetMapping
    public ResponseEntity<List<Training>> getAll(){
        return new ResponseEntity<>(trainingService.getAll(), HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Training> getTraining(@PathVariable("id") long trainingId){
        return trainingService.select(trainingId)
                .map(training -> new ResponseEntity<>(training, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/trainee-username/{username}")
    public ResponseEntity<List<Training>> getByTrainee(@PathVariable(name = "username") String username){
        List<Training> trainings = trainingService.getByTraineeUsername(username).orElse(new ArrayList<>());
        return trainings.isEmpty() ?
                new ResponseEntity<>(HttpStatus.NOT_FOUND) :
                new ResponseEntity<>(trainings, HttpStatus.OK);
    }

    @GetMapping("/trainer-username/{username}")
    public ResponseEntity<List<Training>> getByTrainer(@PathVariable(name = "username") String username){
        List<Training> trainings = trainingService.getByTrainerUsername(username).orElse(new ArrayList<>());
        return trainings.isEmpty() ?
                new ResponseEntity<>(HttpStatus.NOT_FOUND) :
                new ResponseEntity<>(trainings, HttpStatus.OK);
    }

    // Create training method (POST)
    @PostMapping("/save")
    public ResponseEntity<Training> createTraining(@RequestBody Training training){
        return new ResponseEntity<>(trainingService.save(training), HttpStatus.CREATED);
    }
}
