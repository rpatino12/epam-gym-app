package com.rpatino12.epam.gym.service;

import com.rpatino12.epam.gym.dao.TrainingRepository;
import com.rpatino12.epam.gym.model.Training;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TrainingService {

    private final TrainingRepository trainingRepository;
    private static final Log LOGGER = LogFactory.getLog(TrainingService.class);

    public TrainingService(TrainingRepository trainingRepository) {
        this.trainingRepository = trainingRepository;
    }

    // Training Service class should support possibility to create/select Training profile.
    @Transactional
    public Training save(Training training){
        LOGGER.info("Creating (persisting) training: " + training);
        return trainingRepository.save(training);
    }

    @Transactional
    public Optional<Training> select(Long trainingId){
        LOGGER.info("Getting training");
        return trainingRepository.findById(trainingId);
    }

    @PostConstruct
    public void init(){
        LOGGER.info("Starting TrainingService");
    }
}
