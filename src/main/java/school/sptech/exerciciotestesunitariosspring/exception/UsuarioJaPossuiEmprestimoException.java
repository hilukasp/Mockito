package school.sptech.exerciciotestesunitariosspring.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "Usuário já possui um empréstimo ativo")
public class UsuarioJaPossuiEmprestimoException extends RuntimeException {
    public UsuarioJaPossuiEmprestimoException(String mensagem) {
        super(mensagem);
    }
}
