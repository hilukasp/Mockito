package school.sptech.exemplomockito.service;

import org.springframework.stereotype.Service;
import school.sptech.exemplomockito.entity.SimulacaoFrete;
import school.sptech.exemplomockito.entity.StatusSimulacao;
import school.sptech.exemplomockito.exception.PagamentoNaoProcessadoException;
import school.sptech.exemplomockito.exception.SimulacaoNaoEncontradaException;
import school.sptech.exemplomockito.exception.SimulacaoNaoProntaException;
import school.sptech.exemplomockito.gateway.PagamentoGateway;
import school.sptech.exemplomockito.notificacao.EmailService;
import school.sptech.exemplomockito.repository.SimulacaoFreteRepository;

@Service
public class SimulacaoFreteService {

    private final SimulacaoFreteRepository repository;

    private final CalculoFreteService calculoFreteService;
    private final EmailService emailService;

    public SimulacaoFreteService(SimulacaoFreteRepository repository,
          CalculoFreteService calculoFreteService, EmailService emailService) {
        this.repository = repository;
        this.calculoFreteService = calculoFreteService;
        this.emailService = emailService;
    }

    public SimulacaoFrete criar(SimulacaoFrete simulacao) {
        simulacao.setStatus(StatusSimulacao.CRIADA);
        return  repository.save(simulacao);

    }

    public SimulacaoFrete buscar(Long id) {
        return repository.findById(id)
              .orElseThrow(() -> new SimulacaoNaoEncontradaException(id));
    }

    public void deletar(Long id) {
        SimulacaoFrete simulacao = buscar(id);
        repository.delete(simulacao);
    }

    public SimulacaoFrete calcular(Long id, String modalidade) {
        SimulacaoFrete simulacaoFrete = buscar(id);
        Double valor = calculoFreteService.calcularValorFrete(simulacaoFrete, modalidade);
        simulacaoFrete.setModalidade(modalidade);
        simulacaoFrete.setCustoFinal(valor);
        simulacaoFrete.setStatus(StatusSimulacao.CALCULADA);
        return repository.save(simulacaoFrete);
    }

    public SimulacaoFrete contratar(Long id) {
        SimulacaoFrete simulacao = buscar(id);

        if (!simulacao.getStatus().equals(StatusSimulacao.CALCULADA)) {
            throw new SimulacaoNaoProntaException(
                  "Simulação precisa ser calculada antes de contratar");
        }

        PagamentoGateway pagamentoGateway = new PagamentoGateway();
        Boolean pagamentoProcessado = pagamentoGateway
              .processarPagamento(simulacao.getTelefoneCliente(), simulacao.getCustoFinal());

        if (!pagamentoProcessado) {
            throw new PagamentoNaoProcessadoException(simulacao.getId());
        }

        String mensagem = "Parabéns! Você contratou o serviço de frete realizado na simulação: "
              + simulacao.getId();

        emailService.enviar(
              simulacao.getEmailCliente(),
              mensagem);

        simulacao.setStatus(StatusSimulacao.APROVADA);
        return repository.save(simulacao);
    }
}
