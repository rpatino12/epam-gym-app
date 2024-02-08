package com.rpatino12.epam.gym.service;

import com.rpatino12.epam.gym.dto.UserLogin;
import com.rpatino12.epam.gym.model.Trainee;
import com.rpatino12.epam.gym.model.User;
import com.rpatino12.epam.gym.repo.TraineeRepository;
import com.rpatino12.epam.gym.repo.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TraineeServiceTest {

    @InjectMocks
    private TraineeService traineeService;

    @Mock
    private UserService userService;
    @Mock
    private TraineeRepository traineeRepository;

    private Trainee trainee;
    private User user;
    private List<Trainee> traineeList;

    @BeforeEach
    public void setUp(){
        user = new User();
        user.setId(1L);
        user.setFirstName("testFirst");
        user.setLastName("testLast");
        user.setUsername(user.getFirstName() + "." + user.getLastName());
        user.setPassword("testPassword");
        user.setIsActive(true);

        trainee = new Trainee();
        trainee.setTraineeId(1L);
        trainee.setAddress("1234 Main Street #1");
        trainee.setDateOfBirth(new Date(System.currentTimeMillis()));
        trainee.setUser(user);

        traineeList = getMockTrainees(2);
    }

    @Test
    @DisplayName("When getAll() should return all trainees list")
    void getAll(){
        Mockito.doReturn(this.traineeList).when(traineeRepository).findAll();
        List<Trainee> trainees = traineeService.getAll();

        assertNotNull(trainees);
        assertEquals(2, (long) trainees.size());
        assertIterableEquals(this.traineeList, trainees);
    }

    @Test
    void save() {
        Mockito.doReturn(trainee).when(traineeRepository).save(trainee);
        Mockito.doReturn(user).when(userService).registerUser(user);
        UserLogin userLogin = traineeService.save(trainee);

        assertNotNull(userLogin);
        assertEquals(user.getUsername(), userLogin.getUsername());
        assertEquals(user.getPassword(), userLogin.getPassword());
    }

    @Test
    void update() {

    }

    @Test
    void deleteByUsername() {
        traineeService.deleteByUsername(user.getUsername());
        Optional<Trainee> optionalTrainee = traineeService.getByUsername(user.getUsername());

        assertEquals(Optional.empty(), optionalTrainee);
    }

    @Test
    @DisplayName("When getByUsername() with given username should return Trainee object")
    void getByUsername() {
        Mockito.doReturn(Optional.of(trainee)).when(traineeRepository).findTraineeByUserUsername(user.getUsername());
        Optional<Trainee> optionalTrainee = traineeService.getByUsername(user.getUsername());

        assertNotNull(optionalTrainee.get());
        assertEquals("testFirst", optionalTrainee.get().getUser().getFirstName());
    }

    @Test
    void updatePassword() {
    }

    @Test
    void updateActiveStatus() {
    }

    private List<Trainee> getMockTrainees(int count){
        List<Trainee> trainees = new ArrayList<>(count);
        for (long i = 0; i < count; i++) {
            long id = i + 1;
            User u =  new User();
            u.setId(id);
            u.setFirstName("testFirst" + id);
            u.setLastName("testLast" + id);
            u.setUsername(u.getFirstName() + "." + u.getLastName());
            u.setPassword("testPassword" + id);
            u.setIsActive(true);

            Trainee t = new Trainee();
            t.setTraineeId(id);
            t.setAddress("1234 Main Street #" + id);
            t.setDateOfBirth(new Date(System.currentTimeMillis()));
            t.setUser(u);

            trainees.add(t);
        }
        return trainees;
    }
}