package com.rpatino12.epam.gym.service;

import com.rpatino12.epam.gym.dao.TrainerDAO;
import com.rpatino12.epam.gym.dao.UserDAO;
import com.rpatino12.epam.gym.model.Trainer;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TrainerService {
    @Autowired
    private TrainerDAO trainerDAO;
    @Autowired
    private UserDAO userDAO;
    private static final Log LOG = LogFactory.getLog(TrainerService.class);

    // Trainer Service class should support possibility to create/update/select Trainer profile.
    public Trainer save(Trainer newTrainer){
        return trainerDAO.save(newTrainer);
    }

    public Trainer update(Trainer newTrainer, Long trainerId){
        Optional<Trainer> trainer = select(trainerId);
        if (trainer.isPresent()){
            trainer.get().setSpecializationId(newTrainer.getSpecializationId());
        }
        return save(trainer.get());
    }

    public Optional<Trainer> select(Long trainerId){
        return trainerDAO.findById(trainerId);
    }
}
