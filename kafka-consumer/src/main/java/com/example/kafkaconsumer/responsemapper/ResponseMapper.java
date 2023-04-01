package com.example.kafkaconsumer.responsemapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ResponseMapper {
    @Autowired
    private ResponseMapperUtil util;

    public ResponseEntity<?> isSuccess(Object data){
        return ResponseEntity.status(HttpStatus.OK).body(util.isSuccess(data));
    }

    public ResponseEntity<?> isNotFound(Object data){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(util.isNotFound(data));
    }

}
