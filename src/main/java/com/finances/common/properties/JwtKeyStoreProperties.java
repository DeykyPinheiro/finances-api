package com.finances.common.properties;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;


// TODO
// configure properties
//@Validated
//@Component
//@Getter
//@Setter
//@ConfigurationProperties("finance.jwt.keystore")
//public class JwtKeyStoreProperties {
//
//
//    @NotBlank
//    private String password;
//
//    @NotBlank
//    private String keypairAlias;
//
//    @NotNull
//    private Resource jksLocation;
//
//
//}