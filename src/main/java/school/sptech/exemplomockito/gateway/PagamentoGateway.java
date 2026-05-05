package school.sptech.exemplomockito.gateway;

public class PagamentoGateway {

    public boolean processarPagamento(String telefone, Double valor) {
        // Simulação de processamento de pagamento
        System.out.println(
              "Processando pagamento de R$ " + valor + " para o telefone: " + telefone);
        return true;
    }
}
