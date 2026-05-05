package school.sptech.exemplomockito.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

@Schema(description = "Dados para criação de uma simulação de frete")
public class SimulacaoFreteRequest {

    @NotBlank
    @Email
    @Schema(description = "E-mail do cliente", example = "cliente@email.com")
    private String emailCliente;

    @NotBlank
    @Schema(description = "Telefone do cliente", example = "11999998888")
    private String telefoneCliente;

    @NotBlank
    @Schema(description = "UF de destino", example = "SP")
    private String destinoUf;

    @Positive
    @Schema(description = "Peso da encomenda em kg", example = "3.5")
    private double pesoKg;

    @Positive
    @Schema(description = "Valor declarado da encomenda", example = "250.00")
    private double valorDeclarado;

    public String getEmailCliente() { return emailCliente; }
    public String getTelefoneCliente() { return telefoneCliente; }
    public String getDestinoUf() { return destinoUf; }
    public double getPesoKg() { return pesoKg; }
    public double getValorDeclarado() { return valorDeclarado; }

    public void setEmailCliente(String emailCliente) { this.emailCliente = emailCliente; }
    public void setTelefoneCliente(String telefoneCliente) { this.telefoneCliente = telefoneCliente; }
    public void setDestinoUf(String destinoUf) { this.destinoUf = destinoUf; }
    public void setPesoKg(double pesoKg) { this.pesoKg = pesoKg; }
    public void setValorDeclarado(double valorDeclarado) { this.valorDeclarado = valorDeclarado; }
}
