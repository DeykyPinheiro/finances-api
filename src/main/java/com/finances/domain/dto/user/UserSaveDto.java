package com.finances.domain.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

//TODO realizar validacoes
public record UserSaveDto(


        @NotBlank
//                só pode ter letras, validar no front tbm
        String name,

        @NotBlank
//                minimo de 8
        String password,

        @Email
        @NotBlank
//                email tem que ser unico
        String email,

//        TODO thow exception validation fo date
//        tem que ser data no passado
        @DateTimeFormat
        @NotNull
        Date birthDate
) {

}
