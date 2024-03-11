package com.rpatino12.epam.gym.dto;

import com.rpatino12.epam.gym.model.Trainee;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.HashSet;
import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = true)
public class TrainerDto extends UserDto{
    @NotNull
    private long specializationId;
    private boolean active;
    private Set<Trainee> traineesList = new HashSet<>();
}
