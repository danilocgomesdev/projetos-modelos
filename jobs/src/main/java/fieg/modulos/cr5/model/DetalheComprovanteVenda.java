package fieg.modulos.cr5.model;


import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "CR5_DETALHE_COMPROVANTE_VENDA")
public class DetalheComprovanteVenda extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_DETALHE_COMPROVANTE_PAGAMENTO")
    private Integer id;

    @Column(name = "DCV_CODIGO_AUTORIZACAO")
    private String codigoAutorizacao;

    @Column(name = "DCV_CODIGO_PEDIDO")
    private String codigoPedido;

    @Column(name = "DCV_DATA_VENDA")
    private Date dataVenda;

    @Column(name = "DCV_EMITIDO_EXTERIOR")
    private String emitidoExterior; // Aparentemente não existe mais, porém parece que não é usado

    @Column(name = "DCV_ESTABELECIMENTO_SUBMISSOR")
    private String estabelecimentoSubmissor;

    @Column(name = "DCV_HORA_TRANSACAO")
    private String horaTransacao;

    @Column(name = "DCV_IDENTIFICADOR_TAXA_EMBARQUE")
    private String identificadorTaxaEmbarque; // Tudo em branco no banco, ainda existe a taxa de embarque

    @Column(name = "DCV_INDICADOR_CIELO_PROMOCAO")
    private String indicadorCieloPromocao; // Existe S e em branco, ainda existe

    @Column(name = "DCV_MOTIVO_REJEICAO")
    private String motivoRejeicao;

    @Column(name = "DCV_NUM_CARTAO")
    private String numeroCartao; // Foi quebrado em dois, é possível juntar

    @Column(name = "DCV_NUMERO_NSU")
    private String numeroNSU;

    @Column(name = "DCV_NUM_NOTA_FISCAL_POS")
    private String numeroNotaFiscalPOS;

    @Column(name = "DCV_NUM_PARCELA")
    private String numeroParcela;

    @Column(name = "DCV_NUM_RESUMO_OPERACAO")
    private String numeroResumoOperacao; // Não ser usado

    @Column(name = "DCV_NUM_TERMINAL")
    private String numeroTerminal;

    @Column(name = "DCV_NUMERO_UNICO_RO")
    private String numeroUnicoRO; // Foi trocado. Avliar se colocar o novo valor aqui

    @Column(name = "DCV_NUM_UNICO_TRANSACAO")
    private String numeroUnicoTransacao;

    @Column(name = "DCV_QTDE_DIGITOS_CARTAO")
    private String qtdeDigitosCartao; // Não existe mais, não é usado. Existe o indicador de bandeira do cartão.

    @Column(name = "DCV_QTDE_PARCELAS")
    private String qtdeParcelas;

    @Column(name = "DCV_TID")
    private String tid;
    //Crédito: +
    //Débito: -
    @Column(name = "DCV_TIPO_OPERACAO")
    private String tipoOperacao; // Derivar do sinal do valorBrutoParcela

    @Column(name = "DCV_VALOR_COMPLEMENTAR")
    private BigDecimal valorComplementar; // Se tornou valor saque

    @Column(name = "DCV_VALOR_PROXIMA_PARCELA")
    private BigDecimal valorProximaParcela; // Não existe mais, não é usado

    @Column(name = "DCV_VALOR_TOTAL_VENDA")
    private BigDecimal valorTotalVenda;

    @Column(name = "DCV_VALOR_VENDA")
    private BigDecimal valorVenda; // valorBrutoParcela

    @ManyToOne
    @JoinColumn(name = "ID_CABECALHO_ARQ_VENDA", foreignKey = @ForeignKey(name = "FK_CABECALHO_ARQ_VENDA"))
    private CabecalhoArquivoVenda cabecalho;

    public DetalheComprovanteVenda(){
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCodigoAutorizacao() {
        return codigoAutorizacao;
    }

    public void setCodigoAutorizacao(String codigoAutorizacao) {
        this.codigoAutorizacao = codigoAutorizacao;
    }

    public String getCodigoPedido() {
        return codigoPedido;
    }

    public void setCodigoPedido(String codigoPedido) {
        this.codigoPedido = codigoPedido;
    }

    public Date getDataVenda() {
        return dataVenda;
    }

    public void setDataVenda(Date dataVenda) {
        this.dataVenda = dataVenda;
    }

    public String getEmitidoExterior() {
        return emitidoExterior;
    }

    public void setEmitidoExterior(String emitidoExterior) {
        this.emitidoExterior = emitidoExterior;
    }

    public String getEstabelecimentoSubmissor() {
        return estabelecimentoSubmissor;
    }

    public void setEstabelecimentoSubmissor(String estabelecimentoSubmissor) {
        this.estabelecimentoSubmissor = estabelecimentoSubmissor;
    }

    public String getHoraTransacao() {
        return horaTransacao;
    }

    public void setHoraTransacao(String horaTransacao) {
        this.horaTransacao = horaTransacao;
    }

    public String getIdentificadorTaxaEmbarque() {
        return identificadorTaxaEmbarque;
    }

    public void setIdentificadorTaxaEmbarque(String identificadorTaxaEmbarque) {
        this.identificadorTaxaEmbarque = identificadorTaxaEmbarque;
    }

    public String getIndicadorCieloPromocao() {
        return indicadorCieloPromocao;
    }

    public void setIndicadorCieloPromocao(String indicadorCieloPromocao) {
        this.indicadorCieloPromocao = indicadorCieloPromocao;
    }

    public String getMotivoRejeicao() {
        return motivoRejeicao;
    }

    public void setMotivoRejeicao(String motivoRejeicao) {
        this.motivoRejeicao = motivoRejeicao;
    }

    public String getNumeroCartao() {
        return numeroCartao;
    }

    public void setNumeroCartao(String numeroCartao) {
        this.numeroCartao = numeroCartao;
    }

    public String getNumeroNSU() {
        return numeroNSU;
    }

    public void setNumeroNSU(String numeroNSU) {
        this.numeroNSU = numeroNSU;
    }

    public String getNumeroNotaFiscalPOS() {
        return numeroNotaFiscalPOS;
    }

    public void setNumeroNotaFiscalPOS(String numeroNotaFiscalPOS) {
        this.numeroNotaFiscalPOS = numeroNotaFiscalPOS;
    }

    public String getNumeroParcela() {
        return numeroParcela;
    }

    public void setNumeroParcela(String numeroParcela) {
        this.numeroParcela = numeroParcela;
    }

    public String getNumeroResumoOperacao() {
        return numeroResumoOperacao;
    }

    public void setNumeroResumoOperacao(String numeroResumoOperacao) {
        this.numeroResumoOperacao = numeroResumoOperacao;
    }

    public String getNumeroTerminal() {
        return numeroTerminal;
    }

    public void setNumeroTerminal(String numeroTerminal) {
        this.numeroTerminal = numeroTerminal;
    }

    public String getNumeroUnicoRO() {
        return numeroUnicoRO;
    }

    public void setNumeroUnicoRO(String numeroUnicoRO) {
        this.numeroUnicoRO = numeroUnicoRO;
    }

    public String getNumeroUnicoTransacao() {
        return numeroUnicoTransacao;
    }

    public void setNumeroUnicoTransacao(String numeroUnicoTransacao) {
        this.numeroUnicoTransacao = numeroUnicoTransacao;
    }

    public String getQtdeDigitosCartao() {
        return qtdeDigitosCartao;
    }

    public void setQtdeDigitosCartao(String qtdeDigitosCartao) {
        this.qtdeDigitosCartao = qtdeDigitosCartao;
    }

    public String getQtdeParcelas() {
        return qtdeParcelas;
    }

    public void setQtdeParcelas(String qtdeParcelas) {
        this.qtdeParcelas = qtdeParcelas;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getTipoOperacao() {
        return tipoOperacao;
    }

    public void setTipoOperacao(String tipoOperacao) {
        this.tipoOperacao = tipoOperacao;
    }

    public BigDecimal getValorComplementar() {
        return valorComplementar;
    }

    public void setValorComplementar(BigDecimal valorComplementar) {
        this.valorComplementar = valorComplementar;
    }

    public BigDecimal getValorProximaParcela() {
        return valorProximaParcela;
    }

    public void setValorProximaParcela(BigDecimal valorProximaParcela) {
        this.valorProximaParcela = valorProximaParcela;
    }

    public BigDecimal getValorTotalVenda() {
        return valorTotalVenda;
    }

    public void setValorTotalVenda(BigDecimal valorTotalVenda) {
        this.valorTotalVenda = valorTotalVenda;
    }

    public BigDecimal getValorVenda() {
        return valorVenda;
    }

    public void setValorVenda(BigDecimal valorVenda) {
        this.valorVenda = valorVenda;
    }

    public CabecalhoArquivoVenda getCabecalho() {
        return cabecalho;
    }

    public void setCabecalho(CabecalhoArquivoVenda cabecalho) {
        this.cabecalho = cabecalho;
    }

    @Override
    public String toString() {
        return "DetalheComprovanteVenda{" +
                "id=" + id +
                ", codigoAutorizacao='" + codigoAutorizacao + '\'' +
                ", codigoPedido='" + codigoPedido + '\'' +
                ", dataVenda=" + dataVenda +
                ", emitidoExterior='" + emitidoExterior + '\'' +
                ", estabelecimentoSubmissor='" + estabelecimentoSubmissor + '\'' +
                ", horaTransacao='" + horaTransacao + '\'' +
                ", identificadorTaxaEmbarque='" + identificadorTaxaEmbarque + '\'' +
                ", indicadorCieloPromocao='" + indicadorCieloPromocao + '\'' +
                ", motivoRejeicao='" + motivoRejeicao + '\'' +
                ", numeroCartao='" + numeroCartao + '\'' +
                ", numeroNSU='" + numeroNSU + '\'' +
                ", numeroNotaFiscalPOS='" + numeroNotaFiscalPOS + '\'' +
                ", numeroParcela='" + numeroParcela + '\'' +
                ", numeroResumoOperacao='" + numeroResumoOperacao + '\'' +
                ", numeroTerminal='" + numeroTerminal + '\'' +
                ", numeroUnicoRO='" + numeroUnicoRO + '\'' +
                ", numeroUnicoTransacao='" + numeroUnicoTransacao + '\'' +
                ", qtdeDigitosCartao='" + qtdeDigitosCartao + '\'' +
                ", qtdeParcelas='" + qtdeParcelas + '\'' +
                ", tid='" + tid + '\'' +
                ", tipoOperacao='" + tipoOperacao + '\'' +
                ", valorComplementar=" + valorComplementar +
                ", valorProximaParcela=" + valorProximaParcela +
                ", valorTotalVenda=" + valorTotalVenda +
                ", valorVenda=" + valorVenda +
                "}";
    }
}
