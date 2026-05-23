package school.sptech.exerciciotestesunitariosspring.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.sptech.exerciciotestesunitariosspring.dto.livro.LivroRequestDto;
import school.sptech.exerciciotestesunitariosspring.dto.livro.LivroResponseDto;
import school.sptech.exerciciotestesunitariosspring.mapper.LivroMapper;
import school.sptech.exerciciotestesunitariosspring.service.LivroService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/livros")
@Tag(name = "Livros", description = "Gerenciamento de livros da biblioteca")
public class LivroController {

    private final LivroService livroService;

    public LivroController(LivroService livroService) {
        this.livroService = livroService;
    }

    @GetMapping
    @Operation(summary = "Listar todos os livros")
    public ResponseEntity<List<LivroResponseDto>> listar() {
        var livros = livroService.listar().stream()
                .map(LivroMapper::toResponseDto)
                .toList();
        return ResponseEntity.ok(livros);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar livro por ID")
    public ResponseEntity<LivroResponseDto> buscarPorId(@PathVariable Long id) {
        var livro = livroService.buscarPorId(id);
        return ResponseEntity.ok(LivroMapper.toResponseDto(livro));
    }

    @PostMapping
    @Operation(summary = "Criar novo livro")
    public ResponseEntity<LivroResponseDto> criar(@RequestBody @Valid LivroRequestDto dto) {
        var entity = LivroMapper.toEntity(dto);
        var salvo = livroService.criar(entity);
        var response = LivroMapper.toResponseDto(salvo);
        return ResponseEntity.created(URI.create("/livros/" + salvo.getId())).body(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar livro")
    public ResponseEntity<LivroResponseDto> atualizar(@PathVariable Long id, @RequestBody @Valid LivroRequestDto dto) {
        var entity = LivroMapper.toEntity(dto);
        var atualizado = livroService.atualizar(id, entity);
        return ResponseEntity.ok(LivroMapper.toResponseDto(atualizado));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar livro")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        livroService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
