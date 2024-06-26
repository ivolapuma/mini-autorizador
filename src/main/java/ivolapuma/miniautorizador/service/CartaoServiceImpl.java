package ivolapuma.miniautorizador.service;

import ivolapuma.miniautorizador.dto.CriaCartaoRequestDTO;
import ivolapuma.miniautorizador.dto.CriaCartaoResponseDTO;
import ivolapuma.miniautorizador.entity.CartaoEntity;
import ivolapuma.miniautorizador.repository.CartaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class CartaoServiceImpl implements CartaoService {

    private static final BigDecimal SALDO_DEFAULT = BigDecimal.valueOf(500.0);

    @Autowired
    private CartaoRepository repository;

    @Override
    public CriaCartaoResponseDTO criarCartao(CriaCartaoRequestDTO request) {
        // TODO: validar request
        // TODO: verificar se cartao já existe no repositorio
        // TODO: gravar cartao no repositorio
        CartaoEntity cartao = new CartaoEntity();
        cartao.setNumeroCartao(Long.valueOf(request.getNumeroCartao()));
        cartao.setSenha(Integer.valueOf(request.getSenha()));
        cartao.setSaldo(SALDO_DEFAULT);
        CartaoEntity saved = repository.save(cartao);
        CriaCartaoResponseDTO response = new CriaCartaoResponseDTO();
        response.setNumeroCartao(String.valueOf(saved.getNumeroCartao()));
        response.setSenha(String.valueOf(saved.getNumeroCartao()));
        return response;
    }

}
