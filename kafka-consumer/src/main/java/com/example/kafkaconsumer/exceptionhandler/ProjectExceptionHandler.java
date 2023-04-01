package com.example.kafkaconsumer.exceptionhandler;


import com.example.kafkaconsumer.exceptionhandler.exceptions.NotFoundException;
import com.example.kafkaconsumer.responsemapper.ResponseMapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ProjectExceptionHandler extends ResponseEntityExceptionHandler {

    @Autowired
    private ResponseMapperUtil responseMapperUtil;

    @ExceptionHandler(NotFoundException.class)
    protected ResponseEntity<?> notFoundHandler(NotFoundException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseMapperUtil.isNotFound(ex.getData()));
    }
}
