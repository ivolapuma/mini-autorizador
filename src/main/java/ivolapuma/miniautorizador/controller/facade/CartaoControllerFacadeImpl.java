package ivolapuma.miniautorizador.controller.facade;

import ivolapuma.miniautorizador.dto.CriaCartaoRequestDTO;
import ivolapuma.miniautorizador.dto.CriaCartaoResponseDTO;
import ivolapuma.miniautorizador.entity.CartaoEntity;
import ivolapuma.miniautorizador.service.CartaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class CartaoControllerFacadeImpl implements CartaoControllerFacade {

    @Autowired
    private CartaoService service;

    @Override
    public ResponseEntity<CriaCartaoResponseDTO> post(CriaCartaoRequestDTO request) {
        CartaoEntity cartao = new CartaoEntity();
        cartao.setNumeroCartao(Long.valueOf(request.getNumeroCartao()));
        cartao.setSenha(Integer.valueOf(request.getSenha()));

        CartaoEntity cartaoCriado = service.criarCartao(cartao);

        CriaCartaoResponseDTO response = new CriaCartaoResponseDTO();
        response.setNumeroCartao(String.valueOf(cartaoCriado.getNumeroCartao()));
        response.setSenha(String.valueOf(cartaoCriado.getSenha()));

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Override
    public ResponseEntity<BigDecimal> getSaldoByNumeroCartao(String numeroCartao) {
        BigDecimal saldo = service.consultarSaldo(Long.valueOf(numeroCartao));
        return ResponseEntity.status(HttpStatus.OK).body(saldo);
    }
}
