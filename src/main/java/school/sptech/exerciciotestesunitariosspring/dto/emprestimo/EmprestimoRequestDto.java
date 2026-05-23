package school.sptech.exerciciotestesunitariosspring.dto.emprestimo;

import jakarta.validation.constraints.NotNull;

public class EmprestimoRequestDto {

    @NotNull(message = "ID do livro é obrigatório")
    private Long livroId;

    @NotNull(message = "ID do usuário é obrigatório")
    private Long usuarioId;

    public Long getLivroId() {
        return livroId;
    }

    public void setLivroId(Long livroId) {
        this.livroId = livroId;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }
}
