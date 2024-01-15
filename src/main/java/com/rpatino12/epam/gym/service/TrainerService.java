package com.rpatino12.epam.gym.service;

import com.rpatino12.epam.gym.dao.TrainerRepository;
import com.rpatino12.epam.gym.model.Trainer;
import jakarta.transaction.Transactional;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TrainerService {

    private final TrainerRepository trainerRepository;
    private final UserService userService;
    private static final Log LOGGER = LogFactory.getLog(TrainerService.class);

    public TrainerService(TrainerRepository trainerRepository, UserService userService) {
        this.trainerRepository = trainerRepository;
        this.userService = userService;
    }

    // Trainer Service class should support possibility to create/update/select Trainer profile.
    @Transactional
    public Trainer save(Trainer newTrainer){
        newTrainer.setUser(userService.registerUser(newTrainer.getUser()));
        return trainerRepository.save(newTrainer);
    }

    @Transactional
    public Trainer update(Trainer newTrainer, Long trainerId){
        userService.updateUser(newTrainer.getUser(), newTrainer.getUser().getId());
        return trainerRepository.findById(trainerId)
                .map(
                        trainer -> {
                            trainer.setSpecialization(newTrainer.getSpecialization());
                            return trainerRepository.save(trainer);
                        }
                ).get();
    }

    @Transactional
    public Optional<Trainer> select(Long trainerId){
        return trainerRepository.findById(trainerId);
    }
}
