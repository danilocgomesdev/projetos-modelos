package fieg.modulos.cadastro.viculodependentes.dto;

import fieg.modulos.cadastro.cliente.dto.PessoaCr5DTO;
import lombok.Data;

@Data
public class DependenteResponsavelDTO {

    private Integer idDependenteResponsavel;
    private DependenteDTO dependente;
    private PessoaCr5DTO pessoaResponsavel;
}
