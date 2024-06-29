package ivolapuma.miniautorizador.service;

public interface NumeroCartaoService {

    void validate(String numeroCartao) throws Throwable;
}
