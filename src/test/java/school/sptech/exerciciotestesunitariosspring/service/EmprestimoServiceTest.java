package school.sptech.exerciciotestesunitariosspring.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import school.sptech.exerciciotestesunitariosspring.entity.Emprestimo;
import school.sptech.exerciciotestesunitariosspring.entity.Livro;
import school.sptech.exerciciotestesunitariosspring.entity.Usuario;
import school.sptech.exerciciotestesunitariosspring.exception.RequisicaoInvalidaException;
import school.sptech.exerciciotestesunitariosspring.repository.EmprestimoRepository;
import school.sptech.exerciciotestesunitariosspring.repository.LivroRepository;
import school.sptech.exerciciotestesunitariosspring.repository.UsuarioRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
@DisplayName("Emprestimo teste")
class EmprestimoServiceTest {
    @Mock
    private EmprestimoRepository emprestimoRepository;

    @Mock
    private LivroRepository livroRepository;

    @Mock
    private UsuarioRepository usuarioRepository;
    @InjectMocks
    private EmprestimoService emprestimoService;


    Emprestimo emprestimoconstruct(){
        Livro livro = new Livro();
        livro.setId(1L);
        livro.setAutor("autor");
        livro.setIsbn("isbn");
        livro.setNome("nome");

        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setCpf("222222");
        usuario.setEmail("lucas@gmail");
        usuario.setNome("lucas");

        Emprestimo emprestimo = new Emprestimo();
        emprestimo.setUsuario(usuario);
        emprestimo.setLivro(livro);
        emprestimo.setStatus("EMPRESTADO");
        emprestimo.setId(1L);


        return emprestimo;
    }
    @Test
    @DisplayName("criar emprestimo")
    void criar() {
        Emprestimo emprestimo = emprestimoconstruct();

        Mockito.when(usuarioRepository.findById(emprestimo.getUsuario().getId())).
                thenReturn(Optional.of(emprestimo.getUsuario()));

        Mockito.when(livroRepository.findById(emprestimo.getLivro().getId())).
                thenReturn(Optional.of(emprestimo.getLivro()));

        Mockito.when(emprestimoRepository.findByUsuarioAndStatus(emprestimo.getUsuario(),"EMPRESTADO"))
                .thenReturn(Optional.empty());

        Mockito.when(emprestimoRepository.findByLivroAndStatus(emprestimo.getLivro(),"EMPRESTADO"))
                .thenReturn(Optional.empty());

        Mockito.when(emprestimoRepository.save(any(Emprestimo.class)))
                .thenReturn(emprestimo);


        emprestimo = emprestimoService.criar(emprestimo.getUsuario().getId(),emprestimo.getLivro().getId());

        Assertions.assertEquals("EMPRESTADO",emprestimo.getStatus());
        Assertions.assertEquals(1L,emprestimo.getId());
    }

    @Nested
    @DisplayName("Teste do método devolver")
    class metodoDevolver{
        @Test
        @DisplayName("Deve marcar o livro como devolvido")
        void deveDevolver() {
            Long id = 1L;
            Emprestimo emprestimo = emprestimoconstruct();
            Mockito.when(emprestimoRepository.findById(id)).thenReturn(Optional.of(emprestimo));
            Mockito.when(emprestimoRepository.save(any(Emprestimo.class)))
                    .thenReturn(emprestimo);
            Emprestimo emprestimoretorno= emprestimoService.devolver(id);

            Assertions.assertEquals(1L,emprestimoretorno.getId());
            Assertions.assertEquals("DEVOLVIDO",emprestimoretorno.getStatus());
            Assertions.assertEquals(LocalDate.now(),emprestimoretorno.getDataDevolucao());
        }
        @Test
        void deveLancarExcessaoStatusDevolvido() {

            Long id = 1L;

            Emprestimo emprestimo = new Emprestimo();
            emprestimo.setId(id);
            emprestimo.setStatus("DEVOLVIDO");

            Mockito.when(emprestimoRepository.findById(id)).thenReturn(Optional.of(emprestimo));

            Assertions.assertThrows(RequisicaoInvalidaException.class,
                    () -> emprestimoService.devolver(id));

            Mockito.verify(emprestimoRepository, Mockito.never())
                    .findAll();
        }
    }

    @Nested
    @DisplayName("Teste do método devolver")
    class metodoPrazoMedio{
        @Test
        @DisplayName("Deve informar o prazo médio de empréstimos")
        void deveInformarOPrazoMedio() {
            Emprestimo emprestimo = emprestimoconstruct();
            emprestimo.setStatus("DEVOLVIDO");

            Mockito.when(emprestimoRepository.findByStatus("DEVOLVIDO"))
                    .thenReturn(List.of(emprestimo));
            emprestimo.setDataEmprestimo(LocalDate.now().minusDays(5));
            emprestimo.setDataDevolucao(LocalDate.now());
            emprestimoService.prazoMedio();

            Assertions.assertEquals("DEVOLVIDO",emprestimo.getStatus());
        }
    }



    @Nested
    @DisplayName("Teste do método listar")
    class metodoListar {

        @Test
        @DisplayName("Deve listar todos os empréstimos quando status for null")
        void deveListarTodosOsEmprestimos() {

            Emprestimo emp1 = emprestimoconstruct();
            Emprestimo emp2 = emprestimoconstruct();
            emp2.setId(2L);

            List<Emprestimo> lista = List.of(emp1, emp2);

            Mockito.when(emprestimoRepository.findAll()).thenReturn(lista);

            List<Emprestimo> retorno = emprestimoService.listar(null);

            Assertions.assertEquals(2, retorno.size());

        }

        @Test
        @DisplayName("Deve listar empréstimos com status EMPRESTADO")
        void deveListarEmprestimosComStatusEmprestado() {

            Emprestimo emp1 = emprestimoconstruct();
            Emprestimo emp2 = emprestimoconstruct();
            emp2.setId(2L);

            List<Emprestimo> lista = List.of(emp1, emp2);

            Mockito.when(emprestimoRepository.findByStatus("EMPRESTADO"))
                    .thenReturn(lista);

            List<Emprestimo> retorno =
                    emprestimoService.listar("EMPRESTADO");

            Assertions.assertEquals(2, retorno.size());

        }

        @Test
        @DisplayName("Deve listar empréstimos com status DEVOLVIDO")
        void deveListarEmprestimosComStatusDevolvido() {

            Emprestimo emp1 = emprestimoconstruct();
            Emprestimo emp2 = emprestimoconstruct();
            emp2.setId(2L);
            emp2.setStatus("DEVOLVIDO");

            List<Emprestimo> lista = List.of(emp1, emp2);

            Mockito.when(emprestimoRepository.findByStatus("DEVOLVIDO"))
                    .thenReturn(lista);

            List<Emprestimo> retorno =
                    emprestimoService.listar("DEVOLVIDO");

            Assertions.assertEquals(2, retorno.size());

        }

        @Test
        @DisplayName("Deve lançar exceção quando status for inválido")
        void deveLancarExcecaoQuandoStatusForInvalido() {

            String status = "PENDENTE";

            Assertions.assertThrows(
                    RequisicaoInvalidaException.class,
                    () -> emprestimoService.listar(status)
            );



            Mockito.verify(emprestimoRepository, Mockito.never())
                    .findAll();
        }
    }

}