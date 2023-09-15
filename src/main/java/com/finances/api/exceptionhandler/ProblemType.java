package com.finances.api.exceptionhandler;

import lombok.Getter;

@Getter
public enum ProblemType {

    RESOURCE_NOT_FOUND("/recurso-nao-encontrado", "recurso nao encontrado"),

    INVALID_DATA("/dados-invalidos", "dados invalidos");


    private String title;

    private String uri;

    ProblemType(String path, String title) {
        this.uri = "URI_AQUI_NO_BACKEND" + path;
        this.title = title;
    }

}