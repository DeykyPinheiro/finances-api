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
}

