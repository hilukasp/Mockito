package school.sptech.exemplomockito.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class PagamentoNaoProcessadoException extends RuntimeException {

    public PagamentoNaoProcessadoException(Long simulacaoId) {
        super("O pagamento da simulação de frete #" + simulacaoId + " não pôde ser processado.");
    }
}
