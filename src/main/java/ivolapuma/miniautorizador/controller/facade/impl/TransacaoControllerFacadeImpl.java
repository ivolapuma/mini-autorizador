package ivolapuma.miniautorizador.controller.facade.impl;

import ivolapuma.miniautorizador.builder.GenericBuilder;
import ivolapuma.miniautorizador.controller.facade.TransacaoControllerFacade;
import ivolapuma.miniautorizador.dto.ExecuteTransacaoRequestDTO;
import ivolapuma.miniautorizador.entity.TransacaoEntity;
import ivolapuma.miniautorizador.exception.BadRequestException;
import ivolapuma.miniautorizador.exception.InsufficientSaldoException;
import ivolapuma.miniautorizador.exception.InvalidSenhaCartaoException;
import ivolapuma.miniautorizador.exception.NotFoundEntityException;
import ivolapuma.miniautorizador.service.TransacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class TransacaoControllerFacadeImpl implements TransacaoControllerFacade {

    @Autowired
    private TransacaoService service;

    @Autowired
    private MessageSource messages;

    @Override
    public ResponseEntity<String> post(ExecuteTransacaoRequestDTO request) throws BadRequestException {
        service.validate(request);
        TransacaoEntity transacao =
            GenericBuilder.of(TransacaoEntity::new)
                    .with(TransacaoEntity::setNumeroCartao, Long.valueOf(request.getNumeroCartao()))
                    .with(TransacaoEntity::setSenhaCartao, Integer.valueOf(request.getSenhaCartao()))
                    .with(TransacaoEntity::setValor, request.getValor())
                    .build();
        HttpStatus status;
        String body;
        try {
            service.execute(transacao);
            status = HttpStatus.CREATED;
            body = messages.getMessage("response.body.ok", null, null);
            service.log(transacao);
        } catch (InvalidSenhaCartaoException e) {
            status = HttpStatus.UNPROCESSABLE_ENTITY;
            body = messages.getMessage("response.body.senhaInvalida", null, null);
            transacao.setMotivoFalha(body);
            service.log(transacao);
        } catch (InsufficientSaldoException e) {
            status = HttpStatus.UNPROCESSABLE_ENTITY;
            body = messages.getMessage("response.body.saldoInsuficiente", null, null);
            transacao.setMotivoFalha(body);
            service.log(transacao);
        } catch (NotFoundEntityException e) {
            status = HttpStatus.UNPROCESSABLE_ENTITY;
            body = messages.getMessage("response.body.cartaoInexistente", null, null);
            transacao.setMotivoFalha(body);
            service.log(transacao);
        }
        return ResponseEntity.status(status).body(body);
    }

}
