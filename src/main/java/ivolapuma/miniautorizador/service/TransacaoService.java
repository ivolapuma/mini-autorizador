package ivolapuma.miniautorizador.service;

import ivolapuma.miniautorizador.dto.ExecuteTransacaoRequestDTO;
import ivolapuma.miniautorizador.entity.TransacaoEntity;

public interface TransacaoService {
    void validate(ExecuteTransacaoRequestDTO request) throws Throwable;

    TransacaoEntity execute(TransacaoEntity transacao) throws Throwable;

}
