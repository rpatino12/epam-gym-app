package com.rpatino12.epam.gym.facade;

import com.rpatino12.epam.gym.service.TraineeService;
import com.rpatino12.epam.gym.service.TrainerService;
import com.rpatino12.epam.gym.service.TrainingService;
import org.springframework.stereotype.Service;

@Service
public class GymFacade {
    private final TraineeService traineeService;
    private final TrainerService trainerService;
    private final TrainingService trainingService;

    public GymFacade(TraineeService traineeService, TrainerService trainerService, TrainingService trainingService) {
        this.traineeService = traineeService;
        this.trainerService = trainerService;
        this.trainingService = trainingService;
    }
}
