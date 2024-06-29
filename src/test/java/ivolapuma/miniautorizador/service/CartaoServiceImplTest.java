package ivolapuma.miniautorizador.service;

import ivolapuma.miniautorizador.entity.CartaoEntity;
import ivolapuma.miniautorizador.repository.CartaoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CartaoServiceImplTest {

    @InjectMocks
    private CartaoServiceImpl service;

    @Mock
    private CartaoRepository repository;

    @Test
    public void criarCartao_comDtoValido_deveCriarCartaoERetornarDto() throws Throwable {

        CartaoEntity cartao = new CartaoEntity();
        cartao.setNumeroCartao(1234567890123456L);
        cartao.setSenha(1234);

        CartaoEntity expected = new CartaoEntity();
        cartao.setNumeroCartao(cartao.getNumeroCartao());
        cartao.setSenha(cartao.getSenha());
        cartao.setSaldo(BigDecimal.valueOf(500.0));

        when(repository.save(cartao)).thenReturn(expected);

        CartaoEntity actual = service.create(cartao);
        Assertions.assertNotNull(actual, "actual nao pode ser nulo");
        Assertions.assertEquals(expected.getNumeroCartao(), actual.getNumeroCartao(), "numeroCartao deve ser igual");
        Assertions.assertEquals(expected.getSenha(), actual.getSenha(), "senha deve ser igual");
        Assertions.assertEquals(expected.getSaldo(), actual.getSaldo(), "saldo deve ser igual");
    }

}
