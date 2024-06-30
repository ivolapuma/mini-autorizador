package ivolapuma.miniautorizador.dto;

import java.io.Serial;
import java.io.Serializable;

/**
 * Classe DTO que representa os dados retornados na resposta da requisição de Criação de Cartão.
 */
public class CreateCartaoResponseDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 5387055976579406223L;

    /**
     * Número do Cartão criado.
     */
    private String numeroCartao;

    /**
     * Senha do Cartão criado.
     */
    private String senha;

    public CreateCartaoResponseDTO() {
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

