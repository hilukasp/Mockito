package school.sptech.exerciciotestesunitariosspring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import school.sptech.exerciciotestesunitariosspring.entity.Livro;

import java.util.Optional;

public interface LivroRepository extends JpaRepository<Livro, Long> {

    Optional<Livro> findByIsbn(String isbn);

    Optional<Livro> findByIsbnAndIdNot(String isbn, Long id);
}
