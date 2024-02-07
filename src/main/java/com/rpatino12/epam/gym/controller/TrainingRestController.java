package com.rpatino12.epam.gym.controller;

import com.rpatino12.epam.gym.dto.TrainingTypeDto;
import com.rpatino12.epam.gym.model.Training;
import com.rpatino12.epam.gym.model.TrainingType;
import com.rpatino12.epam.gym.service.TrainingService;
import com.rpatino12.epam.gym.service.TrainingTypeService;
import com.rpatino12.epam.gym.util.DateUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/trainings")
@Tag(name = "Training Controller", description = "Operations for creating and retrieving trainings")
@Slf4j
public class TrainingRestController {
    private final TrainingService trainingService;
    private final TrainingTypeService trainingTypeService;
    private final DateUtils dateUtils;

    public TrainingRestController(TrainingService trainingService, TrainingTypeService trainingTypeService, DateUtils dateUtils) {
        this.trainingService = trainingService;
        this.trainingTypeService = trainingTypeService;
        this.dateUtils = dateUtils;
    }

    // Select training method (GET)
    @GetMapping
    @Operation(summary = "View all trainings")
    public ResponseEntity<List<Training>> getAll(){
        log.info("Received GET request to /api/trainings");

        return new ResponseEntity<>(trainingService.getAll(), HttpStatus.OK);
    }
    @GetMapping("/{id}")
    @Operation(summary = "Retrieve specific training with the supplied training Id")
    public ResponseEntity<Training> getTraining(@PathVariable("id") long trainingId){
        log.info("Received GET request to /api/trainings/{id} with parameter: {}", trainingId);

        return trainingService.select(trainingId)
                .map(training -> new ResponseEntity<>(training, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/trainee-username/{username}")
    @Operation(summary = "Retrieve specific training with the supplied trainee username")
    public ResponseEntity<List<Training>> getByTrainee(@PathVariable(name = "username") String username){
        log.info("Received GET request to /api/trainings/trainee-username/{username} with parameter: {}", username);

        List<Training> trainings = trainingService.getByTraineeUsername(username).orElse(new ArrayList<>());
        return trainings.isEmpty() ?
                new ResponseEntity<>(HttpStatus.NOT_FOUND) :
                new ResponseEntity<>(trainings, HttpStatus.OK);
    }

    @GetMapping("/trainer-username/{username}")
    @Operation(summary = "Retrieve specific training with the supplied trainer username")
    public ResponseEntity<List<Training>> getByTrainer(@PathVariable(name = "username") String username){
        log.info("Received GET request to /api/trainings/trainer-username/{username} with parameter: {}", username);

        List<Training> trainings = trainingService.getByTrainerUsername(username).orElse(new ArrayList<>());
        return trainings.isEmpty() ?
                new ResponseEntity<>(HttpStatus.NOT_FOUND) :
                new ResponseEntity<>(trainings, HttpStatus.OK);
    }

    // Create training method (POST)
    @PostMapping("/save")
    @Operation(summary = "Create a new training")
    public ResponseEntity<String> createTraining(
            @RequestHeader(name = "traineeUsername") String traineeUsername,
            @RequestHeader(name = "trainerUsername") String trainerUsername,
            @RequestHeader(name = "trainingName") String trainingName,
            @RequestHeader(name = "trainingDuration") double duration,
            @RequestHeader(name = "trainingTypeId") long trainingTypeId,
            @RequestHeader(name = "trainingDate", required = false) String dateString){
        log.info("Received POST request to /api/trainings/save");

        if (trainingTypeId < 1 || trainingTypeId > 5) {
            return new ResponseEntity<>("Enter a valid Training Type Id", HttpStatus.BAD_REQUEST);
        }

        Date date = new Date(dateUtils.createDateFromDateString(dateString).getTime());
        TrainingType trainingType = new TrainingType();
        trainingType.setTrainingTypeId(trainingTypeId);

        Training training = new Training();
        training.setTrainingName(trainingName);
        training.setTrainingDate(date);
        training.setTrainingDuration(duration);
        training.setTrainingType(trainingType);

        boolean isTrainingSaved = trainingService.save(training, traineeUsername, trainerUsername);

        if (isTrainingSaved){
            return new ResponseEntity<>("Training created", HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("Trainee/Trainer not found", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/training-types")
    @Operation(summary = "View all training types")
    public ResponseEntity<List<TrainingTypeDto>> getTrainingTypes(){
        log.info("Received GET request to /api/trainings/training-types");

        return new ResponseEntity<>(trainingTypeService.getAll(), HttpStatus.OK);
    }
}
