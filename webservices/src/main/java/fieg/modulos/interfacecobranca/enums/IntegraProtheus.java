package fieg.modulos.interfacecobranca.enums;

import com.fasterxml.jackson.databind.JsonNode;
import fieg.core.exceptions.ValorInvalidoException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public enum IntegraProtheus {

    CONTRATO_PROTHEUS('C', "Contrato Protheus"),
    INTEGRACAO_FINANCEIRA('F', "Integração Financeira"),
    ISENTO('I', "Título isento"),
    VENDA_PARCELADA('P', "Venda Parcelada"),
    VENDA_DIRETA('V', "Venda Direta"),
    VENDA_AVULSA_PARCELADA('A', "Venda Avulsa Parcelada"),
    CONTRATO_PROTHEUS_25('T', "Contrato Protheus 25");

    private final Character letra;
    private final String descricao;

    IntegraProtheus(Character letra, String descricao) {
        this.letra = letra;
        this.descricao = descricao;
    }

    public Character getLetra() {
        return letra;
    }

    public String getDescricao() {
        return descricao;
    }

    public static Optional<IntegraProtheus> getByLetra(Character letra) {
        return Arrays.stream(values()).filter(it -> it.getLetra().equals(letra)).findFirst();
    }

    public static List<IntegraProtheus> integracoesFinanceira() {
        return List.of(IntegraProtheus.INTEGRACAO_FINANCEIRA, IntegraProtheus.CONTRATO_PROTHEUS_25);
    }

    public static List<IntegraProtheus> convertJsonNodeToList(JsonNode node) throws ValorInvalidoException {
        List<IntegraProtheus> lista = new ArrayList<>();

        if (!node.isArray()) {
            throw new ValorInvalidoException("Valores Integra Protheus deve ser um array");
        }

        for (JsonNode element : node) {
            String nome = element.asText();
            IntegraProtheus integraProtheus = IntegraProtheus.valueOf(nome);
            lista.add(integraProtheus);
        }

        return lista;
    }

}
