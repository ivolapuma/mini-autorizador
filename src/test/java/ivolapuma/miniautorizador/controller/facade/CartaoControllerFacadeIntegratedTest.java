package ivolapuma.miniautorizador.controller.facade;

import ivolapuma.miniautorizador.dto.CriaCartaoRequestDTO;
import ivolapuma.miniautorizador.dto.CriaCartaoResponseDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class CartaoControllerFacadeIntegratedTest {

    @Autowired
    private CartaoControllerFacadeImpl facade;

    @Test
    @Sql(scripts = "/sql/basic-setup-before.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/sql/basic-setup-after.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void post_comCartaoNovo_deveRetornarStatusCreatedECartaoNoBody() {
        CriaCartaoRequestDTO request = new CriaCartaoRequestDTO();
        request.setNumeroCartao("1234123412341234");
        request.setSenha("1234");
        ResponseEntity<CriaCartaoResponseDTO> response = facade.post(request);
        Assertions.assertNotNull(response, "response nao pode ser nulo");
        Assertions.assertEquals(HttpStatus.CREATED.value(), response.getStatusCode().value(), "statusCode deve ser igual");
        CriaCartaoResponseDTO body = response.getBody();
        Assertions.assertNotNull(body, "response body nao pode ser nulo");
        Assertions.assertEquals(request.getNumeroCartao(), body.getNumeroCartao(), "numeroCartao deve ser igual");
        Assertions.assertEquals(request.getSenha(), body.getSenha(), "senha deve ser igual");
    }

    @Test
    @Sql(scripts = "/sql/basic-setup-before.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/sql/basic-setup-after.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void post_comCartaoJaExistente_deveRetornarStatusUnprocessableEntityECartaoNoBody() {
        CriaCartaoRequestDTO request = new CriaCartaoRequestDTO();
        request.setNumeroCartao("1111222233334444");
        request.setSenha("1234");
        ResponseEntity<CriaCartaoResponseDTO> response = facade.post(request);
        Assertions.assertNotNull(response, "response nao pode ser nulo");
        Assertions.assertEquals(HttpStatus.UNPROCESSABLE_ENTITY.value(), response.getStatusCode().value(), "statusCode deve ser igual");
        CriaCartaoResponseDTO body = response.getBody();
        Assertions.assertNotNull(body, "response body nao pode ser nulo");
        Assertions.assertEquals(request.getNumeroCartao(), body.getNumeroCartao(), "numeroCartao deve ser igual");
        Assertions.assertEquals(request.getSenha(), body.getSenha(), "senha deve ser igual");
    }

    @Test
    @Sql(scripts = "/sql/basic-setup-before.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/sql/basic-setup-after.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void getSaldoByNumeroCartao_comCartaoRecemCriado_deveRetornarStatusOKESaldoDefault() {
        CriaCartaoRequestDTO criaCartaoRequest = new CriaCartaoRequestDTO();
        criaCartaoRequest.setNumeroCartao("1234123412341234");
        criaCartaoRequest.setSenha("1234");
        ResponseEntity<CriaCartaoResponseDTO> criaCartaoResponse = facade.post(criaCartaoRequest);
        Assertions.assertEquals(HttpStatus.CREATED.value(), criaCartaoResponse.getStatusCode().value(), "statusCode deve ser igual");
        String numeroCartao = criaCartaoResponse.getBody().getNumeroCartao();
        BigDecimal saldoExpected = BigDecimal.valueOf(500.0);
        ResponseEntity<BigDecimal> response = facade.getSaldoByNumeroCartao(numeroCartao);
        Assertions.assertNotNull(response, "response body nao pode ser nulo");
        Assertions.assertEquals(HttpStatus.OK.value(), response.getStatusCode().value(), "statusCode deve ser igual");
        BigDecimal body = response.getBody();
        Assertions.assertNotNull(body, "response body nao pode ser nulo");
        Assertions.assertEquals(saldoExpected.doubleValue(), body.doubleValue(), "saldo deve ser igual");
        Assertions.assertEquals(saldoExpected, body, "saldo deve ser igual");
    }

}
