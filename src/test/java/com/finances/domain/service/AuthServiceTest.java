package com.finances.domain.service;

import com.finances.domain.dto.authentication.AuthenticationDto;
import com.finances.domain.dto.token.TokenDto;
import com.finances.domain.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.time.Duration;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@SpringBootTest
class AuthServiceTest {


    @InjectMocks
    private AuthService authService;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private TokenService tokenService;

//    @BeforeEach
//    void setUp() {
//        authService = new AuthService(authenticationManager, tokenService);
//    }


    @Test
    void shoudReturnTokenDto_WhenLoginWithUserValid() {
        Authentication authentication = Mockito.mock(Authentication.class);

        AuthenticationDto authenticationDto = new AuthenticationDto("deyky", "teste@teste.com");
        UsernamePasswordAuthenticationToken credentials = new UsernamePasswordAuthenticationToken(authenticationDto.email(), authenticationDto.password());

        Mockito.when(authenticationManager.authenticate(credentials)).thenReturn(authentication);
        Mockito.when(authentication.getPrincipal()).thenReturn(user());
        Mockito.when(tokenService.tokenGenerator(Mockito.any())).thenReturn(new TokenDto("1", "12", new Date().toInstant()));
//        Mockito.when(tokenService.tokenGenerator(user())).thenReturn(tokenDto());

        TokenDto tokenDto = authService.login(authenticationDto);

        Assertions.assertNotNull(tokenDto);
        tokenDto.tokenType();
    }


    @Test
    void shoudReturnException_WhenLoginWithInvalidUser() {
        Authentication authentication = Mockito.mock(Authentication.class);
        AuthenticationDto authenticationDto = new AuthenticationDto("deyky", "teste@teste.com");
        UsernamePasswordAuthenticationToken credentials = new UsernamePasswordAuthenticationToken(authenticationDto.email(), authenticationDto.password());
        Mockito.when(authenticationManager.authenticate(credentials)).thenThrow(new UsernameNotFoundException("invalid user"));

        UsernameNotFoundException error =
                Assertions.assertThrows(UsernameNotFoundException.class, () -> {
                    authService.login(authenticationDto);
                });

        Assertions.assertTrue(error instanceof UsernameNotFoundException);
    }


    private User user() {
        Date date = new Date();
        Set<GrantedAuthority> authorityList = new HashSet<>();
        User user = new User(1l, "deyky", "teste@teste.com", date);
        return user;
    }

    private TokenDto tokenDto() {
        String accessToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJkZXlreUB0ZXN0ZS5jb20iLCJjaGF2ZSB0ZXN0IjoidmFsb3IgdGVzdGUiLCJpc3MiOiJhdXRoMCIsImV4cCI6MTY5NDczMTIwOH0.6WdZkLzotu_NAKhsVEoqgpv_EBUYjOsw_aN7Q7azpCo";
        return new TokenDto("accessToken", "Bearer", new Date().toInstant().plus(Duration.ofMinutes(30)));
    }
}