package fieg.modulos.pedido.model;
import fieg.modulos.pedido.enums.MeioUtilizadoPedido;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@Table(name = "CR5_PEDIDO")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_PEDIDO", nullable = false)
    private Integer idPedido;

    @Column(name = "PED_CODIGO_COMPOSTO")
    @Size(max = 255, message = "Tamanho do Campo: codigoComposto")
    private String codigoComposto;

    @Column(name = "PED_BANCO_EMISSOR")
    @Size(max = 255, message = "Tamanho do Campo: bancoEmissor")
    private String bancoEmissor;

    @Column(name = "PED_BANDEIRA")
    @Size(max = 255, message = "Tamanho do Campo: bandeira")
    private String bandeira;

    @Column(name = "PED_DTGERACAO")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime dataGeracao;

    @Column(name = "PED_IDENTIFICADOR_TERCEIRO")
    @Size(max = 255, message = "Tamanho do Campo: identificadorTerceiro")
    private String identificadorTerceiro;

    @Column(name = "PED_MEIO_UTILIZADO", nullable = false)
    @Enumerated(EnumType.STRING)
    private MeioUtilizadoPedido meioUtilizado;

    @Column(name = "PED_QTDE_PARCELAS")
    private Integer quantidadeParcelas;

    @Column(name = "PED_STATUS", nullable = false, columnDefinition = "char(1)")
    private String status;

    @Column(name = "PED_URL_CHECKOUT")
    @Size(max = 255, message = "Tamanho do Campo: urlCheckout")
    private String urlCheckout;

    @Column(name = "PED_NUMERO_CARTAO", length = 100)
    @Size(max = 100, message = "Tamanho do Campo: numeroCartao")
    private String numeroCartao;

    @Column(name = "PED_DATA_HORA_CANCELAMENTO")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime dataHoraCancelamento;

    @Column(name = "PED_IDENTIFICADOR_CONTRATO", length = 300)
    @Size(max = 300, message = "Tamanho do Campo: identificadorContrato")
    private String identificadorContrato;

    @Column(name = "PED_CPF", length = 20)
    @Size(max = 20, message = "Tamanho do Campo: cpf")
    private String cpf;

    @Column(name = "ID_OPERADOR")
    private Integer idOperador;

    @Column(name = "TERMINAL")
    @Size(max = 255, message = "Tamanho do Campo: terminal")
    private String terminal;

    @Column(name = "IP_CAIXA")
    @Size(max = 255, message = "Tamanho do Campo: ipCaixa")
    private String ipCaixa;

    public Integer getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(Integer idPedido) {
        this.idPedido = idPedido;
    }

    public String getCodigoComposto() {
        return codigoComposto;
    }

    public void setCodigoComposto(String codigoComposto) {
        this.codigoComposto = codigoComposto;
    }

    public String getBancoEmissor() {
        return bancoEmissor;
    }

    public void setBancoEmissor(String bancoEmissor) {
        this.bancoEmissor = bancoEmissor;
    }

    public String getBandeira() {
        return bandeira;
    }

    public void setBandeira(String bandeira) {
        this.bandeira = bandeira;
    }

    public LocalDateTime getDataGeracao() {
        return dataGeracao;
    }

    public void setDataGeracao(LocalDateTime dataGeracao) {
        this.dataGeracao = dataGeracao;
    }

    public String getIdentificadorTerceiro() {
        return identificadorTerceiro;
    }

    public void setIdentificadorTerceiro(String identificadorTerceiro) {
        this.identificadorTerceiro = identificadorTerceiro;
    }

    public MeioUtilizadoPedido getMeioUtilizado() {
        return meioUtilizado;
    }

    public void setMeioUtilizado(MeioUtilizadoPedido meioUtilizado) {
        this.meioUtilizado = meioUtilizado;
    }

    public Integer getQuantidadeParcelas() {
        return quantidadeParcelas;
    }

    public void setQuantidadeParcelas(Integer quantidadeParcelas) {
        this.quantidadeParcelas = quantidadeParcelas;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUrlCheckout() {
        return urlCheckout;
    }

    public void setUrlCheckout(String urlCheckout) {
        this.urlCheckout = urlCheckout;
    }

    public String getNumeroCartao() {
        return numeroCartao;
    }

    public void setNumeroCartao(String numeroCartao) {
        this.numeroCartao = numeroCartao;
    }

    public LocalDateTime getDataHoraCancelamento() {
        return dataHoraCancelamento;
    }

    public void setDataHoraCancelamento(LocalDateTime dataHoraCancelamento) {
        this.dataHoraCancelamento = dataHoraCancelamento;
    }

    public String getIdentificadorContrato() {
        return identificadorContrato;
    }

    public void setIdentificadorContrato(String identificadorContrato) {
        this.identificadorContrato = identificadorContrato;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Integer getIdOperador() {
        return idOperador;
    }

    public void setIdOperador(Integer idOperador) {
        this.idOperador = idOperador;
    }

    public String getTerminal() {
        return terminal;
    }

    public void setTerminal(String terminal) {
        this.terminal = terminal;
    }

    public String getIpCaixa() {
        return ipCaixa;
    }

    public void setIpCaixa(String ipCaixa) {
        this.ipCaixa = ipCaixa;
    }
}

