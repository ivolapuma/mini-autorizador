package ivolapuma.miniautorizador.controller.facade;

import ivolapuma.miniautorizador.dto.RealizaTransacaoRequestDTO;
import ivolapuma.miniautorizador.entity.CartaoEntity;
import ivolapuma.miniautorizador.service.CartaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class TransacaoControllerFacadeImpl implements TransacaoControllerFacade {

    @Autowired
    private CartaoService cartaoService;

    @Override
    public ResponseEntity<String> post(RealizaTransacaoRequestDTO request) {
        // TODO: verificar se cartao existe
        // TODO: validar senha do cartao
        // TODO: validar saldo do cartao
        // TODO: atualizar saldo
        Long numeroCartao = Long.valueOf(request.getNumeroCartao());
        BigDecimal valor = request.getValor();
        CartaoEntity cartao = cartaoService.buscarCartao(numeroCartao);
        cartaoService.atualizarSaldo(cartao, valor);
        return ResponseEntity.status(HttpStatus.CREATED).body("OK");
    }

}
