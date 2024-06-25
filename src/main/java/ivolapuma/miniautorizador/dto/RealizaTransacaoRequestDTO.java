package ivolapuma.miniautorizador.dto;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

public class RealizaTransacaoRequestDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 8725115162929629804L;

    private String numeroCartao;
    private String senhaCartao;
    private BigDecimal valor;

    public RealizaTransacaoRequestDTO() {
    }

    public String getNumeroCartao() {
        return numeroCartao;
    }

    public void setNumeroCartao(String numeroCartao) {
        this.numeroCartao = numeroCartao;
    }

    public String getSenhaCartao() {
        return senhaCartao;
    }

    public void setSenhaCartao(String senhaCartao) {
        this.senhaCartao = senhaCartao;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }
}
