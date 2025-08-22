package fieg.modulo.dto;

import fieg.core.util.UtilString;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ReciboPagadorDTO {

    private List<String> mensagens;

    public void setMensagens(List<String> mensagens) {
        if (mensagens != null) {
            List<String> mensagensTruncadas = new ArrayList<>();
            for (String mensagem : mensagens) {
                if (mensagem != null && mensagem.length() > 40) {
                    mensagem = mensagem.substring(0, 40);
                }
                mensagensTruncadas.add(mensagem);
            }
            this.mensagens = mensagensTruncadas;
        } else {
            this.mensagens = new ArrayList<>();
        }
    }

}
