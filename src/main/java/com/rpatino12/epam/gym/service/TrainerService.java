package com.rpatino12.epam.gym.service;

import com.rpatino12.epam.gym.dao.TrainerDAO;
import com.rpatino12.epam.gym.dao.UserDAO;
import com.rpatino12.epam.gym.model.Trainer;
import com.rpatino12.epam.gym.model.User;
import jakarta.transaction.Transactional;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TrainerService {

    private final TrainerDAO trainerDAO;
    private final UserDAO userDAO;
    private static final Log LOGGER = LogFactory.getLog(TrainerService.class);

    public TrainerService(TrainerDAO trainerDAO, UserDAO userDAO) {
        this.trainerDAO = trainerDAO;
        this.userDAO = userDAO;
    }

    // Trainer Service class should support possibility to create/update/select Trainer profile.
    @Transactional
    public Trainer save(Trainer newTrainer){
//        User newUser = userDAO.save(newTrainer.getUser());
//        newTrainer.setUserId(newUser.getId());
        return trainerDAO.save(newTrainer);
    }

    public Trainer update(Trainer newTrainer, Long trainerId){
//        userDAO.findById(newTrainer.getUserId())
//                .map(
//                        user -> {
//                            user.setFirstName(newTrainer.getUser().getFirstName());
//                            user.setLastName(newTrainer.getUser().getLastName());
//                            user.setUsername(newTrainer.getUser().getUsername());
//                            user.setPassword(newTrainer.getUser().getPassword());
//                            user.setIsActive(newTrainer.getUser().getIsActive());
//                            return userDAO.save(user);
//                        }
//                );
        return trainerDAO.findById(trainerId)
                .map(
                        trainer -> {
                            trainer.setSpecialization(newTrainer.getSpecialization());
                            return trainerDAO.save(trainer);
                        }
                ).get();
    }

    public Optional<Trainer> select(Long trainerId){
        return trainerDAO.findById(trainerId);
    }
}
