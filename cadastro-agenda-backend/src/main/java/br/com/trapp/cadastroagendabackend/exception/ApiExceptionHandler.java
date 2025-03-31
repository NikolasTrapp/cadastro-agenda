package br.com.trapp.cadastroagendabackend.exception;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.PropertyBindingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.flywaydb.core.internal.util.ExceptionUtils;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static br.com.trapp.cadastroagendabackend.util.Utils.format;
import static java.util.Objects.isNull;
import static org.apache.commons.lang3.exception.ExceptionUtils.getRootCause;

@Slf4j
@ControllerAdvice
@RequiredArgsConstructor
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    private static final String GENERIC_ERROR_MESSAGE = "Um erro inesperado aconteceu. Por gentileza tente novamente mais tarde.";
    private static final String RESOURCE_NOT_FOUND_MESSAGE = "Recurso não encontrado.";
    private static final String TITLE_GENERIC_ERROR = "Ocorreu um erro a processar a requisicao.";
    private static final String URI = "/api/v1/errors";

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotAcceptable(HttpMediaTypeNotAcceptableException ex,
                                                                      HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        var problem = createProblemBuilder(status, "Media type is not acceptable.", URI, TITLE_GENERIC_ERROR).build();
        return ResponseEntity.status(status).headers(headers).body(problem);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        return handleValidationInternal(ex, headers, status, request, ex.getBindingResult());
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
                                                             HttpStatusCode status, WebRequest request) {
        var mensagem = getRootCause(ex).getMessage();
        if (isNull(body)) {
            body = ApiError.builder()
                    .timestamp(OffsetDateTime.now())
                    .title(TITLE_GENERIC_ERROR)
                    .status(status.value())
                    .userMessage(GENERIC_ERROR_MESSAGE)
                    .detail(mensagem)
                    .build();
        } else if (body instanceof String strBody) {
            body = ApiError.builder()
                    .timestamp(OffsetDateTime.now())
                    .title(strBody)
                    .status(status.value())
                    .detail(mensagem)
                    .userMessage(GENERIC_ERROR_MESSAGE)
                    .build();
        }

        return super.handleExceptionInternal(ex, body, headers, status, request);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleUncaught(Exception ex, WebRequest request) {
        var status = HttpStatus.INTERNAL_SERVER_ERROR;
        var detail = getRootCause(ex).getMessage();

        log.error(ex.getMessage(), ex);

        var problem = createProblemBuilder(status, detail, URI, TITLE_GENERIC_ERROR)
                .userMessage(detail)
                .build();

        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex,
                                                                   HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        var detail = format("O recurso '{}' nao existe.", ex.getRequestURL());

        var problem = createProblemBuilder(status, detail, URI, TITLE_GENERIC_ERROR)
                .userMessage(RESOURCE_NOT_FOUND_MESSAGE)
                .build();

        return handleExceptionInternal(ex, problem, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers,
                                                        HttpStatusCode status, WebRequest request) {

        if (ex instanceof MethodArgumentTypeMismatchException matme) {
            return handleMethodArgumentTypeMismatch(matme, headers, status, request);
        }

        return super.handleTypeMismatch(ex, headers, status, request);
    }

    private ResponseEntity<Object> handleMethodArgumentTypeMismatch(
            MethodArgumentTypeMismatchException ex, HttpHeaders headers,
            HttpStatusCode status, WebRequest request) {

        var detail = format("O parametro '{}' recebeu um valor invalido: '{}'. Tipo esperado: '{}'.",
                ex.getName(), ex.getValue(), ex.getRequiredType().getSimpleName());

        var problem = createProblemBuilder(status, detail, URI, TITLE_GENERIC_ERROR)
                .userMessage(GENERIC_ERROR_MESSAGE)
                .build();

        return handleExceptionInternal(ex, problem, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                  HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        var rootCause = ExceptionUtils.getRootCause(ex);

        if (rootCause instanceof InvalidFormatException ife) {
            return handleInvalidFormat(ife, headers, status, request);
        } else if (rootCause instanceof PropertyBindingException pbe) {
            return handlePropertyBinding(pbe, headers, status, request);
        }

        var detail = "O corpo da requisição é inválido. Por favor, verifique se há erros de sintaxe.";

        var problem = createProblemBuilder(status, detail, URI, TITLE_GENERIC_ERROR)
                .userMessage(GENERIC_ERROR_MESSAGE)
                .build();

        return handleExceptionInternal(ex, problem, headers, status, request);
    }

    private ResponseEntity<Object> handlePropertyBinding(PropertyBindingException ex,
                                                         HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        var path = joinPath(ex.getPath());

        var detail = format("A propriedade '{}' não existe. Por favor, corrija ou remova essa propriedade e tente novamente.", path);

        var problem = createProblemBuilder(status, detail, URI, TITLE_GENERIC_ERROR)
                .userMessage(GENERIC_ERROR_MESSAGE)
                .build();

        return handleExceptionInternal(ex, problem, headers, status, request);
    }

    private ResponseEntity<Object> handleInvalidFormat(InvalidFormatException ex,
                                                       HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        var path = joinPath(ex.getPath());
        var detail = format("A propriedade '{}' recebeu o valor '{}', " +
                        "que é de um tipo inválido. Por favor, corrija e forneça um valor compatível com o tipo {}.",
                path, ex.getValue(), ex.getTargetType().getSimpleName());


        var problem = createProblemBuilder(status, detail, URI, TITLE_GENERIC_ERROR)
                .userMessage(GENERIC_ERROR_MESSAGE)
                .build();

        return handleExceptionInternal(ex, problem, headers, status, request);
    }

    private ResponseEntity<Object> handleValidationInternal(Exception ex, HttpHeaders headers,
                                                            HttpStatusCode status, WebRequest request, BindingResult bindingResult) {
        var detail = "Um ou mais campos estao invalidos.";

        var fieldValidationErrors = bindingResult.getAllErrors().stream()
                .map(objectError -> {
                    var message = objectError.getDefaultMessage();
                    var name = objectError.getObjectName();

                    if (objectError instanceof FieldError fe) {
                        name = fe.getField();
                    }

                    return ApiError.FieldValidationError.builder()
                            .field(name)
                            .message(message)
                            .build();
                })
                .collect(Collectors.toList());

        var apiError = createProblemBuilder(status, detail, URI, "Erro de validação")
                .userMessage(detail)
                .fieldErrors(fieldValidationErrors)
                .build();

        return handleExceptionInternal(ex, apiError, headers, status, request);
    }

    private ApiError.ApiErrorBuilder createProblemBuilder(HttpStatusCode status, String detail, String uri, String title) {
        return ApiError.builder()
                .timestamp(OffsetDateTime.now())
                .status(status.value())
                .type(uri)
                .title(title)
                .detail(detail);
    }

    private String joinPath(List<JsonMappingException.Reference> references) {
        return references.stream()
                .map(JsonMappingException.Reference::getFieldName)
                .collect(Collectors.joining("."));
    }
}
