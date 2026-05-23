package school.sptech.exerciciotestesunitariosspring.dto.emprestimo;

import school.sptech.exerciciotestesunitariosspring.dto.livro.LivroResponseDto;
import school.sptech.exerciciotestesunitariosspring.dto.usuario.UsuarioResponseDto;

import java.time.LocalDate;

public class EmprestimoResponseDto {

    private Long id;
    private LivroResponseDto livro;
    private UsuarioResponseDto usuario;
    private String status;
    private LocalDate dataEmprestimo;
    private LocalDate dataDevolucao;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LivroResponseDto getLivro() {
        return livro;
    }

    public void setLivro(LivroResponseDto livro) {
        this.livro = livro;
    }

    public UsuarioResponseDto getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioResponseDto usuario) {
        this.usuario = usuario;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getDataEmprestimo() {
        return dataEmprestimo;
    }

    public void setDataEmprestimo(LocalDate dataEmprestimo) {
        this.dataEmprestimo = dataEmprestimo;
    }

    public LocalDate getDataDevolucao() {
        return dataDevolucao;
    }

    public void setDataDevolucao(LocalDate dataDevolucao) {
        this.dataDevolucao = dataDevolucao;
    }
}
