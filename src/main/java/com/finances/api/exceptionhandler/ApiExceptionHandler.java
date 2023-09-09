package com.finances.api.exceptionhandler;

import com.finances.domain.exception.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@Slf4j
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
                                                             HttpStatusCode statusCode, WebRequest request) {
        if (body == null) {
            body = Problem.builder().title(statusCode.toString()).status(statusCode.value()).build();
        } else if (body instanceof String) {
            body = Problem.builder().title(ex.getMessage()).status(statusCode.value()).build();
        }
        return super.handleExceptionInternal(ex, body, headers, statusCode, request);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> handleEntidadeEmUsoException(EntityNotFoundException ex, WebRequest request) {

        HttpHeaders header = new HttpHeaders();
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;
        String details = ex.getMessage();
        Problem problem = createProblemBuilder(httpStatus, ProblemType.RECURSO_NAO_ENCONTRADO, details).build();

        return handleExceptionInternal(ex, problem, header, httpStatus, request);
    }

    private Problem.ProblemBuilder createProblemBuilder(HttpStatus status, ProblemType problemType, String details) {
        return Problem.builder().status(status.value()).type(problemType.getUri()).title(problemType.getTitle()).detail(details);
    }
}
