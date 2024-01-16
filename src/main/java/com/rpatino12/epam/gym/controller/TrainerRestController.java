package com.rpatino12.epam.gym.controller;

import com.rpatino12.epam.gym.model.Trainer;
import com.rpatino12.epam.gym.service.TrainerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/trainers")
public class TrainerRestController {
    private final TrainerService trainerService;

    public TrainerRestController(TrainerService trainerService) {
        this.trainerService = trainerService;
    }

    // Select trainers method (GET)
    @GetMapping
    public ResponseEntity<List<Trainer>> getAll(){
        return new ResponseEntity<>(trainerService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Trainer> getTrainer(@PathVariable("id") long trainerId){
        return trainerService.select(trainerId)
                .map(trainer -> new ResponseEntity<>(trainer, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Create trainer method (POST)
    @PostMapping("/save")
    public ResponseEntity<Trainer> createTrainer(@RequestBody Trainer trainer){
        return new ResponseEntity<>(trainerService.save(trainer), HttpStatus.CREATED);
    }

    // Update trainer method (POST)
    @PostMapping("/update/{id}")
    public ResponseEntity<Trainer> updateTrainer(@RequestBody Trainer newTrainer, @PathVariable("id") long trainerId){
        return new ResponseEntity<>(trainerService.update(newTrainer, trainerId), HttpStatus.ACCEPTED);
    }
}
