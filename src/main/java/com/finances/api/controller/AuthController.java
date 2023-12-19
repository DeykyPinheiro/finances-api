package com.finances.api.controller;

import com.finances.domain.dto.authentication.AuthenticationDto;
import com.finances.domain.dto.token.TokenDto;
import com.finances.domain.service.AuthService;
import com.finances.domain.service.JwtKeyStoreService;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import jakarta.validation.Valid;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.PublicKey;
import java.security.cert.Certificate;
import java.util.Map;

@RestController

@RequestMapping("/auth")
public class AuthController {

    private AuthService authService;

    private JwtKeyStoreService jwtKeyStoreService;

    @Autowired
    public AuthController(AuthService authService, JwtKeyStoreService jwtKeyStoreService) {
        this.authService = authService;
        this.jwtKeyStoreService = jwtKeyStoreService;
    }

    @PostMapping("/login")
    public TokenDto login(@RequestBody @Valid AuthenticationDto authenticationDto) {
        return authService.login(authenticationDto);
    }

    @GetMapping("/jwks")
    public Map<String, Object> getRSAPublicKey() {
        return jwtKeyStoreService.publicKey();
    }
}

