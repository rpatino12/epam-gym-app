package com.rpatino12.epam.gym.dto;

import com.rpatino12.epam.gym.model.Trainer;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.HashSet;
import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = true)
public class TraineeDto extends UserDto{
    private String birthdate;
    private String address;
    private boolean active;
    private Set<Trainer> trainersList = new HashSet<>();
}
