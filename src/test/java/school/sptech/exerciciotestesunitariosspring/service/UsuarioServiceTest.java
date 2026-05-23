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
import school.sptech.exerciciotestesunitariosspring.entity.Usuario;
import school.sptech.exerciciotestesunitariosspring.entity.Usuario;
import school.sptech.exerciciotestesunitariosspring.exception.CpfDuplicadoException;
import school.sptech.exerciciotestesunitariosspring.exception.EntidadeNaoEncontradaException;
import school.sptech.exerciciotestesunitariosspring.exception.EmailDuplicadoException;
import school.sptech.exerciciotestesunitariosspring.exception.ItemIndisponivelException;
import school.sptech.exerciciotestesunitariosspring.repository.EmprestimoRepository;
import school.sptech.exerciciotestesunitariosspring.repository.UsuarioRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("UsuarioService")
class UsuarioServiceTest {

    @Mock
    UsuarioRepository usuarioRepository;

    @Mock
    EmprestimoRepository emprestimoRepository;

    @InjectMocks
    UsuarioService usuarioService;

    @Nested
    @DisplayName("teste metodo listar")
    class listar{
        @Test
        @DisplayName("listar")
        void deveListar(){
            Usuario usuario = new Usuario();
            usuario.setId(1L);
            usuario.setNome("lucas");
            usuario.setEmail("lucas@gmail");
            usuario.setCpf("222222");
            List<Usuario> listausuario= List.of(usuario);


            Mockito.when(usuarioRepository.findAll()).thenReturn(listausuario);

            List<Usuario> usuariosRetorno = usuarioService.listar();

            Assertions.assertEquals(listausuario,usuariosRetorno);


        }

    }


    Usuario usuarioconstruct(){

        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setCpf("222222");
        usuario.setEmail("lucas@gmail");
        usuario.setNome("lucas");
        return usuario;
    }


    @Nested
    @DisplayName("teste metodo criar")
    class criar{
        @Test
        @DisplayName("criar")
        void deveCriar(){
            //AAA
            //arrange
            //act
            //assert

            Usuario usuario=usuarioconstruct();

            //
            Mockito.when(usuarioRepository.findByEmail(usuario.getEmail())).thenReturn(Optional.empty());
            Mockito.when(usuarioRepository.save(usuario)).thenReturn(usuario);
            Usuario usuarioretorno =usuarioService.criar(usuario);

            Assertions.assertEquals(1L,usuarioretorno.getId());
            Assertions.assertEquals("222222",usuarioretorno.getCpf());
            Assertions.assertEquals("lucas@gmail",usuarioretorno.getEmail());
            Assertions.assertEquals("lucas",usuarioretorno.getNome());

        }

        @Test
        @DisplayName("deve lançar exceção ao criar usuario nulo")
        void excessao() {
            Usuario usuario=usuarioconstruct();

            Mockito.when(usuarioRepository.findByEmail("lucas@gmail"))
                    .thenReturn(Optional.of(new Usuario()));

            Assertions.assertThrows(
                    EmailDuplicadoException.class,
                    () -> usuarioService.criar(usuario)
            );


            Mockito.verify(usuarioRepository, Mockito.never()).save(Mockito.any());
        }

        @Test
        @DisplayName("deve lançar exceção ao criar com cpf existente")
        void excessao2() {
            Usuario usuario=usuarioconstruct();

            Mockito.when(usuarioRepository.findByCpf(usuario.getCpf()))
                    .thenReturn(Optional.of(usuario));

            Assertions.assertThrows(
                    CpfDuplicadoException.class,
                    () -> usuarioService.criar(usuario)
            );


            Mockito.verify(usuarioRepository, Mockito.never()).save(Mockito.any());
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

            Usuario usuario=usuarioconstruct();
            usuario.setNome("lucas2");

            Mockito.when(usuarioRepository.existsById(1L)).thenReturn(true);
            Mockito.when(usuarioRepository.findByEmailAndIdNot(usuario.getEmail(),1L)).thenReturn(Optional.empty());

            Mockito.when(usuarioRepository.save(Mockito.any(Usuario.class))).thenReturn(usuario);
            Usuario usuarioretorno =usuarioService.atualizar(1L,usuario);

            Assertions.assertEquals(1L,usuarioretorno.getId());
            Assertions.assertEquals("222222",usuarioretorno.getCpf());
            Assertions.assertEquals("lucas@gmail",usuarioretorno.getEmail());
            Assertions.assertEquals("lucas2",usuarioretorno.getNome());

        }

        @Test
        @DisplayName("deve lançar exceção ao informar id não existente")
        void excessao() {
            Usuario usuario=usuarioconstruct();

            Mockito.when(usuarioRepository.existsById(1L)).thenReturn(false);

            Assertions.assertThrows(
                    EntidadeNaoEncontradaException.class,
                    () -> usuarioService.atualizar(1L,usuario)
            );


            Mockito.verify(usuarioRepository, Mockito.never()).save(Mockito.any());
        }

        @Test
        @DisplayName("deve lançar exceção ao informar email duplicado")
        void excessao2() {
            Usuario usuario=usuarioconstruct();

            Mockito.when(usuarioRepository.existsById(1L)).thenReturn(true);
            Mockito.when(usuarioRepository.findByEmailAndIdNot(usuario.getEmail(),1L)).thenReturn(Optional.of(usuario));

            Assertions.assertThrows(
                    EmailDuplicadoException.class,
                    () -> usuarioService.atualizar(1L,usuario)
            );


            Mockito.verify(usuarioRepository, Mockito.never()).save(Mockito.any());
        }

        @Test
        @DisplayName("deve lançar exceção ao criar com cpf existente")
        void excessao3() {
            Usuario usuario=usuarioconstruct();

            Mockito.when(usuarioRepository.existsById(1L)).thenReturn(true);
            Mockito.when(usuarioRepository.findByCpfAndIdNot("222222",1L))
                    .thenReturn(Optional.of(usuario));

            Assertions.assertThrows(
                    CpfDuplicadoException.class,
                    () -> usuarioService.atualizar(usuario.getId(),usuario)
            );


            Mockito.verify(usuarioRepository, Mockito.never()).save(Mockito.any());
        }
    }

    @Nested
    @DisplayName("Deletar")
    class deletar{
        @Test
        void deveDeletar() {

            Usuario usuario=usuarioconstruct();
            usuario.setId(1L);


            Mockito.when(usuarioRepository.findById(usuario.getId())).thenReturn(Optional.of(usuario));


            // Assert
            Assertions.assertDoesNotThrow(()->usuarioService.deletar(usuario.getId()));
            Mockito.verify(usuarioRepository).delete(usuario);
        }

        @Test
        @DisplayName("Usuário possui empréstimo em andamento e não pode ser excluído")
        void excessao2() {
            Usuario usuario=usuarioconstruct();
            Emprestimo emprestimo= new Emprestimo();
            emprestimo.setId(1L);

            Mockito.when(usuarioRepository.findById(usuario.getId())).thenReturn(Optional.of(usuario));
            Mockito.when(emprestimoRepository
                    .findByUsuarioAndStatus(usuario, "EMPRESTADO")
            ).thenReturn(Optional.of(emprestimo));

            Assertions.assertThrows(
                    ItemIndisponivelException.class,
                    () -> usuarioService.deletar(usuario.getId())
            );


            Mockito.verify(usuarioRepository, Mockito.never()).save(Mockito.any());
        }
    }

    @Nested
    @DisplayName("Pesquisar")
    class pesquisar{
        @Test
        void devePesquisar(){
            Usuario usuario=usuarioconstruct();

            Mockito.when(usuarioRepository.findById(usuario.getId())).thenReturn(Optional.of(usuario));

            Usuario usuariossimulado= usuarioService.buscarPorId(usuario.getId());

            Assertions.assertEquals(usuario,usuariossimulado);
        }
        @Test
        @DisplayName("deve lançar exceção ao informar id não existente")
        void excessao() {
            Usuario usuario=usuarioconstruct();

            Mockito.when(usuarioRepository.findById(usuario.getId())).thenReturn(Optional.empty());

            Assertions.assertThrows(
                    EntidadeNaoEncontradaException.class,
                    () -> usuarioService.buscarPorId(usuario.getId())
            );


            Mockito.verify(usuarioRepository, Mockito.never()).save(Mockito.any());
        }

    }
}