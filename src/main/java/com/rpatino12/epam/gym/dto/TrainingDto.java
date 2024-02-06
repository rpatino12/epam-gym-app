package com.rpatino12.epam.gym.dto;

import lombok.Data;

import java.sql.Date;

@Data
public class TrainingDto {
    private String trainingName;
    private Date trainingDate;
    private String trainingType;
    private Double trainingDuration;
    private String trainerName;
    private String traineeName;
}
