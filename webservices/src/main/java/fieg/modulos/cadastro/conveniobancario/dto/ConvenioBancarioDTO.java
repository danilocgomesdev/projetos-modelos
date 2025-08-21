package fieg.modulos.cadastro.conveniobancario.dto;

import fieg.modulos.cadastro.contacorrente.dto.ContaCorrenteDTO;
import fieg.modulos.cadastro.conveniobancario.enums.SistemaBancario;
import fieg.modulos.cadastro.conveniobancario.enums.CarteiraConvenioBancario;
import fieg.modulos.unidade.dto.UnidadeDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString
public class ConvenioBancarioDTO {

    private Integer id;
    private ContaCorrenteDTO contaCorrente;
    private String nomeCedente;
    private String numero;
    private CarteiraConvenioBancario carteira;
    private MoedaDTO moeda;
    private String tituloEspecie;
    private String tipoEmissao;
    private Boolean aceite;
    private Float indiceMulta;
    private Float indiceJuros;
    private SistemaBancario sistemaBancario;
    private String observacao1;
    private String observacao2;
    private String observacao3;
    private String observacao4;
    private String observacao5;
    private String localPagamento;
    private Integer idUnidade;
    private LocalDateTime dataInativo;
    private LocalDateTime dataInclusao;
    private Integer idOperadorInclusao;
    private LocalDateTime dataAlteracao;
    private Integer idOperadorAlteracao;
    private Boolean utilizaUnCentralizadora;
    private List<FaixaNossoNumeroDTO> faixasNossoNumero;

    private UnidadeDTO unidade;
}
