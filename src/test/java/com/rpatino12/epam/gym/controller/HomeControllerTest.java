package com.rpatino12.epam.gym.controller;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.rpatino12.epam.gym.config.SecurityConfig;
import com.rpatino12.epam.gym.service.TokenService;

@WebMvcTest({ HomeController.class, AuthController.class })
@Import({ SecurityConfig.class, TokenService.class })
class HomeControllerTest {

    @Autowired
    MockMvc mvc;

    @Test
    void tokenWhenAnonymousThenStatusIsUnauthorized() throws Exception {
        this.mvc.perform(post("/api/auth/token"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser
    public void tokenWithMockUserStatusIsOK() throws Exception {
        this.mvc.perform(post("/api/auth/token"))
                .andExpect(status().isOk());
    }

    @Test
    void tokenWithBasicThenGetToken() throws Exception {
        MvcResult result = this.mvc.perform(post("/api/auth/token")
                        .with(httpBasic("rpatino12", "epam")))
                .andExpect(status().isOk())
                .andReturn();

        assertFalse(result.getResponse().getContentAsString().isEmpty());
    }

    @Test
    void rootWhenAuthenticatedThenSaysHelloUser() throws Exception {
        MvcResult result = this.mvc.perform(post("/api/auth/token")
                        .with(httpBasic("rpatino12", "epam")))
                .andExpect(status().isOk())
                .andReturn();

        String token = result.getResponse().getContentAsString();

        this.mvc.perform(get("/api/hello")
                .header("Authorization", "Bearer " + token))
                .andExpect(content().string("Hello rpatino12"));
    }

    @Test
    void rootWhenUnauthenticatedThen401() throws Exception {
        this.mvc.perform(get("/api/hello"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void rootWithBasicStatusIsForbidden() throws Exception {
        this.mvc.perform(get("/api/hello")
                .with(httpBasic("rpatino12", "epam")))
                .andExpect(status().isForbidden());
    }
}