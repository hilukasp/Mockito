package school.sptech.exemplomockito.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import school.sptech.exemplomockito.dto.SimulacaoFreteRequest;
import school.sptech.exemplomockito.dto.SimulacaoFreteResponse;
import school.sptech.exemplomockito.mapper.SimulacaoFreteMapper;
import school.sptech.exemplomockito.service.SimulacaoFreteService;

@RestController
@RequestMapping("/simulacoes-frete")
public class SimulacaoFreteController {

    private final SimulacaoFreteService service;
    private final SimulacaoFreteMapper mapper;

    public SimulacaoFreteController(SimulacaoFreteService service, SimulacaoFreteMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping("/{id}")
    public ResponseEntity<SimulacaoFreteResponse> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(mapper.toResponse(service.buscar(id)));
    }

    @PostMapping
    public ResponseEntity<SimulacaoFreteResponse> criar(
          @RequestBody @Valid SimulacaoFreteRequest request) {
        return ResponseEntity.status(201).body(
              mapper.toResponse(service.criar(mapper.toEntity(request)))
        );
    }

    @PostMapping("/{id}/calcular")
    public ResponseEntity<SimulacaoFreteResponse> calcular(
          @PathVariable Long id,
          @RequestParam String modalidade) {
        return ResponseEntity.ok(mapper.toResponse(service.calcular(id, modalidade)));
    }

    @PostMapping("/{id}/contratar")
    public ResponseEntity<SimulacaoFreteResponse> contratar(@PathVariable Long id) {
        return ResponseEntity.ok(mapper.toResponse(service.contratar(id)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
