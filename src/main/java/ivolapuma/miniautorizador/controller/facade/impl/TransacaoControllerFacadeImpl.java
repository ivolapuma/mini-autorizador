package ivolapuma.miniautorizador.controller.facade.impl;

import ivolapuma.miniautorizador.controller.facade.TransacaoControllerFacade;
import ivolapuma.miniautorizador.dto.ExecuteTransacaoRequestDTO;
import ivolapuma.miniautorizador.entity.CartaoEntity;
import ivolapuma.miniautorizador.entity.TransacaoEntity;
import ivolapuma.miniautorizador.service.CartaoService;
import ivolapuma.miniautorizador.service.TransacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class TransacaoControllerFacadeImpl implements TransacaoControllerFacade {

    @Autowired
    private TransacaoService service;

    @Override
    public ResponseEntity<String> post(ExecuteTransacaoRequestDTO request) throws Throwable {
        service.validate(request);
        TransacaoEntity transacao = new TransacaoEntity();
        transacao.setNumeroCartao(Long.valueOf(request.getNumeroCartao()));
        transacao.setSenhaCartao(Integer.valueOf(request.getSenhaCartao()));
        transacao.setValor(request.getValor());
        try {
            service.execute(transacao);
            return ResponseEntity.status(HttpStatus.CREATED).body("OK");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(e.getMessage());
        }
    }

}
