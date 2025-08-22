package fieg.modulo.dto.response;

import jakarta.xml.bind.annotation.*;
import lombok.Data;

@Data
@XmlRootElement(name = "Envelope", namespace = "http://schemas.xmlsoap.org/soap/envelope/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {"body"})
public class ManutencaoBoletoResponseDTO {
    @XmlElement(name = "Body", namespace = "http://schemas.xmlsoap.org/soap/envelope/", required = true)
    private SoapBody body;

    @Data
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {"servicoSaida"})
    public static class SoapBody {
        @XmlElement(name = "SERVICO_SAIDA", namespace = "http://caixa.gov.br/sibar/manutencao_cobranca_bancaria/boleto/externo", required = true)
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
        private Header header;
        @XmlElement(name = "COD_RETORNO", required = true)
        private String codRetorno;
        @XmlElement(name = "ORIGEM_RETORNO", required = true)
        private String origemRetorno;
        @XmlElement(name = "MSG_RETORNO")
        private String msgRetorno;
        @XmlElement(name = "DADOS")
        private Dados dados;
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
    public static class Header {
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
    @XmlType(name = "", propOrder = {
            "controleNegocial",
            "incluiBoleto",
            "alteraBoleto",
            "excecao"
    })
    public static class Dados {
        @XmlElement(name = "CONTROLE_NEGOCIAL")
        private ControleNegocial controleNegocial;
        @XmlElement(name = "INCLUI_BOLETO")
        private IncluiBoleto incluiBoleto;
        @XmlElement(name = "ALTERA_BOLETO")
        private AlteraBoleto alteraBoleto;
        @XmlElement(name = "EXCECAO")
        private String excecao;
    }

    @Data
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
            "origemRetorno",
            "codRetorno",
            "mensagens"
    })
    public static class ControleNegocial {
        @XmlElement(name = "ORIGEM_RETORNO", required = true)
        protected String origemRetorno;
        @XmlElement(name = "COD_RETORNO", required = true)
        protected String codRetorno;
        @XmlElement(name = "MENSAGENS", required = true)
        protected Mensagens mensagens;
    }

    @Data
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
            "codigoBarras",
            "linhaDigitavel",
            "nossoNumero",
            "url"
    })
    public static class IncluiBoleto {
        @XmlElement(name = "CODIGO_BARRAS")
        protected String codigoBarras;
        @XmlElement(name = "LINHA_DIGITAVEL")
        protected String linhaDigitavel;
        @XmlElement(name = "NOSSO_NUMERO")
        protected String nossoNumero;
        @XmlElement(name = "URL")
        protected String url;
    }

    @Data
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
            "codigoBarras",
            "linhaDigitavel",
            "url"
    })
    public static class AlteraBoleto {
        @XmlElement(name = "CODIGO_BARRAS")
        protected String codigoBarras;
        @XmlElement(name = "LINHA_DIGITAVEL")
        protected String linhaDigitavel;
        @XmlElement(name = "URL")
        protected String url;
    }

    @Data
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
            "retorno"
    })
    public static class Mensagens {
        @XmlElement(name = "RETORNO", required = true)
        protected String retorno;
    }

}

