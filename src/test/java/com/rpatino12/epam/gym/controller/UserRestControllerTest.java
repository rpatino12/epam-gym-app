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
class UserRestControllerTest {

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
    void login() throws Exception {
        this.mvc.perform(get("/api/users/login")
                        .header("Authorization", "Bearer " + token)
                        .header("username", "shea.mcfater")
                        .header("password", "pmilyjaewb"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Welcome")));
    }

    @Test
    void wrongUsernameOrPasswordThen401() throws Exception {
        this.mvc.perform(get("/api/users/login")
                        .header("Authorization", "Bearer " + token)
                        .header("username", "manya.whitcomb")
                        .header("password", "wrong-password"))
                .andExpect(status().isUnauthorized());

        this.mvc.perform(get("/api/users/login")
                        .header("Authorization", "Bearer " + token)
                        .header("username", "manya.whitcomb1")
                        .header("password", "vbxowmkpue"))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string(containsString("Wrong username or password")));
    }
}