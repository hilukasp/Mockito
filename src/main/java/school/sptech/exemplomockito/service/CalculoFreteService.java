package school.sptech.exemplomockito.service;

import org.springframework.stereotype.Service;
import school.sptech.exemplomockito.entity.SimulacaoFrete;
import school.sptech.exemplomockito.exception.ModalidadeFreteInvalidaException;

@Service
public class CalculoFreteService {

    public Double calcularValorFrete(SimulacaoFrete s, String modalidade) {
        modalidade = modalidade.trim().toUpperCase();

        Double taxaBase = 10.0;

        // 📦 Faixa de peso
        Double custoPeso;
        if (s.getPesoKg() <= 1) {
            custoPeso = 5.0;
        } else if (s.getPesoKg() <= 5) {
            custoPeso = 10.0;
        } else {
            custoPeso = 18.0;
        }

        // 🌎 Região (simples: SP/RJ = perto)
        Double custoRegiao =
              ("SP".equalsIgnoreCase(s.getDestinoUf()) || "RJ".equalsIgnoreCase(s.getDestinoUf()))
                    ? 5.0
                    : 15.0;

        // 💰 Seguro (ó cobra se passar de um valor)
        Double seguro = 0.0;
        if (s.getValorDeclarado() > 100) {
            seguro = s.getValorDeclarado() * 0.01;
        }

        Double valorBase = taxaBase + custoPeso + custoRegiao + seguro;

        if ("ECONOMICO".equals(modalidade)) {
            return valorBase;
        }

        if ("PADRAO".equals(modalidade)) {
            return valorBase + 8.0;
        }

        if ("EXPRESSO".equals(modalidade)) {
            return valorBase + 20.0;
        }

        throw new ModalidadeFreteInvalidaException(modalidade);
    }
}
