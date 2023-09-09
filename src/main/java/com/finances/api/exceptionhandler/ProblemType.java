package com.finances.api.exceptionhandler;

import lombok.Getter;

@Getter
public enum ProblemType {

    RECURSO_NAO_ENCONTRADO("/recurso-nao-encontrado", "recurso nao encontrado");


    private String title;

    private String uri;

    ProblemType(String path, String title) {
        this.uri = "URI_AQUI_NO_BACKEND" + path;
        this.title = title;
    }

}