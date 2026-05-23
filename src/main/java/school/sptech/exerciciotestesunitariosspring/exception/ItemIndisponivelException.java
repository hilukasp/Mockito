package school.sptech.exerciciotestesunitariosspring.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "Item indisponível")
public class ItemIndisponivelException extends RuntimeException {
    public ItemIndisponivelException(String mensagem) {
        super(mensagem);
    }
}
