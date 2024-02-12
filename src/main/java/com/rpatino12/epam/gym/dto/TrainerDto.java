package com.rpatino12.epam.gym.dto;

import com.rpatino12.epam.gym.model.Trainee;
import com.rpatino12.epam.gym.model.TrainingType;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.HashSet;
import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = true)
public class TrainerDto extends UserDto{
    private TrainingType specialization;
    private boolean isActive;
    private Set<Trainee> traineesList = new HashSet<>();
}
