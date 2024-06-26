package ivolapuma.miniautorizador.service;

import ivolapuma.miniautorizador.dto.CriaCartaoRequestDTO;
import ivolapuma.miniautorizador.dto.CriaCartaoResponseDTO;
import ivolapuma.miniautorizador.entity.CartaoEntity;
import ivolapuma.miniautorizador.repository.CartaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class CartaoServiceImpl implements CartaoService {

    private static final BigDecimal SALDO_DEFAULT = BigDecimal.valueOf(500.0);

    @Autowired
    private CartaoRepository repository;

    @Override
    public CartaoEntity criarCartao(CartaoEntity cartao) {
        // TODO: validar request
        // TODO: verificar se cartao já existe no repositorio
        // TODO: gravar cartao no repositorio
        cartao.setSaldo(SALDO_DEFAULT);
        CartaoEntity saved = repository.save(cartao);
        return saved;
    }

    @Override
    public BigDecimal consultarSaldo(Long numeroCartao) {
        CartaoEntity cartao = repository.findById(numeroCartao).get();
        return cartao.getSaldo();
    }
}
