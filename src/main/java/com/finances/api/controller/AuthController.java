package com.finances.api.controller;

import com.finances.common.properties.JwtKeyStoreProperties;
import com.finances.domain.dto.authentication.AuthenticationDto;
import com.finances.domain.dto.token.TokenDto;
import com.finances.domain.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.interfaces.RSAPublicKey;

@RestController

@RequestMapping("/auth")
public class AuthController {

    private AuthService authService;

    private JwtKeyStoreProperties jwtKeyStoreProperties;

    @Autowired
    public AuthController(AuthService authService, JwtKeyStoreProperties jwtKeyStoreProperties) {
        this.authService = authService;
        this.jwtKeyStoreProperties = jwtKeyStoreProperties;
    }

    @PostMapping("/login")
    public TokenDto login(@RequestBody @Valid AuthenticationDto authenticationDto) {
        return authService.login(authenticationDto);
    }

    @GetMapping("/jwks")
    public RSAPublicKey getRSAPublicKey() {
        RSAPublicKey a = jwtKeyStoreProperties.getPublicKey();
        return  a;
    }

}
