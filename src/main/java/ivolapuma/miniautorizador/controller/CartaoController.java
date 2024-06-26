package ivolapuma.miniautorizador.controller;

import ivolapuma.miniautorizador.controller.facade.CartaoControllerFacade;
import ivolapuma.miniautorizador.dto.CreateCartaoRequestDTO;
import ivolapuma.miniautorizador.dto.CreateCartaoResponseDTO;
import ivolapuma.miniautorizador.exception.BadRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/cartoes")
public class CartaoController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CartaoController.class);

    @Autowired
    private CartaoControllerFacade facade;

    @PostMapping
    public ResponseEntity<CreateCartaoResponseDTO> post(@RequestBody CreateCartaoRequestDTO request) throws BadRequestException {
        LOGGER.info("Chamado serviço de criação de cartão");
        return facade.post(request);
    }

    @GetMapping("/{numeroCartao}")
    public ResponseEntity<BigDecimal> getSaldoByNumeroCartao(@PathVariable String numeroCartao) throws BadRequestException {
        LOGGER.info("Chamado serviço de consulta de saldo de cartão");
        return facade.getSaldoByNumeroCartao(numeroCartao);
    }

}
