package school.sptech.exerciciotestesunitariosspring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import school.sptech.exerciciotestesunitariosspring.entity.Emprestimo;
import school.sptech.exerciciotestesunitariosspring.entity.Livro;
import school.sptech.exerciciotestesunitariosspring.entity.Usuario;

import java.util.List;
import java.util.Optional;

public interface EmprestimoRepository extends JpaRepository<Emprestimo, Long> {

    List<Emprestimo> findByStatus(String status);

    Optional<Emprestimo> findByUsuarioAndStatus(Usuario usuario, String status);

    Optional<Emprestimo> findByLivroAndStatus(Livro livro, String status);

    List<Emprestimo> findByUsuario(Usuario usuario);

    List<Emprestimo> findByLivro(Livro livro);
}
