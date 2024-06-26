package ivolapuma.miniautorizador.controller.facade;

import ivolapuma.miniautorizador.dto.RealizaTransacaoRequestDTO;
import org.springframework.http.ResponseEntity;

public interface TransacaoControllerFacade {

    ResponseEntity<String> post(RealizaTransacaoRequestDTO request);

}
