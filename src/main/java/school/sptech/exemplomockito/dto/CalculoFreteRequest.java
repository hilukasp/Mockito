package school.sptech.exemplomockito.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Dados para cálculo da simulação")
public class CalculoFreteRequest {

    @NotBlank
    @Schema(
        description = "Modalidade de frete",
        example = "EXPRESSO",
        allowableValues = {"ECONOMICO", "PADRAO", "EXPRESSO", "XPTO"}
    )
    private String modalidade;

    public String getModalidade() { return modalidade; }
    public void setModalidade(String modalidade) { this.modalidade = modalidade; }
}
