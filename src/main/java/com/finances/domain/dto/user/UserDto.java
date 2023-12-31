package com.finances.domain.dto.user;

import com.finances.domain.model.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;


public record UserDto(

        Long id,

        String name,


        String email,

        @DateTimeFormat
        Date birthDate
) {
    public UserDto(User user) {
        this(user.getId(), user.getName(), user.getEmail(), user.getBirthDate());
    }

}
