package school.sptech.exerciciotestesunitariosspring.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "ISBN já cadastrado")
public class IsbnDuplicadoException extends RuntimeException {
    public IsbnDuplicadoException(String mensagem) {
        super(mensagem);
    }
}
