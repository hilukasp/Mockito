package school.sptech.exerciciotestesunitariosspring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import school.sptech.exerciciotestesunitariosspring.entity.Usuario;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByEmail(String email);

    Optional<Usuario> findByCpf(String cpf);

    Optional<Usuario> findByEmailAndIdNot(String email, Long id);

    Optional<Usuario> findByCpfAndIdNot(String cpf, Long id);
}
