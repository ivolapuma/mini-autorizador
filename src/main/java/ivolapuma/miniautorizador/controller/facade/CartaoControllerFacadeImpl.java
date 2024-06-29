package ivolapuma.miniautorizador.controller.facade;

import ivolapuma.miniautorizador.dto.CriaCartaoRequestDTO;
import ivolapuma.miniautorizador.dto.CriaCartaoResponseDTO;
import ivolapuma.miniautorizador.entity.CartaoEntity;
import ivolapuma.miniautorizador.exception.NotFoundEntityException;
import ivolapuma.miniautorizador.exception.UnprocessableEntityException;
import ivolapuma.miniautorizador.service.CartaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class CartaoControllerFacadeImpl implements CartaoControllerFacade {

    @Autowired
    private CartaoService service;

    @Override
    public ResponseEntity<CriaCartaoResponseDTO> post(CriaCartaoRequestDTO request) throws Throwable {
        service.validate(request);
        CartaoEntity cartao = buildCartao(request);
        try {
            CartaoEntity created = service.create(cartao);
            return ResponseEntity.status(HttpStatus.CREATED).body(buildResponse(created));
        } catch (UnprocessableEntityException e) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(buildResponse(cartao));
        }
    }

    private static CriaCartaoResponseDTO buildResponse(CartaoEntity created) {
        CriaCartaoResponseDTO response = new CriaCartaoResponseDTO();
        response.setNumeroCartao(String.valueOf(created.getNumeroCartao()));
        response.setSenha(String.valueOf(created.getSenha()));
        return response;
    }

    private static CartaoEntity buildCartao(CriaCartaoRequestDTO request) {
        CartaoEntity cartao = new CartaoEntity();
        cartao.setNumeroCartao(Long.valueOf(request.getNumeroCartao()));
        cartao.setSenha(Integer.valueOf(request.getSenha()));
        return cartao;
    }

    @Override
    public ResponseEntity<BigDecimal> getSaldoByNumeroCartao(String numeroCartao) throws Throwable {
        service.validateNumeroCartao(numeroCartao);
        try {
            BigDecimal saldo = service.getSaldo(Long.valueOf(numeroCartao));
            return ResponseEntity.status(HttpStatus.OK).body(saldo);
        } catch (NotFoundEntityException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}
