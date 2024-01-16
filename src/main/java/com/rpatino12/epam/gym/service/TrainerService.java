package com.rpatino12.epam.gym.service;

import com.rpatino12.epam.gym.dao.TrainerRepository;
import com.rpatino12.epam.gym.model.Trainer;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import java.util.List;
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
        if (null == newTrainer){
            throw new RuntimeException("Trainer cannot be null");
        }
        newTrainer.setUser(userService.registerUser(newTrainer.getUser()));
        LOGGER.info("Creating (persisting) trainer: " + newTrainer);
        return trainerRepository.save(newTrainer);
    }

    @Transactional
    public Trainer update(Trainer newTrainer, Long trainerId){
        userService.updateUser(newTrainer.getUser(), newTrainer.getUser().getId());
        LOGGER.info("Updating trainer: \nNewTrainer: " + newTrainer + "\nId: " + trainerId);
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
        LOGGER.info("Getting trainer");
        return trainerRepository.findById(trainerId);
    }

    @Transactional
    public List<Trainer> getAll(){
        LOGGER.info("Getting all trainers");
        return trainerRepository.findAll();
    }

    @PostConstruct
    public void init(){
        LOGGER.info("Starting TrainerService");
    }
}
