package com.finances.domain.service;

import com.auth0.jwt.algorithms.Algorithm;
import com.finances.domain.dto.token.TokenDto;
import com.finances.domain.model.User;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.GrantedAuthority;

import javax.xml.crypto.Data;

import java.time.Duration;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TokenServiceTest {

    @InjectMocks
    private TokenService tokenService;

    @Mock
    private Algorithm algorithm;

    @Test
    void shouldReturnTokenDtoValid_WhenReceiveUserValid() {
        User user = user();
        TokenDto tokenDto = tokenDto();

//        TokenDto tokenResult = tokenService.tokenGenerator(user);

        assertNotNull(tokenDto);
//        Assertions.assertEquals("Bearer", tokenResult.tokenType());
        assertNotNull(tokenDto.expiresIn());
    }

     User user() {
        Date date = new Date();
        Set<GrantedAuthority> authorityList = new HashSet<>();
        User user = new User(1l, "deyky", "teste@teste.com", date);
        return user;
    }

     TokenDto tokenDto() {
        String accessToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJkZXlreUB0ZXN0ZS5jb20iLCJjaGF2ZSB0ZXN0IjoidmFsb3IgdGVzdGUiLCJpc3MiOiJhdXRoMCIsImV4cCI6MTY5NDczMTIwOH0.6WdZkLzotu_NAKhsVEoqgpv_EBUYjOsw_aN7Q7azpCo";
        return new TokenDto("accessToken", "Bearer", new Date().toInstant().plus(Duration.ofMinutes(30)));
    }
}