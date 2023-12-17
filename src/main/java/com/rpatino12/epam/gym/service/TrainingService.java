package com.rpatino12.epam.gym.service;

import com.rpatino12.epam.gym.dao.TrainingDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TrainingService {
    @Autowired
    private TrainingDAO trainingDAO;
}
