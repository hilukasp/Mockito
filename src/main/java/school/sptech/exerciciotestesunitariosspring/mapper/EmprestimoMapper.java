package school.sptech.exerciciotestesunitariosspring.mapper;

import school.sptech.exerciciotestesunitariosspring.dto.emprestimo.EmprestimoResponseDto;
import school.sptech.exerciciotestesunitariosspring.entity.Emprestimo;

public class EmprestimoMapper {

    private EmprestimoMapper() {}

    public static EmprestimoResponseDto toResponseDto(Emprestimo emprestimo) {
        EmprestimoResponseDto dto = new EmprestimoResponseDto();
        dto.setId(emprestimo.getId());
        dto.setLivro(LivroMapper.toResponseDto(emprestimo.getLivro()));
        dto.setUsuario(UsuarioMapper.toResponseDto(emprestimo.getUsuario()));
        dto.setStatus(emprestimo.getStatus());
        dto.setDataEmprestimo(emprestimo.getDataEmprestimo());
        dto.setDataDevolucao(emprestimo.getDataDevolucao());
        return dto;
    }
}
