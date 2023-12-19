package com.rpatino12.epam.gym.service;

import com.rpatino12.epam.gym.dao.TraineeDAO;
import com.rpatino12.epam.gym.dao.UserDAO;
import com.rpatino12.epam.gym.model.Trainee;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TraineeService {
    @Autowired
    private TraineeDAO traineeDAO;
    @Autowired
    private UserDAO userDAO;
    private static final Log LOG = LogFactory.getLog(TraineeService.class);

    // Trainee Service class should support possibility to create/update/delete/select Trainee profile.
    public Trainee save(Trainee newTrainee){
        return traineeDAO.save(newTrainee);
    }

    public Trainee update(Trainee newTrainee, Long traineeId) {
        Optional<Trainee> trainee = select(traineeId);
        if (trainee.isPresent()){
            trainee.get().setAddress(newTrainee.getAddress());
            trainee.get().setDateOfBirth(newTrainee.getDateOfBirth());
            trainee.get().setUserId(newTrainee.getUserId());
        }
        return save(trainee.get());
    }

    public boolean delete(Long traineeId){
        try {
            traineeDAO.deleteById(traineeId);
            return true;
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
    }

    public Optional<Trainee> select(Long traineeId){
        return traineeDAO.findById(traineeId);
    }
}
