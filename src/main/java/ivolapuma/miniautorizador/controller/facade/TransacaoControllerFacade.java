package ivolapuma.miniautorizador.controller.facade;

import ivolapuma.miniautorizador.dto.ExecuteTransacaoRequestDTO;
import org.springframework.http.ResponseEntity;

public interface TransacaoControllerFacade {

    ResponseEntity<String> post(ExecuteTransacaoRequestDTO request);

}
