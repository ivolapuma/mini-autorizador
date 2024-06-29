package ivolapuma.miniautorizador.service;

import ivolapuma.miniautorizador.dto.CriaCartaoRequestDTO;
import ivolapuma.miniautorizador.entity.CartaoEntity;
import ivolapuma.miniautorizador.exception.NotFoundEntityException;
import ivolapuma.miniautorizador.exception.UnprocessableEntityException;
import ivolapuma.miniautorizador.repository.CartaoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CartaoServiceImplTest {

    @InjectMocks
    private CartaoServiceImpl service;

    @Mock
    private CartaoRepository repository;

    @Test
    public void create_withValidCartao_shouldCreateCartao() throws Throwable {
        CartaoEntity cartao = new CartaoEntity();
        cartao.setNumeroCartao(1234567890123456L);
        cartao.setSenha(1234);
        CartaoEntity expected = new CartaoEntity();
        cartao.setNumeroCartao(cartao.getNumeroCartao());
        cartao.setSenha(cartao.getSenha());
        cartao.setSaldo(BigDecimal.valueOf(500.0));
        when(repository.existsById(cartao.getNumeroCartao())).thenReturn(false);
        when(repository.save(cartao)).thenReturn(expected);
        CartaoEntity actual = service.create(cartao);
        Assertions.assertNotNull(actual, "actual cant be null");
        Assertions.assertEquals(expected.getNumeroCartao(), actual.getNumeroCartao(), "numeroCartao should be equal");
        Assertions.assertEquals(expected.getSenha(), actual.getSenha(), "senha should be equal");
        Assertions.assertEquals(expected.getSaldo(), actual.getSaldo(), "saldo should be equal");
    }

    @Test
    public void create_withInexistentCartao_shouldThrowUnprocessableEntityException() throws Throwable {
        CartaoEntity cartao = new CartaoEntity();
        cartao.setNumeroCartao(1234567890123456L);
        cartao.setSenha(1234);
        when(repository.existsById(cartao.getNumeroCartao())).thenReturn(true);
        UnprocessableEntityException e = Assertions.assertThrows(
                UnprocessableEntityException.class,
                () -> service.create(cartao)
        );
        Assertions.assertEquals("Cartão já existe na base de dados", e.getMessage(), "message should be equal");
    }

    @Test
    public void getSaldo_withValidNumeroCartao_shouldReturnSaldo() throws Throwable {
        long numeroCartao = 1234567890123456L;
        CartaoEntity cartao = new CartaoEntity();
        cartao.setNumeroCartao(numeroCartao);
        cartao.setSenha(1234);
        cartao.setSaldo(BigDecimal.valueOf(500.0));
        when(repository.existsById(numeroCartao)).thenReturn(true);
        when(repository.findById(numeroCartao)).thenReturn(Optional.of(cartao));
        BigDecimal saldoActual = service.getSaldo(numeroCartao);
        Assertions.assertNotNull(saldoActual, "saldo cant be null");
        Assertions.assertEquals(cartao.getSaldo(), saldoActual, "saldo should be equal");
    }

    @Test
    public void getSaldo_withInexistentNumeroCartao_shouldThrowNotFoundEntityException() throws Throwable {
        long numeroCartao = 1234567890123456L;
        CartaoEntity cartao = new CartaoEntity();
        cartao.setNumeroCartao(numeroCartao);
        cartao.setSenha(1234);
        cartao.setSaldo(BigDecimal.valueOf(500.0));
        when(repository.existsById(numeroCartao)).thenReturn(false);
        NotFoundEntityException e = Assertions.assertThrows(
                NotFoundEntityException.class,
                () -> service.getSaldo(numeroCartao)
        );
        Assertions.assertEquals("Cartão não existe na base de dados", e.getMessage(), "message should be equal");
    }

    @Test
    public void getByNumeroCartao_withValidNumeroCartao_shouldReturnCartao() throws Throwable {
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
    public void getByNumeroCartao_withInexistentNumeroCartao_shouldThrowNoSuchElementException() throws Throwable {
        Long numeroCartao = 1234567890123456L;
        when(repository.findById(numeroCartao)).thenReturn(Optional.ofNullable(null));
        NoSuchElementException e = Assertions.assertThrows(
                NoSuchElementException.class,
                () -> service.getByNumeroCartao(numeroCartao)
        );
    }

    @Test
    public void validate_withValidRequest_shouldRunOk() {
        CriaCartaoRequestDTO request = new CriaCartaoRequestDTO();
        request.setNumeroCartao("1234567890123456");
        request.setSenha("1234");
        Assertions.assertDoesNotThrow(
                ()->service.validate(request)
        );
    }

    @Test
    public void validate_withNull_shouldThrowException() {
        CriaCartaoRequestDTO request = null;
        IllegalArgumentException e = Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> service.validate(request)
        );
        Assertions.assertEquals("Dados da requisição inválidos", e.getMessage(), "message should be equal");
    }

    @Test
    public void validate_withNumeroCartaoEmpty_shouldThrowException() {
        CriaCartaoRequestDTO request = new CriaCartaoRequestDTO();
        request.setNumeroCartao("");
        request.setSenha("1234");
        IllegalArgumentException e = Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> service.validate(request)
        );
        Assertions.assertEquals("Número do cartão não pode ser vazio ou nulo", e.getMessage(), "message should be equal");
    }

    @Test
    public void validate_withSenhaEmpty_shouldThrowException() {
        CriaCartaoRequestDTO request = new CriaCartaoRequestDTO();
        request.setNumeroCartao("1234567890123456");
        request.setSenha("");
        IllegalArgumentException e = Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> service.validate(request)
        );
        Assertions.assertEquals("Senha do cartão não pode ser vazia ou nula", e.getMessage(), "message should be equal");
    }

    @Test
    public void validate_withNumeroCartaoInvalid_shouldThrowException() {
        CriaCartaoRequestDTO request = new CriaCartaoRequestDTO();
        request.setNumeroCartao("x234567890123456");
        request.setSenha("1234");
        IllegalArgumentException e = Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> service.validate(request)
        );
        Assertions.assertEquals("Número do cartão deve conter 16 dígitos", e.getMessage(), "message should be equal");
    }

    @Test
    public void validate_withSenhaInvalid_shouldThrowException() {
        CriaCartaoRequestDTO request = new CriaCartaoRequestDTO();
        request.setNumeroCartao("1234567890123456");
        request.setSenha("x234");
        IllegalArgumentException e = Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> service.validate(request)
        );
        Assertions.assertEquals("Senha do cartão deve conter 4 dígitos", e.getMessage(), "message should be equal");
    }

    @Test
    public void validateNumeroCartao_withNumeroCartaoOk_shouldRunOk() {
        String numeroCartao = "1234567890123456";
        Assertions.assertDoesNotThrow(
                () -> service.validateNumeroCartao(numeroCartao)
        );
    }

    @Test
    public void validateNumeroCartao_withNumeroCartaoEmpty_shouldThrowException() {
        String numeroCartao = "";
        IllegalArgumentException e = Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> service.validateNumeroCartao(numeroCartao)
        );
        Assertions.assertEquals("Número do cartão não pode ser vazio ou nulo", e.getMessage(), "message should be equal");
    }

    @Test
    public void validateNumeroCartao_withNumeroCartaoInvalid_shouldThrowException() {
        String numeroCartao = "x234567890123456";
        IllegalArgumentException e = Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> service.validateNumeroCartao(numeroCartao)
        );
        Assertions.assertEquals("Número do cartão deve conter 16 dígitos", e.getMessage(), "message should be equal");
    }

}
