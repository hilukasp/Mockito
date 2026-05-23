package school.sptech.exerciciotestesunitariosspring.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.sptech.exerciciotestesunitariosspring.dto.usuario.UsuarioRequestDto;
import school.sptech.exerciciotestesunitariosspring.dto.usuario.UsuarioResponseDto;
import school.sptech.exerciciotestesunitariosspring.mapper.UsuarioMapper;
import school.sptech.exerciciotestesunitariosspring.service.UsuarioService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/usuarios")
@Tag(name = "Usuários", description = "Gerenciamento de usuários da biblioteca")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    @Operation(summary = "Listar todos os usuários")
    public ResponseEntity<List<UsuarioResponseDto>> listar() {
        var usuarios = usuarioService.listar().stream()
                .map(UsuarioMapper::toResponseDto)
                .toList();
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar usuário por ID")
    public ResponseEntity<UsuarioResponseDto> buscarPorId(@PathVariable Long id) {
        var usuario = usuarioService.buscarPorId(id);
        return ResponseEntity.ok(UsuarioMapper.toResponseDto(usuario));
    }

    @PostMapping
    @Operation(summary = "Criar novo usuário")
    public ResponseEntity<UsuarioResponseDto> criar(@RequestBody @Valid UsuarioRequestDto dto) {
        var entity = UsuarioMapper.toEntity(dto);
        var salvo = usuarioService.criar(entity);
        var response = UsuarioMapper.toResponseDto(salvo);
        return ResponseEntity.created(URI.create("/usuarios/" + salvo.getId())).body(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar usuário")
    public ResponseEntity<UsuarioResponseDto> atualizar(@PathVariable Long id, @RequestBody @Valid UsuarioRequestDto dto) {
        var entity = UsuarioMapper.toEntity(dto);
        var atualizado = usuarioService.atualizar(id, entity);
        return ResponseEntity.ok(UsuarioMapper.toResponseDto(atualizado));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar usuário")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        usuarioService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
