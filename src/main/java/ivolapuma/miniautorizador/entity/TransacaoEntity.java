package ivolapuma.miniautorizador.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "Transacao")
public class TransacaoEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "numeroCartao", length = 16, nullable = false, updatable = false)
    private Long numeroCartao;

    @Column(name = "senha", length = 4, nullable = false, updatable = false)
    private Integer senhaCartao;

    @Column(name = "valor", scale = 2, nullable = false, updatable = false)
    private BigDecimal valor;

    @Column(name = "saldo", scale = 2, updatable = false)
    private BigDecimal saldo;

    @Column(name = "sucesso", nullable = false, updatable = false)
    private boolean sucesso;

    @Column(name = "motivoFalha", length = 50, updatable = false)
    private String motivoFalha;

    @Column(name = "timestamp", updatable = false)
    private LocalDateTime timestamp;

    public TransacaoEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
                "Transacao: { id:%d, numeroCartao:%d, senhaCartao:%d, saldo:%.2f, valor:%.2f, sucesso:%s, motivoFalha:%s, timestamp:%s }",
                this.id,
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
