package com.rpatino12.epam.gym.controller;

import com.rpatino12.epam.gym.model.Trainee;
import com.rpatino12.epam.gym.service.TraineeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/trainees")
public class TraineeRestController {
    private final TraineeService traineeService;

    public TraineeRestController(TraineeService traineeService) {
        this.traineeService = traineeService;
    }

    @GetMapping
    public ResponseEntity<List<Trainee>> getAll(){
        return new ResponseEntity<>(traineeService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Trainee> getTrainee(@PathVariable("id") long traineeId){
        return traineeService.select(traineeId)
                .map(trainee -> new ResponseEntity<>(trainee, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<Trainee> getTraineeByUsername(@PathVariable("username") String username){
        return traineeService.getByUsername(username)
                .map(trainee -> new ResponseEntity<>(trainee, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/save")
    public ResponseEntity<Trainee> createTrainee(@RequestBody Trainee trainee){
        return new ResponseEntity<>(traineeService.save(trainee), HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteTrainee(@PathVariable("id") long traineeId){
        if (traineeService.delete(traineeId)){
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<Trainee> updateTrainee(@RequestBody Trainee newTrainee, @PathVariable("id") long traineeId){
        return new ResponseEntity<>(traineeService.update(newTrainee, traineeId), HttpStatus.ACCEPTED);
    }
}
