package ivolapuma.miniautorizador.controller.facade;

import ivolapuma.miniautorizador.dto.CriaCartaoRequestDTO;
import ivolapuma.miniautorizador.dto.CriaCartaoResponseDTO;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;

public interface CartaoControllerFacade {

    ResponseEntity<CriaCartaoResponseDTO> post(CriaCartaoRequestDTO criaCartaoRequestDTO);

    ResponseEntity<BigDecimal> getSaldoByNumeroCartao(String numeroCartao);
}
