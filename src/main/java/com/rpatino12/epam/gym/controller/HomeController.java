package com.rpatino12.epam.gym.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api")
public class HomeController {

    @GetMapping("/hello")
    public String home(Principal principal){
        return "Hello " + principal.getName();
    }
}
