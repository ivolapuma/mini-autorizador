package ivolapuma.miniautorizador.service;

import ivolapuma.miniautorizador.dto.CriaCartaoRequestDTO;
import ivolapuma.miniautorizador.dto.CriaCartaoResponseDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CartaoServiceImplTest {

    @Test
    public void criarCartao_quandoDtoValido_deveCriarCartaoERetornarDto() {
        CartaoService service = new CartaoServiceImpl();
        CriaCartaoRequestDTO request = new CriaCartaoRequestDTO();
        request.setNumeroCartao("1234567890123456");
        request.setSenha("1234");
        CriaCartaoResponseDTO response = service.criarCartao(request);
        Assertions.assertNotNull(response, "response nao pode ser nulo");
        Assertions.assertEquals(request.getNumeroCartao(), response.getNumeroCartao(), "numeroCartao deve ser igual");
        Assertions.assertEquals(request.getSenha(), response.getSenha(), "senha deve ser igual");
    }

}
