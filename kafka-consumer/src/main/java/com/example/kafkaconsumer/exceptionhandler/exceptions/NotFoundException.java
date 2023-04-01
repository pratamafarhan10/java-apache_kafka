package com.example.kafkaconsumer.exceptionhandler.exceptions;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@Setter
@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends Exception{
    private Object data;
    public NotFoundException(String message, Object data){
        super(message);
        this.data = data;
    }
}
