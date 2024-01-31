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
        LOGGER.info("Creating trainer: " + newTrainer);
        return trainerRepository.save(newTrainer);
    }

    @Transactional
    public Trainer update(Trainer newTrainer, Long trainerId){
        userService.updateUser(newTrainer.getUser(), newTrainer.getUser().getId());
        LOGGER.info("Updating trainer: Id=" + trainerId + "\nNewTrainer: " + newTrainer);
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
        LOGGER.info("Getting trainer " + trainerId);
        return trainerRepository.findById(trainerId);
    }

    @Transactional
    public List<Trainer> getAll(){
        LOGGER.info("Getting all trainers");
        return trainerRepository.findAll();
    }

    @Transactional
    public Optional<Trainer> getByUsername(String username){
        LOGGER.info("Searching trainer: " + username);
        return trainerRepository.findTrainerByUserUsername(username);
    }

    @Transactional
    public String updatePassword(String username, String oldPassword, String newPassword){
        LOGGER.info("Updating trainer password");
        Trainer trainer = this.getByUsername(username).orElse(new Trainer());
        String result = "";
        if (null==trainer.getUser() || !oldPassword.equals(trainer.getUser().getPassword())){
            result = "Wrong username or password";
            LOGGER.error(result);
        } else {
            if (oldPassword.equals(newPassword)) {
                result = "New password cannot be the same as old password";
                LOGGER.error(result);
            } else if (newPassword.isEmpty()) {
                result = "New password cannot be empty, please enter new password";
                LOGGER.error(result);
            } else {
                trainer.setUser(userService.updateUserPassword(newPassword, trainer.getUser().getId()));
                result = "Password updated";
                LOGGER.info(result);
            }
        }
        return result;
    }

    @Transactional
    public String updateActiveStatus(String username, String password){
        Trainer trainer = this.getByUsername(username).orElse(new Trainer());
        String result = "";
        if (null==trainer.getUser() || !password.equals(trainer.getUser().getPassword())){
            result = "Wrong username or password";
            LOGGER.error(result);
        } else {
            if (trainer.getUser().getIsActive()){
                trainer.setUser(userService.updateStatus(false, trainer.getUser().getId()));
                result = "User deactivated";
                LOGGER.info(result);
            } else {
                trainer.setUser(userService.updateStatus(true, trainer.getUser().getId()));
                result = "User activated";
                LOGGER.info(result);
            }
        }
        return result;
    }

    @PostConstruct
    public void init(){
        LOGGER.info("Starting TrainerService");
    }
}
