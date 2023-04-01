package com.example.kafkaconsumer.responsemapper.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorSchema {
    private String errorCode;
    private ErrorMessage errorMessage;
    @Data
    @Builder
    public static class ErrorMessage{
        private String errorEn;
        private String errorId;
    }
}
