package school.sptech.exemplomockito.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import school.sptech.exemplomockito.entity.StatusSimulacao;

@Schema(description = "Dados para atualização de status da simulação")
public class AtualizarStatusSimulacaoRequest {

    @NotNull
    @Schema(
        description = "Novo status da simulação",
        example = "APROVADA",
        allowableValues = {"CRIADA", "CALCULADA", "APROVADA", "CANCELADA"}
    )
    private StatusSimulacao status;

    public StatusSimulacao getStatus() { return status; }
    public void setStatus(StatusSimulacao status) { this.status = status; }
}
