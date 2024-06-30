package ivolapuma.miniautorizador.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;

@Entity
@Table(name = "Cartao")
public class CartaoEntity {

    @Id
    @Column(name = "numeroCartao", length = 16, nullable = false, updatable = false)
    private Long numeroCartao;

    @Column(name = "senha", length = 4, nullable = false)
    private Integer senha;

    @Column(name = "saldo", scale = 2, nullable = false)
    private BigDecimal saldo;

    public CartaoEntity() {
    }

    public Long getNumeroCartao() {
        return numeroCartao;
    }

    public void setNumeroCartao(Long numeroCartao) {
        this.numeroCartao = numeroCartao;
    }

    public Integer getSenha() {
        return senha;
    }

    public void setSenha(Integer senha) {
        this.senha = senha;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    @Override
    public String toString() {
        return String.format(
                "Cartao: { numeroCartao:%d, senha:%d, saldo:%.2f }",
                this.numeroCartao,
                this.senha,
                this.saldo
        );
    }
}
