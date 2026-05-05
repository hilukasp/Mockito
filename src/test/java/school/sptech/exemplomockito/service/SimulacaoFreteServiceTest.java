package school.sptech.exemplomockito.service;

import jakarta.validation.constraints.Email;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import school.sptech.exemplomockito.entity.SimulacaoFrete;
import school.sptech.exemplomockito.entity.StatusSimulacao;
import school.sptech.exemplomockito.gateway.PagamentoGateway;
import school.sptech.exemplomockito.notificacao.EmailService;
import school.sptech.exemplomockito.repository.SimulacaoFreteRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

//importa o mockito
@ExtendWith(MockitoExtension.class)
class SimulacaoFreteServiceTest {

    //o que estiver comentado em bloco representa o código do service
    /*

    private final SimulacaoFreteRepository repository;
    private final CalculoFreteService calculoFreteService;
    private final EmailService emailService;
     */

    //injeta as dependências da classe de serviço
    @Mock
    private CalculoFreteService calculoFreteService;
    @Mock
    private EmailService emailService;
    @Mock
    private SimulacaoFreteRepository repository;

    //Inject é o metodo real que você quer testar
    @InjectMocks
    private SimulacaoFreteService simulacaoFreteService;


    // Indica que essa classe é um grupo de testes relacionado a um mesmo metodo/comportamento
    @Nested
    @DisplayName("teste do método criar")
    class MetodoCriar{
        @Test
        @DisplayName("deve criar uma simulação de frete corretamente")
        void deveCriarSimularFrete(){
            /*
            public SimulacaoFrete criar(SimulacaoFrete simulacao) {
                simulacao.setStatus(StatusSimulacao.CRIADA);
                repository.save(simulacao);
                return simulacao;
            }
             */
            //AAA

            //Arrange
            /*public SimulacaoFrete criar(SimulacaoFrete simulacao) {*/
            SimulacaoFrete simulacao=new SimulacaoFrete();
            simulacao.setEmailCliente("teste@email.com");
            simulacao.setTelefoneCliente("11999999999");
            simulacao.setDestinoUf("SP");
            simulacao.setPesoKg(10.0);
            simulacao.setValorDeclarado(100.0);

            //Act
            /*
                repository.save(simulacao);
             */
            //esse metodo é chamado dentro da função criar. Como não queremos testar esse metodo do repository, só especificamos o Tipo de objeto que ele passa e o Objeto que ele retorna
            //em outras palavras especifica o que esse o metodo do repository retorna
            Mockito.when(repository.save(simulacao)) //ou .save(Mockito.any(SimulacaoFrete.class))
                    .thenReturn(simulacao);

            //testa a function que nós queremos
            SimulacaoFrete resultado=simulacaoFreteService.criar(simulacao);

            //Assert
            Assertions.assertEquals(StatusSimulacao.CRIADA, resultado.getStatus());
            Assertions.assertEquals("teste@email.com", resultado.getEmailCliente());
            Assertions.assertEquals("11999999999", resultado.getTelefoneCliente());
            Assertions.assertEquals(10.0, resultado.getPesoKg());


        }
    }

    @Nested
    @DisplayName("teste do método pesquisar")
    class MetodoPesquisar{
        @Test
        @DisplayName("deve pesquisar uma simulação de frete corretamente")
        void devePesquisarSimularFrete(){
            //Arrange
            SimulacaoFrete simulacao=new SimulacaoFrete();
            simulacao.setId(1L);
            //Act
            /*
               return repository.findById(id).orElseThrow(() -> new SimulacaoNaoEncontradaException(id));
             */
            Mockito.when(repository.findById(simulacao.getId()))
                    .thenReturn(Optional.of(simulacao));

            //testa a function que nós queremos
            SimulacaoFrete resultado=simulacaoFreteService.buscar(simulacao.getId());

            //Assert
            Assertions.assertEquals(simulacao, resultado);


        }
    }

    @Nested
    @DisplayName("teste do método deletar")
    class MetodoDeletar {

        @Test
        @DisplayName("deve deletar uma simulação de frete corretamente")
        void deveDeletarSimulacaoFrete() {

            // Arrange
            SimulacaoFrete simulacao = new SimulacaoFrete();
            simulacao.setId(1L);

            // Act
            Mockito.when(repository.findById(simulacao.getId()))
                    .thenReturn(Optional.of(simulacao));


            // Assert
            Assertions.assertDoesNotThrow(()->simulacaoFreteService.deletar(simulacao.getId()));
            Mockito.verify(repository).delete(simulacao);
        }
    }


    @Nested
    @DisplayName("teste do método deletar")
    class MetodoContratar {

        @Test
        @DisplayName("deve deletar uma simulação de frete corretamente")
        void deveDeletarSimulacaoFrete() {

            // Arrange
            SimulacaoFrete simulacao = new SimulacaoFrete();
            simulacao.setId(1L);

            // Act
            Mockito.when(repository.findById(simulacao.getId()))
                    .thenReturn(Optional.of(simulacao));

            PagamentoGateway pagamentoGateway = new PagamentoGateway();
            Boolean pagamentoProcessado = pagamentoGateway.processarPagamento(simulacao.getTelefoneCliente(), simulacao.getCustoFinal());
            /*


        if (!pagamentoProcessado) {
            throw new PagamentoNaoProcessadoException(simulacao.getId());
        }

        String mensagem = "Parabéns! Você contratou o serviço de frete realizado na simulação: "
              + simulacao.getId();

             */

            SimulacaoFrete resultado=simulacaoFreteService.contratar(simulacao.getId());

            // Assert
            Assertions.assertEquals(simulacao, resultado );
        }
    }
}