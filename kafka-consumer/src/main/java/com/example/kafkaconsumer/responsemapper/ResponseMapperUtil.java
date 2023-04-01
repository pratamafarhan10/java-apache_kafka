package com.example.kafkaconsumer.responsemapper;

import com.example.kafkaconsumer.responsemapper.dto.ErrorSchema;
import com.example.kafkaconsumer.responsemapper.dto.Response;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class ResponseMapperUtil {

    public Response isSuccess(Object data){
        return Response.builder()
                .errorSchema(
                        ErrorSchema.builder()
                                .errorCode(HttpStatus.OK.toString())
                                .errorMessage(ErrorSchema.ErrorMessage.builder()
                                        .errorEn(HttpStatus.OK.getReasonPhrase())
                                        .errorId("OK")
                                        .build())
                                .build()
                )
                .outputSchema(data)
                .build();
    }

    public Response isNotFound(Object data){
        return Response.builder()
                .errorSchema(
                        ErrorSchema.builder()
                                .errorCode(HttpStatus.NOT_FOUND.toString())
                                .errorMessage(ErrorSchema.ErrorMessage.builder()
                                        .errorEn(HttpStatus.NOT_FOUND.getReasonPhrase())
                                        .errorId("Tidak Ditemukan")
                                        .build())
                                .build()
                )
                .outputSchema(data)
                .build();
    }
}
