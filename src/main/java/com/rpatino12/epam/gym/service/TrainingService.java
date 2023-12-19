package com.rpatino12.epam.gym.service;

import com.rpatino12.epam.gym.dao.TrainingDAO;
import com.rpatino12.epam.gym.model.Training;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TrainingService {
    @Autowired
    private TrainingDAO trainingDAO;
    private static final Log LOG = LogFactory.getLog(TrainingService.class);

    // Training Service class should support possibility to create/select Training profile.
    public Training save(Training training){
        return trainingDAO.save(training);
    }

    public Optional<Training> select(Long trainingId){
        return trainingDAO.findById(trainingId);
    }
}
