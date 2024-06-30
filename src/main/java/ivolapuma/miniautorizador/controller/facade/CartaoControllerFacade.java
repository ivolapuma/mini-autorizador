package ivolapuma.miniautorizador.controller.facade;

import ivolapuma.miniautorizador.dto.CreateCartaoRequestDTO;
import ivolapuma.miniautorizador.dto.CreateCartaoResponseDTO;
import ivolapuma.miniautorizador.exception.BadRequestException;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;

/**
 * Interface Facade para os métodos definidos em CartaoController.
 * Cada método em CartaoController deve ter um comportamento definido no Facade.
 */
public interface CartaoControllerFacade {

    /**
     * Método facade para serviço POST de criação de Cartão.
     * @param criaCartaoRequestDTO
     * @return
     * @throws BadRequestException
     */
    ResponseEntity<CreateCartaoResponseDTO> post(CreateCartaoRequestDTO criaCartaoRequestDTO) throws BadRequestException;

    /**
     * Método facade para serviço GET de consulta de saldo de Cartão.
     * @param numeroCartao
     * @return
     * @throws BadRequestException
     */
    ResponseEntity<BigDecimal> getSaldoByNumeroCartao(String numeroCartao) throws BadRequestException;
}
