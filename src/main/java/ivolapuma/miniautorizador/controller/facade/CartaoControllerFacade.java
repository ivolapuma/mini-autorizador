package ivolapuma.miniautorizador.controller.facade;

import ivolapuma.miniautorizador.dto.CreateCartaoRequestDTO;
import ivolapuma.miniautorizador.dto.CreateCartaoResponseDTO;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;

public interface CartaoControllerFacade {

    ResponseEntity<CreateCartaoResponseDTO> post(CreateCartaoRequestDTO criaCartaoRequestDTO) throws Throwable;

    ResponseEntity<BigDecimal> getSaldoByNumeroCartao(String numeroCartao) throws Throwable;
}
