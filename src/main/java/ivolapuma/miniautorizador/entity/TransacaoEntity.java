package ivolapuma.miniautorizador.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TransacaoEntity {

    private Long numeroCartao;
    private Integer senhaCartao;
    private BigDecimal saldo;
    private BigDecimal valor;
    private LocalDateTime timestamp;
    private boolean sucesso;
    private String motivoFalha;

    public TransacaoEntity() {
    }

    public Long getNumeroCartao() {
        return numeroCartao;
    }

    public void setNumeroCartao(Long numeroCartao) {
        this.numeroCartao = numeroCartao;
    }

    public Integer getSenhaCartao() {
        return senhaCartao;
    }

    public void setSenhaCartao(Integer senhaCartao) {
        this.senhaCartao = senhaCartao;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public boolean isSucesso() {
        return sucesso;
    }

    public void setSucesso(boolean sucesso) {
        this.sucesso = sucesso;
    }

    public String getMotivoFalha() {
        return motivoFalha;
    }

    public void setMotivoFalha(String motivoFalha) {
        this.motivoFalha = motivoFalha;
    }

    @Override
    public String toString() {
        return String.format(
                "Transacao: { numeroCartao:%d, senhaCartao:%d, saldo:%.2f, valor:%.2f, sucesso:%s, motivoFalha:%s, timestamp:%s }",
                this.numeroCartao,
                this.senhaCartao,
                this.saldo,
                this.valor,
                this.sucesso,
                this.motivoFalha,
                this.timestamp.format(DateTimeFormatter.ISO_DATE_TIME)
        );
    }
}
