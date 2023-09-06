package com.finances.domain.dto.user;

import com.finances.domain.model.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;


public record UserDto(

        String name,


        String email,

        Date birthDate
) {
    public UserDto(User user) {
        this(user.getName(), user.getEmail(), user.getBirthDate());
    }
}
