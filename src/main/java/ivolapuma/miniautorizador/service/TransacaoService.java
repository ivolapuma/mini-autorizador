package ivolapuma.miniautorizador.service;

import ivolapuma.miniautorizador.dto.ExecuteTransacaoRequestDTO;
import ivolapuma.miniautorizador.entity.TransacaoEntity;
import ivolapuma.miniautorizador.exception.BadRequestException;
import ivolapuma.miniautorizador.exception.InsufficientSaldoException;
import ivolapuma.miniautorizador.exception.InvalidSenhaCartaoException;
import ivolapuma.miniautorizador.exception.NotFoundEntityException;

public interface TransacaoService {

    void validate(ExecuteTransacaoRequestDTO request) throws BadRequestException;

    TransacaoEntity execute(TransacaoEntity transacao) throws InvalidSenhaCartaoException, InsufficientSaldoException, NotFoundEntityException;

    void log(TransacaoEntity transacao);
}
