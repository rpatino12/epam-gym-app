package com.rpatino12.epam.gym.service;

import com.rpatino12.epam.gym.dao.TraineeDAO;
import com.rpatino12.epam.gym.dao.UserDAO;
import com.rpatino12.epam.gym.model.Trainee;
import com.rpatino12.epam.gym.model.User;
import jakarta.transaction.Transactional;
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
    @Transactional
    public Trainee save(Trainee newTrainee){
        User newUser = userDAO.save(newTrainee.getUser());
        newTrainee.setUserId(newUser.getId());
        return traineeDAO.save(newTrainee);
    }

    public Trainee update(Trainee newTrainee, Long traineeId) {
        userDAO.findById(newTrainee.getUserId())
                .map(
                        user -> {
                            user.setFirstName(newTrainee.getUser().getFirstName());
                            user.setLastName(newTrainee.getUser().getLastName());
                            user.setUsername(newTrainee.getUser().getUsername());
                            user.setPassword(newTrainee.getUser().getPassword());
                            user.setActive(newTrainee.getUser().getActive());
                            return userDAO.save(user);
                        }
                );
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
