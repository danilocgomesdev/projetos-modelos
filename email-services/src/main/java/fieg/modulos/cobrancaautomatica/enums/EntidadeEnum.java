package fieg.modulos.cobrancaautomatica.enums;

public enum EntidadeEnum {

    FIEG(1, "fieg"),
    SESI(2, "sesi"),
    SENAI(3, "senai"),
    IEL(4, "iel");

    private final int codigo;
    private final String nome;

    EntidadeEnum(int codigo, String nome) {
        this.codigo = codigo;
        this.nome = nome;
    }

    public int getCodigo() {
        return codigo;
    }

    public String getNome() {
        return nome;
    }

    public static String getNomePorCodigo(int codigo) {
        for (EntidadeEnum instituicao : EntidadeEnum.values()) {
            if (instituicao.getCodigo() == codigo) {
                return instituicao.getNome();
            }
        }
        throw new IllegalArgumentException("Código inválido: " + codigo);
    }
}
