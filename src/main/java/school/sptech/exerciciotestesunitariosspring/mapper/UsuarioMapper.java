package school.sptech.exerciciotestesunitariosspring.mapper;

import school.sptech.exerciciotestesunitariosspring.dto.usuario.UsuarioRequestDto;
import school.sptech.exerciciotestesunitariosspring.dto.usuario.UsuarioResponseDto;
import school.sptech.exerciciotestesunitariosspring.entity.Usuario;

public class UsuarioMapper {

    private UsuarioMapper() {}

    public static Usuario toEntity(UsuarioRequestDto dto) {
        Usuario usuario = new Usuario();
        usuario.setNome(dto.getNome());
        usuario.setEmail(dto.getEmail());
        usuario.setCpf(dto.getCpf());
        return usuario;
    }

    public static UsuarioResponseDto toResponseDto(Usuario usuario) {
        UsuarioResponseDto dto = new UsuarioResponseDto();
        dto.setId(usuario.getId());
        dto.setNome(usuario.getNome());
        dto.setEmail(usuario.getEmail());
        dto.setCpf(usuario.getCpf());
        return dto;
    }
}
