package com.rpatino12.epam.gym.util;

import com.rpatino12.epam.gym.dao.TraineeDAO;
import com.rpatino12.epam.gym.dao.TrainerDAO;
import com.rpatino12.epam.gym.dao.TrainingDAO;
import com.rpatino12.epam.gym.dao.UserDAO;
import com.rpatino12.epam.gym.model.User;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class AppStartupEvent implements ApplicationListener<ApplicationReadyEvent> {
    private final UserDAO userDAO;
    private final TrainerDAO trainerDAO;
    private final TraineeDAO traineeDAO;
    private final TrainingDAO trainingDAO;

    public AppStartupEvent(UserDAO userDAO, TrainerDAO trainerDAO, TraineeDAO traineeDAO, TrainingDAO trainingDAO) {
        this.userDAO = userDAO;
        this.trainerDAO = trainerDAO;
        this.traineeDAO = traineeDAO;
        this.trainingDAO = trainingDAO;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        Iterable<User> users = this.userDAO.findAll();
        users.forEach(System.out::println);
    }
}
