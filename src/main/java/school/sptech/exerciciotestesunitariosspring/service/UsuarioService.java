package school.sptech.exerciciotestesunitariosspring.service;

import org.springframework.stereotype.Service;
import school.sptech.exerciciotestesunitariosspring.entity.Usuario;
import school.sptech.exerciciotestesunitariosspring.exception.*;
import school.sptech.exerciciotestesunitariosspring.repository.EmprestimoRepository;
import school.sptech.exerciciotestesunitariosspring.repository.UsuarioRepository;

import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final EmprestimoRepository emprestimoRepository;

    public UsuarioService(UsuarioRepository usuarioRepository, EmprestimoRepository emprestimoRepository) {
        this.usuarioRepository = usuarioRepository;
        this.emprestimoRepository = emprestimoRepository;
    }

    public List<Usuario> listar() {
         return usuarioRepository.findAll();
    }

    public Usuario buscarPorId(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Usuário com id " + id + " não encontrado"));
    }

    public Usuario criar(Usuario usuario) {
        if (usuarioRepository.findByEmail(usuario.getEmail()).isPresent()) {
            throw new EmailDuplicadoException("Email '" + usuario.getEmail() + "' já cadastrado");
        }
        if (usuarioRepository.findByCpf(usuario.getCpf()).isPresent()) {
            throw new CpfDuplicadoException("CPF '" + usuario.getCpf() + "' já cadastrado");
        }
        return usuarioRepository.save(usuario);
    }

    public Usuario atualizar(Long id, Usuario usuario) {
        if (!usuarioRepository.existsById(id)) {
            throw new EntidadeNaoEncontradaException("Usuário com id " + id + " não encontrado");
        }
        if (usuarioRepository.findByEmailAndIdNot(usuario.getEmail(), id).isPresent()) {
            throw new EmailDuplicadoException("Email '" + usuario.getEmail() + "' já cadastrado");
        }
        if (usuarioRepository.findByCpfAndIdNot(usuario.getCpf(), id).isPresent()) {
            throw new CpfDuplicadoException("CPF '" + usuario.getCpf() + "' já cadastrado");
        }
        usuario.setId(id);
        return usuarioRepository.save(usuario);
    }

    public void deletar(Long id) {
        Usuario usuario = buscarPorId(id);
        boolean temEmprestimoAtivo = emprestimoRepository
                .findByUsuarioAndStatus(usuario, "EMPRESTADO")
                .isPresent();

        if (temEmprestimoAtivo) {
            throw new ItemIndisponivelException("Usuário possui empréstimo em andamento e não pode ser excluído");
        }

        emprestimoRepository.deleteAll(emprestimoRepository.findByUsuario(usuario));
        usuarioRepository.delete(usuario);
    }
}
