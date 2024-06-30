package ivolapuma.miniautorizador.dto;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Classe DTO que representa os dados enviados em uma requisição de Realiação de Transação de Cartão.
 */
public class ExecuteTransacaoRequestDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 8725115162929629804L;

    /**
     * Número do Cartão da Transação.
     */
    private String numeroCartao;

    /**
     * Senha do Cartão informada para a Transação.
     */
    private String senhaCartao;

    /**
     * Valor a ser debitado do saldo do Cartão.
     */
    private BigDecimal valor;

    public ExecuteTransacaoRequestDTO() {
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
