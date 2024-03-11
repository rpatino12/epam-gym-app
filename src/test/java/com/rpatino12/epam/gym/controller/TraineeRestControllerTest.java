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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class TraineeRestControllerTest {

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
        this.mvc.perform(get("/api/trainees").header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("manya.whitcomb")))
                .andExpect(content().string(containsString("miquela.trembley")));
    }

    @Test
    void getTraineeByUsername() throws Exception {
        this.mvc.perform(get("/api/trainees/shea.mcfater")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("\"firstName\":\"Shea\"")))
                .andExpect(content().string(containsString("\"lastName\":\"McFater\"")));
    }

    @Test
    void whenTraineeNotFoundThen404() throws Exception {
        this.mvc.perform(get("/api/trainees/cristiano.ronaldo")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isNotFound());

        this.mvc.perform(delete("/api/trainees/delete/no.trainee")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isNotFound());

        this.mvc.perform(put("/api/trainees/update")
                        .header("Authorization", "Bearer " + token)
                        .header("username", "trainee404")
                        .header("firstName", "NoTrainee")
                        .header("lastName", "Smith")
                        .header("isActive", "true"))
                .andExpect(status().isNotFound());
    }

    @Test
    void createTrainee() throws Exception {
        this.mvc.perform(post("/api/trainees/save")
                        .header("firstName", "Elton John")
                        .header("lastName", "Doe")
                        .header("birthdate", "1999-01-31")
                        .header("address", "123 Elm Street"))
                .andExpect(status().isCreated())
                .andExpect(content().string(containsString("\"username\":\"elton.doe\"")));
    }

    @Test
    void whenNoNameOrLastNameThen400() throws Exception {
        this.mvc.perform(post("/api/trainees/save")
                        .header("lastName", "Doe")
                        .header("birthdate", "2001-07-21")
                        .header("address", "5th Ave, New York"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deleteTraineeByUsername() throws Exception {
        this.mvc.perform(delete("/api/trainees/delete/miquela.trembley")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }

    @Test
    void updateTrainee() throws Exception {
        this.mvc.perform(put("/api/trainees/update")
                        .header("Authorization", "Bearer " + token)
                        .header("username", "shea.mcfater")
                        .header("firstName", "Kylian")
                        .header("lastName", "Mbappe")
                        .header("isActive", "false")
                        .header("birthdate", "1999-02-02")
                        .header("address", "Eiffel tower, Paris"))
                .andExpect(status().isAccepted())
                .andExpect(content().string(containsString("\"username\":\"shea.mcfater\"")))
                .andExpect(content().string(containsString("\"firstName\":\"Kylian\"")))
                .andExpect(content().string(containsString("\"isActive\":false")))
                .andExpect(content().string(containsString("\"address\":\"Eiffel tower, Paris\"")));
    }

    @Test
    void updatePassword() throws Exception {
        this.mvc.perform(put("/api/trainees/update-password")
                        .header("Authorization", "Bearer " + token)
                        .header("username", "manya.whitcomb")
                        .header("password", "vbxowmkpue")
                        .header("newPassword", "very-strong-password1234!"))
                .andExpect(status().isAccepted())
                .andExpect(content().string(containsString("Password updated")));
    }

    @Test
    void whenPasswordsNotMatchThen401() throws Exception {
        this.mvc.perform(put("/api/trainees/update-password")
                        .header("Authorization", "Bearer " + token)
                        .header("username", "manya.whitcomb")
                        .header("password", "wrong-password")
                        .header("newPassword", "easy-password"))
                .andExpect(status().isUnauthorized());

        this.mvc.perform(patch("/api/trainees/activate")
                        .header("Authorization", "Bearer " + token)
                        .header("username", "manya.whitcomb")
                        .header("password", "wrong-password"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void whenNoPasswordProvidedThen400() throws Exception {
        this.mvc.perform(put("/api/trainees/update-password")
                        .header("Authorization", "Bearer " + token)
                        .header("username", "manya.whitcomb")
                        .header("password", "vbxowmkpue")
                        .header("newPassword", ""))
                .andExpect(status().isBadRequest());
    }


    @Test
    void updateStatus() throws Exception {
        this.mvc.perform(patch("/api/trainees/activate")
                        .header("Authorization", "Bearer " + token)
                        .header("username", "manya.whitcomb")
                        .header("password", "vbxowmkpue"))
                .andExpect(status().isAccepted())
                .andExpect(content().string(containsString("User deactivated")));
    }
}