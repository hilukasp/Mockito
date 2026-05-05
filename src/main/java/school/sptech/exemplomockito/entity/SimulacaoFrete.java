package school.sptech.exemplomockito.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "simulacao_frete")
public class SimulacaoFrete {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String emailCliente;
    private String telefoneCliente;
    private String destinoUf;
    private double pesoKg;
    private double valorDeclarado;
    private String modalidade;
    private double custoFinal;

    @Enumerated(EnumType.STRING)
    private StatusSimulacao status;

    public SimulacaoFrete() {
    }


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
