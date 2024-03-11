package com.rpatino12.epam.gym.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class UpdateUserDto extends UserLogin{
    private String newPassword;
}
