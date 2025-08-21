package fieg.core.exceptions;

// Não mudar os códigos!!! Manter em sincronia com a documentação, sempre adicionar no final.
public enum CodigoErro {

    INTERNO_DO_SERVIDOR(1),
    REGRA_DE_NEGOCIO(2),
    SERVICO_EXTERNO(3),
    OPERACAO_JA_REALIZADA(4),
    ENTIDADE_NAO_ENCONTRADA(5),
    NAO_ESPECIFICADO(6),
    SEM_PERMISSAO(7),
    VALOR_FORNECIDO_INVALIDO(8),
    INCONSISTENCIA_INTERNA(9);

    public final int codigo;

    CodigoErro(int codigo) {
        this.codigo = codigo;
    }
}
