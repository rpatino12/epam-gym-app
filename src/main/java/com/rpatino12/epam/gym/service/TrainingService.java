package com.rpatino12.epam.gym.service;

import com.rpatino12.epam.gym.exception.ResourceNotFoundException;
import com.rpatino12.epam.gym.exception.TrainingNullException;
import com.rpatino12.epam.gym.model.Trainee;
import com.rpatino12.epam.gym.model.Trainer;
import com.rpatino12.epam.gym.repo.TrainingRepository;
import com.rpatino12.epam.gym.model.Training;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class TrainingService {

    private final TrainingRepository trainingRepository;
    private final TraineeService traineeService;
    private final TrainerService trainerService;

    public TrainingService(TrainingRepository trainingRepository, TraineeService traineeService, TrainerService trainerService) {
        this.trainingRepository = trainingRepository;
        this.traineeService = traineeService;
        this.trainerService = trainerService;
    }

    // Training Service class should support possibility to create/select Training profile.
    @Transactional
    public boolean save(Training newTraining, String traineeUsername, String trainerUsername){
        if (null == newTraining){
            log.error("Cannot save a null or empty entity");
            throw new TrainingNullException("Training cannot be null");
        }
        Optional<Trainee> trainee = traineeService.getByUsername(traineeUsername);
        Optional<Trainer> trainer = trainerService.getByUsername(trainerUsername);
        if (trainee.isPresent() && trainer.isPresent()){
            newTraining.setTrainee(trainee.get());
            newTraining.setTrainer(trainer.get());
            Training training = trainingRepository.save(newTraining);
            log.info("Creating training: " + training);
            return true;
        } else {
            log.info("Trainee/Trainer not found");
            return false;
        }
    }

    @Transactional
    public List<Training> getAll(){
        log.info("Getting all trainings");
        List<Training> trainings = trainingRepository.findAll();
        if (trainings.isEmpty()){
            log.error("There are no trainings registered");
            throw new ResourceNotFoundException("Training");
        }
        return trainings;
    }

    @Transactional
    public Optional<List<Training>> getByTraineeUsername(String username){
        log.info("Getting trainee " + username + " trainings: ");
        return trainingRepository.findTrainingByTraineeUserUsername(username);
    }

    @Transactional
    public Optional<List<Training>> getByTrainerUsername(String username){
        log.info("Getting trainer " + username + " trainings: ");
        return trainingRepository.findTrainingByTrainerUserUsername(username);
    }

    @PostConstruct
    public void init(){
        log.info("Starting TrainingService");
    }
}
