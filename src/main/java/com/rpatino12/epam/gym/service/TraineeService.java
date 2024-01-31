package com.rpatino12.epam.gym.service;

import com.rpatino12.epam.gym.repo.TraineeRepository;
import com.rpatino12.epam.gym.model.Trainee;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class TraineeService {

    private final TraineeRepository traineeRepository;
    private final UserService userService;

    public TraineeService(TraineeRepository traineeRepository, UserService userService) {
        this.traineeRepository = traineeRepository;
        this.userService = userService;
    }

    // Trainee Service class should support possibility to create/update/delete/select Trainee profile.
    @Transactional
    public Trainee save(Trainee newTrainee){
        if (null == newTrainee){
            throw new RuntimeException("Trainee cannot be null");
        }
        newTrainee.setUser(userService.registerUser(newTrainee.getUser()));
        log.info("Creating trainee: " + newTrainee);
        return traineeRepository.save(newTrainee);
    }

    @Transactional
    public Trainee update(Trainee newTrainee, Long traineeId) {
        userService.updateUser(newTrainee.getUser(), newTrainee.getUser().getId());
        log.info("Updating trainee: Id=" + traineeId + "  \nNewTrainee: " + newTrainee);
        return traineeRepository.findById(traineeId)
                .map(
                        trainee -> {
                            trainee.setDateOfBirth(newTrainee.getDateOfBirth());
                            trainee.setAddress(newTrainee.getAddress());
                            trainee.setUser(newTrainee.getUser());
                            return traineeRepository.save(trainee);
                        }
                ).get();
    }

    @Transactional
    public boolean delete(Long traineeId){
        boolean isDeleteSuccessful = false;
        log.info("Deleting trainee " + traineeId);
        Optional<Trainee> optionalTrainee = traineeRepository.findById(traineeId);
        if (optionalTrainee.isPresent()){
            traineeRepository.deleteById(traineeId);
            isDeleteSuccessful = !traineeRepository.existsById(traineeId);
        }
        log.info(isDeleteSuccessful?"Delete successful":"Couldn't delete trainee");
        return isDeleteSuccessful;
    }

    @Transactional
    public boolean deleteByUsername(String username){
        boolean isDeleteSuccessful = false;
        log.info("Deleting trainee " + username);
        Optional<Trainee> optionalTrainee = this.getByUsername(username);
        if (optionalTrainee.isPresent()){
            traineeRepository.deleteByUserUsername(username);
            isDeleteSuccessful = true;
        }
        log.info(isDeleteSuccessful?"Delete successful":"Couldn't delete trainee");
        return isDeleteSuccessful;
    }

    @Transactional
    public Optional<Trainee> select(Long traineeId){
        log.info("Getting trainee " + traineeId);
        return traineeRepository.findById(traineeId);
    }

    @Transactional
    public List<Trainee> getAll(){
        log.info("Getting all trainees");
        return traineeRepository.findAll();
    }

    @Transactional
    public Optional<Trainee> getByUsername(String username){
        log.info("Searching trainee: " + username);
        return traineeRepository.findTraineeByUserUsername(username);
    }

    @Transactional
    public String updatePassword(String username, String oldPassword, String newPassword){
        log.info("Updating trainee password");
        Trainee trainee = this.getByUsername(username).orElse(new Trainee());
        String result = "";
        if (null==trainee.getUser() || !oldPassword.equals(trainee.getUser().getPassword())){
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
                trainee.setUser(userService.updateUserPassword(newPassword, trainee.getUser().getId()));
                result = "Password updated";
                log.info(result);
            }
        }
        return result;
    }

    @Transactional
    public String updateActiveStatus(String username, String password){
        Trainee trainee = this.getByUsername(username).orElse(new Trainee());
        String result = "";
        if (null==trainee.getUser() || !password.equals(trainee.getUser().getPassword())){
            result = "Wrong username or password";
            log.error(result);
        } else {
            if (trainee.getUser().getIsActive()){
                trainee.setUser(userService.updateStatus(false, trainee.getUser().getId()));
                result = "User deactivated";
                log.info(result);
            } else {
                trainee.setUser(userService.updateStatus(true, trainee.getUser().getId()));
                result = "User activated";
                log.info(result);
            }
        }
        return result;
    }

    @PostConstruct
    public void init(){
        log.info("Starting TraineeService");
    }
}
