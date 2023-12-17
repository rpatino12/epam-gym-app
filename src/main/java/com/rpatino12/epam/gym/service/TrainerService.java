package com.rpatino12.epam.gym.service;

import com.rpatino12.epam.gym.dao.TrainerDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TrainerService {
    @Autowired
    private TrainerDAO trainerDAO;
}
