package ivolapuma.miniautorizador.service;

import ivolapuma.miniautorizador.entity.CartaoEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CartaoServiceImplTest {

    @Test
    public void criarCartao_comDtoValido_deveCriarCartaoERetornarDto() {
        CartaoService service = new CartaoServiceImpl();
        CartaoEntity cartao = new CartaoEntity();
        cartao.setNumeroCartao(1234567890123456L);
        cartao.setSenha(1234);
        CartaoEntity cartaoCriado = service.criarCartao(cartao);
        Assertions.assertNotNull(cartaoCriado, "cartaoCriado nao pode ser nulo");
        Assertions.assertEquals(cartao.getNumeroCartao(), cartaoCriado.getNumeroCartao(), "numeroCartao deve ser igual");
        Assertions.assertEquals(cartao.getSenha(), cartaoCriado.getSenha(), "senha deve ser igual");
    }

}
