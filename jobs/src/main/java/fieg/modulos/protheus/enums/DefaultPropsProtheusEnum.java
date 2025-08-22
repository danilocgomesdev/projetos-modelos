package fieg.modulos.protheus.enums;



public final class DefaultPropsProtheusEnum {
    enum EnumAfirmacao {
        SIM("1"),
        NAO("2");

        String afirmacao;

        EnumAfirmacao(String afirmacao) {
            this.afirmacao = afirmacao;
        }

        public String getAfirmacao() {
            return this.afirmacao;
        }
    }

    public static final class CNB_TAB {
        private CNB_TAB() {}

        public static final Integer CNB_QUANT = 1;
        private static final String ITEM_PLANILHA_PADRAO = "001";
        private static final String PRODUTO_PADRAO = "00000001";
        private static final String COM_CONTEUDO = "2";
        private static final String CONTA_CONTABIL_VALIDA_PADRAO = "2";
        private static final String CENTRO_DE_CUSTO_VALIDA_PADRAO = "000001";
        private static final String ITEM_CONTABIL_VALIDA_PADRAO = "10101010101";

        /**
         * Como será utilizado apenas um item (venda do ensino/evento), por padrão,
         * será utilizado apenas um item.
         */
        public static final String CNB_ITEM = ITEM_PLANILHA_PADRAO;

        /**
         * CÓDIGO VÁLIDO DE UM PRODUTO QUE IRÁ COMPOR A OPERAÇAO DE
         * VENDA
         */
        public static final String CNB_PRODUT = PRODUTO_PADRAO;

        /**
         * INDICA SE O FINANCEIRO SERÁ GERADO NO ENCERRAMENTO DA
         * MEDIÇÃO OU NO PROCESSO DE SAÍDA DA NF. NO CENÁRIO ATUAL,
         * DEVERÁ SER SEMPRE COM CONTEÚDO 2-GERAÇAO NO
         * ENCERRAMENTO DA MEDICAO
         */
        public static final String CNB_PEDTIT = COM_CONTEUDO;
        public static final String CNB_CONTA = CONTA_CONTABIL_VALIDA_PADRAO;
        public static final String CNB_CC = CENTRO_DE_CUSTO_VALIDA_PADRAO;
        public static final String CNB_ITEMCT = ITEM_CONTABIL_VALIDA_PADRAO;
    }

    public static final class CNAT_TAB {
        private CNAT_TAB() {}

        private static final String PLANILHA_GENERICA = "007";
        private static final String NUMERO_PLANILHA_PADRAO = "000001";

        /**
         * TERÁ OU NÃO REAJUSTE. (1-SIM,2-NAO). NESTA PRIMEIRA ETAPA
         * DEIXAR SEMPRE COMO 2-NÃO.
         */
        public static final String CNA_FLREAJ = EnumAfirmacao.NAO.getAfirmacao();

        /**
         * CÓDIGO DO TIPO DE PLANILHA PREVIAMENTE CADASTRADO NO
         * MÓDULO. RECOMENDA-SE USAR UMA PLANILHA "GENÉRICA"OU
         * SEJA, QUE SEGUE AS CONFIGURAÇÕES DO CONTRATO.
         */
        public static final String CNA_TIPPLA = PLANILHA_GENERICA;

        /**
         * NÚMERO DA PLANILHA. COMO NESTE MOMENTO TEREMOS APENAS
         * UMA PLANILHA POR CONTRATO. USAR SEMPRE O VALOR 000001
         */
        public static final String CNA_NUMERO = NUMERO_PLANILHA_PADRAO;
    }

    public static final class CNN_TAB {
        private CNN_TAB() {}

        private static final String CODIGO_USUARIO_PADRAO = "001309";
        private static final String GRUPO_USUARIO_PADRAO = "000054";
        private static final String CODIGO_TRAFEGO_PADRAO = "001";

        public static final String CNN_USRCOD = CODIGO_USUARIO_PADRAO;

        public static final String CNN_GRPCOD = GRUPO_USUARIO_PADRAO;

        public static final String CNN_TRACOD = CODIGO_TRAFEGO_PADRAO;
    }

    public static final class CNC_TAB {
        private CNC_TAB() {}

        private static final String CODIGO_FORNECEDOR_PADRAO = "00000013";
        private static final String LOJA_FORNECEDOR_PADRAO = "0001";
        private static final String NUMERO_REVISAO_CONTRATO_PADRAO = " ";

        /**
         * CÓDIGO DO FORNECEDOR. APESAR SER UM CAMPO PARA USO EM
         * CONTRATOS DE COMPRA, A ROTINA DO PROTHEUS EXIGE QUE ELE
         * SEJA PREENCHIDO COM ALGUM FORNECEDOR VÁLIDO
         */
        public static final String CNC_CODIGO = CODIGO_FORNECEDOR_PADRAO;

        /**
         * LOJA FORNECEDOR. APESAR SER UM CAMPO PARA USO EM
         * CONTRATOS DE COMPRA, A ROTINA DO PROTHEUS EXIGE QUE ELE
         * SEJA PREENCHIDO COM ALGUM FORNECEDOR VÁLIDO
         */
        public static final String CNC_LOJA = LOJA_FORNECEDOR_PADRAO;

        /**
         * NÚMERO DA REVISÃO DO CONTRATO. COMO O CONTRATO NÃO
         * TERÁ REVISÃO NO MOMENTO DE SUA CRIAÇÃO, PREENCHER COM
         * UM ESPAÇO EM BRANCO.
         */
        public static final String CNC_REVISA = NUMERO_REVISAO_CONTRATO_PADRAO;
    }

    public static final class FINANCEIRO {
        private FINANCEIRO() {}


      public enum EnumPeriodicidade {
            MENSAL(1),
            QUINZENAL(2),
            DIARIO(3),
            COND_PAG(4);

            Integer periodicidade;

            EnumPeriodicidade(Integer periodicidade) {
                this.periodicidade = periodicidade;
            }

            public Integer getPeriodicidade() {
                return periodicidade;
            }
        }

        private static final Integer DIAS_PARCELAS_PADRAO = 30;

        /**
         * INTERVALO DE DIAS ENTRE UMA PARCELA E OUTRA. SENDO
         * PAGAMENTO MENSAL, RECOMENdA-SE COLOCAR 30 (DIAS)
         */
        public static final Integer DIAS_PARCELAS = DIAS_PARCELAS_PADRAO;

        public static final String ULT_DIA = ".T.";

        public static final String COMPAT = "";

        public static final String COND_PAG = "";
    }

    public static final class CN9_TAB {
        private CN9_TAB() {}

        enum EnumEspecieContrato {
            COMPRA("1"),
            VENDA("2");

            String especie;

            EnumEspecieContrato(String especie) {
                this.especie = especie;
            }


            public String getEspecie() {
                return this.especie;
            }
        }

        enum EnumUnidadeVigente {
            DIAS("1"),
            MESES("2"),
            ANOS("3"),
            INDETERMINADA("4");

            String unidadeVigente;

            EnumUnidadeVigente(String unidadeVigente) {
                this.unidadeVigente = unidadeVigente;
            }


            public String getUnidadeVigente() {
                return this.unidadeVigente;
            }
        }

        enum EnumMoeda {
            REAL(1);

            Integer moeda;

            EnumMoeda(Integer moeda) {
                this.moeda = moeda;
            }

            public Integer getMoeda() { return this.moeda; }
        }

        enum EnumCondicaoPagamento {
            A_VISTA("001");

            String condicaoPagamento;

            EnumCondicaoPagamento(String condicaoPagamento) {
                this.condicaoPagamento = condicaoPagamento;
            }

            public String getCondicaoPagamento() {
                return this.condicaoPagamento;
            }
        }

        private static final String TIPO_CONTRATO_PADRAO = "018";

        /**
         * ESPÉCIE DE CONTRATO (1-COMPRA , 2-VENDA)
         */
        public static final String CN9_ESPCTR = EnumEspecieContrato.VENDA.getEspecie();

        /**
         * UNIDADE VIGENTE. (1-DIAS, 2-MESES, 3-ANOS, 4-INDETERMINADA)
         */
        public static final String CN9_UNVIGE = EnumUnidadeVigente.MESES.getUnidadeVigente();

        /**
         * MOEDA DO CONTRATO. USAR SEMPRE 1 (REAL)
         */
        public static final Integer CN9_MOEDA = EnumMoeda.REAL.getMoeda();

        public static final String CN9_NUMERO = "";

        /**
         * CÓDIGO DA COND DE PAG. RECOMENDA-SE USAR SEMPRE 001 (A VISTA)
         */
        public static final String CN9_CONDPG = EnumCondicaoPagamento.A_VISTA.getCondicaoPagamento();

        /**
         * TIPO DE CONTRATO PREVIAMENTE CADASTRADO. DEVERÃO
         * SER USADOS TIPOS ESPECÍFICOS PARA A INTEGRAÇÃO
         */
        public static final String CN9_TPCTO = TIPO_CONTRATO_PADRAO;

        /**
         * SE CONTRATO VAI SOFRER REAJUSTE. NESTA ETAPA DEIXAR
         * SEMPRE COMO 2-NÃO
         */
        public static final String CN9_FLGREJ = EnumAfirmacao.NAO.getAfirmacao();

        /**
         * TEM CAUÇÃO. DEIXAR COMO 2-NAO
         */
        public static final String CN9_FLGCAU = EnumAfirmacao.NAO.getAfirmacao();

        /**
         * REGISTRO DE PREÇO. CAMPO CUSTOMIZADO O QUAL DEVERÁ
         * FICAR SEMPRE COMO 1-SIM. CASO FIQUE COMO 2-NAO ELE
         * BLOQUEIA INCLUSAO DO PRODUTO NA PLANILHA
         */
        public static final String CN9_XREGP = EnumAfirmacao.SIM.getAfirmacao();

        /**
         * MODALIDADE DE AQUISICAO. CAMPO SEM UTILIDADE PARA A
         * CRIAÇAO DO CONTRATO DE VENDA, PORÉM POR SER
         * OBRIGATÓRIO, O MESMO DEVERÁ SER PREENCHIDO COM
         * ALGUM CONTEÚDO VÁLIDO. RECOMENDA-SE CADASTRAR COM
         * O CONTEÚDO DL O QUAL DISPENSA O CONTRATO DE TER QUE
         * SER PREENCHIDO COM OS CAMPOS CN9_XPROTO, CN9_XLICIT E
         * CN9_XJURID
         */
        public static final String CN9_XMDAQU = "DL";

        /**
         * CÓDIGO DO USUÁRIO PROTHEUS RESPONSÁVEL PELO
         * CONTRATO
         */
        public static final String CN9_XRESPO = "000142";

        public static final String CN9_NATURE = "1010140039";
    }

    public static final class SA1_TAB {
        private SA1_TAB() {}

        enum EnumTipoPessoa {
            FISICA("F"),
            JURIDICA("J");

            String tipoPessoa;

            EnumTipoPessoa(String tipoPessoa) {
                this.tipoPessoa = tipoPessoa;
            }

            public String getTipoPessoa() {
                return this.tipoPessoa;
            }
        }

        enum EnumPaises {
            BRASIL("105");

            String pais;

            EnumPaises(String pais) {
                this.pais = pais;
            }

            public String getPais() {
                return this.pais;
            }
        }


        /**
         * TIPO DE PESSOA USADA NO PROTHEUS. NO CENÁRIO ATUAL
         * SERIAM USADOS OS TIPOS F-PESSOA FÍSICA E J-PESSOA JURÍDICA
         */
        public static final String A1_PESSOA = EnumTipoPessoa.FISICA.getTipoPessoa();

        /**
         * TIPO CLIENTE. NO CENÁRIO ATUAL SERIA USADO O FCONSUMIDOR FINAL
         */
        public static final String A1_TIPO = EnumTipoPessoa.FISICA.getTipoPessoa();

        public static final String A1_PAIS = EnumPaises.BRASIL.getPais();
    }

    public static final class EXECUCAO {
        public static final Integer PERIODICIDADE = 1;

        public static final Integer DIAS_PARCELAS = 30;
        public static final String ULT_DIA = ".F.";
    }
}
