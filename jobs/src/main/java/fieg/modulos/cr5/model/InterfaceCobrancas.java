package fieg.modulos.cr5.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import fieg.modulos.compartilhado.VisaoUnidade;
import fieg.modulos.cr5.enums.StatusInterfaceCobrancaEnum;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.*;

@Entity
@Table(name = "CR5_INTERFACE_COBRANCAS")
public class InterfaceCobrancas extends PanacheEntityBase {
    @Id
    @Column(name = "ID_INTERFACE")
    private Integer idInterface;
    @Column(name = "CONT_ID")
    private Integer contId;

    @Column(name = "CONT_DT_INICIO_VIGENCIA")
    private Date contDtInicioVigencia;

    @Column(name = "CONT_DT_TERMINO_VIGENCIA")
    private Date contDtTerminoVigencia;

    @Column(name = "CONT_QTDE_DE_PARCELAS")
    private Integer contQtdeDeParcelas;

    @Column(name = "CONT_DIA_VENCIMENTO_PARCELAS")
    private Integer contDiaVencimentoParcelas;

    @Column(name = "CONT_DT_PRIMEIRO_VENCIMENTO")
    private Date contDtPrimeiroVencimento;

    @Column(name = "CONT_VL_TOTAL_DO_CONTRATO")
    private BigDecimal contVlTotalDoContrato;

    @Column(name = "CONT_VL_DA_PARCELA_A_PAGAR")
    private BigDecimal contVlDaParcelaAPagar;

    @Column(name = "CONT_VL_BOLSA")
    private BigDecimal contVlBolsa;

    @Column(name = "CONT_VL_DESC_COMERCIAL")
    private BigDecimal contVlDescComercial;

    @Column(name = "CONT_DESC_COMERCIAL")
    private String contDescComercial;

    @Column(name = "CONT_AGENTE_NOME")
    private String contAgenteNome;

    @Column(name = "CONT_AGENTE_PERC")
    private Double contAgentePerc;

    @Column(name = "CONT_AGENTE_VALOR")
    private BigDecimal contAgenteValor;

    @Column(name = "CONT_MENSAGEM_BOLETO")
    private String contMensagemBoleto;

    @Column(name = "CONT_DT_CADASTRO")
    private Date contDtCadastro;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_UNIDADE_CONTRATO", referencedColumnName = "ID_UNIDADE")
    private VisaoUnidade unidade;

    @Column(name = "ID_PRODUTO")
    private Integer idProduto;

    @Column(name = "ID_SERVICO")
    private Integer idServico;

    @Column(name = "ANO")
    private Integer ano;

    @Column(name = "OBJETO_CONTRATO")
    private String objetoContrato;

    @Column(name = "SACADO_ID")
    private Integer sacadoId;

    @Column(name = "SACADO_NOME")
    private String sacadoNome;

    @Column(name = "SACADO_TIPO_DOC_IDENT")
    private String sacadoTipoDocIdent;

    @Column(name = "SACADO_NUM_DOC_IDENT")
    private String sacadoNumDocIdent;

    @Column(name = "SACADO_CPF_CNPJ")
    private String sacadoCpfCnpj;

    @Column(name = "SACADO_DT_NASCIMENTO")
    private Date sacadoDtNascimento;

    @Column(name = "SACADO_LOGRADOURO")
    private String sacadoLogradouro;

    @Column(name = "SACADO_COMPLEMENTO")
    private String sacadoComplemento;

    @Column(name = "SACADO_BAIRRO")
    private String sacadoBairro;

    @Column(name = "SACADO_CIDADE")
    private String sacadoCidade;
    @Column(name = "SACADO_ESTADO")
    private String sacadoEstado;

    @Column(name = "SACADO_CEP")
    private String sacadoCep;

    @Column(name = "SACADO_FONE")
    private String sacadoFone;

    @Column(name = "SACADO_FONE2")
    private String sacadoFone2;

    @Column(name = "CONSUMIDOR_ID")
    private Integer consumidorId;

    @Column(name = "CONSUMIDOR_NOME")
    private String consumidorNome;
    @Column(name = "RESCISAO_ANTES")
    private Integer rescisaoAntes;

    @Column(name = "RESCISAO_DURANTE")
    private Integer rescisaoDurante;

    @Column(name = "CONT_ADM_ANTES_INSTALACAO")
    private String contAdmAntesInstalacao;

    @Column(name = "ID_SISTEMA")
    private Integer idSistema;

    @Column(name = "STATUS_INTERFACE")
    private String statusInterface;

    @Column(name = "DESC_ATE_VENCIMENTO")
    private Double descAteVencimento;

    @Column(name = "CONT_ID_ORIGEM")
    private Integer contIdOrigem;

    @Column(name = "MOTIVO_CANCELAMENTO")
    private String motivoCancelamento;

    @Column(name = "CONT_DT_CANCELAMENTO")
    private Date contDtCancelamento;

    @Column(name = "DATA_LIMITE_CONSUMO")
    private Date dataLimiteConsumo;

    @Column(name = "CONT_NR_NOTA_FISCAL")
    private String contNrNotaFiscal;

    @Column(name = "CONT_NOTA_DT_EMISSAO")
    private Date contNotaDtEmissao;

    @Column(name = "CONT_PRE_MATRICULA")
    private String contPreMatricula;

    @Column(name = "ID_OPERADOR_INCLUSAO")
    private Integer idOperadorInclusao;

    @Column(name = "DATA_INCLUSAO")
    private Date dataInclusao;

    @Column(name = "DATA_ALTERACAO")
    private Date dataAlteracao;

    @Column(name = "ID_OPERADOR_ALTERACAO")
    private Integer idOperadorAlteracao;

    @Column(name = "CONT_DT_SEGUNDO_VENCIMENTO")
    private Date contDtSegundoVencimento;

    @Column(name = "CONSUMIDOR_DT_NASCIMENTO")
    private Date consumidorDtNascimento;

    @Column(name = "CONT_DT_INICIO_VIGENCIA_COBRANCA")
    private Date contDtInicioVigenciaCobranca;

    @Column(name = "CONT_DT_TERMINO_VIGENCIA_COBRANCA")
    private Date contDtTerminoVigenciaCobranca;

    @Column(name = "RECEBIMENTO_PADRAO_BOLETO")
    private String recebimentoPadraoBoleto;

    @Column(name = "CONT_IS_EDICAO_SNE_AUTORIZADA")
    private Integer contIsEdicaoSneAutorizada;

    @Column(name = "QTD_MAX_PARCELA_CARTAO")
    private Integer qtdMaxParcelaCartao;

    @Column(name = "PAGAMENTO_BOLETO")
    private Boolean pagamentoBoleto;

    @Column(name = "DESC_ATE_VENCIMENTO_ORIGINAL")
    private Double descAteVencimentoOriginal;

    @Column(name = "SACADO_NUMERO")
    private String sacadoNumero;

    @Column(name = "SACADO_EMAIL")
    private String sacadoEmail;

    @Column(name = "SACADO_CEL_FONE2")
    private String sacadoCelFone2;

    @Column(name = "SACADO_CEL_FONE")
    private String sacadoCelFone;

    @Column(name = "ATUALIZA_PESSOA")
    private Boolean atualizaPessoa;

    @Column(name = "CONSUMIDOR_CPF_CNPJ")
    private String consumidorCpfCnpj;

    @Column(name = "ID_OPERADOR_CONSULTOR")
    private Integer idOperadorConsultor;

    @Column(name = "INTEGRA_PROTHEUS")
    private String integraProtheus;

    @Column(name = "CONT_PROTHEUS")
    private String contProtheus;

    @Transient
    private Set<CobrancasClientes> cobrancasClientes;
    public InterfaceCobrancas(){}

    public Integer getIdInterface() {
        return idInterface;
    }

    public void setIdInterface(Integer idInterface) {
        this.idInterface = idInterface;
    }

    public Integer getContId() {
        return contId;
    }

    public void setContId(Integer contId) {
        this.contId = contId;
    }

    public Date getContDtInicioVigencia() {
        return contDtInicioVigencia;
    }

    public void setContDtInicioVigencia(Date contDtInicioVigencia) {
        this.contDtInicioVigencia = contDtInicioVigencia;
    }

    public Date getContDtTerminoVigencia() {
        return contDtTerminoVigencia;
    }

    public void setContDtTerminoVigencia(Date contDtTerminoVigencia) {
        this.contDtTerminoVigencia = contDtTerminoVigencia;
    }

    public Integer getContQtdeDeParcelas() {
        return contQtdeDeParcelas;
    }

    public void setContQtdeDeParcelas(Integer contQtdeDeParcelas) {
        this.contQtdeDeParcelas = contQtdeDeParcelas;
    }

    public Integer getContDiaVencimentoParcelas() {
        return contDiaVencimentoParcelas;
    }

    public void setContDiaVencimentoParcelas(Integer contDiaVencimentoParcelas) {
        this.contDiaVencimentoParcelas = contDiaVencimentoParcelas;
    }

    public Date getContDtPrimeiroVencimento() {
        return contDtPrimeiroVencimento;
    }

    public void setContDtPrimeiroVencimento(Date contDtPrimeiroVencimento) {
        this.contDtPrimeiroVencimento = contDtPrimeiroVencimento;
    }

    public BigDecimal getContVlTotalDoContrato() {
        return contVlTotalDoContrato;
    }

    public void setContVlTotalDoContrato(BigDecimal contVlTotalDoContrato) {
        this.contVlTotalDoContrato = contVlTotalDoContrato;
    }

    public BigDecimal getContVlDaParcelaAPagar() {
        return contVlDaParcelaAPagar;
    }

    public void setContVlDaParcelaAPagar(BigDecimal contVlDaParcelaAPagar) {
        this.contVlDaParcelaAPagar = contVlDaParcelaAPagar;
    }

    public BigDecimal getContVlBolsa() {
        return contVlBolsa;
    }

    public void setContVlBolsa(BigDecimal contVlBolsa) {
        this.contVlBolsa = contVlBolsa;
    }

    public BigDecimal getContVlDescComercial() {
        return contVlDescComercial;
    }

    public void setContVlDescComercial(BigDecimal contVlDescComercial) {
        this.contVlDescComercial = contVlDescComercial;
    }

    public String getContDescComercial() {
        return contDescComercial;
    }

    public void setContDescComercial(String contDescComercial) {
        this.contDescComercial = contDescComercial;
    }

    public String getContAgenteNome() {
        return contAgenteNome;
    }

    public void setContAgenteNome(String contAgenteNome) {
        this.contAgenteNome = contAgenteNome;
    }

    public Double getContAgentePerc() {
        return contAgentePerc;
    }

    public void setContAgentePerc(Double contAgentePerc) {
        this.contAgentePerc = contAgentePerc;
    }

    public BigDecimal getContAgenteValor() {
        return contAgenteValor;
    }

    public void setContAgenteValor(BigDecimal contAgenteValor) {
        this.contAgenteValor = contAgenteValor;
    }

    public String getContMensagemBoleto() {
        return contMensagemBoleto;
    }

    public void setContMensagemBoleto(String contMensagemBoleto) {
        this.contMensagemBoleto = contMensagemBoleto;
    }

    public Date getContDtCadastro() {
        return contDtCadastro;
    }

    public void setContDtCadastro(Date contDtCadastro) {
        this.contDtCadastro = contDtCadastro;
    }

    public VisaoUnidade getUnidade() {
        return unidade;
    }

    public void setUnidade(VisaoUnidade unidade) {
        this.unidade = unidade;
    }

    public Integer getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(Integer idProduto) {
        this.idProduto = idProduto;
    }

    public Integer getAno() {
        return ano;
    }

    public void setAno(Integer ano) {
        this.ano = ano;
    }

    public String getObjetoContrato() {
        return objetoContrato;
    }

    public void setObjetoContrato(String objetoContrato) {
        this.objetoContrato = objetoContrato;
    }

    public Integer getSacadoId() {
        return sacadoId;
    }

    public void setSacadoId(Integer sacadoId) {
        this.sacadoId = sacadoId;
    }

    public String getSacadoNome() {
        return sacadoNome;
    }

    public void setSacadoNome(String sacadoNome) {
        this.sacadoNome = sacadoNome;
    }

    public String getSacadoTipoDocIdent() {
        return sacadoTipoDocIdent;
    }

    public void setSacadoTipoDocIdent(String sacadoTipoDocIdent) {
        this.sacadoTipoDocIdent = sacadoTipoDocIdent;
    }

    public String getSacadoNumDocIdent() {
        return sacadoNumDocIdent;
    }

    public void setSacadoNumDocIdent(String sacadoNumDocIdent) {
        this.sacadoNumDocIdent = sacadoNumDocIdent;
    }

    public String getSacadoCpfCnpj() {
        return sacadoCpfCnpj;
    }

    public void setSacadoCpfCnpj(String sacadoCpfCnpj) {
        this.sacadoCpfCnpj = sacadoCpfCnpj;
    }

    public Date getSacadoDtNascimento() {
        return sacadoDtNascimento;
    }

    public void setSacadoDtNascimento(Date sacadoDtNascimento) {
        this.sacadoDtNascimento = sacadoDtNascimento;
    }

    public String getSacadoLogradouro() {
        return sacadoLogradouro;
    }

    public void setSacadoLogradouro(String sacadoLogradouro) {
        this.sacadoLogradouro = sacadoLogradouro;
    }

    public String getSacadoComplemento() {
        return sacadoComplemento;
    }

    public void setSacadoComplemento(String sacadoComplemento) {
        this.sacadoComplemento = sacadoComplemento;
    }

    public String getSacadoBairro() {
        return sacadoBairro;
    }

    public void setSacadoBairro(String sacadoBairro) {
        this.sacadoBairro = sacadoBairro;
    }

    public String getSacadoCidade() {
        return sacadoCidade;
    }

    public void setSacadoCidade(String sacadoCidade) {
        this.sacadoCidade = sacadoCidade;
    }

    public String getSacadoEstado() {
        return sacadoEstado;
    }

    public void setSacadoEstado(String sacadoEstado) {
        this.sacadoEstado = sacadoEstado;
    }

    public String getSacadoCep() {
        return sacadoCep;
    }

    public void setSacadoCep(String sacadoCep) {
        this.sacadoCep = sacadoCep;
    }

    public String getSacadoFone() {
        return sacadoFone;
    }

    public void setSacadoFone(String sacadoFone) {
        this.sacadoFone = sacadoFone;
    }

    public String getSacadoFone2() {
        return sacadoFone2;
    }

    public void setSacadoFone2(String sacadoFone2) {
        this.sacadoFone2 = sacadoFone2;
    }

    public Integer getConsumidorId() {
        return consumidorId;
    }

    public void setConsumidorId(Integer consumidorId) {
        this.consumidorId = consumidorId;
    }

    public String getConsumidorNome() {
        return consumidorNome;
    }

    public void setConsumidorNome(String consumidorNome) {
        this.consumidorNome = consumidorNome;
    }

    public Integer getRescisaoAntes() {
        return rescisaoAntes;
    }

    public void setRescisaoAntes(Integer rescisaoAntes) {
        this.rescisaoAntes = rescisaoAntes;
    }

    public Integer getRescisaoDurante() {
        return rescisaoDurante;
    }

    public void setRescisaoDurante(Integer rescisaoDurante) {
        this.rescisaoDurante = rescisaoDurante;
    }

    public String getContAdmAntesInstalacao() {
        return contAdmAntesInstalacao;
    }

    public void setContAdmAntesInstalacao(String contAdmAntesInstalacao) {
        this.contAdmAntesInstalacao = contAdmAntesInstalacao;
    }

    public Integer getIdSistema() {
        return idSistema;
    }

    public void setIdSistema(Integer idSistema) {
        this.idSistema = idSistema;
    }

    public String getStatusInterface() {
        return statusInterface;
    }

    public void setStatusInterface(String statusInterface) {
        this.statusInterface = statusInterface;
    }

    public Double getDescAteVencimento() {
        return descAteVencimento;
    }

    public void setDescAteVencimento(Double descAteVencimento) {
        this.descAteVencimento = descAteVencimento;
    }

    public Integer getContIdOrigem() {
        return contIdOrigem;
    }

    public void setContIdOrigem(Integer contIdOrigem) {
        this.contIdOrigem = contIdOrigem;
    }

    public String getMotivoCancelamento() {
        return motivoCancelamento;
    }

    public void setMotivoCancelamento(String motivoCancelamento) {
        this.motivoCancelamento = motivoCancelamento;
    }

    public Date getContDtCancelamento() {
        return contDtCancelamento;
    }

    public void setContDtCancelamento(Date contDtCancelamento) {
        this.contDtCancelamento = contDtCancelamento;
    }

    public Date getDataLimiteConsumo() {
        return dataLimiteConsumo;
    }

    public void setDataLimiteConsumo(Date dataLimiteConsumo) {
        this.dataLimiteConsumo = dataLimiteConsumo;
    }

    public String getContNrNotaFiscal() {
        return contNrNotaFiscal;
    }

    public void setContNrNotaFiscal(String contNrNotaFiscal) {
        this.contNrNotaFiscal = contNrNotaFiscal;
    }

    public Date getContNotaDtEmissao() {
        return contNotaDtEmissao;
    }

    public void setContNotaDtEmissao(Date contNotaDtEmissao) {
        this.contNotaDtEmissao = contNotaDtEmissao;
    }

    public String getContPreMatricula() {
        return contPreMatricula;
    }

    public void setContPreMatricula(String contPreMatricula) {
        this.contPreMatricula = contPreMatricula;
    }

    public Integer getIdOperadorInclusao() {
        return idOperadorInclusao;
    }

    public void setIdOperadorInclusao(Integer idOperadorInclusao) {
        this.idOperadorInclusao = idOperadorInclusao;
    }

    public Date getDataInclusao() {
        return dataInclusao;
    }

    public void setDataInclusao(Date dataInclusao) {
        this.dataInclusao = dataInclusao;
    }

    public Date getDataAlteracao() {
        return dataAlteracao;
    }

    public void setDataAlteracao(Date dataAlteracao) {
        this.dataAlteracao = dataAlteracao;
    }

    public Integer getIdOperadorAlteracao() {
        return idOperadorAlteracao;
    }

    public void setIdOperadorAlteracao(Integer idOperadorAlteracao) {
        this.idOperadorAlteracao = idOperadorAlteracao;
    }

    public Date getContDtSegundoVencimento() {
        return contDtSegundoVencimento;
    }

    public void setContDtSegundoVencimento(Date contDtSegundoVencimento) {
        this.contDtSegundoVencimento = contDtSegundoVencimento;
    }

    public Date getConsumidorDtNascimento() {
        return consumidorDtNascimento;
    }

    public void setConsumidorDtNascimento(Date consumidorDtNascimento) {
        this.consumidorDtNascimento = consumidorDtNascimento;
    }

    public Date getContDtInicioVigenciaCobranca() {
        return contDtInicioVigenciaCobranca;
    }

    public void setContDtInicioVigenciaCobranca(Date contDtInicioVigenciaCobranca) {
        this.contDtInicioVigenciaCobranca = contDtInicioVigenciaCobranca;
    }

    public Date getContDtTerminoVigenciaCobranca() {
        return contDtTerminoVigenciaCobranca;
    }

    public void setContDtTerminoVigenciaCobranca(Date contDtTerminoVigenciaCobranca) {
        this.contDtTerminoVigenciaCobranca = contDtTerminoVigenciaCobranca;
    }

    public String getRecebimentoPadraoBoleto() {
        return recebimentoPadraoBoleto;
    }

    public void setRecebimentoPadraoBoleto(String recebimentoPadraoBoleto) {
        this.recebimentoPadraoBoleto = recebimentoPadraoBoleto;
    }

    public Integer getContIsEdicaoSneAutorizada() {
        return contIsEdicaoSneAutorizada;
    }

    public void setContIsEdicaoSneAutorizada(Integer contIsEdicaoSneAutorizada) {
        this.contIsEdicaoSneAutorizada = contIsEdicaoSneAutorizada;
    }

    public Integer getQtdMaxParcelaCartao() {
        return qtdMaxParcelaCartao;
    }

    public void setQtdMaxParcelaCartao(Integer qtdMaxParcelaCartao) {
        this.qtdMaxParcelaCartao = qtdMaxParcelaCartao;
    }

    public Boolean getPagamentoBoleto() {
        return pagamentoBoleto;
    }

    public void setPagamentoBoleto(Boolean pagamentoBoleto) {
        this.pagamentoBoleto = pagamentoBoleto;
    }

    public Double getDescAteVencimentoOriginal() {
        return descAteVencimentoOriginal;
    }

    public void setDescAteVencimentoOriginal(Double descAteVencimentoOriginal) {
        this.descAteVencimentoOriginal = descAteVencimentoOriginal;
    }

    public String getSacadoNumero() {
        return sacadoNumero;
    }

    public void setSacadoNumero(String sacadoNumero) {
        this.sacadoNumero = sacadoNumero;
    }

    public String getSacadoEmail() {
        return sacadoEmail;
    }

    public void setSacadoEmail(String sacadoEmail) {
        this.sacadoEmail = sacadoEmail;
    }

    public String getSacadoCelFone2() {
        return sacadoCelFone2;
    }

    public void setSacadoCelFone2(String sacadoCelFone2) {
        this.sacadoCelFone2 = sacadoCelFone2;
    }

    public String getSacadoCelFone() {
        return sacadoCelFone;
    }

    public void setSacadoCelFone(String sacadoCelFone) {
        this.sacadoCelFone = sacadoCelFone;
    }

    public Boolean getAtualizaPessoa() {
        return atualizaPessoa;
    }

    public void setAtualizaPessoa(Boolean atualizaPessoa) {
        this.atualizaPessoa = atualizaPessoa;
    }

    public String getConsumidorCpfCnpj() {
        return consumidorCpfCnpj;
    }

    public void setConsumidorCpfCnpj(String consumidorCpfCnpj) {
        this.consumidorCpfCnpj = consumidorCpfCnpj;
    }

    public Integer getIdOperadorConsultor() {
        return idOperadorConsultor;
    }

    public void setIdOperadorConsultor(Integer idOperadorConsultor) {
        this.idOperadorConsultor = idOperadorConsultor;
    }

    public void setCobrancasClientes(Set<CobrancasClientes> cobrancasClientes) {
        this.cobrancasClientes = cobrancasClientes;
    }

    public String getIntegraProtheus() {
        return integraProtheus;
    }

    public void setIntegraProtheus(String integraProtheus) {
        this.integraProtheus = integraProtheus;
    }

    public String getContProtheus() {
        return contProtheus;
    }

    public void setContProtheus(String contProtheus) {
        this.contProtheus = contProtheus;
    }

    public Integer getIdServico() {
        return idServico;
    }

    public void setIdServico(Integer idServico) {
        this.idServico = idServico;
    }

    @JsonIgnore
    @OneToMany(mappedBy = "interfaceCobrancas",
            fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @Where(clause = "CBC_SITUACAO <> 'Deletado' AND CBC_NUMPARCELA > 0")
    public Set<CobrancasClientes> getCobrancasClientes() {
        if (cobrancasClientes == null) {
            cobrancasClientes = new HashSet<>();
        }
        return cobrancasClientes;
    }

    public PessoaCr5 getClienteContrato() {
        return getCobrancasClientes().isEmpty() ? null : getCobrancasClientes().iterator().next().getIdPessoa();
    }
}
