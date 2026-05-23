package school.sptech.exerciciotestesunitariosspring.mapper;

import school.sptech.exerciciotestesunitariosspring.dto.livro.LivroRequestDto;
import school.sptech.exerciciotestesunitariosspring.dto.livro.LivroResponseDto;
import school.sptech.exerciciotestesunitariosspring.entity.Livro;

public class LivroMapper {

    private LivroMapper() {}

    public static Livro toEntity(LivroRequestDto dto) {
        Livro livro = new Livro();
        livro.setNome(dto.getNome());
        livro.setAutor(dto.getAutor());
        livro.setIsbn(dto.getIsbn());
        return livro;
    }

    public static LivroResponseDto toResponseDto(Livro livro) {
        LivroResponseDto dto = new LivroResponseDto();
        dto.setId(livro.getId());
        dto.setNome(livro.getNome());
        dto.setAutor(livro.getAutor());
        dto.setIsbn(livro.getIsbn());
        return dto;
    }
}
