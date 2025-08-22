package fieg.modulos.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmailDTO {
    
    @NotBlank(message = "O campo {body} é obrigatório.")
    String body;
  
    List<String> emailsCopias = new ArrayList<>();

    @NotBlank(message = "O campo {emailDestinatario} é obrigatório.")
    @Email(message = "Precisa ser um email valido")
    String emailDestinatario;

    @NotBlank(message = "O campo {assunto} é obrigatório.")
    String assunto;


}
