package com.rpatino12.epam.gym.service;

import com.rpatino12.epam.gym.dao.TrainingDAO;
import com.rpatino12.epam.gym.model.Training;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TrainingService {

    private final TrainingDAO trainingDAO;
    private static final Log LOGGER = LogFactory.getLog(TrainingService.class);

    public TrainingService(TrainingDAO trainingDAO) {
        this.trainingDAO = trainingDAO;
    }

    // Training Service class should support possibility to create/select Training profile.
    public Training save(Training training){
        return trainingDAO.save(training);
    }

    public Optional<Training> select(Long trainingId){
        return trainingDAO.findById(trainingId);
    }
}
