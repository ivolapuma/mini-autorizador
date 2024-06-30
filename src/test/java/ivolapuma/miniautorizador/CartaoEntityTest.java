package ivolapuma.miniautorizador;

import ivolapuma.miniautorizador.entity.CartaoEntity;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

public class CartaoEntityTest {

    @Test
    public void toString_withCartao_shouldReturnString() {
        CartaoEntity cartao = new CartaoEntity();
        cartao.setNumeroCartao(1111222233334444L);
        cartao.setSenha(1234);
        cartao.setSaldo(BigDecimal.valueOf(500.0));
        System.out.println(cartao);
    }

}
