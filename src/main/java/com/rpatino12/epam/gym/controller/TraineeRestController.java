package com.rpatino12.epam.gym.controller;

import com.rpatino12.epam.gym.service.TraineeService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/trainee")
public class TraineeRestController {
    private final TraineeService traineeService;

    public TraineeRestController(TraineeService traineeService) {
        this.traineeService = traineeService;
    }
}
