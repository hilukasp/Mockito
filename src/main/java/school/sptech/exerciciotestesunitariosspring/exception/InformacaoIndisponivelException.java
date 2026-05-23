package school.sptech.exerciciotestesunitariosspring.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.UNPROCESSABLE_CONTENT, reason = "Informação indisponível")
public class InformacaoIndisponivelException extends RuntimeException {
    public InformacaoIndisponivelException(String mensagem) {
        super(mensagem);
    }
}
