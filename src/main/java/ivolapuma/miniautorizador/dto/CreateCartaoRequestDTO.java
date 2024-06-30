package ivolapuma.miniautorizador.dto;

import java.io.Serial;
import java.io.Serializable;

/**
 * Classe DTO que representa os dados enviados em uma requisição de Criação de Cartão.
 */
public class CreateCartaoRequestDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 3204527345740077362L;

    /**
     * Número do Cartão a ser criado.
     */
    private String numeroCartao;

    /**
     * Senha do Cartão a ser criado.
     */
    private String senha;

    public CreateCartaoRequestDTO() {
    }

    public String getNumeroCartao() {
        return numeroCartao;
    }

    public void setNumeroCartao(String numeroCartao) {
        this.numeroCartao = numeroCartao;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
