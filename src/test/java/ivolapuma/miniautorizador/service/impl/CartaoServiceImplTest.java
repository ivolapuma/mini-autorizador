package ivolapuma.miniautorizador.service.impl;

import ivolapuma.miniautorizador.builder.GenericBuilder;
import ivolapuma.miniautorizador.dto.CreateCartaoRequestDTO;
import ivolapuma.miniautorizador.entity.CartaoEntity;
import ivolapuma.miniautorizador.exception.*;
import ivolapuma.miniautorizador.repository.CartaoRepository;
import ivolapuma.miniautorizador.service.NumeroCartaoService;
import ivolapuma.miniautorizador.service.SaldoService;
import ivolapuma.miniautorizador.service.SenhaCartaoService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CartaoServiceImplTest {

    @InjectMocks
    private CartaoServiceImpl service;

    @Mock
    private MessageSource messages;

    @Mock
    private NumeroCartaoService numeroCartaoService;

    @Mock
    private SenhaCartaoService senhaCartaoService;

    @Mock
    private SaldoService saldoService;

    @Mock
    private CartaoRepository repository;

    @Test
    public void create_withCartaoValid_shouldCreateCartao() throws Throwable {
        BigDecimal saldoExpected = BigDecimal.valueOf(1234.56);
        CartaoEntity cartao = new CartaoEntity();
        cartao.setNumeroCartao(1234567890123456L);
        cartao.setSenha(1234);
        CartaoEntity expected = new CartaoEntity();
        cartao.setNumeroCartao(cartao.getNumeroCartao());
        cartao.setSenha(cartao.getSenha());
        cartao.setSaldo(saldoExpected);
        when(repository.existsById(cartao.getNumeroCartao())).thenReturn(false);
        when(saldoService.getSaldoDefault()).thenReturn(saldoExpected);
        when(repository.save(cartao)).thenReturn(expected);
        CartaoEntity actual = service.create(cartao);
        Assertions.assertNotNull(actual, "actual cant be null");
        Assertions.assertEquals(expected.getNumeroCartao(), actual.getNumeroCartao(), "numeroCartao should be equal");
        Assertions.assertEquals(expected.getSenha(), actual.getSenha(), "senha should be equal");
        Assertions.assertEquals(expected.getSaldo(), actual.getSaldo(), "saldo should be equal");
    }

    @Test
    public void create_withCartaoInexistent_shouldThrowUnprocessableEntityException() throws Throwable {
        CartaoEntity cartao = new CartaoEntity();
        cartao.setNumeroCartao(1234567890123456L);
        cartao.setSenha(1234);
        when(repository.existsById(cartao.getNumeroCartao())).thenReturn(true);
        UnprocessableEntityException e = Assertions.assertThrows(
                UnprocessableEntityException.class,
                () -> service.create(cartao)
        );
    }

    @Test
    public void getSaldo_withNumeroCartaoValid_shouldReturnSaldo() throws Throwable {
        long numeroCartao = 1234567890123456L;
        CartaoEntity cartao = new CartaoEntity();
        cartao.setNumeroCartao(numeroCartao);
        cartao.setSenha(1234);
        cartao.setSaldo(BigDecimal.valueOf(500.0));
        when(repository.findById(numeroCartao)).thenReturn(Optional.of(cartao));
        BigDecimal saldoActual = service.getSaldo(numeroCartao);
        Assertions.assertNotNull(saldoActual, "saldo cant be null");
        Assertions.assertEquals(cartao.getSaldo(), saldoActual, "saldo should be equal");
    }

    @Test
    public void getSaldo_withNumeroCartaoInexistent_shouldThrowNotFoundEntityException() {
        long numeroCartao = 1234567890123456L;
        CartaoEntity cartao = new CartaoEntity();
        cartao.setNumeroCartao(numeroCartao);
        cartao.setSenha(1234);
        cartao.setSaldo(BigDecimal.valueOf(500.0));
        when(repository.findById(numeroCartao)).thenReturn(Optional.ofNullable(null));
        NotFoundEntityException e = Assertions.assertThrows(
                NotFoundEntityException.class,
                () -> service.getSaldo(numeroCartao)
        );
    }

    @Test
    public void getByNumeroCartao_withNumeroCartaoValid_shouldReturnCartao() throws NotFoundEntityException {
        long numeroCartao = 1234567890123456L;
        CartaoEntity expected = new CartaoEntity();
        expected.setNumeroCartao(numeroCartao);
        expected.setSenha(1234);
        expected.setSaldo(BigDecimal.valueOf(500.0));
        when(repository.findById(numeroCartao)).thenReturn(Optional.of(expected));
        CartaoEntity actual = service.getByNumeroCartao(numeroCartao);
        Assertions.assertNotNull(actual, "actual cant be null");
        Assertions.assertEquals(expected.getNumeroCartao(), actual.getNumeroCartao(), "numeroCartao should be equal");
        Assertions.assertEquals(expected.getSenha(), actual.getSenha(), "senha should be equal");
        Assertions.assertEquals(expected.getSaldo(), actual.getSaldo(), "saldo should be equal");
    }

    @Test
    public void getByNumeroCartao_withNumeroCartaoInexistent_shouldThrowNoSuchElementException() {
        Long numeroCartao = 1234567890123456L;
        when(repository.findById(numeroCartao)).thenReturn(Optional.ofNullable(null));
        NotFoundEntityException e = Assertions.assertThrows(
                NotFoundEntityException.class,
                () -> service.getByNumeroCartao(numeroCartao)
        );
    }

    @Test
    public void validate_withRequestValid_shouldRunOk() {
        CreateCartaoRequestDTO request = new CreateCartaoRequestDTO();
        request.setNumeroCartao("1234567890123456");
        request.setSenha("1234");
        assertDoesNotThrow(
                () -> service.validate(request)
        );
    }

    @Test
    public void validate_withNull_shouldThrowException() {
        BadRequestException e = Assertions.assertThrows(
                BadRequestException.class,
                () -> service.validate(null)
        );
    }

    @Test
    public void validate_withNumeroCartaoEmpty_shouldThrowException() throws InvalidNumeroCartaoException {
        CreateCartaoRequestDTO request = new CreateCartaoRequestDTO();
        request.setNumeroCartao("");
        request.setSenha("1234");
        doThrow(InvalidNumeroCartaoException.class).when(numeroCartaoService).validate(request.getNumeroCartao());
        BadRequestException e = Assertions.assertThrows(
                BadRequestException.class,
                () -> service.validate(request)
        );
    }

    @Test
    public void validate_withSenhaEmpty_shouldThrowException() throws InvalidNumeroCartaoException, InvalidSenhaCartaoException {
        CreateCartaoRequestDTO request = new CreateCartaoRequestDTO();
        request.setNumeroCartao("1234567890123456");
        request.setSenha("");
        doNothing().when(numeroCartaoService).validate(request.getNumeroCartao());
        doThrow(InvalidSenhaCartaoException.class).when(senhaCartaoService).validate(request.getSenha());
        BadRequestException e = Assertions.assertThrows(
                BadRequestException.class,
                () -> service.validate(request)
        );
    }

    @Test
    public void validate_withNumeroCartaoInvalid_shouldThrowException() throws InvalidNumeroCartaoException {
        CreateCartaoRequestDTO request = new CreateCartaoRequestDTO();
        request.setNumeroCartao("x234567890123456");
        request.setSenha("1234");
        doThrow(InvalidNumeroCartaoException.class).when(numeroCartaoService).validate(request.getNumeroCartao());
        BadRequestException e = Assertions.assertThrows(
                BadRequestException.class,
                () -> service.validate(request)
        );
    }

    @Test
    public void validate_withSenhaInvalid_shouldThrowException() throws InvalidNumeroCartaoException, InvalidSenhaCartaoException {
        CreateCartaoRequestDTO request = new CreateCartaoRequestDTO();
        request.setNumeroCartao("1234567890123456");
        request.setSenha("x234");
        doNothing().when(numeroCartaoService).validate(request.getNumeroCartao());
        doThrow(InvalidSenhaCartaoException.class).when(senhaCartaoService).validate(request.getSenha());
        BadRequestException e = Assertions.assertThrows(
                BadRequestException.class,
                () -> service.validate(request)
        );
    }

    @Test
    public void debitSaldo_withNumeroCartaoAndValor_shouldDebitSaldoCartao() throws InsufficientSaldoException, NotFoundEntityException {
        Long numeroCartao = 1111222233334444L;
        BigDecimal valor = BigDecimal.valueOf(10.0);
        BigDecimal saldoExpected = BigDecimal.valueOf(500.0).subtract(BigDecimal.valueOf(10.0));
        CartaoEntity cartao =
            GenericBuilder.of(CartaoEntity::new)
                    .with(CartaoEntity::setNumeroCartao, numeroCartao)
                    .with(CartaoEntity::setSenha, 1111)
                    .with(CartaoEntity::setSaldo, BigDecimal.valueOf(500.0))
                    .build();
        when(repository.findByIdWithLock(numeroCartao)).thenReturn(cartao);
        doNothing().when(saldoService).verifyIfSufficient(cartao.getSaldo(), valor);
        when(repository.save(cartao)).thenReturn(cartao);
        CartaoEntity debited = service.debitSaldo(numeroCartao, valor);
        assertNotNull(debited, "debited cannot be null");
        assertEquals(saldoExpected, debited.getSaldo(), "saldo should be equal");
    }

    @Test
    public void debitSaldo_withCartaoInexistent_shouldThrowException() throws InsufficientSaldoException {
        Long numeroCartao = 1111222233334444L;
        BigDecimal valor = BigDecimal.valueOf(10.0);
        CartaoEntity cartao =
                GenericBuilder.of(CartaoEntity::new)
                        .with(CartaoEntity::setNumeroCartao, numeroCartao)
                        .with(CartaoEntity::setSenha, 1111)
                        .with(CartaoEntity::setSaldo, BigDecimal.valueOf(500.0))
                        .build();
        when(repository.findByIdWithLock(numeroCartao)).thenReturn(null);
        assertThrows(
                NotFoundEntityException.class,
                () -> service.debitSaldo(numeroCartao, valor)
        );
    }

    @Test
    public void debitSaldo_withSaldoInsufficient_shouldThrowException() throws InsufficientSaldoException {
        Long numeroCartao = 1111222233334444L;
        BigDecimal valor = BigDecimal.valueOf(1000.0);
        CartaoEntity cartao =
                GenericBuilder.of(CartaoEntity::new)
                        .with(CartaoEntity::setNumeroCartao, numeroCartao)
                        .with(CartaoEntity::setSenha, 1111)
                        .with(CartaoEntity::setSaldo, BigDecimal.valueOf(500.0))
                        .build();
        when(repository.findByIdWithLock(numeroCartao)).thenReturn(cartao);
        doThrow(InsufficientSaldoException.class).when(saldoService).verifyIfSufficient(cartao.getSaldo(), valor);
        assertThrows(
                InsufficientSaldoException.class,
                () -> service.debitSaldo(numeroCartao, valor)
        );
    }

}
