package com.finances.api.exceptionhandler;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

import java.time.OffsetDateTime;
import java.util.List;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Problem {

    private Integer status;

    private String type;

    private String title;

    private String detail;

    List<Object> objects;

    OffsetDateTime date;

    @Getter
    @Builder
    static class Object {

        String name;

        String userMessage;

    }

}
