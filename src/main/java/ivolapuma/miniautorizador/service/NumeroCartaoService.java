package ivolapuma.miniautorizador.service;

import ivolapuma.miniautorizador.exception.InvalidNumeroCartaoException;

/**
 * Interface que define os serviços referentes ao domínio de Número de Cartão do Miniautorizador.
 */
public interface NumeroCartaoService {

    /**
     * Serviço específico para validar um Número de Cartão informado.
     *
     * @param numeroCartao
     * @throws InvalidNumeroCartaoException Lançado quando o Número de Cartão é inválido para os padrões da aplicação.
     */
    void validate(String numeroCartao) throws InvalidNumeroCartaoException;
}
