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
import school.sptech.exerciciotestesunitariosspring.exception.EntidadeNaoEncontradaException;
import school.sptech.exerciciotestesunitariosspring.exception.IsbnDuplicadoException;
import school.sptech.exerciciotestesunitariosspring.exception.ItemIndisponivelException;
import school.sptech.exerciciotestesunitariosspring.repository.EmprestimoRepository;
import school.sptech.exerciciotestesunitariosspring.repository.LivroRepository;

import java.util.List;
import java.util.Optional;

//importa o mockito
@ExtendWith(MockitoExtension.class)
@DisplayName("LivroService")
class LivroServiceTest {

    //o que estiver comentado em bloco representa o código do service
    /*

    private final LivroRepository livroRepository;
    private final EmprestimoRepository emprestimoRepository;
     */

    //injeta as dependências da classe de serviço
    @Mock
    private LivroRepository livroRepository;

    @Mock
    private EmprestimoRepository emprestimoRepository;

    //Inject é o metodo real que você quer testar
    @InjectMocks
    private LivroService livroService;

    //cria o objeto livro com os campos porque os métodos dependem dele
    /*public Livro criar(Livro livro)*/
    Livro livroconstruct(Long id){

        Livro livro = new Livro();
        livro.setId(id);
        livro.setAutor("autor");
        livro.setIsbn("isbn");
        livro.setNome("nome");
        return livro;
    }


    @Nested
    @DisplayName("teste metodo criar")
    class criar{
        @Test
        @DisplayName("criar")
        void deveCriar(){
            /*
            if (livroRepository.findByIsbn(livro.getIsbn()).isPresent()) {
                throw new IsbnDuplicadoException("ISBN '" + livro.getIsbn() + "' já cadastrado");
            }
            return livroRepository.save(livro);
             */

            //AAA
            //arrange
            Livro livro=livroconstruct(1L);
            //esse metodo é chamado dentro da função criar. Como não queremos testar esse metodo do repository, só especificamos o Tipo de objeto que ele passa e o Objeto que ele retorna
            //em outras palavras especifica o que o metodo do repository retorna
            Mockito.when(livroRepository.findByIsbn(livro.getIsbn())).thenReturn(Optional.empty()); //if (livroRepository.findByIsbn(livro.getIsbn()).isPresent()) {
            Mockito.when(livroRepository.save(livro)).thenReturn(livro);//ou .save(Mockito.any(Livro.class))

            //act
            //testa a function que nós queremos
            Livro livroretorno =livroService.criar(livro);

            //assert
            Assertions.assertEquals(1L,livroretorno.getId());
            Assertions.assertEquals("autor",livroretorno.getAutor());
            Assertions.assertEquals("isbn",livroretorno.getIsbn());
            Assertions.assertEquals("nome",livroretorno.getNome());

        }

        @Test
        @DisplayName("deve lançar exceção ao criar livro nulo")
        void excessao() {
            Livro livro=livroconstruct(1L);

            Mockito.when(livroRepository.findByIsbn("isbn"))
                    .thenReturn(Optional.of(new Livro()));

            Assertions.assertThrows(
                    IsbnDuplicadoException.class,
                    () -> livroService.criar(livro)
            );


            Mockito.verify(livroRepository, Mockito.never()).save(Mockito.any());
        }

    }

    @Nested
    @DisplayName("Listar")
    class listar{
            @Test
          void deveListar(){
              Livro livro=livroconstruct(1L);
              List<Livro> lista=List.of(livro);
              Mockito.when(livroRepository.findAll())
                      .thenReturn(lista);

              List<Livro> livrossimulado= livroService.listar();

              Assertions.assertEquals(1,livrossimulado.size());
              Assertions.assertEquals(lista,livrossimulado);
          }
    }

    @Nested
    @DisplayName("Atualizar")
    class atualizar{
        @Test
        @DisplayName("atuaizar")
        void deveatualizar(){
            //AAA
            //arrange
            //act
            //assert

            Livro livro=livroconstruct(1L);
            livro.setNome("nome2");

            Mockito.when(livroRepository.existsById(1L)).thenReturn(true);
            Mockito.when(livroRepository.findByIsbnAndIdNot(livro.getIsbn(),1L)).thenReturn(Optional.empty());

            Mockito.when(livroRepository.save(Mockito.any(Livro.class))).thenReturn(livro);
            Livro livroretorno =livroService.atualizar(1L,livro);

            Assertions.assertEquals(1L,livroretorno.getId());
            Assertions.assertEquals("autor",livroretorno.getAutor());
            Assertions.assertEquals("isbn",livroretorno.getIsbn());
            Assertions.assertEquals("nome2",livroretorno.getNome());

        }

        @Test
        @DisplayName("deve lançar exceção ao informar id não existente")
        void excessao() {
            Livro livro=livroconstruct(1L);

            Mockito.when(livroRepository.existsById(1L)).thenReturn(false);

            Assertions.assertThrows(
                    EntidadeNaoEncontradaException.class,
                    () -> livroService.atualizar(1L,livro)
            );


            Mockito.verify(livroRepository, Mockito.never()).save(Mockito.any());
        }

        @Test
        @DisplayName("deve lançar exceção ao informar isbn duplicado")
        void excessao2() {
            Livro livro=livroconstruct(1L);

            Mockito.when(livroRepository.existsById(1L)).thenReturn(true);
            Mockito.when(livroRepository.findByIsbnAndIdNot(livro.getIsbn(),1L)).thenReturn(Optional.of(livro));

            Assertions.assertThrows(
                    IsbnDuplicadoException.class,
                    () -> livroService.atualizar(1L,livro)
            );


            Mockito.verify(livroRepository, Mockito.never()).save(Mockito.any());
        }

    }

    @Nested
    @DisplayName("Deletar")
    class deletar{
        @Test
        void deveDeletar() {

            Livro livro=livroconstruct(1L);


            Emprestimo emprestimo= new Emprestimo();
            emprestimo.setId(1L);
            emprestimo.setLivro(livro);
            emprestimo.setStatus("DEVOLVIDO");

            Mockito.when(livroRepository.findById(livro.getId())).thenReturn(Optional.of(livro));
            Mockito.when(emprestimoRepository.findByLivroAndStatus(livro,"EMPRESTADO")).thenReturn(Optional.empty());
            Mockito.when(emprestimoRepository.findByLivro(livro)).thenReturn(List.of(emprestimo));


            // Assert
            Assertions.assertDoesNotThrow(()->livroService.deletar(livro.getId()));
            Mockito.verify(livroRepository).delete(livro);
        }

        @Test
        @DisplayName("deve lançar exceção ao informar id não existente")
        void excessao() {
            Livro livro=livroconstruct(1L);

            Mockito.when(livroRepository.findById(livro.getId())).thenReturn(Optional.empty());

            Assertions.assertThrows(
                    EntidadeNaoEncontradaException.class,
                    () -> livroService.buscarPorId(livro.getId())
            );


            Mockito.verify(livroRepository, Mockito.never()).save(Mockito.any());
        }

        @Test
        @DisplayName("Livro possui empréstimo em andamento e não pode ser excluído")
        void excessao2() {
            Livro livro=livroconstruct(1L);
            Emprestimo emprestimo= new Emprestimo();
            emprestimo.setId(1L);

            Mockito.when(livroRepository.findById(livro.getId())).thenReturn(Optional.of(livro));
            Mockito.when(emprestimoRepository
                    .findByLivroAndStatus(livro, "EMPRESTADO")
            ).thenReturn(Optional.of(emprestimo));

            Assertions.assertThrows(
                    ItemIndisponivelException.class,
                    () -> livroService.deletar(livro.getId())
            );


            Mockito.verify(livroRepository, Mockito.never()).save(Mockito.any());
        }
    }

    @Nested
    @DisplayName("Pesquisar")
    class pesquisar{
        @Test
        void devePesquisar(){
            Livro livro=livroconstruct(1L);

            Mockito.when(livroRepository.findById(livro.getId())).thenReturn(Optional.of(livro));

            Livro livrossimulado= livroService.buscarPorId(livro.getId());

            Assertions.assertEquals(livro,livrossimulado);
        }
        @Test
        @DisplayName("deve lançar exceção ao informar id não existente")
        void excessao() {
            Livro livro=livroconstruct(1L);

            Mockito.when(livroRepository.findById(livro.getId())).thenReturn(Optional.empty());

            Assertions.assertThrows(
                    EntidadeNaoEncontradaException.class,
                    () -> livroService.buscarPorId(livro.getId())
            );


            Mockito.verify(livroRepository, Mockito.never()).save(Mockito.any());
        }
    }




}
