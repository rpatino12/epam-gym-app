package com.rpatino12.epam.gym.dto;

import com.rpatino12.epam.gym.model.Trainer;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = true)
public class TraineeDto extends UserDto{
    private Date birthdate;
    private String address;
    private boolean isActive;
    private Set<Trainer> trainersList = new HashSet<>();
}
