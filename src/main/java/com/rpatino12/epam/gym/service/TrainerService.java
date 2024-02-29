package com.rpatino12.epam.gym.service;

import com.rpatino12.epam.gym.dto.UserLogin;
import com.rpatino12.epam.gym.exception.ResourceNotFoundException;
import com.rpatino12.epam.gym.exception.TrainerNullException;
import com.rpatino12.epam.gym.model.User;
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
    public UserLogin save(Trainer newTrainer){
        if (null == newTrainer){
            log.error("Cannot save a null or empty entity");
            throw new TrainerNullException("Trainer cannot be null");
        }
        newTrainer.setUser(userService.registerUser(newTrainer.getUser()));
        Trainer trainer = trainerRepository.save(newTrainer);
        log.info("Creating trainer: " + trainer);

        return new UserLogin(trainer.getUser().getUsername(), trainer.getUser().getPassword());
    }

    @Transactional
    public Trainer update(Trainer updatedTrainer, String username){
        if (null == updatedTrainer){
            log.error("Cannot update trainer");
            throw new TrainerNullException("Trainer cannot be null");
        }
        User updatedUser = userService.updateUser(updatedTrainer.getUser(), username);

        if (trainerRepository.findTrainerByUserUsername(username).isEmpty()){
            log.error("The entity to be updated does not exist");
            throw new ResourceNotFoundException("Trainer", "username", username);
        }
        Trainer trainer = trainerRepository.findTrainerByUserUsername(username).get();
        trainer.setSpecialization(updatedTrainer.getSpecialization());
        trainer.setUser(updatedUser);

        log.info("""
                        Updating trainer {}:\s
                        First Name: {}\s
                        Last Name: {}\s
                        Specialization Id: {}\s
                        Is Active: {}""",
                username,
                updatedUser.getFirstName(),
                updatedUser.getLastName(),
                trainer.getSpecialization().getTrainingTypeId(),
                updatedUser.getIsActive());

        return trainerRepository.save(trainer);
    }

    @Transactional
    public List<Trainer> getAll(){
        log.info("Getting all trainers");
        List<Trainer> trainers = trainerRepository.findAll();
        if (trainers.isEmpty()){
            log.error("There are no trainees registered");
            throw new ResourceNotFoundException("Trainer");
        }
        return trainers;
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
