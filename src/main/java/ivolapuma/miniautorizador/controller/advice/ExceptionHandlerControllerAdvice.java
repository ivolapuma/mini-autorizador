package ivolapuma.miniautorizador.controller.advice;

import ivolapuma.miniautorizador.dto.ErrorResponseDTO;
import ivolapuma.miniautorizador.exception.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice
public class ExceptionHandlerControllerAdvice {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponseDTO> handle(BadRequestException e, WebRequest request) {
        // TODO: implementar o GenericBuilder
        ErrorResponseDTO response = new ErrorResponseDTO();
        response.setError(e.getClass().getName());
        response.setMessage(e.getMessage());
        response.setTimestamp(LocalDateTime.now());
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handle(Exception e, WebRequest request) {
        // TODO: implementar o GenericBuilder
        ErrorResponseDTO response = new ErrorResponseDTO();
        response.setError(e.getClass().getName());
        response.setMessage(e.getMessage());
        response.setTimestamp(LocalDateTime.now());
        return ResponseEntity.internalServerError().body(response);
    }
}
