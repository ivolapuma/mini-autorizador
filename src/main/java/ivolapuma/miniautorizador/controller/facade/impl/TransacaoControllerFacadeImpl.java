package ivolapuma.miniautorizador.controller.facade.impl;

import ivolapuma.miniautorizador.controller.facade.TransacaoControllerFacade;
import ivolapuma.miniautorizador.dto.ExecuteTransacaoRequestDTO;
import ivolapuma.miniautorizador.entity.TransacaoEntity;
import ivolapuma.miniautorizador.exception.BadRequestException;
import ivolapuma.miniautorizador.exception.InsufficientSaldoException;
import ivolapuma.miniautorizador.exception.InvalidSenhaCartaoException;
import ivolapuma.miniautorizador.exception.NotFoundEntityException;
import ivolapuma.miniautorizador.service.TransacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class TransacaoControllerFacadeImpl implements TransacaoControllerFacade {

    @Autowired
    private TransacaoService service;

    @Override
    public ResponseEntity<String> post(ExecuteTransacaoRequestDTO request) throws BadRequestException {
        service.validate(request);
        TransacaoEntity transacao = new TransacaoEntity();
        transacao.setNumeroCartao(Long.valueOf(request.getNumeroCartao()));
        transacao.setSenhaCartao(Integer.valueOf(request.getSenhaCartao()));
        transacao.setValor(request.getValor());
        HttpStatus status;
        String body;
        try {
            service.execute(transacao);
            status = HttpStatus.CREATED;
            body = "OK";
            service.log(transacao);
        } catch (InvalidSenhaCartaoException e) {
            status = HttpStatus.UNPROCESSABLE_ENTITY;
            body = "SENHA_INVALIDA";
            transacao.setMotivoFalha(body);
            service.log(transacao);
        } catch (InsufficientSaldoException e) {
            status = HttpStatus.UNPROCESSABLE_ENTITY;
            body = "SALDO_INSUFICIENTE";
            transacao.setMotivoFalha(body);
            service.log(transacao);
        } catch (NotFoundEntityException e) {
            status = HttpStatus.UNPROCESSABLE_ENTITY;
            body = "CARTAO_INEXISTENTE";
            transacao.setMotivoFalha(body);
            service.log(transacao);
        }
        return ResponseEntity.status(status).body(body);
    }

}
