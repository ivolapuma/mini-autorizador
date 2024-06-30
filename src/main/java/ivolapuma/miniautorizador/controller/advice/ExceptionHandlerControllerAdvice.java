package ivolapuma.miniautorizador.controller.advice;

import ivolapuma.miniautorizador.builder.GenericBuilder;
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
        ErrorResponseDTO response =
            GenericBuilder.of(ErrorResponseDTO::new)
                    .with(ErrorResponseDTO::setError, e.getClass().getName())
                    .with(ErrorResponseDTO::setMessage, e.getMessage())
                    .with(ErrorResponseDTO::setTimestamp, LocalDateTime.now())
                    .build();
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handle(Exception e, WebRequest request) {
        ErrorResponseDTO response =
                GenericBuilder.of(ErrorResponseDTO::new)
                        .with(ErrorResponseDTO::setError, e.getClass().getName())
                        .with(ErrorResponseDTO::setMessage, e.getMessage())
                        .with(ErrorResponseDTO::setTimestamp, LocalDateTime.now())
                        .build();
        return ResponseEntity.internalServerError().body(response);
    }
}
