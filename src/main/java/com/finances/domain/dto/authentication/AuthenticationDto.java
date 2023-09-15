package com.finances.domain.dto.authentication;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record AuthenticationDto(

        @NotBlank
        @Email
        String email,

        @NotBlank
        String password
) {
}
