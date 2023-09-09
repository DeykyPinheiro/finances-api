package com.finances.domain.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public record UserUpdateDto(

        String name,

        String email,

        @DateTimeFormat
        Date birthDate
) {
}
