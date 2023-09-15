package com.finances.domain.service;

import com.finances.domain.dto.authentication.AuthenticationDto;
import com.finances.domain.dto.token.TokenDto;
import com.finances.domain.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private AuthenticationManager authenticationManager;

    private TokenService tokenService;

    @Autowired
    public AuthenticationService(AuthenticationManager authenticationManager, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }


    public TokenDto login(AuthenticationDto authenticationDto) {
        UsernamePasswordAuthenticationToken credentials = new UsernamePasswordAuthenticationToken(authenticationDto.email(), authenticationDto.password());
        Authentication authentication = authenticationManager.authenticate(credentials);

        User user = (User) authentication.getPrincipal();

        return tokenService.tokenGenerator(user);
    }


}
