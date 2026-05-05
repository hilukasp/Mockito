package school.sptech.exemplomockito.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ModalidadeFreteInvalidaException extends RuntimeException {

    public ModalidadeFreteInvalidaException(String modalidade) {
        super("Modalidade de frete desconhecida: " + modalidade);
    }
}
