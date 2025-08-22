package fieg.modulo.dto.response;


import jakarta.xml.bind.annotation.*;
import lombok.Data;

import java.util.List;

@Data
@XmlRootElement(name = "Envelope", namespace = "http://schemas.xmlsoap.org/soap/envelope/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {"body"})
public class ConsultaBoletoResponseDTO {

    @XmlElement(name = "Body", namespace = "http://schemas.xmlsoap.org/soap/envelope/", required = true)
    private SoapBody body;

    @Data
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {"servicoSaida"})
    private static class SoapBody {

        @XmlElement(name = "SERVICO_SAIDA", namespace = "http://caixa.gov.br/sibar/consulta_cobranca_bancaria/boleto", required = true)
        private ServicoSaida servicoSaida;
    }

    @Data
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
            "header",
            "codRetorno",
            "origemRetorno",
            "msgRetorno",
            "dados"
    })
    public static class ServicoSaida {

        @XmlElement(name = "HEADER", namespace = "http://caixa.gov.br/sibar", required = true)
        private HeaderDTO header;

        @XmlElement(name = "COD_RETORNO", required = true)
        private String codRetorno;

        @XmlElement(name = "ORIGEM_RETORNO", required = true)
        private String origemRetorno;

        @XmlElement(name = "MSG_RETORNO")
        private String msgRetorno;

        @XmlElement(name = "DADOS", required = true)
        private DadosDTO dados;
    }

    @Data
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
            "versao",
            "autenticacao",
            "usuarioServico",
            "operacao",
            "sistemaOrigem",
            "dataHora"
    })
    public static class HeaderDTO {

        @XmlElement(name = "VERSAO", required = true)
        private String versao;

        @XmlElement(name = "AUTENTICACAO", required = true)
        private String autenticacao;

        @XmlElement(name = "USUARIO_SERVICO", required = true)
        private String usuarioServico;

        @XmlElement(name = "OPERACAO", required = true)
        private String operacao;

        @XmlElement(name = "SISTEMA_ORIGEM", required = true)
        private String sistemaOrigem;

        @XmlElement(name = "DATA_HORA", required = true)
        private String dataHora;
    }

    @Data
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(propOrder = {"controleNegocial", "consultaBoleto"})
    public static class DadosDTO {

        @XmlElement(name = "CONTROLE_NEGOCIAL", required = true)
        private ControleNegocialDTO controleNegocial;

        @XmlElement(name = "CONSULTA_BOLETO", required = true)
        private ConsultaBoletoDTO consultaBoleto;
    }

    @Data
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(propOrder = {"origemRetorno", "codRetorno", "mensagens"})
    public static class ControleNegocialDTO {

        @XmlElement(name = "ORIGEM_RETORNO", required = true)
        private String origemRetorno;

        @XmlElement(name = "COD_RETORNO", required = true)
        private String codRetorno;

        @XmlElement(name = "MENSAGENS", required = true)
        private List<MensagemControleNegocialDTO> mensagens;
    }

    @Data
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(propOrder = {"retorno"})
    public static class MensagemControleNegocialDTO {
        @XmlElement(name = "RETORNO", required = true)
        private String retorno;
    }

    @Data
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(propOrder = {"titulo", "flagRegistro"})
    public static class ConsultaBoletoDTO {

        @XmlElement(name = "TITULO", required = true)
        private TituloDTO titulo;

        @XmlElement(name = "FLAG_REGISTRO", required = true)
        private String flagRegistro;
    }

    @Data
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(propOrder = {"numeroDocumento", "dataVencimento", "valor", "tipoEspecie", "flagAceite", "dataEmissao", "jurosMora",
            "valorAbatimento", "posVencimento", "codigoMoeda", "pagador", "multa", "descontos", "valorIof",
            "identificacaoEmpresa", "pagamento", "codigoBarras", "linhaDigitavel", "url"})
    public static class TituloDTO {

        @XmlElement(name = "NUMERO_DOCUMENTO", required = true)
        private String numeroDocumento;

        @XmlElement(name = "DATA_VENCIMENTO", required = true)
        private String dataVencimento;

        @XmlElement(name = "VALOR", required = true)
        private String valor;

        @XmlElement(name = "TIPO_ESPECIE", required = true)
        private String tipoEspecie;

        @XmlElement(name = "FLAG_ACEITE", required = true)
        private String flagAceite;

        @XmlElement(name = "DATA_EMISSAO", required = true)
        private String dataEmissao;

        @XmlElement(name = "JUROS_MORA", required = true)
        private JurosMoraDTO jurosMora;

        @XmlElement(name = "VALOR_ABATIMENTO", required = true)
        private String valorAbatimento;

        @XmlElement(name = "POS_VENCIMENTO", required = true)
        private PosVencimentoDTO posVencimento;

        @XmlElement(name = "CODIGO_MOEDA", required = true)
        private String codigoMoeda;

        @XmlElement(name = "PAGADOR", required = true)
        private PagadorDTO pagador;

        @XmlElement(name = "MULTA", required = true)
        private MultaDTO multa;

        @XmlElement(name = "DESCONTOS", required = true)
        private List<DescontoDTO> descontos;

        @XmlElement(name = "VALOR_IOF", required = true)
        private String valorIof;

        @XmlElement(name = "IDENTIFICACAO_EMPRESA")
        private String identificacaoEmpresa;

        @XmlElement(name = "PAGAMENTO", required = true)
        private PagamentoDTO pagamento;

        @XmlElement(name = "CODIGO_BARRAS", required = true)
        private String codigoBarras;

        @XmlElement(name = "LINHA_DIGITAVEL", required = true)
        private String linhaDigitavel;

        @XmlElement(name = "URL", required = true)
        private String url;
    }

    @Data
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(propOrder = {"tipo"})
    public static class JurosMoraDTO {

        @XmlElement(name = "TIPO", required = true)
        private String tipo;
    }

    @Data
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(propOrder = {"acao", "numeroDias"})
    public static class PosVencimentoDTO {

        @XmlElement(name = "ACAO", required = true)
        private String acao;

        @XmlElement(name = "NUMERO_DIAS", required = true)
        private String numeroDias;
    }

    @Data
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(propOrder = {"cpf", "nome", "endereco","cnpj","razao_SOCIAL"})
    public static class PagadorDTO {

        @XmlElement(name = "CPF", required = true)
        private String cpf;

        @XmlElement(name = "NOME", required = true)
        private String nome;

        @XmlElement(name = "ENDERECO", required = true)
        private EnderecoDTO endereco;

        @XmlElement(name = "CNPJ")
        private String cnpj;

        @XmlElement(name = "RAZAO_SOCIAL")
        private String razao_SOCIAL;
    }

    @Data
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(propOrder = {"logradouro", "bairro", "cidade", "uf", "cep"})
    public static class EnderecoDTO {

        @XmlElement(name = "LOGRADOURO", required = true)
        private String logradouro;

        @XmlElement(name = "BAIRRO", required = true)
        private String bairro;

        @XmlElement(name = "CIDADE", required = true)
        private String cidade;

        @XmlElement(name = "UF", required = true)
        private String uf;

        @XmlElement(name = "CEP", required = true)
        private String cep;
    }

    @Data
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(propOrder = {"data", "valor","percentual"})
    public static class MultaDTO {

        @XmlElement(name = "DATA", required = true)
        private String data;

        @XmlElement(name = "VALOR")
        private String valor;

        @XmlElement(name = "PERCENTUAL")
        private String percentual;
    }





    @Data
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(propOrder = {"desconto"})
    public static class DescontoDTO {

        @XmlElement(name = "DESCONTO")
        private DescontoItensDTO desconto;

    }


    @Data
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(propOrder = {"data", "valor","percentual"})
    public static class DescontoItensDTO {

        @XmlElement(name = "DATA")
        private String data;

        @XmlElement(name = "VALOR")
        private String valor;

        @XmlElement(name = "PERCENTUAL")
        private String percentual;
    }



    @Data
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(propOrder = {"quantidadePermitida", "tipo", "valorMaximo", "valorMinimo"})
    public static class PagamentoDTO {

        @XmlElement(name = "QUANTIDADE_PERMITIDA", required = true)
        private String quantidadePermitida;

        @XmlElement(name = "TIPO", required = true)
        private String tipo;

        @XmlElement(name = "VALOR_MAXIMO", required = true)
        private String valorMaximo;

        @XmlElement(name = "VALOR_MINIMO", required = true)
        private String valorMinimo;
    }
}
