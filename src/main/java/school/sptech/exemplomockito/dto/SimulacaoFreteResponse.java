package school.sptech.exemplomockito.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import school.sptech.exemplomockito.entity.StatusSimulacao;

@Schema(description = "Dados da simulação de frete retornados pela API")
public class SimulacaoFreteResponse {

    @Schema(description = "Identificador único da simulação", example = "1")
    private Long id;

    @Schema(description = "E-mail do cliente", example = "cliente@email.com")
    private String emailCliente;

    @Schema(description = "Telefone do cliente", example = "11999998888")
    private String telefoneCliente;

    @Schema(description = "UF de destino", example = "SP")
    private String destinoUf;

    @Schema(description = "Peso em kg", example = "3.5")
    private double pesoKg;

    @Schema(description = "Valor declarado", example = "250.00")
    private double valorDeclarado;

    @Schema(description = "Modalidade usada no cálculo", example = "EXPRESSO")
    private String modalidade;

    @Schema(description = "Resultado final da simulação", example = "78.50")
    private double custoFinal;

    @Schema(description = "Status da simulação", example = "CALCULADA")
    private StatusSimulacao status;

    public Long getId() {
        return id;
    }

    public String getEmailCliente() {
        return emailCliente;
    }

    public String getTelefoneCliente() {
        return telefoneCliente;
    }

    public String getDestinoUf() {
        return destinoUf;
    }

    public double getPesoKg() {
        return pesoKg;
    }

    public double getValorDeclarado() {
        return valorDeclarado;
    }

    public String getModalidade() {
        return modalidade;
    }

    public double getCustoFinal() {
        return custoFinal;
    }

    public StatusSimulacao getStatus() {
        return status;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setEmailCliente(String emailCliente) {
        this.emailCliente = emailCliente;
    }

    public void setTelefoneCliente(String telefoneCliente) {
        this.telefoneCliente = telefoneCliente;
    }

    public void setDestinoUf(String destinoUf) {
        this.destinoUf = destinoUf;
    }

    public void setPesoKg(double pesoKg) {
        this.pesoKg = pesoKg;
    }

    public void setValorDeclarado(double valorDeclarado) {
        this.valorDeclarado = valorDeclarado;
    }

    public void setModalidade(String modalidade) {
        this.modalidade = modalidade;
    }

    public void setCustoFinal(double custoFinal) {
        this.custoFinal = custoFinal;
    }

    public void setStatus(StatusSimulacao status) {
        this.status = status;
    }
}
