package fieg.modulos.cadastro.conveniobancario.enums;

import fieg.core.interfaces.EnumBanco;
import fieg.modulos.cadastro.conveniobancario.dto.MoedaDTO;

public enum Moeda implements EnumBanco {

    REAL("Real", "R$", "9");

    private final String nome;
    private final String representacao;
    private final String codigo;

    Moeda(String nome, String representacao, String codigo) {
        this.nome = nome;
        this.representacao = representacao;
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public String getRepresentacao() {
        return representacao;
    }

    public String getCodigo() {
        return codigo;
    }

    public MoedaDTO paraDTO() {
        return new MoedaDTO(nome, representacao, codigo);
    }

    @Override
    public String getValorBanco() {
        return codigo;
    }
}
