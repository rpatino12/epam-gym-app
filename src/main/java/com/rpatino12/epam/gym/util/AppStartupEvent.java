package com.rpatino12.epam.gym.util;

import com.rpatino12.epam.gym.dao.TraineeRepository;
import com.rpatino12.epam.gym.dao.TrainerRepository;
import com.rpatino12.epam.gym.dao.TrainingRepository;
import com.rpatino12.epam.gym.dao.TrainingTypeRepository;
import com.rpatino12.epam.gym.dao.UserRepository;
import com.rpatino12.epam.gym.model.Trainee;
import com.rpatino12.epam.gym.model.Trainer;
import com.rpatino12.epam.gym.model.Training;
import com.rpatino12.epam.gym.model.TrainingType;
import com.rpatino12.epam.gym.model.User;
import com.rpatino12.epam.gym.service.TraineeService;
import com.rpatino12.epam.gym.service.TrainerService;
import com.rpatino12.epam.gym.service.TrainingService;
import com.rpatino12.epam.gym.service.UserService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class AppStartupEvent implements ApplicationListener<ApplicationReadyEvent> {
    private final UserRepository userRepository;
    private final TrainerRepository trainerRepository;
    private final TraineeRepository traineeRepository;
    private final TrainingRepository trainingRepository;
    private final TrainingTypeRepository trainingTypeRepository;
    private final UserService userService;
    private final TraineeService traineeService;
    private final TrainerService trainerService;
    private final TrainingService trainingService;
    private static final Log LOGGER = LogFactory.getLog(AppStartupEvent.class);

    public AppStartupEvent(UserRepository userRepository, TrainerRepository trainerRepository, TraineeRepository traineeRepository, TrainingRepository trainingRepository, TrainingTypeRepository trainingTypeRepository, UserService userService, TraineeService traineeService, TrainerService trainerService, TrainingService trainingService) {
        this.userRepository = userRepository;
        this.trainerRepository = trainerRepository;
        this.traineeRepository = traineeRepository;
        this.trainingRepository = trainingRepository;
        this.trainingTypeRepository = trainingTypeRepository;
        this.userService = userService;
        this.traineeService = traineeService;
        this.trainerService = trainerService;
        this.trainingService = trainingService;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        System.out.println("The gym just started!");

        // Testing Trainee service
//        LOGGER.debug("Trainee service!");
//        Iterable<Trainee> trainees = this.traineeRepository.findAll();
//        trainees.forEach(System.out::println);

//        LOGGER.info("Instantiating user Ricardo: ");
//        User user = new User();
//        user.setFirstName("Ricardo");
//        user.setLastName("Patino");
//        user.setIsActive(true);
//        System.out.println(user);
//
//        LOGGER.info("Instantiating user Laura: ");
//        User user2 = new User();
//        user2.setFirstName("Laura");
//        user2.setLastName("Oquendo");
//        user2.setIsActive(false);
//        System.out.println(user2);
//
//        LOGGER.info("Instantiating trainee Ricardo: ");
//        Trainee trainee = new Trainee();
//        trainee.setUser(user);
//        trainee.setDateOfBirth(Date.valueOf("1996-03-08"));
//        trainee.setAddress("Calle 45 A Sur #39 B-02");
//        System.out.println(trainee);
//
//        LOGGER.info("Instantiating trainee Laura: ");
//        Trainee trainee2 = new Trainee();
//        trainee2.setUser(user2);
//        System.out.println(trainee2);
//
//        LOGGER.info("Creating (persisting) trainee Ricardo y Laura: ");
//        Trainee traineeSaved = traineeService.save(trainee);
//        Trainee traineeSaved2 = traineeService.save(trainee2);
//
//        LOGGER.info("Updating trainee Ricardo with user Andres: ");
//        trainee.getUser().setFirstName("Andres");
//        trainee.getUser().setLastName("Rios");
//        trainee.setDateOfBirth(Date.valueOf(("1998-02-02")));
//        Trainee traineeUpdated = traineeService.update(trainee, 26L);
//
//        LOGGER.info("Deleting trainee Laura: ");
//        System.out.println(traineeService.delete(27L));
//
//        LOGGER.info("Selecting trainee 1: " + traineeService.select(1L));

//        trainees = this.traineeRepository.findAll();
//        trainees.forEach(System.out::println);

//        Iterable<User> users = this.userRepository.findAll();
//        users.forEach(System.out::println);

        // Testing Trainer service
//        LOGGER.debug("Trainer service!");

//        Iterable<Trainer> trainers = this.trainerRepository.findAll();
//        trainers.forEach(System.out::println);

//        LOGGER.info("Instantiating user 3 Guillermo: ");
//        User user3 = new User();
//        user3.setFirstName("Guillermo");
//        user3.setLastName("Patino");
//        user3.setIsActive(false);
//        System.out.println(user3);
//
//        LOGGER.info("Instantiating user 4 Myriam: ");
//        User user4 = new User();
//        user4.setFirstName("Myriam");
//        user4.setLastName("Echeverri");
//        user4.setIsActive(true);
//        System.out.println(user4);
//
//        Iterable<TrainingType> trainingTypes = this.trainingTypeRepository.findAll();
//        List<TrainingType> specializations = new ArrayList<>();
//
//        trainingTypes.forEach(specializations::add);
//
//        LOGGER.info("Instantiating trainer with user Guillermo: ");
//        Trainer trainer = new Trainer();
//        trainer.setSpecialization(specializations.get(2));
//        trainer.setUser(user3);
//        System.out.println(trainer);
//
//        LOGGER.info("Instantiating trainer with user Myriam: ");
//        Trainer trainer2 = new Trainer();
//        trainer2.setSpecialization(specializations.get(3));
//        trainer2.setUser(user4);
//        System.out.println(trainer2);
//
//        LOGGER.info("Creating Trainer: ");
//        Trainer trainerSaved = trainerService.save(trainer);
//        Trainer trainerSaved2 = trainerService.save(trainer2);
//
//        LOGGER.info("Updating Trainer: ");
//        trainer.setSpecialization(specializations.get(0));
//        trainer.getUser().setFirstName("Leon");
//
//        trainer2.getUser().setIsActive(false);
//        trainer2.getUser().setLastName("Gomez");
//        System.out.println(trainerService.update(trainer, 26L));
//        System.out.println(trainerService.update(trainer2, 27L));
//
//        LOGGER.info("Selecting Trainer: ");
//        System.out.println(trainerService.select(2L).get().getSpecialization().getTrainingTypeName());

//        trainers = this.trainerRepository.findAll();
//        trainers.forEach(System.out::println);
//
//        users = this.userRepository.findAll();
//        users.forEach(System.out::println);

        // Testing training service
//        LOGGER.debug("Training service!");
//
//        Iterable<Training> trainings = this.trainingRepository.findAll();
//        trainings.forEach(System.out::println);

//        LOGGER.info("Selecting Training: ");
//        Optional<Training> training = trainingService.select(2L);
//        System.out.println(training);
//
//        LOGGER.info("Instantiating Training: ");
//        Training training1 = new Training();
//        training1.setTrainingName("New year resolutions!");
//        training1.setTrainingDate(Date.valueOf("2024-01-01"));
//        training1.setTrainingDuration(60.0);
//        training1.setTrainingType(specializations.get(0));
//        training1.setTrainer(trainerSaved);
//        training1.setTrainee(traineeUpdated);
//
//        LOGGER.info("Creating Training: ");
//        trainingService.save(training1);

//        trainings = this.trainingRepository.findAll();
//        trainings.forEach(System.out::println);

//        users = this.userRepository.findAll();
//        users.forEach(System.out::println);
    }
}
