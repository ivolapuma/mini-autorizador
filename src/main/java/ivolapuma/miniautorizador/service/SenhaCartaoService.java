package ivolapuma.miniautorizador.service;

public interface SenhaCartaoService {

    void validate(String senhaCartao) throws Throwable;

    void validate(Integer senhaCartao, Integer senhaTransacao) throws Throwable;
}
