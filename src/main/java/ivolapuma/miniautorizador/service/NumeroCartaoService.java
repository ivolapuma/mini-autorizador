package ivolapuma.miniautorizador.service;

import ivolapuma.miniautorizador.exception.InvalidNumeroCartaoException;

public interface NumeroCartaoService {

    void validate(String numeroCartao) throws InvalidNumeroCartaoException;
}
