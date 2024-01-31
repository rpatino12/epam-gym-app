package com.rpatino12.epam.gym.service;

import com.rpatino12.epam.gym.repo.TrainerRepository;
import com.rpatino12.epam.gym.model.Trainer;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class TrainerService {

    private final TrainerRepository trainerRepository;
    private final UserService userService;

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
        log.info("Creating trainer: " + newTrainer);
        return trainerRepository.save(newTrainer);
    }

    @Transactional
    public Trainer update(Trainer newTrainer, Long trainerId){
        userService.updateUser(newTrainer.getUser(), newTrainer.getUser().getId());
        log.info("Updating trainer: Id=" + trainerId + "\nNewTrainer: " + newTrainer);
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
        log.info("Getting trainer " + trainerId);
        return trainerRepository.findById(trainerId);
    }

    @Transactional
    public List<Trainer> getAll(){
        log.info("Getting all trainers");
        return trainerRepository.findAll();
    }

    @Transactional
    public Optional<Trainer> getByUsername(String username){
        log.info("Searching trainer: " + username);
        return trainerRepository.findTrainerByUserUsername(username);
    }

    @Transactional
    public String updatePassword(String username, String oldPassword, String newPassword){
        log.info("Updating trainer password");
        Trainer trainer = this.getByUsername(username).orElse(new Trainer());
        String result = "";
        if (null==trainer.getUser() || !oldPassword.equals(trainer.getUser().getPassword())){
            result = "Wrong username or password";
            log.error(result);
        } else {
            if (oldPassword.equals(newPassword)) {
                result = "New password cannot be the same as old password";
                log.error(result);
            } else if (newPassword.isEmpty()) {
                result = "New password cannot be empty, please enter new password";
                log.error(result);
            } else {
                trainer.setUser(userService.updateUserPassword(newPassword, trainer.getUser().getId()));
                result = "Password updated";
                log.info(result);
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
            log.error(result);
        } else {
            if (trainer.getUser().getIsActive()){
                trainer.setUser(userService.updateStatus(false, trainer.getUser().getId()));
                result = "User deactivated";
                log.info(result);
            } else {
                trainer.setUser(userService.updateStatus(true, trainer.getUser().getId()));
                result = "User activated";
                log.info(result);
            }
        }
        return result;
    }

    @PostConstruct
    public void init(){
        log.info("Starting TrainerService");
    }
}
