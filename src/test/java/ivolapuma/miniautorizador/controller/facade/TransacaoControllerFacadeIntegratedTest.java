package ivolapuma.miniautorizador.controller.facade;

import ivolapuma.miniautorizador.dto.ExecuteTransacaoRequestDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class TransacaoControllerFacadeIntegratedTest {

    @Autowired
    private TransacaoControllerFacade facade;

    @Test
    @Sql(scripts = "/sql/cartao-saldo-default-before.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/sql/todos-casos-after.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void post_withTransacaoValid_shouldReturnStatusCreatedAndOK() throws Throwable {
        ExecuteTransacaoRequestDTO request = new ExecuteTransacaoRequestDTO();
        request.setNumeroCartao("1111222233334444");
        request.setSenhaCartao("1234");
        request.setValor(BigDecimal.valueOf(10.0));
        ResponseEntity<String> response = facade.post(request);
        assertNotNull(response, "response cannot be null");
        assertEquals(HttpStatus.CREATED.value(), response.getStatusCode().value(), "statusCode should be equal");
    }

    @Test
    @Sql(scripts = "/sql/cartao-sem-saldo-before.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/sql/todos-casos-after.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void post_withTransacaoSaldoInsufficient_shouldReturnStatusUnprocessableEntityAndSaldoInsuficiente() throws Throwable {
        ExecuteTransacaoRequestDTO request = new ExecuteTransacaoRequestDTO();
        request.setNumeroCartao("1111222233334444");
        request.setSenhaCartao("1234");
        request.setValor(BigDecimal.valueOf(10.0));
        ResponseEntity<String> response = facade.post(request);
        assertNotNull(response, "response cannot be null");
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY.value(), response.getStatusCode().value(), "statusCode should be equal");
        assertEquals("SALDO_INSUFICIENTE", response.getBody(), "body should be equal");
    }

    @Test
    @Sql(scripts = "/sql/cartao-saldo-default-before.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/sql/todos-casos-after.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void post_withTransacaoSenhaInvalid_shouldReturnStatusStatusUnprocessableEntityAndSenhaInvalida() throws Throwable {
        ExecuteTransacaoRequestDTO request = new ExecuteTransacaoRequestDTO();
        request.setNumeroCartao("1111222233334444");
        request.setSenhaCartao("4321");
        request.setValor(BigDecimal.valueOf(10.0));
        ResponseEntity<String> response = facade.post(request);
        assertNotNull(response, "response cannot be null");
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY.value(), response.getStatusCode().value(), "statusCode should be equal");
        assertEquals("SENHA_INVALIDA", response.getBody(), "body should be equal");
    }

    @Test
    @Sql(scripts = "/sql/cartao-saldo-default-before.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/sql/todos-casos-after.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void post_withTransacaoCartaoInexistent_shouldReturnStatusStatusUnprocessableEntityAndCartaoInexistente() throws Throwable {
        ExecuteTransacaoRequestDTO request = new ExecuteTransacaoRequestDTO();
        request.setNumeroCartao("1111222233335555");
        request.setSenhaCartao("4321");
        request.setValor(BigDecimal.valueOf(10.0));
        ResponseEntity<String> response = facade.post(request);
        assertNotNull(response, "response cannot be null");
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY.value(), response.getStatusCode().value(), "statusCode should be equal");
        assertEquals("CARTAO_INEXISTENTE", response.getBody(), "body should be equal");
    }
}
