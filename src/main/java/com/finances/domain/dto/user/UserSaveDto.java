package com.finances.domain.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public record UserSaveDto(

        @NotBlank
        String name,

        @NotBlank
        String password,

        @Email
        String email,

        Date birthDate
) {

}
