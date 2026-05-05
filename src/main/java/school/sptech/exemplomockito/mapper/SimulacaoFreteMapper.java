package school.sptech.exemplomockito.mapper;

import org.springframework.stereotype.Component;
import school.sptech.exemplomockito.dto.SimulacaoFreteRequest;
import school.sptech.exemplomockito.dto.SimulacaoFreteResponse;
import school.sptech.exemplomockito.entity.SimulacaoFrete;

@Component
public class SimulacaoFreteMapper {

    public SimulacaoFrete toEntity(SimulacaoFreteRequest request) {
        SimulacaoFrete simulacaoFrete = new SimulacaoFrete();
        simulacaoFrete.setEmailCliente(request.getEmailCliente());
        simulacaoFrete.setTelefoneCliente(request.getTelefoneCliente());
        simulacaoFrete.setDestinoUf(request.getDestinoUf());
        simulacaoFrete.setPesoKg(request.getPesoKg());
        simulacaoFrete.setValorDeclarado(request.getValorDeclarado());
        return simulacaoFrete;
    }

    public SimulacaoFreteResponse toResponse(SimulacaoFrete simulacao) {
        SimulacaoFreteResponse response = new SimulacaoFreteResponse();
        response.setId(simulacao.getId());
        response.setEmailCliente(simulacao.getEmailCliente());
        response.setTelefoneCliente(simulacao.getTelefoneCliente());
        response.setDestinoUf(simulacao.getDestinoUf());
        response.setPesoKg(simulacao.getPesoKg());
        response.setValorDeclarado(simulacao.getValorDeclarado());
        response.setModalidade(simulacao.getModalidade());
        response.setCustoFinal(simulacao.getCustoFinal());
        response.setStatus(simulacao.getStatus());
        return response;
    }
}
