package school.sptech.exerciciotestesunitariosspring.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.sptech.exerciciotestesunitariosspring.dto.emprestimo.EmprestimoRequestDto;
import school.sptech.exerciciotestesunitariosspring.dto.emprestimo.EmprestimoResponseDto;
import school.sptech.exerciciotestesunitariosspring.dto.emprestimo.PrazoMedioResponseDto;
import school.sptech.exerciciotestesunitariosspring.mapper.EmprestimoMapper;
import school.sptech.exerciciotestesunitariosspring.service.EmprestimoService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/emprestimos")
@Tag(name = "Empréstimos", description = "Gerenciamento de empréstimos da biblioteca")
public class EmprestimoController {

    private final EmprestimoService emprestimoService;

    public EmprestimoController(EmprestimoService emprestimoService) {
        this.emprestimoService = emprestimoService;
    }

    @GetMapping
    @Operation(summary = "Listar empréstimos, opcionalmente filtrados por status (EMPRESTADO ou DEVOLVIDO)")
    public ResponseEntity<List<EmprestimoResponseDto>> listar(
            @RequestParam(required = false) String status) {
        var lista = emprestimoService.listar(status).stream()
                .map(EmprestimoMapper::toResponseDto)
                .toList();
        return ResponseEntity.ok(lista);
    }

    @PostMapping
    @Operation(summary = "Criar novo empréstimo")
    public ResponseEntity<EmprestimoResponseDto> criar(@RequestBody @Valid EmprestimoRequestDto dto) {
        var salvo = emprestimoService.criar(dto.getUsuarioId(), dto.getLivroId());
        var response = EmprestimoMapper.toResponseDto(salvo);
        return ResponseEntity.created(URI.create("/emprestimos/" + salvo.getId())).body(response);
    }

    @PatchMapping("/{id}/devolver")
    @Operation(summary = "Registrar devolução de um empréstimo")
    public ResponseEntity<EmprestimoResponseDto> devolver(@PathVariable Long id) {
        var emprestimo = emprestimoService.devolver(id);
        return ResponseEntity.ok(EmprestimoMapper.toResponseDto(emprestimo));
    }

    @GetMapping("/prazo-medio")
    @Operation(summary = "Calcular o prazo médio de devolução dos empréstimos já devolvidos")
    public ResponseEntity<PrazoMedioResponseDto> prazoMedio() {
        return ResponseEntity.ok(emprestimoService.prazoMedio());
    }
}
