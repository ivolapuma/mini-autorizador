package ivolapuma.miniautorizador.controller;

import ivolapuma.miniautorizador.util.BasicAuthUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class TransacaoControllerIntegratedTest {

    @Value("${spring.security.user.name}")
    private static String username = "username";
    @Value("${spring.security.user.password}")
    private static String password = "password";

    @Autowired
    private MockMvc mvc;

    @Test
    @Sql(scripts = "/sql/cartao-saldo-default-before.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/sql/todos-casos-after.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void transacoesPost_withBodyValid_shouldReturnStatusCreatedAndOK() throws Exception {
        String body = "{ \"numeroCartao\": \"1111222233334444\", \"senhaCartao\": \"1234\", \"valor\": 10.00 }";
        String expected = "OK";
        mvc.perform(post("/transacoes")
                        .header("Authorization", BasicAuthUtil.getAuthorizarion(username, password))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isCreated())
                .andExpect(content().string(expected));
    }

    @Test
    @Sql(scripts = "/sql/cartao-saldo-default-before.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/sql/todos-casos-after.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void transacoesPost_withTransacaoSenhaInvalid_shouldReturnStatusUnprocessableEntityAndSenhaInvalida() throws Exception {
        String body = "{ \"numeroCartao\": \"1111222233334444\", \"senhaCartao\": \"9999\", \"valor\": 10.00 }";
        String expected = "SENHA_INVALIDA";
        mvc.perform(post("/transacoes")
                        .header("Authorization", BasicAuthUtil.getAuthorizarion(username, password))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().string(expected));
    }

    @Test
    @Sql(scripts = "/sql/cartao-saldo-default-before.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/sql/todos-casos-after.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void transacoesPost_withTransacaoCartaoInexistent_shouldReturnStatusUnprocessableEntityAndCartaoInexistente() throws Exception {
        String body = "{ \"numeroCartao\": \"1111222233335555\", \"senhaCartao\": \"1234\", \"valor\": 10.00 }";
        String expected = "CARTAO_INEXISTENTE";
        mvc.perform(post("/transacoes")
                        .header("Authorization", BasicAuthUtil.getAuthorizarion(username, password))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().string(expected));
    }

    @Test
    @Sql(scripts = "/sql/cartao-saldo-default-before.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/sql/todos-casos-after.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void transacoesPost_withTransacaoSaldoInsufficient_shouldReturnStatusUnprocessableEntityAndSaldoInsuficiente() throws Exception {
        String body = "{ \"numeroCartao\": \"1111222233334444\", \"senhaCartao\": \"1234\", \"valor\": 1000.00 }";
        String expected = "SALDO_INSUFICIENTE";
        mvc.perform(post("/transacoes")
                        .header("Authorization", BasicAuthUtil.getAuthorizarion(username, password))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().string(expected));
    }

    @Test
    @Sql(scripts = "/sql/cartao-saldo-default-before.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/sql/todos-casos-after.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void transacoesPost_withInvalidCredentials_shouldReturnStatusNotAuthorized() throws Exception {
        String body = "{ \"numeroCartao\": \"1111222233334444\", \"senhaCartao\": \"1234\", \"valor\": 1000.00 }";
        mvc.perform(post("/transacoes")
                        .header("Authorization", BasicAuthUtil.getAuthorizarion(username, "pass"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @Sql(scripts = "/sql/cartao-saldo-default-before.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/sql/todos-casos-after.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void cartoesPost_withInvalidCredentials_shouldReturnStatusNotAuthorized() throws Exception {
        String body = "{ \"numeroCartao\": \"1111222233334444\", \"senhaCartao\": \"1234\", \"valor\": 1000.00 }";
        mvc.perform(post("/cartoes")
                        .header("Authorization", BasicAuthUtil.getAuthorizarion(username, "pass"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @Sql(scripts = "/sql/cartao-saldo-default-before.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/sql/todos-casos-after.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void cartoesPost_withNumeroCartaoInvalid_shouldReturnStatusBadRequest() throws Exception {
        String body = "{ \"numeroCartao\": \"111122223333444x\", \"senhaCartao\": \"1234\", \"valor\": 1000.00 }";
        mvc.perform(post("/cartoes")
                        .header("Authorization", BasicAuthUtil.getAuthorizarion(username, password))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest());
    }

}
