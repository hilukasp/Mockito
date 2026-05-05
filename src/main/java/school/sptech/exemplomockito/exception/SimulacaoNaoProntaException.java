package school.sptech.exemplomockito.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.PRECONDITION_FAILED)
public class SimulacaoNaoProntaException extends RuntimeException {

    public SimulacaoNaoProntaException(String message) {
        super(message);
    }

}
