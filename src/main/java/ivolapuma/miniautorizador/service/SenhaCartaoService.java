package ivolapuma.miniautorizador.service;

import ivolapuma.miniautorizador.exception.InvalidSenhaCartaoException;

public interface SenhaCartaoService {

    void validate(String senha) throws InvalidSenhaCartaoException;

    void validate(Integer expected, Integer actual) throws InvalidSenhaCartaoException;
}
