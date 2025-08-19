package com.WeimarJr.ApiDeNotasETarefas.ApiCrudDeNotasETarefas.Exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler{

    @ExceptionHandler(ExceptionsNota.class)
    private ResponseEntity<String> exceptionsNota(ExceptionsNota exception)
    {
        return ResponseEntity.status(417).body(exception.getMessage());
    }
    @ExceptionHandler(ExceptionsTarefa.class)
    private ResponseEntity<String> exceptionsTarefa(ExceptionsTarefa exception)
    {
        return ResponseEntity.status(417).body(exception.getMessage());
    }
}
