package fieg.modulos.cadastro.conveniobancario.dto;

import fieg.modulos.cadastro.conveniobancario.enums.Moeda;
import fieg.modulos.cadastro.conveniobancario.enums.SistemaBancario;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ConvenioBancarioCRUDDTO {

    @NotNull
    private Integer idContaCorrente;

    @NotBlank
    private String nomeCedente;

    @NotBlank
    private String numero;

    @NotBlank
    private String carteira;

    @NotNull
    private String moeda = Moeda.REAL.getCodigo(); // Fixo, pois até hoje sempre foi real.

    @NotBlank
    private String tituloEspecie = "DM"; // Fixo, sempre é DM = 2 - Cliente Emite

    @NotBlank
    private String tipoEmissao = "2"; // Fixo, sempre é 2 = DM Duplicata mercantil

    @NotNull
    private Boolean aceite;

    @NotNull
    private Float indiceMulta;

    @NotNull
    private Float indiceJuros;

    @NotNull
    private SistemaBancario sistemaBancario;

    private String observacao1;

    private String observacao2;

    private String observacao3;

    private String observacao4;

    private String observacao5;

    @NotBlank
    private String localPagamento;

    @NotNull
    private Integer idUnidade;

    private Boolean utilizaUnCentralizadora;
}
