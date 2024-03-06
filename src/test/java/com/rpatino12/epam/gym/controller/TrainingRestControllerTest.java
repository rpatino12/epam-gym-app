package com.rpatino12.epam.gym.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class TrainingRestControllerTest {

    @Autowired
    private MockMvc mvc;

    private String token;

    @BeforeEach
    public void setUp() throws Exception {
        MvcResult result = this.mvc.perform(post("/api/auth/token")
                        .with(httpBasic("ricardo.patino", "password")))
                .andExpect(status().isOk())
                .andReturn();

        this.token = result.getResponse().getContentAsString();
    }

    @Test
    void getAll() throws Exception {
        this.mvc.perform(get("/api/trainings").header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("\"trainingName\":\"Hard\"")))
                .andExpect(content().string(containsString("\"username\":\"roddy.patman\"")))
                .andExpect(content().string(containsString("\"username\":\"manya.whitcomb\"")))
                .andExpect(content().string(containsString("\"trainingTypeName\":\"Fitness\"")));
    }

    @Test
    void getTrainingTypes() throws Exception {
        this.mvc.perform(get("/api/trainings/training-types")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("\"trainingType\":\"Yoga\"")))
                .andExpect(content().string(containsString("\"trainingType\":\"Zumba\"")))
                .andExpect(content().string(containsString("\"trainingTypeId\":5")))
                .andExpect(content().string(containsString("\"trainingTypeId\":1")));
    }

    @Test
    void getByTrainee() throws Exception {
        this.mvc.perform(get("/api/trainings/trainee-username/manya.whitcomb")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("\"firstName\":\"Manya\"")))
                .andExpect(content().string(containsString("\"lastName\":\"Whitcomb\"")))
                .andExpect(content().string(containsString("\"trainingDate\":\"2023-09-10\"")));
    }

    @Test
    void getByTrainer() throws Exception {
        this.mvc.perform(get("/api/trainings/trainer-username/betteann.staten")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("\"firstName\":\"Betteann\"")))
                .andExpect(content().string(containsString("\"lastName\":\"Staten\"")))
                .andExpect(content().string(containsString("\"trainingId\":2")))
                .andExpect(content().string(containsString("\"trainingName\":\"Easy\"")));
    }

    @Test
    void whenTrainingsNotFoundThen404() throws Exception {
        this.mvc.perform(get("/api/trainings/trainee-username/shea.mcfater")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isNotFound());

        this.mvc.perform(get("/api/trainings/trainer-username/ricardo.patino")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isNotFound());
    }

    @Test
    void whenUsernameNotFoundThen404() throws Exception {
        this.mvc.perform(get("/api/trainings/trainee-username/not.trainee")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isNotFound());

        this.mvc.perform(get("/api/trainings/trainer-username/not.trainer")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isNotFound());
    }

    @Test
    void createTraining() throws Exception {
        this.mvc.perform(post("/api/trainings/save")
                        .header("Authorization", "Bearer " + token)
                        .header("traineeUsername", "manya.whitcomb")
                        .header("trainerUsername", "betteann.staten")
                        .header("trainingName", "Mr. Olympia Training")
                        .header("trainingDuration", 35.2)
                        .header("trainingTypeId", 1)
                        .header("trainingDate", "2024-01-10"))
                .andExpect(status().isCreated())
                .andExpect(content().string(containsString("Training created")));
    }

    @Test
    void whenWrongSpecializationThen400() throws Exception {
        this.mvc.perform(post("/api/trainings/save")
                        .header("Authorization", "Bearer " + token)
                        .header("traineeUsername", "manya.whitcomb")
                        .header("trainerUsername", "ricardo.patino")
                        .header("trainingName", "Fake training")
                        .header("trainingDuration", 1000)
                        .header("trainingTypeId", 6))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenWrongUsernamesThen400() throws Exception {
        this.mvc.perform(post("/api/trainings/save")
                        .header("Authorization", "Bearer " + token)
                        .header("traineeUsername", "cristiano.ronaldo")
                        .header("trainerUsername", "lionel.messi")
                        .header("trainingName", "Goat training")
                        .header("trainingDuration", 50)
                        .header("trainingTypeId", 5)
                        .header("trainingDate", "2024-01-10"))
                .andExpect(status().isBadRequest());
    }
}