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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class TrainerRestControllerTest {

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
        this.mvc.perform(get("/api/trainers").header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("roddy.patman")))
                .andExpect(content().string(containsString("ricardo.patino")));
    }

    @Test
    void getTrainerByUsername() throws Exception {
        this.mvc.perform(get("/api/trainers/ricardo.patino")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("\"firstName\":\"Ricardo\"")))
                .andExpect(content().string(containsString("\"lastName\":\"Patino\"")))
                .andExpect(content().string(containsString("\"trainingTypeName\":\"Resistance\"")));
    }

    @Test
    void whenTrainerNotFoundThen404() throws Exception {
        this.mvc.perform(get("/api/trainers/cristiano.ronaldo")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isNotFound());

        this.mvc.perform(put("/api/trainers/update")
                        .header("Authorization", "Bearer " + token)
                        .header("username", "trainee404")
                        .header("firstName", "Jane")
                        .header("lastName", "Doe")
                        .header("isActive", "false")
                        .header("specializationId", 5))
                .andExpect(status().isNotFound());
    }

    @Test
    void createTrainer() throws Exception {
        this.mvc.perform(post("/api/trainers/save")
                        .header("firstName", "Chris")
                        .header("lastName", "Bumstead")
                        .header("specializationId", 1))
                .andExpect(status().isCreated())
                .andExpect(content().string(containsString("\"username\":\"chris.bumstead\"")));
    }

    @Test
    void whenNoNameLastNameOrSpecializationThen400() throws Exception {
        this.mvc.perform(post("/api/trainers/save")
                        .header("firstName", "Homer")
                        .header("specializationId", 1))
                .andExpect(status().isBadRequest());

        this.mvc.perform(post("/api/trainers/save")
                        .header("firstName", "Homer")
                        .header("lastName", "Simpson"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenWrongSpecializationThen400() throws Exception {
        this.mvc.perform(post("/api/trainers/save")
                        .header("firstName", "Homer")
                        .header("lastName", "Simpson")
                        .header("specializationId", 8))
                .andExpect(status().isBadRequest());

        this.mvc.perform(put("/api/trainers/update")
                        .header("Authorization", "Bearer " + token)
                        .header("username", "betteann.staten")
                        .header("firstName", "John")
                        .header("lastName", "Doe")
                        .header("isActive", "false")
                        .header("specializationId", 0))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateTrainer() throws Exception {
        this.mvc.perform(put("/api/trainers/update")
                        .header("Authorization", "Bearer " + token)
                        .header("username", "betteann.staten")
                        .header("firstName", "Ronnie")
                        .header("lastName", "Coleman")
                        .header("isActive", "false")
                        .header("specializationId", 5))
                .andExpect(status().isAccepted())
                .andExpect(content().string(containsString("\"username\":\"betteann.staten\"")))
                .andExpect(content().string(containsString("\"firstName\":\"Ronnie\"")))
                .andExpect(content().string(containsString("\"isActive\":false")))
                .andExpect(content().string(containsString("\"trainingTypeName\":\"Resistance\"")));
    }

    @Test
    void updatePassword() throws Exception {
        this.mvc.perform(put("/api/trainers/update-password")
                        .header("Authorization", "Bearer " + token)
                        .header("username", "betteann.staten")
                        .header("password", "npwrbgzsom")
                        .header("newPassword", "very-strong-password1234!"))
                .andExpect(status().isAccepted())
                .andExpect(content().string(containsString("Password updated")));
    }

    @Test
    void whenPasswordsNotMatchThen401() throws Exception {
        this.mvc.perform(put("/api/trainers/update-password")
                        .header("Authorization", "Bearer " + token)
                        .header("username", "ricardo.patino")
                        .header("password", "wrong-password")
                        .header("newPassword", "easy-password"))
                .andExpect(status().isUnauthorized());

        this.mvc.perform(patch("/api/trainers/activate")
                        .header("Authorization", "Bearer " + token)
                        .header("username", "ricardo.patino")
                        .header("password", "wrong-password"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void whenNoPasswordProvidedThen400() throws Exception {
        this.mvc.perform(put("/api/trainers/update-password")
                        .header("Authorization", "Bearer " + token)
                        .header("username", "ricardo.patino")
                        .header("password", "password")
                        .header("newPassword", ""))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateStatus() throws Exception {
        this.mvc.perform(patch("/api/trainers/activate")
                        .header("Authorization", "Bearer " + token)
                        .header("username", "betteann.staten")
                        .header("password", "npwrbgzsom"))
                .andExpect(status().isAccepted())
                .andExpect(content().string(containsString("User deactivated")));
    }
}