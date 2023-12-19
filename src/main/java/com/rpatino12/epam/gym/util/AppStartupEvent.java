package com.rpatino12.epam.gym.util;

import com.rpatino12.epam.gym.dao.TraineeDAO;
import com.rpatino12.epam.gym.dao.TrainerDAO;
import com.rpatino12.epam.gym.dao.TrainingDAO;
import com.rpatino12.epam.gym.dao.UserDAO;
import com.rpatino12.epam.gym.model.Trainee;
import com.rpatino12.epam.gym.model.User;
import com.rpatino12.epam.gym.service.TraineeService;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Optional;

@Component
public class AppStartupEvent implements ApplicationListener<ApplicationReadyEvent> {
    private final UserDAO userDAO;
    private final TrainerDAO trainerDAO;
    private final TraineeDAO traineeDAO;
    private final TrainingDAO trainingDAO;
    private final TraineeService traineeService;
    private final DateUtils dateUtils;

    public AppStartupEvent(UserDAO userDAO, TrainerDAO trainerDAO, TraineeDAO traineeDAO, TrainingDAO trainingDAO, TraineeService traineeService, DateUtils dateUtils) {
        this.userDAO = userDAO;
        this.trainerDAO = trainerDAO;
        this.traineeDAO = traineeDAO;
        this.trainingDAO = trainingDAO;
        this.traineeService = traineeService;
        this.dateUtils = dateUtils;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        System.out.println("The gym just started!");
//        Iterable<Trainee> trainee = this.traineeDAO.findAll();
//        trainee.forEach(System.out::println);

        // Select
        Optional<Trainee> trainee10 = this.traineeService.select(10L);
        System.out.println(trainee10.get());

        User user1 = new User("Ricardo", "Patino", "1234567890", true);
        Trainee trainee = new Trainee(dateUtils.createDateFromDateString("1996-03-08"), "Bosques de San Carlos", user1);

        System.out.println(user1.getUsername());

        System.out.println(traineeService.save(trainee));

        System.out.println(userDAO.findById(51L).get());

//        System.out.println(traineeService.delete(11L));

        Iterable<Trainee> traineeList = this.traineeDAO.findAll();
        traineeList.forEach(System.out::println);

    }
}
