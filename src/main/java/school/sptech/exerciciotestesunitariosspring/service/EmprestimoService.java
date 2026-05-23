package school.sptech.exerciciotestesunitariosspring.service;

import org.springframework.stereotype.Service;
import school.sptech.exerciciotestesunitariosspring.dto.emprestimo.PrazoMedioResponseDto;
import school.sptech.exerciciotestesunitariosspring.entity.Emprestimo;
import school.sptech.exerciciotestesunitariosspring.entity.Livro;
import school.sptech.exerciciotestesunitariosspring.entity.Usuario;
import school.sptech.exerciciotestesunitariosspring.exception.*;
import school.sptech.exerciciotestesunitariosspring.repository.EmprestimoRepository;
import school.sptech.exerciciotestesunitariosspring.repository.LivroRepository;
import school.sptech.exerciciotestesunitariosspring.repository.UsuarioRepository;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class EmprestimoService {

    private final EmprestimoRepository emprestimoRepository;
    private final UsuarioRepository usuarioRepository;
    private final LivroRepository livroRepository;

    public EmprestimoService(EmprestimoRepository emprestimoRepository,
                             UsuarioRepository usuarioRepository,
                             LivroRepository livroRepository) {
        this.emprestimoRepository = emprestimoRepository;
        this.usuarioRepository = usuarioRepository;
        this.livroRepository = livroRepository;
    }

    public List<Emprestimo> listar(String status) {
        if (status == null) {
            return emprestimoRepository.findAll();
        }
        if (!status.equals("EMPRESTADO") && !status.equals("DEVOLVIDO")) {
            throw new RequisicaoInvalidaException("Status inválido. Use 'EMPRESTADO' ou 'DEVOLVIDO'");
        }
        return emprestimoRepository.findByStatus(status);
    }

    public Emprestimo criar(Long usuarioId, Long livroId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Usuário com id " + usuarioId + " não encontrado"));
        Livro livro = livroRepository.findById(livroId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Livro com id " + livroId + " não encontrado"));

        emprestimoRepository.findByUsuarioAndStatus(usuario, "EMPRESTADO")
                .ifPresent(e -> {
                    throw new UsuarioJaPossuiEmprestimoException("Usuário já possui um empréstimo em andamento");
                });
        emprestimoRepository.findByLivroAndStatus(livro, "EMPRESTADO")
                .ifPresent(e -> {
                    throw new ItemIndisponivelException("Livro já está emprestado");
                });

        Emprestimo emprestimo = new Emprestimo();
        emprestimo.setUsuario(usuario);
        emprestimo.setLivro(livro);
        emprestimo.setStatus("EMPRESTADO");
        emprestimo.setDataEmprestimo(LocalDate.now());
        return emprestimoRepository.save(emprestimo);
    }

    public Emprestimo devolver(Long id) {
        Emprestimo emprestimo = emprestimoRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Empréstimo com id " + id + " não encontrado"));
        if (emprestimo.getStatus().equals("DEVOLVIDO")) {
            throw new RequisicaoInvalidaException("Empréstimo já foi devolvido");
        }
        emprestimo.setStatus("DEVOLVIDO");
        emprestimo.setDataDevolucao(LocalDate.now());
        return emprestimoRepository.save(emprestimo);
    }

    public PrazoMedioResponseDto prazoMedio() {
        List<Emprestimo> devolvidos = emprestimoRepository.findByStatus("DEVOLVIDO");
        if (devolvidos.isEmpty()) {
            throw new InformacaoIndisponivelException("Nenhum empréstimo foi devolvido ainda. Informação indisponível");
        }
        double media = devolvidos.stream()
                .mapToLong(e -> ChronoUnit.DAYS.between(e.getDataEmprestimo(), e.getDataDevolucao()))
                .average()
                .orElse(0.0);
        return new PrazoMedioResponseDto(media, (long) devolvidos.size());
    }
}
