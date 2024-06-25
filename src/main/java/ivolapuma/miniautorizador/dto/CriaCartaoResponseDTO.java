package ivolapuma.miniautorizador.dto;

import java.io.Serial;
import java.io.Serializable;

public class CriaCartaoResponseDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 5387055976579406223L;

    private String numeroCartao;
    private String senha;

    public CriaCartaoResponseDTO() {
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

