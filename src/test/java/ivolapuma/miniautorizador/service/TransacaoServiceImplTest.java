package ivolapuma.miniautorizador.service;

import ivolapuma.miniautorizador.dto.ExecuteTransacaoRequestDTO;
import ivolapuma.miniautorizador.entity.CartaoEntity;
import ivolapuma.miniautorizador.entity.TransacaoEntity;
import ivolapuma.miniautorizador.exception.*;
import ivolapuma.miniautorizador.service.impl.TransacaoServiceImpl;
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
        ExecuteTransacaoRequestDTO request = new ExecuteTransacaoRequestDTO();
        request.setNumeroCartao("1111222233334444");
        request.setSenhaCartao("1234");
        request.setValor(BigDecimal.valueOf(10.0));
        doNothing().when(numeroCartaoService).validate(request.getNumeroCartao());
        doNothing().when(senhaCartaoService).validate(request.getSenhaCartao());
        assertDoesNotThrow(
                () -> service.validate(request)
        );
    }

    @Test
    public void validate_withRequestNull_shouldThrowException() {
        BadRequestException exception = assertThrows(
                BadRequestException.class,
                () -> service.validate(null)
        );
    }

    @Test
    public void validate_withValorNull_shouldThrowException() {
        ExecuteTransacaoRequestDTO request = new ExecuteTransacaoRequestDTO();
        request.setNumeroCartao("1111222233334444");
        request.setSenhaCartao("1234");
        request.setValor(null);
        BadRequestException exception = assertThrows(
                BadRequestException.class,
                () -> service.validate(request)
        );
    }

    @Test
    public void validate_withValorNegative_shouldThrowException() {
        ExecuteTransacaoRequestDTO request = new ExecuteTransacaoRequestDTO();
        request.setNumeroCartao("1111222233334444");
        request.setSenhaCartao("1234");
        request.setValor(BigDecimal.valueOf(-10.0));
        BadRequestException exception = assertThrows(
                BadRequestException.class,
                () -> service.validate(request)
        );
    }

    @Test
    public void validate_withNumeroCartaoInvalid_shouldThrowException() throws InvalidNumeroCartaoException, InvalidSenhaCartaoException {
        ExecuteTransacaoRequestDTO request = new ExecuteTransacaoRequestDTO();
        request.setNumeroCartao("x111222233334444");
        request.setSenhaCartao("1234");
        request.setValor(BigDecimal.valueOf(10.0));
        doThrow(InvalidNumeroCartaoException.class).when(numeroCartaoService).validate(request.getNumeroCartao());
        assertThrows(
                BadRequestException.class,
                () -> service.validate(request)
        );
    }

    @Test
    public void validate_withSenhaCartaoInvalid_shouldThrowException() throws InvalidNumeroCartaoException, InvalidSenhaCartaoException {
        ExecuteTransacaoRequestDTO request = new ExecuteTransacaoRequestDTO();
        request.setNumeroCartao("1111222233334444");
        request.setSenhaCartao("x234");
        request.setValor(BigDecimal.valueOf(10.0));
        doNothing().when(numeroCartaoService).validate(request.getNumeroCartao());
        doThrow(InvalidSenhaCartaoException.class).when(senhaCartaoService).validate(request.getSenhaCartao());
        assertThrows(
                BadRequestException.class,
                () -> service.validate(request)
        );
    }

    @Test
    public void execute_withTransacaoValid_shouldRunOk() throws InsufficientSaldoException, InvalidSenhaCartaoException, NotFoundEntityException {

        TransacaoEntity transacao = new TransacaoEntity();
        transacao.setNumeroCartao(1111222233334444L);
        transacao.setSenhaCartao(1234);
        transacao.setValor(BigDecimal.valueOf(10.0));
        transacao.setSaldo(BigDecimal.valueOf(500.0));

        CartaoEntity cartao = new CartaoEntity();
        cartao.setNumeroCartao(transacao.getNumeroCartao());
        cartao.setSenha(transacao.getSenhaCartao());
        cartao.setSaldo(BigDecimal.valueOf(500.0));

        when(cartaoService.getByNumeroCartao(transacao.getNumeroCartao())).thenReturn(cartao);
        doNothing().when(senhaCartaoService).validate(cartao.getSenha(), transacao.getSenhaCartao());
        doNothing().when(saldoService).verifyIfSufficient(cartao.getSaldo(), transacao.getValor());
        doNothing().when(cartaoService).debitSaldo(transacao.getNumeroCartao(), transacao.getValor());

        assertDoesNotThrow(
                () -> service.execute(transacao)
        );
    }

    @Test
    public void execute_withNumeroCartaoInexistent_shouldThrowException() throws InsufficientSaldoException, InvalidSenhaCartaoException, NotFoundEntityException {

        TransacaoEntity transacao = new TransacaoEntity();
        transacao.setNumeroCartao(1111222233334444L);
        transacao.setSenhaCartao(1234);
        transacao.setValor(BigDecimal.valueOf(10.0));
        transacao.setSaldo(BigDecimal.valueOf(500.0));

        doThrow(NotFoundEntityException.class).when(cartaoService).getByNumeroCartao(transacao.getNumeroCartao());

        assertThrows(
                NotFoundEntityException.class,
                () -> service.execute(transacao)
        );
    }

    @Test
    public void execute_withSenhaInvalid_shouldThrowException() throws InsufficientSaldoException, InvalidSenhaCartaoException, NotFoundEntityException {

        TransacaoEntity transacao = new TransacaoEntity();
        transacao.setNumeroCartao(1111222233334444L);
        transacao.setSenhaCartao(1234);
        transacao.setValor(BigDecimal.valueOf(10.0));
        transacao.setSaldo(BigDecimal.valueOf(500.0));

        CartaoEntity cartao = new CartaoEntity();
        cartao.setNumeroCartao(transacao.getNumeroCartao());
        cartao.setSenha(transacao.getSenhaCartao());
        cartao.setSaldo(BigDecimal.valueOf(500.0));

        when(cartaoService.getByNumeroCartao(transacao.getNumeroCartao())).thenReturn(cartao);
        doThrow(InvalidSenhaCartaoException.class).when(senhaCartaoService).validate(cartao.getSenha(), transacao.getSenhaCartao());

        assertThrows(
                InvalidSenhaCartaoException.class,
                () -> service.execute(transacao)
        );
    }

    @Test
    public void execute_withSaldoInsufficient_shouldThrowException() throws InsufficientSaldoException, InvalidSenhaCartaoException, NotFoundEntityException {

        TransacaoEntity transacao = new TransacaoEntity();
        transacao.setNumeroCartao(1111222233334444L);
        transacao.setSenhaCartao(1234);
        transacao.setValor(BigDecimal.valueOf(1000.0));
        transacao.setSaldo(BigDecimal.valueOf(500.0));

        CartaoEntity cartao = new CartaoEntity();
        cartao.setNumeroCartao(transacao.getNumeroCartao());
        cartao.setSenha(transacao.getSenhaCartao());
        cartao.setSaldo(BigDecimal.valueOf(500.0));

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

        TransacaoEntity transacao = new TransacaoEntity();
        transacao.setNumeroCartao(1111222233334444L);
        transacao.setSenhaCartao(1234);
        transacao.setValor(BigDecimal.valueOf(10.0));
        transacao.setSaldo(BigDecimal.valueOf(500.0));

        CartaoEntity cartao = new CartaoEntity();
        cartao.setNumeroCartao(transacao.getNumeroCartao());
        cartao.setSenha(transacao.getSenhaCartao());
        cartao.setSaldo(BigDecimal.valueOf(500.0));

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
