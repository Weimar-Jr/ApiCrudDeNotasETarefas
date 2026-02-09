package com.WeimarJr.ApiDeNotasETarefas.ApiCrudDeNotasETarefas.Exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler{

    @ExceptionHandler(NotaException.class)
    private ResponseEntity<String> exceptionsNota(NotaException exception)
    {
        return ResponseEntity.status(417).body(exception.getMessage());
    }
    @ExceptionHandler(TarefaException.class)
    private ResponseEntity<String> exceptionsTarefa(TarefaException exception)
    {
        return ResponseEntity.status(417).body(exception.getMessage());
    }
}
