package school.sptech.exemplomockito.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class SimulacaoNaoEncontradaException extends RuntimeException {

    public SimulacaoNaoEncontradaException(Long id) {
        super("Simulação não encontrada: " + id);
    }
}
