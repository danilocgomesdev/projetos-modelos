package fieg.modulos.cieloecommerce.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ConsultaRecorrenciaDTO {

    private Integer idEntidade;
    private String entidade;
    private String unidade;
    private String responsavelFinanceiro;
    private String cpfCnpj;

    private String idRecorrencia;
    private String statusRecorrencia;
    private String dataInicioRecorrencia;
    private String dataFimRecorrencia;
    private Integer idSistema;

}
