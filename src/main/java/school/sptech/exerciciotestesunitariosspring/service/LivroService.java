package school.sptech.exerciciotestesunitariosspring.service;

import org.springframework.stereotype.Service;
import school.sptech.exerciciotestesunitariosspring.entity.Livro;
import school.sptech.exerciciotestesunitariosspring.exception.*;
import school.sptech.exerciciotestesunitariosspring.repository.EmprestimoRepository;
import school.sptech.exerciciotestesunitariosspring.repository.LivroRepository;

import java.util.List;

@Service
public class LivroService {

    private final LivroRepository livroRepository;
    private final EmprestimoRepository emprestimoRepository;

    public LivroService(LivroRepository livroRepository, EmprestimoRepository emprestimoRepository) {
        this.livroRepository = livroRepository;
        this.emprestimoRepository = emprestimoRepository;
    }

    public List<Livro> listar() {
        return livroRepository.findAll();
    }

    public Livro buscarPorId(Long id) {
        return livroRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Livro com id " + id + " não encontrado"));
    }

    public Livro criar(Livro livro) {
        if (livroRepository.findByIsbn(livro.getIsbn()).isPresent()) {
            throw new IsbnDuplicadoException("ISBN '" + livro.getIsbn() + "' já cadastrado");
        }
        return livroRepository.save(livro);
    }

    public Livro atualizar(Long id, Livro livro) {
        if (!livroRepository.existsById(id)) {
            throw new EntidadeNaoEncontradaException("Livro com id " + id + " não encontrado");
        }
        if (livroRepository.findByIsbnAndIdNot(livro.getIsbn(), id).isPresent()) {
            throw new IsbnDuplicadoException("ISBN '" + livro.getIsbn() + "' já cadastrado");
        }
        livro.setId(id);
        return livroRepository.save(livro);
    }

    public void deletar(Long id) {
        Livro livro = buscarPorId(id);
        boolean temEmprestimoAtivo = emprestimoRepository.findByLivroAndStatus(livro, "EMPRESTADO").isPresent();
        if (temEmprestimoAtivo) {
            throw new ItemIndisponivelException("Livro possui empréstimo em andamento e não pode ser excluído");
        }
        emprestimoRepository.deleteAll(emprestimoRepository.findByLivro(livro));
        livroRepository.delete(livro);
    }
}
