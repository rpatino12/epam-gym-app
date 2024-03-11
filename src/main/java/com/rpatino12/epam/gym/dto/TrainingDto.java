package com.rpatino12.epam.gym.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TrainingDto {
    @NotBlank
    private String name;
    private String date;
    @NotNull
    private Double duration;
    @NotBlank
    private String trainerUsername;
    @NotBlank
    private String traineeUsername;
}
