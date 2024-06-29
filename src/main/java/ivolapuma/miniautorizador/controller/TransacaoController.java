package ivolapuma.miniautorizador.controller;

import ivolapuma.miniautorizador.controller.facade.TransacaoControllerFacade;
import ivolapuma.miniautorizador.dto.ExecuteTransacaoRequestDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transacoes")
public class TransacaoController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TransacaoController.class);

    @Autowired
    private TransacaoControllerFacade facade;

    @PostMapping
    public ResponseEntity<String> post(@RequestBody ExecuteTransacaoRequestDTO request) throws Throwable {
        LOGGER.info("Chamado serviço de realização de transação...");
        return facade.post(request);
    }

}
