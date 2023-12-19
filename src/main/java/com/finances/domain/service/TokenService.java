package com.finances.domain.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.finances.domain.dto.token.TokenDto;
import com.finances.domain.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Date;

@Service
public class TokenService {


    private JwtKeyStoreService jwtKeyStoreService;

    @Autowired
    public TokenService(JwtKeyStoreService jwtKeyStoreService) {
        this.jwtKeyStoreService = jwtKeyStoreService;
    }

    //    TODO
//    should customize token
//    configure Public Key on decode JWT
    public TokenDto tokenGenerator(User user) {
//
        try

    {

        Algorithm algorithm = Algorithm.RSA256(jwtKeyStoreService.getPublicKey(), jwtKeyStoreService.getPrivateKey());
        String jwt = JWT.create()
                .withSubject(user.getEmail())
                .withClaim("chave test", "valor teste, pra saber se ta certo")
                .withIssuer("quem é o auth server")
                .withExpiresAt(new Date().toInstant().plus(Duration.ofMinutes(1440)))
                .sign(algorithm);

        return new TokenDto(jwt, "Bearer", new Date().toInstant().plus(Duration.ofMinutes(1440)));
    } catch(Exception
    exception)

    {
        throw new RuntimeException("teste");
    }
}

    public String validateToken(String token){
        try {
            Algorithm algorithm = Algorithm.RSA256(jwtKeyStoreService.getPublicKey(), jwtKeyStoreService.getPrivateKey());
            return JWT.require(algorithm)
                    .withIssuer("quem é o auth server")
                    .build()
                    .verify(token).
                    getSubject();
        } catch (JWTVerificationException exception) {
            throw new RuntimeException("Invalido ou Expirado");
        }
    }
}


// TODO first create authenticate flow functional