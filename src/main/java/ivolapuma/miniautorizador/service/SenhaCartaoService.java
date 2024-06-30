package ivolapuma.miniautorizador.service;

import ivolapuma.miniautorizador.exception.InvalidSenhaCartaoException;

/**
 * Interface que define os serviços referentes ao domínio de Senha do Cartão do Miniautorizador.
 */
public interface SenhaCartaoService {

    /**
     * Serviço específico para validar uma Senha de Cartão informado.
     *
     * @param senha
     * @throws InvalidSenhaCartaoException Lançado quando a Senha do Cartão é inválido para os padrões da aplicação.
     */
    void validate(String senha) throws InvalidSenhaCartaoException;

    /**
     * Serviço que verifica se a senha informada na Transação confere com a senha do Cartão.
     *
     * @param expected Senha esperada.
     * @param actual Senha informada.
     * @throws InvalidSenhaCartaoException
     */
    void validate(Integer expected, Integer actual) throws InvalidSenhaCartaoException;
}
