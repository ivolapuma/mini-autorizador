package ivolapuma.miniautorizador.controller;

import ivolapuma.miniautorizador.dto.CriaCartaoRequestDTO;
import ivolapuma.miniautorizador.dto.CriaCartaoResponseDTO;
import ivolapuma.miniautorizador.entity.CartaoEntity;
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

        CartaoEntity cartao = new CartaoEntity();
        cartao.setNumeroCartao(Long.valueOf(requestDto.getNumeroCartao()));
        cartao.setSenha(Integer.valueOf(requestDto.getSenha()));

        CartaoEntity cartaoCriado = service.criarCartao(cartao);

        CriaCartaoResponseDTO responseDto = new CriaCartaoResponseDTO();
        responseDto.setNumeroCartao(String.valueOf(cartaoCriado.getNumeroCartao()));
        responseDto.setSenha(String.valueOf(cartaoCriado.getNumeroCartao()));

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @GetMapping("/{numeroCartao}")
    public ResponseEntity<BigDecimal> getSaldoByNumeroCartao(@PathVariable String numeroCartao) {
        LOGGER.info("Chamado serviço de consulta de saldo de cartão...");
        BigDecimal saldo = service.consultarSaldo(Long.valueOf(numeroCartao));
        return ResponseEntity.status(HttpStatus.OK).body(saldo);
    }

}
