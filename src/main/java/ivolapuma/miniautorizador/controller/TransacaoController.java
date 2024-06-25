package ivolapuma.miniautorizador.controller;

import ivolapuma.miniautorizador.dto.RealizaTransacaoRequestDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transacoes")
public class TransacaoController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TransacaoController.class);

    @PostMapping
    public ResponseEntity<String> create(@RequestBody RealizaTransacaoRequestDTO requestDto) {
        LOGGER.info("Chamado serviço de consulta de saldo de cartão...");
        // TODO: implementacao da realizacao da transacao
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(null);
    }

}
