package ivolapuma.miniautorizador.controller;

import ivolapuma.miniautorizador.dto.CreateCartaoRequestDTO;
import ivolapuma.miniautorizador.dto.CreateCartaoResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/cartoes")
public class CartoesController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CartoesController.class);

    @PostMapping
    public ResponseEntity<CreateCartaoResponseDTO> create(@RequestBody CreateCartaoRequestDTO requestDto) {
        LOGGER.info("Chamado serviço de criação de cartão...");
        // TODO: implementacao da criacao do cartao
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(null);
    }

    @GetMapping("/{numeroCartao}")
    public ResponseEntity<BigDecimal> getSaldoByNumeroCartao(@PathVariable String numeroCartao) {
        LOGGER.info("Chamado serviço de consulta de saldo de cartão...");
        // TODO: implementacao da busca do saldo do cartao
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(null);
    }

}
