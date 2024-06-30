package ivolapuma.miniautorizador;

import ivolapuma.miniautorizador.entity.TransacaoEntity;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TransacaoEntityTest {

    @Test
    public void toString_withTransacao_shouldReturnString() {
        TransacaoEntity transacao = new TransacaoEntity();
        transacao.setNumeroCartao(1111222233334444L);
        transacao.setSenhaCartao(1234);
        transacao.setSaldo(BigDecimal.valueOf(500.0));
        transacao.setValor(BigDecimal.valueOf(10.0));
        transacao.setSucesso(true);
        transacao.setTimestamp(LocalDateTime.now());
        System.out.println(transacao);
    }

}
