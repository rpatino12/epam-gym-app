package com.rpatino12.epam.gym.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserDto {
    private String username;
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
}
