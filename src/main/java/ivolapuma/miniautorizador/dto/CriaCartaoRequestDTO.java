package ivolapuma.miniautorizador.dto;

import java.io.Serial;
import java.io.Serializable;

public class CriaCartaoRequestDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 3204527345740077362L;

    private String numeroCartao;
    private String senha;

    public CriaCartaoRequestDTO() {
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
