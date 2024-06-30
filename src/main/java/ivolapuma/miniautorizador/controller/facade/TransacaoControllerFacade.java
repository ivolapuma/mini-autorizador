package ivolapuma.miniautorizador.controller.facade;

import ivolapuma.miniautorizador.dto.ExecuteTransacaoRequestDTO;
import ivolapuma.miniautorizador.exception.BadRequestException;
import org.springframework.http.ResponseEntity;

/**
 * Interface Facade para os métodos definidos em TransacaoController.
 * Cada método em TransacaoController deve ter um comportamento definido no Facade.
 */
public interface TransacaoControllerFacade {

    /**
     * Método facade para serviço POST de realização de transação.
     * @param request
     * @return
     * @throws BadRequestException
     */
    ResponseEntity<String> post(ExecuteTransacaoRequestDTO request) throws BadRequestException;

}
