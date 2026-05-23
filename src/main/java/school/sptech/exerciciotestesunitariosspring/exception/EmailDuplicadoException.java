package school.sptech.exerciciotestesunitariosspring.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "Email já cadastrado")
public class EmailDuplicadoException extends RuntimeException {
    public EmailDuplicadoException(String mensagem) {
        super(mensagem);
    }
}
