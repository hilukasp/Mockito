[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-22041afd0340ce965d47ae6ef1cefeee28c7c493a6346c4f15d667ab976d596c.svg)](https://classroom.github.com/a/BcqqzQFh)
# Exercício — Testes Unitários com Spring Boot

Sistema de gerenciamento de uma biblioteca com empréstimo de livros. A API já está completamente implementada.

> ⚠️ **Regra principal: nenhum arquivo dentro de `src/main/` deve ser modificado.**  
> Sua tarefa é criar exclusivamente os testes unitários dentro de `src/test/`.

---

## 📐 Visão Geral do Sistema

Um **Empréstimo** associa um **Usuário** a um **Livro** e possui um ciclo de vida: `EMPRESTADO` → `DEVOLVIDO`.

---

## 📂 Entidades

### `Livro`

| Campo | Tipo   | Restrições no Request           |
|-------|--------|---------------------------------|
| id    | Long   | PK, gerado automaticamente      |
| nome  | String | obrigatório (`@NotNull`)        |
| autor | String | obrigatório (`@NotNull`)        |
| isbn  | String | obrigatório (`@NotNull`), único |

### `Usuario`

| Campo | Tipo   | Restrições no Request                       |
|-------|--------|---------------------------------------------|
| id    | Long   | PK, gerado automaticamente                  |
| nome  | String | obrigatório (`@NotNull`)                    |
| email | String | obrigatório (`@NotNull`), e-mail válido (`@Email`), único |
| cpf   | String | obrigatório (`@NotNull`), CPF válido (`@CPF`), único |

### `Emprestimo`

| Campo          | Tipo      | Descrição                               |
|----------------|-----------|-----------------------------------------|
| id             | Long      | PK, gerado automaticamente              |
| livro          | Livro     | Many-to-One                             |
| usuario        | Usuario   | Many-to-One                             |
| status         | String    | `"EMPRESTADO"` ou `"DEVOLVIDO"`         |
| dataEmprestimo | LocalDate | preenchido automaticamente na criação   |
| dataDevolucao  | LocalDate | preenchido automaticamente na devolução |

---

## 🎯 Objetivo do Exercício

Criar testes unitários para as três classes de serviço:

- `LivroService`
- `UsuarioService`
- `EmprestimoService`

O JaCoCo está configurado para exigir **100% de cobertura de branches** no pacote `service`. Os testes devem usar **Mockito** para mockar os repositórios — não devem subir o contexto do Spring nem acessar banco de dados real.

### Comando para verificar cobertura

```bash
./mvnw verify
```

---

## 🗂️ Estrutura esperada dos testes

```
src/test/java/school/sptech/exerciciotestesunitariosspring/
├── service/
│   ├── LivroServiceTest.java
│   ├── UsuarioServiceTest.java
│   └── EmprestimoServiceTest.java
```

### Estrutura básica de uma classe de teste unitário

```java
@ExtendWith(MockitoExtension.class)
@DisplayName("LivroService")
class LivroServiceTest {

    @Mock
    private LivroRepository livroRepository;

    @Mock
    private EmprestimoRepository emprestimoRepository;

    @InjectMocks
    private LivroService livroService;
}
```

---

## 📋 Testes Obrigatórios por Serviço

---

### 1. `LivroService`

#### `listar()`

| # | Cenário | Comportamento esperado |
|---|---------|------------------------|
| 1.1 | Há livros cadastrados | retorna a lista com todos os livros |
| 1.2 | Não há livros cadastrados | retorna lista vazia |

#### `buscarPorId(Long id)`

| # | Cenário | Comportamento esperado |
|---|---------|------------------------|
| 2.1 | ID existe | retorna o `Livro` correspondente |
| 2.2 | ID não existe | lança `EntidadeNaoEncontradaException` |

#### `criar(Livro livro)`

| # | Cenário | Comportamento esperado |
|---|---------|------------------------|
| 3.1 | ISBN não está em uso | salva e retorna o livro |
| 3.2 | ISBN já pertence a outro livro | lança `IsbnDuplicadoException` |

#### `atualizar(Long id, Livro livro)`

| # | Cenário | Comportamento esperado |
|---|---------|------------------------|
| 4.1 | ID existe e ISBN está livre | atualiza e retorna o livro com o `id` correto |
| 4.2 | ID não existe | lança `EntidadeNaoEncontradaException` |
| 4.3 | ISBN já pertence a outro livro | lança `IsbnDuplicadoException` |

#### `deletar(Long id)`

| # | Cenário | Comportamento esperado |
|---|---------|------------------------|
| 5.1 | Livro existe e não tem empréstimo ativo | deleta os empréstimos históricos e o livro |
| 5.2 | ID não existe | lança `EntidadeNaoEncontradaException` |
| 5.3 | Livro possui empréstimo com status `EMPRESTADO` | lança `ItemIndisponivelException` |

---

### 2. `UsuarioService`

#### `listar()`

| # | Cenário | Comportamento esperado |
|---|---------|------------------------|
| 1.1 | Há usuários cadastrados | retorna a lista com todos os usuários |
| 1.2 | Não há usuários cadastrados | retorna lista vazia |

#### `buscarPorId(Long id)`

| # | Cenário | Comportamento esperado |
|---|---------|------------------------|
| 2.1 | ID existe | retorna o `Usuario` correspondente |
| 2.2 | ID não existe | lança `EntidadeNaoEncontradaException` |

#### `criar(Usuario usuario)`

| # | Cenário | Comportamento esperado |
|---|---------|------------------------|
| 3.1 | Email e CPF únicos | salva e retorna o usuário |
| 3.2 | Email já cadastrado | lança `EmailDuplicadoException` |
| 3.3 | CPF já cadastrado | lança `CpfDuplicadoException` |

#### `atualizar(Long id, Usuario usuario)`

| # | Cenário | Comportamento esperado |
|---|---------|------------------------|
| 4.1 | ID existe, email e CPF livres | atualiza e retorna o usuário com o `id` correto |
| 4.2 | ID não existe | lança `EntidadeNaoEncontradaException` |
| 4.3 | Email pertence a outro usuário | lança `EmailDuplicadoException` |
| 4.4 | CPF pertence a outro usuário | lança `CpfDuplicadoException` |

#### `deletar(Long id)`

| # | Cenário | Comportamento esperado |
|---|---------|------------------------|
| 5.1 | Usuário existe e não tem empréstimo ativo | deleta os empréstimos históricos e o usuário |
| 5.2 | ID não existe | lança `EntidadeNaoEncontradaException` |
| 5.3 | Usuário possui empréstimo com status `EMPRESTADO` | lança `ItemIndisponivelException` |

---

### 3. `EmprestimoService`

#### `listar(String status)`

| # | Cenário | Comportamento esperado |
|---|---------|------------------------|
| 1.1 | `status` é `null` | retorna todos os empréstimos (`findAll`) |
| 1.2 | `status` é `"EMPRESTADO"` | retorna apenas os empréstimos ativos |
| 1.3 | `status` é `"DEVOLVIDO"` | retorna apenas os empréstimos devolvidos |
| 1.4 | `status` é qualquer outro valor (ex: `"INVALIDO"`) | lança `RequisicaoInvalidaException` |

#### `criar(Long usuarioId, Long livroId)`

| # | Cenário | Comportamento esperado |
|---|---------|------------------------|
| 2.1 | Usuário e livro existem, ambos disponíveis | cria empréstimo com status `EMPRESTADO` e `dataEmprestimo` preenchida, salva e retorna |
| 2.2 | `usuarioId` não existe | lança `EntidadeNaoEncontradaException` |
| 2.3 | `livroId` não existe | lança `EntidadeNaoEncontradaException` |
| 2.4 | Usuário já possui um empréstimo `EMPRESTADO` | lança `UsuarioJaPossuiEmprestimoException` |
| 2.5 | Livro já está com status `EMPRESTADO` | lança `ItemIndisponivelException` |

#### `devolver(Long id)`

| # | Cenário | Comportamento esperado |
|---|---------|------------------------|
| 3.1 | Empréstimo existe e está `EMPRESTADO` | atualiza status para `DEVOLVIDO`, preenche `dataDevolucao`, salva e retorna |
| 3.2 | `id` não existe | lança `EntidadeNaoEncontradaException` |
| 3.3 | Empréstimo já está com status `DEVOLVIDO` | lança `RequisicaoInvalidaException` |

#### `prazoMedio()`

| # | Cenário | Comportamento esperado |
|---|---------|------------------------|
| 4.1 | Há empréstimos devolvidos | retorna `PrazoMedioResponseDto` com a média de dias e o total corretos |
| 4.2 | Nenhum empréstimo foi devolvido ainda | lança `InformacaoIndisponivelException` |

---

## ⚠️ Exceções e seus HTTP Status

| Exceção | HTTP Status |
|---------|-------------|
| `EntidadeNaoEncontradaException` | 404 Not Found |
| `EmailDuplicadoException` | 409 Conflict |
| `CpfDuplicadoException` | 409 Conflict |
| `IsbnDuplicadoException` | 409 Conflict |
| `ItemIndisponivelException` | 409 Conflict |
| `UsuarioJaPossuiEmprestimoException` | 409 Conflict |
| `RequisicaoInvalidaException` | 400 Bad Request |
| `InformacaoIndisponivelException` | 422 Unprocessable Content |

---

## 💡 Dicas de Implementação

### Verificando a exceção lançada

```java
assertThrows(EntidadeNaoEncontradaException.class,
    () -> livroService.buscarPorId(99L));
```

### Verificando que o repositório foi chamado (ou não foi)

```java
// foi chamado exatamente uma vez
verify(livroRepository, times(1)).save(any(Livro.class));

// nunca foi chamado
verify(livroRepository, never()).save(any(Livro.class));
```

### Configurando retornos do mock

```java
// retorna um Optional preenchido
when(livroRepository.findById(1L)).thenReturn(Optional.of(livro));

// simula entidade não encontrada
when(livroRepository.findById(99L)).thenReturn(Optional.empty());

// simula ISBN duplicado
when(livroRepository.findByIsbn("978-0-13-468599-1")).thenReturn(Optional.of(outroLivro));
```

### Validando o `atualizar` (PUT) — o `id` precisa ser propagado para a entidade

O método `atualizar` chama `livro.setId(id)` antes de salvar. Use `ArgumentCaptor` para capturar o objeto que chegou ao `save` e verificar que o `id` foi definido corretamente:

```java
@Captor
private ArgumentCaptor<Livro> livroCaptor;

// após chamar livroService.atualizar(...)
verify(livroRepository).save(livroCaptor.capture());
Livro salvo = livroCaptor.getValue();

assertEquals(idEsperado, salvo.getId());
assertEquals(statusEsperado, salvo.getStatus());
```

### Testando o prazo médio (`EmprestimoService.prazoMedio`)

Monte os empréstimos devolvidos com datas controladas para calcular a média esperada:

```java
Emprestimo e1 = new Emprestimo();
e1.setDataEmprestimo(LocalDate.of(2025, 1, 1));
e1.setDataDevolucao(LocalDate.of(2025, 1, 11)); // 10 dias

Emprestimo e2 = new Emprestimo();
e2.setDataEmprestimo(LocalDate.of(2025, 2, 1));
e2.setDataDevolucao(LocalDate.of(2025, 2, 21)); // 20 dias

// média esperada = 15.0 dias, total = 2
```

---

## 🏆 Critério de Aprovação (JaCoCo)

O `pom.xml` verifica a cobertura em 10 etapas incrementais (10% → 100%) sobre **branches** do pacote `service`. O build só passa completamente com **100% de cobertura de branches**.

```
[INFO] --- jacoco:check-coverage-100 ---
[INFO] All coverage checks have been met.
[INFO] BUILD SUCCESS
```

Todo `if`, `else`, `Optional.isPresent()` e cada lançamento de exceção precisam ser exercitados por pelo menos um teste de caminho feliz e um de caminho de erro.

---

> "As dificuldades revelam capacidades que o conforto nunca mostraria."