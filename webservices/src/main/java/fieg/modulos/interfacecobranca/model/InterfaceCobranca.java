package fieg.modulos.interfacecobranca.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import fieg.core.enums.SimNaoConverter;
import fieg.modulos.cadastro.cliente.model.PessoaCr5;
import fieg.modulos.cobrancacliente.model.CobrancaCliente;
import fieg.modulos.interfacecobranca.enums.*;
import fieg.modulos.protheuscontrato.model.ProtheusContrato;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Where;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "CR5_INTERFACE_COBRANCAS")
public class InterfaceCobranca {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_INTERFACE")
    private Integer id;

    @Column(name = "CONT_ID", nullable = false)
    private Integer contId;

    @Column(name = "CONT_DT_INICIO_VIGENCIA", nullable = false)
    private LocalDateTime dataInicioVigencia;

    @Column(name = "CONT_DT_TERMINO_VIGENCIA", nullable = false)
    private LocalDateTime dataTerminoVigencia;

    @Column(name = "CONT_QTDE_DE_PARCELAS")
    private Integer quantidadeDeParcelas;

    @Column(name = "CONT_DIA_VENCIMENTO_PARCELAS")
    private Integer diaVencimentoParcelas;

    @Column(name = "CONT_DT_PRIMEIRO_VENCIMENTO")
    private LocalDateTime dataPrimeiroVencimento;

    @Column(name = "CONT_VL_TOTAL_DO_CONTRATO", nullable = false)
    private BigDecimal valorTotal;

    @Column(name = "CONT_VL_DA_PARCELA_A_PAGAR")
    private BigDecimal valorParcelas;

    @Column(name = "CONT_VL_BOLSA")
    private BigDecimal valorBolsa;

    @Column(name = "CONT_VL_DESC_COMERCIAL")
    private BigDecimal valorDescontoComercial;

    @Column(name = "CONT_DESC_COMERCIAL", length = 100)
    @Size(max = 100, message = "Tamanho do Campo: descricaoDescontoComercial")
    private String descricaoDescontoComercial;

    @Column(name = "CONT_AGENTE_NOME", length = 80)
    @Size(max = 80, message = "Tamanho do Campo: contAgenteNome")
    private String nomeAgente;

    @Column(name = "CONT_AGENTE_PERC")
    private Float porcentagemAgente;

    @Column(name = "CONT_AGENTE_VALOR")
    private BigDecimal valorAgente;

    @Column(name = "CONT_DT_CADASTRO", nullable = false)
    @CreationTimestamp
    private LocalDateTime dataCadastro;

    @Column(name = "ID_UNIDADE_CONTRATO", nullable = false)
    private Integer idUnidadeContrato;

    @Column(name = "ID_PRODUTO")
    private Integer idProduto;

    @Column(name = "ANO")
    private Integer ano;

    @Column(name = "OBJETO_CONTRATO", length = 300)
    @Size(max = 300, message = "Tamanho do Campo: objetoContrato")
    private String objetoContrato;

    @Column(name = "SACADO_ID", nullable = false)
    private Integer sacodoId;

    @Column(name = "SACADO_NOME", length = 150)
    @Size(max = 150, message = "Tamanho do Campo: sacadoNome")
    private String sacadoNome;

    @Column(name = "SACADO_TIPO_DOC_IDENT", columnDefinition = "char(3)")
    @Convert(converter = TipoDocumentoIdentidadeConverter.class)
    private TipoDocumentoIdentidade sacadoTipoDocumento;

    @Column(name = "SACADO_NUM_DOC_IDENT", length = 20)
    @Size(max = 20, message = "Tamanho do Campo: sacadoNumeroDocumentoIdentidade")
    private String sacadoNumeroDocumentoIdentidade;

    @Column(name = "SACADO_CPF_CNPJ", length = 20)
    @Size(max = 20, message = "Tamanho do Campo: sacadoCpfCnpj")
    private String sacadoCpfCnpj;

    @Column(name = "SACADO_DT_NASCIMENTO")
    private LocalDateTime sacadoDataNascimento;

    @Column(name = "SACADO_LOGRADOURO", nullable = false, length = 150)
    @Size(max = 150, message = "Tamanho do Campo: sacadoLogradouro")
    private String sacadoLogradouro;

    @Column(name = "SACADO_COMPLEMENTO", length = 150)
    @Size(max = 150, message = "Tamanho do Campo: sacadoComplemento")
    private String sacadoComplemento;

    @Column(name = "SACADO_BAIRRO", length = 80)
    @Size(max = 80, message = "Tamanho do Campo: sacadoComplemento")
    private String sacadoBairro;

    @Column(name = "SACADO_CIDADE", length = 80)
    @Size(max = 80, message = "Tamanho do Campo: sacadoCidade")
    private String sacadoCidade;

    // TODO mapear Enum de estados
    @Column(name = "SACADO_ESTADO", columnDefinition = "char(2)")
    @Size(max = 2, message = "Tamanho do Campo: sacadoEstado")
    private String sacadoEstado;

    @Column(name = "SACADO_CEP", columnDefinition = "char(8)")
    @Size(max = 8, message = "Tamanho do Campo: sacadoCep")
    private String sacadoCep;

    @Column(name = "SACADO_FONE", length = 15)
    @Size(max = 15, message = "Tamanho do Campo: sacadoFone")
    private String sacadoFone;

    @Column(name = "SACADO_FONE2", length = 15)
    @Size(max = 15, message = "Tamanho do Campo: sacadoFone2")
    private String sacadoFone2;

    @Column(name = "CONSUMIDOR_ID")
    private Integer consumidorId;

    @Column(name = "CONSUMIDOR_NOME", length = 150)
    @Size(max = 150, message = "Tamanho do Campo: consumidorNome")
    private String consumidorNome;

    @Column(name = "ID_SISTEMA", nullable = false)
    private Integer idSistema;

    @Column(name = "STATUS_INTERFACE", length = 30, nullable = false)
    @Convert(converter = StatusInterfaceConverter.class)
    private StatusInterface statusInterface;

    @Column(name = "DESC_ATE_VENCIMENTO")
    private Float descontoAteVencimento;

    @Column(name = "CONT_ID_ORIGEM")
    private Integer contIdOrigem;

    @Column(name = "MOTIVO_CANCELAMENTO", length = 600)
    @Size(max = 600, message = "Tamanho do Campo: motivoCancelamento")
    private String motivoCancelamento;

    @Column(name = "CONT_DT_CANCELAMENTO")
    private LocalDateTime dataCancelamento;

    @Column(name = "CONT_PRE_MATRICULA")
    @Convert(converter = SimNaoConverter.class)
    private Boolean contPreMatricula = false;

    @Column(name = "ID_OPERADOR_INCLUSAO", nullable = false)
    private Integer idOperadorInclusao;

    @Column(name = "DATA_INCLUSAO", nullable = false)
    @CreationTimestamp
    private LocalDateTime dataInclusao;

    @Column(name = "DATA_ALTERACAO")
    private LocalDateTime dataAlteracao;

    @Column(name = "ID_OPERADOR_ALTERACAO")
    private Integer idOperadorAlteracao;

    @Column(name = "CONT_DT_SEGUNDO_VENCIMENTO")
    private LocalDateTime dataSegundoVencimento;

    @Column(name = "CONSUMIDOR_DT_NASCIMENTO")
    private LocalDateTime consumidorDataNascimento;

    @Column(name = "CONT_DT_INICIO_VIGENCIA_COBRANCA")
     private LocalDateTime dataInicioVigenciaCobranca;

    @Column(name = "CONT_DT_TERMINO_VIGENCIA_COBRANCA")
    private LocalDateTime dataTerminoVigenciaCobranca;

    @Column(name = "CONT_IS_EDICAO_SNE_AUTORIZADA")
    private Integer isEdicaoSneAutorizada; // Bomba

    @Column(name = "QTD_MAX_PARCELA_CARTAO")
    private Integer quantidadeMaxParcelasCartao;

    @Column(name = "PAGAMENTO_BOLETO")
    private Boolean pagamentoBoleto = true;

    @Column(name = "DESC_ATE_VENCIMENTO_ORIGINAL")
    private Float descontoAteVencimentoOriginal;

    @Column(name = "SACADO_NUMERO", length = 10)
    @Size(max = 10, message = "Tamanho do Campo: sacadoNumero")
    private String sacadoNumero;

    @Column(name = "SACADO_EMAIL", length = 250)
    @Size(max = 250, message = "Tamanho do Campo: sacadoEmail")
    private String sacadoEmail;

    @Column(name = "SACADO_CEL_FONE2", length = 15)
    @Size(max = 15, message = "Tamanho do Campo: sacadoCelular2")
    private String sacadoCelular2;

    @Column(name = "SACADO_CEL_FONE", length = 15)
    @Size(max = 15, message = "Tamanho do Campo: sacadoCelular")
    private String sacadoCelular;

    @Column(name = "ATUALIZA_PESSOA")
    private Boolean atualizaPessoa = true;

    @Column(name = "CONSUMIDOR_CPF_CNPJ", length = 20)
    @Size(max = 20, message = "Tamanho do Campo: consumidorCpfCnpj")

    private String consumidorCpfCnpj;

    @Column(name = "ID_OPERADOR_CONSULTOR")
    private Integer idOperadorConsultor;

    @Column(name = "INTEGRA_PROTHEUS")
    @Convert(converter = IntegraProtheusConverter.class)
    private IntegraProtheus integraProtheus = IntegraProtheus.INTEGRACAO_FINANCEIRA;

    @Column(name = "PAGAMENTO_RECORRENTE")
    private Boolean pagamentoRecorrente = false;

    @Column(name = "ID_SERVICO")
    private Integer idServico;

    @Column(name = "PORCENTAGEM_COMISSAO")
    private Integer porcentagemComissao;

    @OneToMany(mappedBy = "interfaceCobranca",
            fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @Where(clause = "CBC_SITUACAO <> 'Deletado' AND CBC_NUMPARCELA > 0")
    private Set<CobrancaCliente> cobrancasCliente;

    @OneToOne(mappedBy = "interfaceCobranca", fetch = FetchType.LAZY)
    private ProtheusContrato protheusContrato;

    @Transient
    public LocalDateTime dataVencimentoAgrupamento;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getContId() {
        return contId;
    }

    public void setContId(Integer contId) {
        this.contId = contId;
    }

    public LocalDateTime getDataInicioVigencia() {
        return dataInicioVigencia;
    }

    public void setDataInicioVigencia(LocalDateTime dataInicioVigencia) {
        this.dataInicioVigencia = dataInicioVigencia;
    }

    public LocalDateTime getDataTerminoVigencia() {
        return dataTerminoVigencia;
    }

    public void setDataTerminoVigencia(LocalDateTime dataTerminoVigencia) {
        this.dataTerminoVigencia = dataTerminoVigencia;
    }

    public Integer getQuantidadeDeParcelas() {
        return quantidadeDeParcelas;
    }

    public void setQuantidadeDeParcelas(Integer quantidadeDeParcelas) {
        this.quantidadeDeParcelas = quantidadeDeParcelas;
    }

    public Integer getDiaVencimentoParcelas() {
        return diaVencimentoParcelas;
    }

    public void setDiaVencimentoParcelas(Integer diaVencimentoParcelas) {
        this.diaVencimentoParcelas = diaVencimentoParcelas;
    }

    public LocalDateTime getDataPrimeiroVencimento() {
        return dataPrimeiroVencimento;
    }

    public void setDataPrimeiroVencimento(LocalDateTime dataPrimeiroVencimento) {
        this.dataPrimeiroVencimento = dataPrimeiroVencimento;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    public BigDecimal getValorParcelas() {
        return valorParcelas;
    }

    public void setValorParcelas(BigDecimal valorParcelas) {
        this.valorParcelas = valorParcelas;
    }

    public BigDecimal getValorBolsa() {
        return valorBolsa;
    }

    public void setValorBolsa(BigDecimal valorBolsa) {
        this.valorBolsa = valorBolsa;
    }

    public BigDecimal getValorDescontoComercial() {
        return valorDescontoComercial;
    }

    public void setValorDescontoComercial(BigDecimal valorDescontoComercial) {
        this.valorDescontoComercial = valorDescontoComercial;
    }

    public String getDescricaoDescontoComercial() {
        return descricaoDescontoComercial;
    }

    public void setDescricaoDescontoComercial(String descricaoDescontoComercial) {
        this.descricaoDescontoComercial = descricaoDescontoComercial;
    }

    public String getNomeAgente() {
        return nomeAgente;
    }

    public void setNomeAgente(String nomeAgente) {
        this.nomeAgente = nomeAgente;
    }

    public Float getPorcentagemAgente() {
        return porcentagemAgente;
    }

    public void setPorcentagemAgente(Float porcentagemAgente) {
        this.porcentagemAgente = porcentagemAgente;
    }

    public BigDecimal getValorAgente() {
        return valorAgente;
    }

    public void setValorAgente(BigDecimal valorAgente) {
        this.valorAgente = valorAgente;
    }

    public LocalDateTime getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(LocalDateTime dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public Integer getIdUnidadeContrato() {
        return idUnidadeContrato;
    }

    public void setIdUnidadeContrato(Integer idUnidadeContrato) {
        this.idUnidadeContrato = idUnidadeContrato;
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

    public Integer getSacodoId() {
        return sacodoId;
    }

    public void setSacodoId(Integer sacodoId) {
        this.sacodoId = sacodoId;
    }

    public String getSacadoNome() {
        return sacadoNome;
    }

    public void setSacadoNome(String sacadoNome) {
        this.sacadoNome = sacadoNome;
    }

    public TipoDocumentoIdentidade getSacadoTipoDocumento() {
        return sacadoTipoDocumento;
    }

    public void setSacadoTipoDocumento(TipoDocumentoIdentidade sacadoTipoDocumento) {
        this.sacadoTipoDocumento = sacadoTipoDocumento;
    }

    public String getSacadoNumeroDocumentoIdentidade() {
        return sacadoNumeroDocumentoIdentidade;
    }

    public void setSacadoNumeroDocumentoIdentidade(String sacadoNumeroDocumentoIdentidade) {
        this.sacadoNumeroDocumentoIdentidade = sacadoNumeroDocumentoIdentidade;
    }

    public String getSacadoCpfCnpj() {
        return sacadoCpfCnpj;
    }

    public void setSacadoCpfCnpj(String sacadoCpfCnpj) {
        this.sacadoCpfCnpj = sacadoCpfCnpj;
    }

    public LocalDateTime getSacadoDataNascimento() {
        return sacadoDataNascimento;
    }

    public void setSacadoDataNascimento(LocalDateTime sacadoDataNascimento) {
        this.sacadoDataNascimento = sacadoDataNascimento;
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

    public Integer getIdSistema() {
        return idSistema;
    }

    public void setIdSistema(Integer idSistema) {
        this.idSistema = idSistema;
    }

    public StatusInterface getStatusInterface() {
        return statusInterface;
    }

    public void setStatusInterface(StatusInterface statusInterface) {
        this.statusInterface = statusInterface;
    }

    public Float getDescontoAteVencimento() {
        return descontoAteVencimento;
    }

    public void setDescontoAteVencimento(Float descontoAteVencimento) {
        this.descontoAteVencimento = descontoAteVencimento;
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

    public LocalDateTime getDataCancelamento() {
        return dataCancelamento;
    }

    public void setDataCancelamento(LocalDateTime dataCancelamento) {
        this.dataCancelamento = dataCancelamento;
    }

    public Boolean getContPreMatricula() {
        return contPreMatricula;
    }

    public void setContPreMatricula(Boolean contPreMatricula) {
        this.contPreMatricula = contPreMatricula;
    }

    public Integer getIdOperadorInclusao() {
        return idOperadorInclusao;
    }

    public void setIdOperadorInclusao(Integer idOperadorInclusao) {
        this.idOperadorInclusao = idOperadorInclusao;
    }

    public LocalDateTime getDataInclusao() {
        return dataInclusao;
    }

    public void setDataInclusao(LocalDateTime dataInclusao) {
        this.dataInclusao = dataInclusao;
    }

    public LocalDateTime getDataAlteracao() {
        return dataAlteracao;
    }

    public void setDataAlteracao(LocalDateTime dataAlteracao) {
        this.dataAlteracao = dataAlteracao;
    }

    public Integer getIdOperadorAlteracao() {
        return idOperadorAlteracao;
    }

    public void setIdOperadorAlteracao(Integer idOperadorAlteracao) {
        this.idOperadorAlteracao = idOperadorAlteracao;
    }

    public LocalDateTime getDataSegundoVencimento() {
        return dataSegundoVencimento;
    }

    public void setDataSegundoVencimento(LocalDateTime dataSegundoVencimento) {
        this.dataSegundoVencimento = dataSegundoVencimento;
    }

    public LocalDateTime getConsumidorDataNascimento() {
        return consumidorDataNascimento;
    }

    public void setConsumidorDataNascimento(LocalDateTime consumidorDataNascimento) {
        this.consumidorDataNascimento = consumidorDataNascimento;
    }

    public LocalDateTime getDataInicioVigenciaCobranca() {
        return dataInicioVigenciaCobranca;
    }

    public void setDataInicioVigenciaCobranca(LocalDateTime dataInicioVigenciaCobranca) {
        this.dataInicioVigenciaCobranca = dataInicioVigenciaCobranca;
    }

    public LocalDateTime getDataTerminoVigenciaCobranca() {
        return dataTerminoVigenciaCobranca;
    }

    public void setDataTerminoVigenciaCobranca(LocalDateTime dataTerminoVigenciaCobranca) {
        this.dataTerminoVigenciaCobranca = dataTerminoVigenciaCobranca;
    }

    public Integer getIsEdicaoSneAutorizada() {
        return isEdicaoSneAutorizada;
    }

    public void setIsEdicaoSneAutorizada(Integer isEdicaoSneAutorizada) {
        this.isEdicaoSneAutorizada = isEdicaoSneAutorizada;
    }

    public Integer getQuantidadeMaxParcelasCartao() {
        return quantidadeMaxParcelasCartao;
    }

    public void setQuantidadeMaxParcelasCartao(Integer quantidadeMaxParcelasCartao) {
        this.quantidadeMaxParcelasCartao = quantidadeMaxParcelasCartao;
    }

    public Boolean getPagamentoBoleto() {
        return pagamentoBoleto;
    }

    public void setPagamentoBoleto(Boolean pagamentoBoleto) {
        this.pagamentoBoleto = pagamentoBoleto;
    }

    public Float getDescontoAteVencimentoOriginal() {
        return descontoAteVencimentoOriginal;
    }

    public void setDescontoAteVencimentoOriginal(Float descontoAteVencimentoOriginal) {
        this.descontoAteVencimentoOriginal = descontoAteVencimentoOriginal;
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

    public String getSacadoCelular2() {
        return sacadoCelular2;
    }

    public void setSacadoCelular2(String sacadoCelular2) {
        this.sacadoCelular2 = sacadoCelular2;
    }

    public String getSacadoCelular() {
        return sacadoCelular;
    }

    public void setSacadoCelular(String sacadoCelular) {
        this.sacadoCelular = sacadoCelular;
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

    public IntegraProtheus getIntegraProtheus() {
        return integraProtheus;
    }

    public void setIntegraProtheus(IntegraProtheus integraProtheus) {
        this.integraProtheus = integraProtheus;
    }

    public Boolean getPagamentoRecorrente() {
        return pagamentoRecorrente;
    }

    public void setPagamentoRecorrente(Boolean pagamentoRecorrente) {
        this.pagamentoRecorrente = pagamentoRecorrente;
    }

    public Integer getIdServico() {
        return idServico;
    }

    public void setIdServico(Integer idServico) {
        this.idServico = idServico;
    }

    public Integer getPorcentagemComissao() {
        return porcentagemComissao;
    }

    public void setPorcentagemComissao(Integer porcentagemComissao) {
        this.porcentagemComissao = porcentagemComissao;
    }

    public Set<CobrancaCliente> getCobrancasCliente() {
        if (cobrancasCliente == null) {
            cobrancasCliente = new HashSet<>();
        }
        return cobrancasCliente;
    }

    public void setCobrancasCliente(Set<CobrancaCliente> cobrancasCliente) {
        this.cobrancasCliente = cobrancasCliente;
    }

    public ProtheusContrato getProtheusContrato() {
        return protheusContrato;
    }

    public void setProtheusContrato(ProtheusContrato protheusContrato) {
        this.protheusContrato = protheusContrato;
    }

    public LocalDateTime getDataVencimentoAgrupamento() {
        return dataVencimentoAgrupamento;
    }

    public void setDataVencimentoAgrupamento(LocalDateTime dataVencimentoAgrupamento) {
        this.dataVencimentoAgrupamento = dataVencimentoAgrupamento;
    }

    @Transient
    public Integer getContIdReal() {
        if (protheusContrato != null) {
            return protheusContrato.getContId();
        }
        return contId;
    }

    @Transient
    public String getContratoProtheusSafe() {
        if (protheusContrato != null) {
            return protheusContrato.getContratoProtheus();
        }
        return null;
    }

    @Transient
    public Integer getQuantidadeRealDeParcelas() {
        // TODO atualmente, quando é contrato Protheus, não sabemos quantas parcelas o contrato tem de verdade. Podemos melhorar isso
        if (protheusContrato != null) {
            return 1;
        }

        return quantidadeDeParcelas;
    }

    @Transient
    public PessoaCr5 getClienteContrato() {
        return getCobrancasCliente().isEmpty() ? null : getCobrancasCliente().iterator().next().getPessoa();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InterfaceCobranca i = (InterfaceCobranca) o;
        return Objects.equals(id, i.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
