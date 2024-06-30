# Resolução do Teste de programação - VR Benefícios

Este projeto é uma proposta de resolução ao teste de programação proposto pela VR Benefícios.

Seguindo as recomendações da proposta, este projeto foi desenvolvido com Java 17, Maven e Spring Boot 3.1.2. O repositório de dados usado foi o MySQL, conforme especificações contidas no arquivo Docker Compose da proposta.

Conforme a proposta, o projeto disponibiliza 3 endpoints do tipo REST:
* Método POST, URL http://localhost:8080/cartoes com Body JSON --> Criar um novo Cartão
* Método GET, URL http://localhost:8080/cartoes/{numeroCartao} --> Consulta saldo do Cartão
* Método POST, URL http://localhost:8080/transacoes com Body JSON --> Realizar uma Transação de débito no saldo do Cartão

A autenticação nos 3 endpoints é feita no modo BASIC, com usuário e senha definidos conforme especificação. Para alterar os valores de usuário e/ou senha, basta editar as entradas "spring.security.user.name" e "spring.security.user.password" do arquivo application.yml.

## Resolução aos desafios propostos

Apesar de opcionais, este projeto buscou resolver os desafios propostos.

### 1. Construir sem usar *if*

Buscou-se cumprir esse desafio por meio da implementação de classes de validação, baseadas numa classe abstrata (*Validator*) definindo comportamentos baseados no padrão Strategy. Ver documentação da interface *ValidatorStrategy*.

Aliás, o único *if* existente no projeto está em um método default da interface *ValidatorStrategy*, pois era uma intenção desta proposta o lançamento de exceções nos casos em que as regras de validação definidas nas classes *Validator* não são atendidas.

### 2. Como evitar problemas de inconsistência no saldo quando há 2 ou mais transações concorrentes

Para evitar que transações concorrentes possam tornar o status do saldo do cartao inconsistente, o método que realiza o serviço de débito no saldo do Cartão usa uma abordagem de trava pessimista do registro do Cartão na base de dados. De tal forma, quando o serviço de débito de saldo é chamado, o registro do Cartão é travado para leitura e gravações por outros requisitantes, impedindo qualquer alteração de estado durante a execução do método.

Conferir código da classe *CartaoServiceImpl*, no método *debitSaldo()*. Lá está sendo usada a anotação *@Transactional* para definir que o método é executado com controle de transação. A trava pessimista está definida no método *findByIdWithLock()*, na interface *CartaoRepository*. 

Não é escopo da proposta, mas outra forma de evitar que o saldo fique inconsistente devido a transações concorrentes poderia ser a definição de uma *trigger* na tabela que lança um erro em caso do saldo ficar negativo.

## Observações gerais

### 1. Sobre o modelo de domínio

Foram definidas duas entidades:
* Cartão, para representar um cartão com número, senha e saldo.
* Transação, para representar uma transação de débito de saldo realizada (com sucesso ou não) no sistema.

Apesar de ser opcional, esta proposta faz a persistência dos dados da Transação na base de dados. Além das transações bem sucedidas, estão sendo persistidas as transações com alguma falha devido aos motivos de:
* Cartão inexistente
* Senha do cartão inválida
* Saldo insuficiente para o valor informado

### 2. Sobre convenções de código

Peço licença aqui para empregar a primeira pessoa. Particularmente, eu não tenho posições rígidas sobre formatação de código. Contudo, busquei seguir o "Google Java Style Guide" (https://google.github.io/styleguide/javaguide.html).

Outro ponto em que não tenho posição rígida é com relação à lingua usada no código. Considero importante que haja algum padrão a ser seguido, não importando se tudo deve estar na língua inglesa ou se pode misturar línguas. Evidentemente em projetos internacionais, usar a língua inglesa acaba sendo impositivo devido ao Inglês ser a língua comum nos negócios.

Entranto, para este projeto, foi buscado escrever o máximo possível na língua inglesa. A exceção ficou para as mensagens a serem exibidas e os termos referentes às entidades e atributos do modelo de domínio do negócio do sistema, tais como Cartão, Transação, Número do Cartão etc.

### 3. Sobre os testes automatizados

Neste projeto, a intenção foi implementar testes automatizados que obtivessem a maior cobertura possível do código sensível.

A abordagem para buscar este objetivo foi implementar testes unitários, principalmente sobre as classes de implementação de serviço, com uso do framework *Mockito* quando necessário. E implementar testes de integração sobre as classes *Controller*, com uso do framework do Spring para Mock de MVC.

Há pelo menos um teste de integração definido para cada situação de teste, conforme especificado, a ser verificada na avaliação da resolução.

#### Padrão de nomenclatura das classes de testes
* testes unitários: *Test.java -->  ex.: NomeClasseAlvoTest.java
* testes de integração: *IntegratedTest.java --> ex.: NomeClasseAlvoIntegratedTest.java

#### Padrão de nomenclatura dos métodos de testes

Foi definido um padrão para os nomes dos métodos de testes inspirados na técnica *Behavior Driven Development (BDD)* em que cada nome descreve o método da classe-alvo a ser testada, o contexto do teste e o que é esperado como resultado pelo teste.

- DADO um cenário, QUANDO algo acontecer, ENTÃO é esperado o resultado.

O template seguido nos testes automatizados, conforme essa orientação foi o seguinte:
```
<nomeDoMetodo>_with<DescricaoCenario>_should<ResultadoEsperado>
```

Exemplo:
```
public class CartaoControllerIntegratedTest {
    ...
    public void cartoesPost_withValidBody_shouldReturnStatusCreatedAndBody() {...}
    ...
}
```

No exemplo acima, é testado o método cartoesPost() da classe CartaoController, o cenário é um Body considerado válido e o resultado esperado é o status CREATED e um Body na resposta.

## Informações adicionais

### Subindo a aplicação

Após subir o banco MySQL (docker compose up), na pasta principal do projeto, digitar na linha de comando:
```
mvn spring-boot:run
```

### Rodandos os testes

Para rodar todos os testes do projeto, na pasta principal do projeto, digitar na linha de comando:
```
mvn test
```

