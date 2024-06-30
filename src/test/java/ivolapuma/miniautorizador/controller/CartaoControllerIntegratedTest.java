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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class CartaoControllerIntegratedTest {

    @Value("${spring.security.user.name}")
    private static String username = "username";
    @Value("${spring.security.user.password}")
    private static String password = "password";

    @Autowired
    private MockMvc mvc;

    @Test
    @Sql(scripts = "/sql/todos-casos-after.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/sql/todos-casos-after.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void cartoesPost_withValidBody_shouldReturnStatusCreatedAndBody() throws Exception {
        String body = "{ \"numeroCartao\": \"1234567890123456\", \"senha\": \"1234\" }";
        String expectedBody = "{ \"senha\": \"1234\", \"numeroCartao\": \"1234567890123456\" }";
        mvc.perform(post("/cartoes")
                    .header("Authorization", BasicAuthUtil.getAuthorizarion(username, password))
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(body))
                .andExpect(status().isCreated())
                .andExpect(content().json(expectedBody));
    }

    @Test
    @Sql(scripts = "/sql/cartao-saldo-default-before.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/sql/todos-casos-after.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void cartoesPost_withExistentCartao_shouldReturnStatusUnprocessableEntityAndBody() throws Exception {
        String body = "{ \"numeroCartao\": \"1111222233334444\", \"senha\": \"1234\" }";
        String expectedBody = "{ \"senha\": \"1234\", \"numeroCartao\": \"1111222233334444\" }";
        mvc.perform(post("/cartoes")
                        .header("Authorization", BasicAuthUtil.getAuthorizarion(username, password))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().json(expectedBody));
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

    @Test
    public void cartoesPost_withInvalidCredentials_shouldReturnStatusNotAuthorized() throws Exception {
        String body = "{ \"numeroCartao\": \"1234567890123456\", \"senha\": \"1234\" }";
        mvc.perform(post("/cartoes")
                        .header("Authorization", BasicAuthUtil.getAuthorizarion(username, "pass"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @Sql(scripts = "/sql/cartao-saldo-default-before.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/sql/todos-casos-after.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void cartoesGetByNumeroCartao_withNumeroCartao_shouldReturnStatusOKAndSaldo() throws Exception {
        String numeroCartao = "1111222233334444";
        String saldoExpected = "500.00";
        mvc.perform(get("/cartoes/{numeroCartao}".replace("{numeroCartao}", numeroCartao))
                .header("Authorization", BasicAuthUtil.getAuthorizarion(username, password)))
                .andExpect(status().isOk())
                .andExpect(content().string(saldoExpected));
    }

    @Test
    @Sql(scripts = "/sql/todos-casos-after.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/sql/todos-casos-after.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void cartoesGetByNumeroCartao_withNumeroCartaoInexistent_shouldReturnStatusNotFoundAndBodyEmpty() throws Exception {
        String numeroCartao = "1111222233334444";
        mvc.perform(get("/cartoes/{numeroCartao}".replace("{numeroCartao}", numeroCartao))
                        .header("Authorization", BasicAuthUtil.getAuthorizarion(username, password)))
                .andExpect(status().isNotFound())
                .andExpect(content().string(""));
    }

    @Test
    public void cartoesGetByNumeroCartao_withInvalidCredentials_shouldReturnStatusNotAuthorized() throws Exception {
        String numeroCartao = "1111222233334444";
        mvc.perform(get("/cartoes/{numeroCartao}".replace("{numeroCartao}", numeroCartao))
                        .header("Authorization", BasicAuthUtil.getAuthorizarion(username, "pass")))
                .andExpect(status().isUnauthorized());
    }

}

