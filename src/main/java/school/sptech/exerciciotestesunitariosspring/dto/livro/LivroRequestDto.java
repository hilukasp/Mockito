package school.sptech.exerciciotestesunitariosspring.dto.livro;

import jakarta.validation.constraints.NotNull;

public class LivroRequestDto {

    @NotNull(message = "Nome é obrigatório")
    private String nome;

    @NotNull(message = "Autor é obrigatório")
    private String autor;

    @NotNull(message = "ISBN é obrigatório")
    private String isbn;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
}
