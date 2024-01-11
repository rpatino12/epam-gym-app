package com.rpatino12.epam.gym.util;

import com.rpatino12.epam.gym.dao.TraineeDAO;
import com.rpatino12.epam.gym.dao.TrainerDAO;
import com.rpatino12.epam.gym.dao.TrainingDAO;
import com.rpatino12.epam.gym.dao.UserDAO;
import com.rpatino12.epam.gym.model.Trainee;
import com.rpatino12.epam.gym.model.Trainer;
import com.rpatino12.epam.gym.model.Training;
import com.rpatino12.epam.gym.model.User;
import com.rpatino12.epam.gym.service.TraineeService;
import com.rpatino12.epam.gym.service.TrainerService;
import com.rpatino12.epam.gym.service.TrainingService;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.util.Optional;

@Component
public class AppStartupEvent implements ApplicationListener<ApplicationReadyEvent> {
    private final UserDAO userDAO;
    private final TrainerDAO trainerDAO;
    private final TraineeDAO traineeDAO;
    private final TrainingDAO trainingDAO;
    private final TraineeService traineeService;
    private final TrainerService trainerService;
    private final TrainingService trainingService;
    private final DateUtils dateUtils;

    public AppStartupEvent(UserDAO userDAO, TrainerDAO trainerDAO, TraineeDAO traineeDAO, TrainingDAO trainingDAO, TraineeService traineeService, TrainerService trainerService, TrainingService trainingService, DateUtils dateUtils) {
        this.userDAO = userDAO;
        this.trainerDAO = trainerDAO;
        this.traineeDAO = traineeDAO;
        this.trainingDAO = trainingDAO;
        this.traineeService = traineeService;
        this.trainerService = trainerService;
        this.trainingService = trainingService;
        this.dateUtils = dateUtils;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        System.out.println("The gym just started!");
//
//        User user1 = new User("Ricardo", "Patino", "1234567890", true);
//        User user2 = new User("Laura", "Oquendo", "aabbccddee", false);
//        User user3 = new User("Myriam", "Echeverri", "000aabb890", false);
//        User user4 = new User("Paulina", "Patino", "000aabb111", true);
//
//
//        Trainer trainer = trainerService.select(15L).get();
//        trainer.setSpecializationId(5L);
//        trainer.setUser(user1);
//
//        Trainer trainer2 = new Trainer(1L, user2);
//
//        System.out.println("   Updating: " + trainerService.update(trainer, 15L));
//        System.out.println("   Saving: " + trainerService.save(trainer2));

//        Iterable<Trainer> trainers = this.trainerDAO.findAll();
//        trainers.forEach(System.out::println);
//
//        Trainee trainee = new Trainee(dateUtils.createDateFromDateString("1998-02-03"), "123 Elm Street", user3);
//        Trainee trainee2 = new Trainee(dateUtils.createDateFromDateString("1959-05-20"), "5th avenue", user4);
//
//        System.out.println("   Saving: " + traineeService.save(trainee));
//        System.out.println("   Saving: " + traineeService.save(trainee2));
//
        Iterable<Trainee> trainees = this.traineeDAO.findAll();
        trainees.forEach(System.out::println);
//
//        traineeService.delete(26L);
//
//        trainees = this.traineeDAO.findAll();
//        trainees.forEach(System.out::println);
//
//        Iterable<User> users = this.userDAO.findAll();
//        users.forEach(System.out::println);

//        Iterable<Training> trainings = this.trainingDAO.findAll();
//        trainings.forEach(System.out::println);

    }
}
