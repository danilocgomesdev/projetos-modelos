package fieg.externos.cadin.parcelacadin.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import fieg.core.util.UtilString;
import fieg.modulos.cobrancacliente.dto.subfiltros.FiltroTipoIntegraProtheus;
import fieg.modulos.formapagamento.enums.FormaPagamentoSimplificado;
import fieg.modulos.interfacecobranca.enums.IntegraProtheus;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Data
public class BuscaParcelaCadinDTO {

    // Datas de pagamento
    LocalDate dataInicial;
    LocalDate dataFinal;

    LocalDate dataVencimentoInicial;
    LocalDate dataVencimentoFinal;

    String sacadoNome;
    String sacadoCpfCnpj;
    Integer numeroParcela;
    Integer contId;
    String nossoNumero;
    Boolean baixaIntegrada;
    Integer idUnidade;
    Integer idEntidade;
    FormaPagamentoSimplificado formaPagamento;
    IntegraProtheus integraProtheus;

    public boolean podePesquisarSemData() {
        return UtilString.isNotBlank(sacadoNome)
                || UtilString.isNotBlank(sacadoCpfCnpj)
                || contId != null
                || UtilString.isNotBlank(nossoNumero)
                || (dataVencimentoInicial != null && dataVencimentoFinal != null);
    }

    @JsonIgnore
    public FiltroTipoIntegraProtheus getFiltroIntegraProtheus() {
        return Optional.ofNullable(integraProtheus)
                .map(List::of)
                .map(FiltroTipoIntegraProtheus::new)
                .orElse(null);
    }
}
