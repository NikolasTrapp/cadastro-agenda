package br.com.trapp.cadastroagendabackend.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.time.OffsetDateTime;
import java.util.List;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiError {

    private Integer status;
    private String type;
    private String title;
    private String detail;
    private String userMessage;
    private OffsetDateTime timestamp;

    private List<FieldValidationError> fieldErrors;

    @Data
    @Builder
    public static class FieldValidationError {
        private String field;
        private String message;
    }

}