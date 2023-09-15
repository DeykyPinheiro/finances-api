package com.finances.domain.dto.token;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;

public record TokenDto(

        @NotBlank
        String accessToken,

        @NotBlank
        String tokenType,

        @NotNull
        Instant expiresIn
) {
}