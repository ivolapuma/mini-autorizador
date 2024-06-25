package ivolapuma.miniautorizador.controller;

import ivolapuma.miniautorizador.dto.CriaCartaoRequestDTO;
import ivolapuma.miniautorizador.dto.CriaCartaoResponseDTO;
import ivolapuma.miniautorizador.service.CartaoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/cartoes")
public class CartaoController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CartaoController.class);

    @Autowired
    private CartaoService service;

    @PostMapping
    public ResponseEntity<CriaCartaoResponseDTO> create(@RequestBody CriaCartaoRequestDTO requestDto) {
        LOGGER.info("Chamado serviço de criação de cartão...");
        CriaCartaoResponseDTO responseDto = service.criarCartao(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @GetMapping("/{numeroCartao}")
    public ResponseEntity<BigDecimal> getSaldoByNumeroCartao(@PathVariable String numeroCartao) {
        LOGGER.info("Chamado serviço de consulta de saldo de cartão...");
        // TODO: implementacao da busca do saldo do cartao
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(null);
    }

}
