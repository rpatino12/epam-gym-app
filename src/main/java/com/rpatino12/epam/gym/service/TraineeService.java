package com.rpatino12.epam.gym.service;

import com.rpatino12.epam.gym.dao.TraineeDAO;
import com.rpatino12.epam.gym.model.Trainee;
import jakarta.transaction.Transactional;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TraineeService {

    private final TraineeDAO traineeDAO;
    private final UserService userService;
    private static final Log LOGGER = LogFactory.getLog(TraineeService.class);

    public TraineeService(TraineeDAO traineeDAO, UserService userService) {
        this.traineeDAO = traineeDAO;
        this.userService = userService;
    }

    // Trainee Service class should support possibility to create/update/delete/select Trainee profile.
    @Transactional
    public Trainee save(Trainee newTrainee){
        newTrainee.setUser(userService.registerUser(newTrainee.getUser()));
        return traineeDAO.save(newTrainee);
    }

    @Transactional
    public Trainee update(Trainee newTrainee, Long traineeId) {
        userService.updateUser(newTrainee.getUser(), newTrainee.getUser().getId());
        return traineeDAO.findById(traineeId)
                .map(
                        trainee -> {
                            trainee.setDateOfBirth(newTrainee.getDateOfBirth());
                            trainee.setAddress(newTrainee.getAddress());
                            trainee.setUser(newTrainee.getUser());
                            return traineeDAO.save(trainee);
                        }
                ).get();
    }

    @Transactional
    public boolean delete(Long traineeId){
        try {
            traineeDAO.deleteById(traineeId);
            return true;
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
    }

    @Transactional
    public Optional<Trainee> select(Long traineeId){
        return traineeDAO.findById(traineeId);
    }
}
