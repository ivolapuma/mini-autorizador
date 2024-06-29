package ivolapuma.miniautorizador.controller;

import ivolapuma.miniautorizador.util.BasicAuthUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class CartaoControllerIntegratedTest {

    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";

    @Autowired
    private MockMvc mvc;

    @Test
    public void cartoesPost_withValidBody_shouldReturnStatusCreatedAndBody() throws Exception {
        String body = "{ \"numeroCartao\": \"1234567890123456\", \"senha\": \"1234\" }";
        String expectedBody = "{ \"senha\": \"1234\", \"numeroCartao\": \"1234567890123456\" }";
        mvc.perform(post("/cartoes")
                    .header("Authorization", BasicAuthUtil.getAuthorizarion(USERNAME, PASSWORD))
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(body))
                .andExpect(status().isCreated())
                .andExpect(content().json(expectedBody));
    }

    @Test
    @Sql(scripts = "/sql/basic-setup-before.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/sql/basic-setup-after.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void cartoesPost_withExistentCartao_shouldReturnStatusUnprocessableEntityAndBody() throws Exception {
        String body = "{ \"numeroCartao\": \"1111222233334444\", \"senha\": \"1234\" }";
        String expectedBody = "{ \"senha\": \"1234\", \"numeroCartao\": \"1111222233334444\" }";
        mvc.perform(post("/cartoes")
                        .header("Authorization", BasicAuthUtil.getAuthorizarion(USERNAME, PASSWORD))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().json(expectedBody));
    }

    @Test
    public void cartoesPost_withInvalidCredentials_shouldReturnStatusNotAuthorized() throws Exception {
        String body = "{ \"numeroCartao\": \"1234567890123456\", \"senha\": \"1234\" }";
        mvc.perform(post("/cartoes")
                        .header("Authorization", BasicAuthUtil.getAuthorizarion(USERNAME, "pass"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isUnauthorized());
    }

}

