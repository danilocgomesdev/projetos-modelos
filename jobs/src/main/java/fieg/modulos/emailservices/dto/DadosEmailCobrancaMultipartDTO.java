package fieg.modulos.emailservices.dto;


import fieg.modulos.cr5.cobrancaautomatica.dto.DadosEmailCobrancaAtumoticaDTO;
import lombok.Data;
import org.jboss.resteasy.reactive.PartType;

import javax.ws.rs.FormParam;
import javax.ws.rs.core.MediaType;


@Data
public class DadosEmailCobrancaMultipartDTO {


    @FormParam("dadosEmail")
    @PartType(MediaType.APPLICATION_JSON)
    private DadosEmailCobrancaAtumoticaDTO dadosEmail;

    @FormParam("anexo")
    @PartType(MediaType.APPLICATION_OCTET_STREAM)
    private byte[] anexo;
}