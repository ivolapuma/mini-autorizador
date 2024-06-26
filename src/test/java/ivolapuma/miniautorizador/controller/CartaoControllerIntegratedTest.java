package ivolapuma.miniautorizador.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class CartaoControllerIntegratedTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void postCartores_comDtoValido_deveRetornarStatusCreated() throws Exception {
        String body = "{ \"numeroCartao\": \"1234567890123456\", \"senha\": \"1234\" }";
        mvc.perform(post("/cartoes")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(body))
                .andExpect(status().isCreated());
    }
}
