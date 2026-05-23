package school.sptech.exerciciotestesunitariosspring.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "CPF já cadastrado")
public class CpfDuplicadoException extends RuntimeException {
    public CpfDuplicadoException(String mensagem) {
        super(mensagem);
    }
}
