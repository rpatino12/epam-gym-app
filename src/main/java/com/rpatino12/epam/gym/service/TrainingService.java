package com.rpatino12.epam.gym.service;

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

    public TrainingService(TrainingRepository trainingRepository) {
        this.trainingRepository = trainingRepository;
    }

    // Training Service class should support possibility to create/select Training profile.
    @Transactional
    public Training save(Training training){
        log.info("Creating training: " + training);
        return trainingRepository.save(training);
    }

    @Transactional
    public Optional<Training> select(Long trainingId){
        log.info("Getting training");
        return trainingRepository.findById(trainingId);
    }

    @Transactional
    public List<Training> getAll(){
        log.info("Getting all trainings");
        return trainingRepository.findAll();
    }

    @Transactional
    public Optional<List<Training>> getByTraineeUsername(String username){
        log.info("Getting " + username + " trainings: ");
        return trainingRepository.findTrainingByTraineeUserUsername(username);
    }

    @Transactional
    public Optional<List<Training>> getByTrainerUsername(String username){
        log.info("Getting " + username + " trainings: ");
        return trainingRepository.findTrainingByTrainerUserUsername(username);
    }

    @PostConstruct
    public void init(){
        log.info("Starting TrainingService");
    }
}
