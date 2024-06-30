package ivolapuma.miniautorizador.service.impl;

import ivolapuma.miniautorizador.builder.GenericBuilder;
import ivolapuma.miniautorizador.dto.ExecuteTransacaoRequestDTO;
import ivolapuma.miniautorizador.entity.CartaoEntity;
import ivolapuma.miniautorizador.entity.TransacaoEntity;
import ivolapuma.miniautorizador.exception.*;
import ivolapuma.miniautorizador.service.CartaoService;
import ivolapuma.miniautorizador.service.NumeroCartaoService;
import ivolapuma.miniautorizador.service.SaldoService;
import ivolapuma.miniautorizador.service.SenhaCartaoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TransacaoServiceImplTest {

    @InjectMocks
    private TransacaoServiceImpl service;

    @Mock
    private MessageSource messages;

    @Mock
    private NumeroCartaoService numeroCartaoService;

    @Mock
    private SenhaCartaoService senhaCartaoService;

    @Mock
    private CartaoService cartaoService;

    @Mock
    private SaldoService saldoService;

    @Test
    public void validate_withRequestValid_shouldRunOk() throws InvalidNumeroCartaoException, InvalidSenhaCartaoException {
        ExecuteTransacaoRequestDTO request =
                GenericBuilder.of(ExecuteTransacaoRequestDTO::new)
                        .with(ExecuteTransacaoRequestDTO::setNumeroCartao, "1111222233334444")
                        .with(ExecuteTransacaoRequestDTO::setSenhaCartao, "1234")
                        .with(ExecuteTransacaoRequestDTO::setValor, BigDecimal.valueOf(10.0))
                        .build();
        doNothing().when(numeroCartaoService).validate(request.getNumeroCartao());
        doNothing().when(senhaCartaoService).validate(request.getSenhaCartao());
        assertDoesNotThrow(
                () -> service.validate(request)
        );
    }

    @Test
    public void validate_withRequestNull_shouldThrowException() {
        assertThrows(
                BadRequestException.class,
                () -> service.validate(null)
        );
    }

    @Test
    public void validate_withValorNull_shouldThrowException() {
        ExecuteTransacaoRequestDTO request =
                GenericBuilder.of(ExecuteTransacaoRequestDTO::new)
                        .with(ExecuteTransacaoRequestDTO::setNumeroCartao, "1111222233334444")
                        .with(ExecuteTransacaoRequestDTO::setSenhaCartao, "1234")
                        .with(ExecuteTransacaoRequestDTO::setValor, null)
                        .build();
        assertThrows(
                BadRequestException.class,
                () -> service.validate(request)
        );
    }

    @Test
    public void validate_withValorNegative_shouldThrowException() {
        ExecuteTransacaoRequestDTO request =
                GenericBuilder.of(ExecuteTransacaoRequestDTO::new)
                        .with(ExecuteTransacaoRequestDTO::setNumeroCartao, "1111222233334444")
                        .with(ExecuteTransacaoRequestDTO::setSenhaCartao, "1234")
                        .with(ExecuteTransacaoRequestDTO::setValor, BigDecimal.valueOf(-10.0))
                        .build();
        assertThrows(
                BadRequestException.class,
                () -> service.validate(request)
        );
    }

    @Test
    public void validate_withNumeroCartaoInvalid_shouldThrowException() throws InvalidNumeroCartaoException, InvalidSenhaCartaoException {
        ExecuteTransacaoRequestDTO request =
                GenericBuilder.of(ExecuteTransacaoRequestDTO::new)
                        .with(ExecuteTransacaoRequestDTO::setNumeroCartao, "x111222233334444")
                        .with(ExecuteTransacaoRequestDTO::setSenhaCartao, "1234")
                        .with(ExecuteTransacaoRequestDTO::setValor, BigDecimal.valueOf(10.0))
                        .build();
        doThrow(InvalidNumeroCartaoException.class).when(numeroCartaoService).validate(request.getNumeroCartao());
        assertThrows(
                BadRequestException.class,
                () -> service.validate(request)
        );
    }

    @Test
    public void validate_withSenhaCartaoInvalid_shouldThrowException() throws InvalidNumeroCartaoException, InvalidSenhaCartaoException {
        ExecuteTransacaoRequestDTO request =
                GenericBuilder.of(ExecuteTransacaoRequestDTO::new)
                        .with(ExecuteTransacaoRequestDTO::setNumeroCartao, "1111222233334444")
                        .with(ExecuteTransacaoRequestDTO::setSenhaCartao, "x234")
                        .with(ExecuteTransacaoRequestDTO::setValor, BigDecimal.valueOf(10.0))
                        .build();
        doNothing().when(numeroCartaoService).validate(request.getNumeroCartao());
        doThrow(InvalidSenhaCartaoException.class).when(senhaCartaoService).validate(request.getSenhaCartao());
        assertThrows(
                BadRequestException.class,
                () -> service.validate(request)
        );
    }

    @Test
    public void execute_withTransacaoValid_shouldRunOk() throws InsufficientSaldoException, InvalidSenhaCartaoException, NotFoundEntityException {
        TransacaoEntity transacao =
                GenericBuilder.of(TransacaoEntity::new)
                        .with(TransacaoEntity::setNumeroCartao, 1111222233334444L)
                        .with(TransacaoEntity::setSenhaCartao, 1234)
                        .with(TransacaoEntity::setValor, BigDecimal.valueOf(10.0))
                        .with(TransacaoEntity::setSaldo, BigDecimal.valueOf(500.0))
                        .build();
        CartaoEntity cartao =
                GenericBuilder.of(CartaoEntity::new)
                        .with(CartaoEntity::setNumeroCartao, transacao.getNumeroCartao())
                        .with(CartaoEntity::setSenha, transacao.getSenhaCartao())
                        .with(CartaoEntity::setSaldo, BigDecimal.valueOf(500.0))
                        .build();
        CartaoEntity debited =
                GenericBuilder.of(CartaoEntity::new)
                        .with(CartaoEntity::setNumeroCartao, cartao.getNumeroCartao())
                        .with(CartaoEntity::setSenha, cartao.getSenha())
                        .with(CartaoEntity::setSaldo, BigDecimal.valueOf(500.0).subtract(BigDecimal.valueOf(10.0)))
                        .build();
        when(cartaoService.getByNumeroCartao(transacao.getNumeroCartao())).thenReturn(cartao);
        doNothing().when(senhaCartaoService).validate(cartao.getSenha(), transacao.getSenhaCartao());
        doNothing().when(saldoService).verifyIfSufficient(cartao.getSaldo(), transacao.getValor());
        when(cartaoService.debitSaldo(transacao.getNumeroCartao(), transacao.getValor())).thenReturn(debited);
        assertDoesNotThrow(
                () -> service.execute(transacao)
        );
    }

    @Test
    public void execute_withNumeroCartaoInexistent_shouldThrowException() throws InsufficientSaldoException, InvalidSenhaCartaoException, NotFoundEntityException {
//        TransacaoEntity transacao = new TransacaoEntity();
//        transacao.setNumeroCartao(1111222233334444L);
//        transacao.setSenhaCartao(1234);
//        transacao.setValor(BigDecimal.valueOf(10.0));
//        transacao.setSaldo(BigDecimal.valueOf(500.0));
        TransacaoEntity transacao =
                GenericBuilder.of(TransacaoEntity::new)
                        .with(TransacaoEntity::setNumeroCartao, 1111222233334444L)
                        .with(TransacaoEntity::setSenhaCartao, 1234)
                        .with(TransacaoEntity::setValor, BigDecimal.valueOf(10.0))
                        .build();
        doThrow(NotFoundEntityException.class).when(cartaoService).getByNumeroCartao(transacao.getNumeroCartao());
        assertThrows(
                NotFoundEntityException.class,
                () -> service.execute(transacao)
        );
    }

    @Test
    public void execute_withSenhaInvalid_shouldThrowException() throws InsufficientSaldoException, InvalidSenhaCartaoException, NotFoundEntityException {
//        TransacaoEntity transacao = new TransacaoEntity();
//        transacao.setNumeroCartao(1111222233334444L);
//        transacao.setSenhaCartao(1234);
//        transacao.setValor(BigDecimal.valueOf(10.0));
//        transacao.setSaldo(BigDecimal.valueOf(500.0));
        TransacaoEntity transacao =
                GenericBuilder.of(TransacaoEntity::new)
                        .with(TransacaoEntity::setNumeroCartao, 1111222233334444L)
                        .with(TransacaoEntity::setSenhaCartao, 1234)
                        .with(TransacaoEntity::setValor, BigDecimal.valueOf(10.0))
                        .build();
        CartaoEntity cartao =
                GenericBuilder.of(CartaoEntity::new)
                        .with(CartaoEntity::setNumeroCartao, transacao.getNumeroCartao())
                        .with(CartaoEntity::setSenha, transacao.getSenhaCartao())
                        .with(CartaoEntity::setSaldo, BigDecimal.valueOf(500.0))
                        .build();
        when(cartaoService.getByNumeroCartao(transacao.getNumeroCartao())).thenReturn(cartao);
        doThrow(InvalidSenhaCartaoException.class).when(senhaCartaoService).validate(cartao.getSenha(), transacao.getSenhaCartao());
        assertThrows(
                InvalidSenhaCartaoException.class,
                () -> service.execute(transacao)
        );
    }

    @Test
    public void execute_withSaldoInsufficient_shouldThrowException() throws InsufficientSaldoException, InvalidSenhaCartaoException, NotFoundEntityException {
        TransacaoEntity transacao =
                GenericBuilder.of(TransacaoEntity::new)
                        .with(TransacaoEntity::setNumeroCartao, 1111222233334444L)
                        .with(TransacaoEntity::setSenhaCartao, 1234)
                        .with(TransacaoEntity::setValor, BigDecimal.valueOf(1000.0))
                        .build();
        CartaoEntity cartao =
                GenericBuilder.of(CartaoEntity::new)
                        .with(CartaoEntity::setNumeroCartao, transacao.getNumeroCartao())
                        .with(CartaoEntity::setSenha, transacao.getSenhaCartao())
                        .with(CartaoEntity::setSaldo, BigDecimal.valueOf(500.0))
                        .build();
        when(cartaoService.getByNumeroCartao(transacao.getNumeroCartao())).thenReturn(cartao);
        doNothing().when(senhaCartaoService).validate(cartao.getSenha(), transacao.getSenhaCartao());
        doThrow(InsufficientSaldoException.class).when(saldoService).verifyIfSufficient(cartao.getSaldo(), transacao.getValor());
        assertThrows(
                InsufficientSaldoException.class,
                () -> service.execute(transacao)
        );
    }

    @Test
    public void execute_withSaldoInsufficientAtDebitTime_shouldThrowException() throws InsufficientSaldoException, InvalidSenhaCartaoException, NotFoundEntityException {
        TransacaoEntity transacao =
                GenericBuilder.of(TransacaoEntity::new)
                        .with(TransacaoEntity::setNumeroCartao, 1111222233334444L)
                        .with(TransacaoEntity::setSenhaCartao, 1234)
                        .with(TransacaoEntity::setValor, BigDecimal.valueOf(10.0))
                        .build();
        CartaoEntity cartao =
                GenericBuilder.of(CartaoEntity::new)
                        .with(CartaoEntity::setNumeroCartao, transacao.getNumeroCartao())
                        .with(CartaoEntity::setSenha, transacao.getSenhaCartao())
                        .with(CartaoEntity::setSaldo, BigDecimal.valueOf(500.0))
                        .build();
        when(cartaoService.getByNumeroCartao(transacao.getNumeroCartao())).thenReturn(cartao);
        doNothing().when(senhaCartaoService).validate(cartao.getSenha(), transacao.getSenhaCartao());
        doNothing().when(saldoService).verifyIfSufficient(cartao.getSaldo(), transacao.getValor());
        doThrow(InsufficientSaldoException.class).when(cartaoService).debitSaldo(transacao.getNumeroCartao(), transacao.getValor());
        assertThrows(
                InsufficientSaldoException.class,
                () -> service.execute(transacao)
        );
    }

}
