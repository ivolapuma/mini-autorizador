package ivolapuma.miniautorizador.controller.facade.impl;

import ivolapuma.miniautorizador.controller.facade.CartaoControllerFacade;
import ivolapuma.miniautorizador.dto.CreateCartaoRequestDTO;
import ivolapuma.miniautorizador.dto.CreateCartaoResponseDTO;
import ivolapuma.miniautorizador.entity.CartaoEntity;
import ivolapuma.miniautorizador.exception.BadRequestException;
import ivolapuma.miniautorizador.exception.InvalidNumeroCartaoException;
import ivolapuma.miniautorizador.exception.NotFoundEntityException;
import ivolapuma.miniautorizador.exception.UnprocessableEntityException;
import ivolapuma.miniautorizador.service.CartaoService;
import ivolapuma.miniautorizador.service.NumeroCartaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class CartaoControllerFacadeImpl implements CartaoControllerFacade {

    @Autowired
    private CartaoService service;

    @Autowired
    private NumeroCartaoService numeroCartaoService;

    @Override
    public ResponseEntity<CreateCartaoResponseDTO> post(CreateCartaoRequestDTO request) throws BadRequestException {
        service.validate(request);
        CartaoEntity cartao = buildCartao(request);
        try {
            CartaoEntity created = service.create(cartao);
            return ResponseEntity.status(HttpStatus.CREATED).body(buildResponse(created));
        } catch (UnprocessableEntityException e) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(buildResponse(cartao));
        }
    }

    private static CreateCartaoResponseDTO buildResponse(CartaoEntity created) {
        CreateCartaoResponseDTO response = new CreateCartaoResponseDTO();
        response.setNumeroCartao(String.valueOf(created.getNumeroCartao()));
        response.setSenha(String.valueOf(created.getSenha()));
        return response;
    }

    private static CartaoEntity buildCartao(CreateCartaoRequestDTO request) {
        CartaoEntity cartao = new CartaoEntity();
        cartao.setNumeroCartao(Long.valueOf(request.getNumeroCartao()));
        cartao.setSenha(Integer.valueOf(request.getSenha()));
        return cartao;
    }

    @Override
    public ResponseEntity<BigDecimal> getSaldoByNumeroCartao(String numeroCartao) throws BadRequestException {
        try {
            numeroCartaoService.validate(numeroCartao);
            BigDecimal saldo = service.getSaldo(Long.valueOf(numeroCartao));
            return ResponseEntity.status(HttpStatus.OK).body(saldo);
        } catch (InvalidNumeroCartaoException e) {
            throw new BadRequestException(e.getMessage(), e);
        } catch (NotFoundEntityException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}
