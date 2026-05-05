package school.sptech.exemplomockito.notificacao;

import org.springframework.stereotype.Service;

@Service
public class EmailService {

    public void enviar(String destinatario, String mensagem) {
        System.out.println("Enviando email para: " + destinatario);
        System.out.println("Mensagem: " + mensagem);
    }
}
