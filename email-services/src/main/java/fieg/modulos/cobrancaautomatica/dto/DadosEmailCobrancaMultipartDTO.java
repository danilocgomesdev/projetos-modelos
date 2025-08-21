package fieg.modulos.cobrancaautomatica.dto;

import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.core.MediaType;
import lombok.Data;
import org.jboss.resteasy.reactive.PartType;


@Data
public class DadosEmailCobrancaMultipartDTO {

    @FormParam("dadosEmail")
    @PartType(MediaType.APPLICATION_JSON)
    private DadosEmailCobrancaAtumoticaDTO dadosEmail;

    @FormParam("anexo")
    @PartType(MediaType.APPLICATION_OCTET_STREAM)
    private byte[] anexo;
}