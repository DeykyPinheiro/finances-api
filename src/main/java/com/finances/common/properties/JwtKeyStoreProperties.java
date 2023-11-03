package com.finances.common.properties;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.MalformedParameterizedTypeException;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.HashMap;
import java.util.Map;


@Validated
@Component
@Getter
@Setter
@ConfigurationProperties("finances.jwt.keystore")
public class JwtKeyStoreProperties {

    @NotBlank
    private String password;

    @NotBlank
    private String keypairAlias;

    @NotNull
    private Resource jksLocation;


    public Map<String, Object> keyPair() throws Exception {
        KeyStore keyStore = KeyStore.getInstance("JKS");
        char[] keyStorePass = this.password.toCharArray();
        InputStream inputStream = jksLocation.getInputStream();

        keyStore.load(inputStream, keyStorePass);
        RSAPrivateKey privateKey = (RSAPrivateKey) keyStore.getKey(keypairAlias, keyStorePass);


        Certificate cert = keyStore.getCertificate(keypairAlias);
        PublicKey publicKey = cert.getPublicKey();

        Map rsaMap = new HashMap<>();
        rsaMap.put("privateKey", privateKey);
        rsaMap.put("publicKey", publicKey);
        return rsaMap;
    }

    public RSAPublicKey getPublicKey() {
        Map keyPair = new HashMap<>();
        try {
            keyPair = this.keyPair();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return (RSAPublicKey) keyPair.get("publicKey");
    }

    public RSAPrivateKey getPrivateKey() {
        Map keyPair = new HashMap<>();
        try {
            keyPair = this.keyPair();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return (RSAPrivateKey) keyPair.get("privateKey");
    }
}
