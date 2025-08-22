package fieg.modulos.protheus.dto;

import fieg.core.util.UtilData;
import fieg.core.util.UtilValorMonetario;
import fieg.core.util.StringUtils;
import fieg.modulos.cr5.model.InterfaceCobrancas;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class ContratoDTO implements Serializable {

    public Integer contId;
    public Integer sistemaId;
    public Integer operadorId;
    public Date contDtInicioVigencia;
    public Date contDtTerminoVigencia;
    public Date dataInicioCobranca;
    public Date dataFimCobranca;
    public BigDecimal contVlTotalDoContrato;
    public Date contDtCadastro;
    public Integer unidadeId;
    public String objetoContrato;
    public Integer contDiaVencimentoParcelas;
    public Date contDtPrimeiroVencimento;
    public Integer contQtdeDeParcelas;
    public Integer idProduto;
    public Integer ano;
    public ClienteDTO cliente;
    public Boolean isEdicaoSneAutorizada;
    public Integer idInterfaceOrigemCadin;
    public Integer idSistemaOrigem;
    public String consumidorNome;
    public String consumidorCpfCnpj;
    public Integer consumidorId;
    public Date consumidorDtNascimento;
    public String sacadoTipoDocIdent;
    public String statusInterface;
    public String contAdmAntesInstalacao;
    public String contAgenteNome;
    public Double contAgentePerc;
    public BigDecimal contAgenteValor;
    public String contDescComercial;
    public Date contDtSegundoVencimento;
    public Integer contIdOrigem;
    public String contMensagemBoleto;
    public String contPreMatricula = "N";
    public BigDecimal contVlBolsa;
    public BigDecimal contVlDaParcelaAPagar;
    public BigDecimal contVlDescComercial;
    public Double descAteVencimento;
    public Integer rescisaoAntes;
    public Integer rescisaoDurante;
    public String motivoCancelamento;
    public List<DescontoParcelaDTO> descontos;
    public List<ParcelaPredefinidaDTO> parcelasPredefinidas;
    public String observacao5;
    public String numeroParcelas;
    public List<ItemContabilDTO> listaItemContabil;
    public String convenioBancarioCodigo;
    public String nossoNumeroExterno;
    public Integer qtdMaxParcelaCartao;
    public Boolean pagamentoBoleto;
    public Boolean isAtualizaPessoa = false;
    public Boolean vendaRapidaDeIngresso = false;
    public Long idConsultor;
    public List<ParcelaProteusDTO> parcelasProteus;
    public String contProtheus;
    public String cndMedicaoProtheus;
    public String integraProtheus;
    public Boolean cancelamentoAutomatico;
    public ProtheusContratoDTO protheusContrato;
    public Boolean pagamentoRecorrente;
    public Integer recno;
    public  ContratoDTO() {
    }

    public  ContratoDTO(InterfaceCobrancas param, Integer idOperador) {
         this(param, idOperador, false);
     }

     public  ContratoDTO(InterfaceCobrancas param, Integer idOperador, Boolean cancelamentoAutomatico) {
         this.setContId(param.getContId());
         this.setSistemaId(param.getIdSistema());
         this.setStatusInterface(param.getStatusInterface().toString());
         this.setContDtInicioVigencia(param.getContDtInicioVigencia());
         this.setContDtTerminoVigencia(param.getContDtTerminoVigencia());
         this.setDataInicioCobranca(param.getContDtInicioVigenciaCobranca());
         this.setDataFimCobranca(param.getContDtTerminoVigenciaCobranca());
         this.setContVlTotalDoContrato(param.getContVlTotalDoContrato());
         this.setContDtCadastro(new Date());
         this.setUnidadeId(param.getUnidade().getIdUnidade());
         this.setObjetoContrato(param.getObjetoContrato());
         this.setContDiaVencimentoParcelas(param.getContDiaVencimentoParcelas());
         this.setQtdMaxParcelaCartao(null);
         this.setIdProduto(param.getIdProduto());
         this.setOperadorId(idOperador);
         this.setAno(UtilData.getAnoAtual());
         this.setIdInterfaceOrigemCadin(0);
         this.setIdSistemaOrigem(0);
         this.setCliente(new ClienteDTO(param.getClienteContrato()));
         this.setSacadoTipoDocIdent(param.getSacadoTipoDocIdent());
         this.setConsumidorNome(param.getConsumidorNome());
         this.setConsumidorId(param.getConsumidorId());
         this.setContVlDaParcelaAPagar(param.getContVlDaParcelaAPagar());
         this.setContPreMatricula(param.getContPreMatricula());
         this.setContQtdeDeParcelas(param.getContQtdeDeParcelas());
         this.setQtdMaxParcelaCartao(param.getQtdMaxParcelaCartao());
         this.setDescAteVencimento(param.getDescAteVencimento());
         this.setContDtPrimeiroVencimento(param.getContDtPrimeiroVencimento());
         this.setConsumidorCpfCnpj(param.getConsumidorCpfCnpj());
         this.setConsumidorDtNascimento(this.getConsumidorDtNascimento());
         this.setContDtSegundoVencimento(this.getContDtSegundoVencimento());

         this.setIntegraProtheus(param.getIntegraProtheus());

         if(param.getContProtheus() != null) {
             protheusContrato = new ProtheusContratoDTO(param.getContProtheus());
         } else {
             System.out.println();
         }
         if (protheusContrato.getCancelamentoAutomatico() == null) {
             protheusContrato.setCancelamentoAutomatico(cancelamentoAutomatico);
         }
         if(protheusContrato.getContProtheus() == null) {
             protheusContrato.setContProtheus(param.getContProtheus());
         }
     }

    public  Integer getQtdMaxParcelaCartao() {
        if (qtdMaxParcelaCartao != null
                && qtdMaxParcelaCartao == 0) {
            this.qtdMaxParcelaCartao = null;
        }
        return qtdMaxParcelaCartao;
    }

    public  void setQtdMaxParcelaCartao(Integer qtdMaxParcelaCartao) {
        this.qtdMaxParcelaCartao = qtdMaxParcelaCartao;
    }

    public  Boolean getPagamentoBoleto() {
        return pagamentoBoleto;
    }

    public  void setPagamentoBoleto(Boolean pagamentoBoleto) {
        this.pagamentoBoleto = pagamentoBoleto;
    }

    public  Date getContDtInicioVigencia() {
        return contDtInicioVigencia;
    }

    public  void setContDtInicioVigencia(Date contDtInicioVigencia) {
        this.contDtInicioVigencia = contDtInicioVigencia;
    }

    public  Date getContDtTerminoVigencia() {
        return contDtTerminoVigencia;
    }

    public  void setContDtTerminoVigencia(Date contDtTerminoVigencia) {
        this.contDtTerminoVigencia = contDtTerminoVigencia;
    }

    public  BigDecimal getContVlTotalDoContrato() {
        return contVlTotalDoContrato;
    }

    public  void setContVlTotalDoContrato(BigDecimal contVlTotalDoContrato) {
        this.contVlTotalDoContrato = contVlTotalDoContrato;
        if (contVlTotalDoContrato != null) {
            this.contVlTotalDoContrato = UtilValorMonetario.definirPrecisao(contVlTotalDoContrato);
        }
    }

    public  Date getContDtCadastro() {
        return contDtCadastro;
    }

    public  void setContDtCadastro(Date contDtCadastro) {
        this.contDtCadastro = contDtCadastro;
    }

    public  Integer getUnidadeId() {
        return unidadeId;
    }

    public  void setUnidadeId(Integer unidadeId) {
        this.unidadeId = unidadeId;
    }

    public  String getObjetoContrato() {
        return objetoContrato;
    }

    public  void setObjetoContrato(String objetoContrato) {
        this.objetoContrato = objetoContrato;
    }

    public  Integer getContDiaVencimentoParcelas() {
        return contDiaVencimentoParcelas;
    }

    public  void setContDiaVencimentoParcelas(Integer contDiaVencimentoParcelas) {
        this.contDiaVencimentoParcelas = contDiaVencimentoParcelas;
    }

    public  Date getContDtPrimeiroVencimento() {
        return contDtPrimeiroVencimento;
    }

    public  void setContDtPrimeiroVencimento(Date contDtPrimeiroVencimento) {
        this.contDtPrimeiroVencimento = contDtPrimeiroVencimento;
    }

    public  Integer getOperadorId() {
        return operadorId;
    }

    public  void setOperadorId(Integer operadorId) {
        this.operadorId = operadorId;
    }

    public  Integer getContId() {
        return contId;
    }

    public  void setContId(Integer contId) {
        this.contId = contId;
    }

    public  Integer getSistemaId() {
        return sistemaId;
    }

    public  void setSistemaId(Integer sistemaId) {
        this.sistemaId = sistemaId;
    }

    public  Integer getContQtdeDeParcelas() {
        return contQtdeDeParcelas;
    }

    public  void setContQtdeDeParcelas(Integer contQtdeDeParcelas) {
        this.contQtdeDeParcelas = contQtdeDeParcelas;
    }

    public  Integer getIdProduto() {
        return idProduto;
    }

    public  void setIdProduto(Integer idProduto) {
        this.idProduto = idProduto;
    }

    public  Integer getAno() {
        return ano;
    }

    public  void setAno(Integer ano) {
        this.ano = ano;
    }

    public  Integer getConsumidorId() {
        return consumidorId;
    }

    public  void setConsumidorId(Integer consumidorId) {
        this.consumidorId = consumidorId;
    }

    public  String getConsumidorNome() {
        return consumidorNome;
    }

    public  void setConsumidorNome(String consumidorNome) {
        if (StringUtils.isNotBlank(consumidorNome)) {
            if (consumidorNome.length() > 150) {
                consumidorNome = StringUtils.substring(consumidorNome, 0, 150);
            }
        }
        this.consumidorNome = consumidorNome;
    }

    public  Date getConsumidorDtNascimento() {
        return consumidorDtNascimento;
    }

    public  void setConsumidorDtNascimento(Date consumidorDtNascimento) {
        this.consumidorDtNascimento = consumidorDtNascimento;
    }

    public  String getContAdmAntesInstalacao() {
        return contAdmAntesInstalacao;
    }

    public  void setContAdmAntesInstalacao(String contAdmAntesInstalacao) {
        this.contAdmAntesInstalacao = contAdmAntesInstalacao;
    }

    public  String getContAgenteNome() {
        return contAgenteNome;
    }

    public  void setContAgenteNome(String contAgenteNome) {
        this.contAgenteNome = contAgenteNome;
    }

    public  Double getContAgentePerc() {
        return contAgentePerc;
    }

    public  void setContAgentePerc(Double contAgentePerc) {
        this.contAgentePerc = contAgentePerc;
    }

    public  BigDecimal getContAgenteValor() {
        return contAgenteValor;
    }

    public  void setContAgenteValor(BigDecimal contAgenteValor) {
        this.contAgenteValor = contAgenteValor;
    }

    public  String getContDescComercial() {
        return contDescComercial;
    }

    public  void setContDescComercial(String contDescComercial) {
        this.contDescComercial = contDescComercial;
    }

    public  Date getContDtSegundoVencimento() {
        return contDtSegundoVencimento;
    }

    public  void setContDtSegundoVencimento(Date contDtSegundoVencimento) {
        this.contDtSegundoVencimento = contDtSegundoVencimento;
    }

    public  Integer getContIdOrigem() {
        return contIdOrigem;
    }

    public  void setContIdOrigem(Integer contIdOrigem) {
        this.contIdOrigem = contIdOrigem;
    }

    public  String getContMensagemBoleto() {
        return contMensagemBoleto;
    }

    public  void setContMensagemBoleto(String contMensagemBoleto) {
        this.contMensagemBoleto = contMensagemBoleto;
    }

    public  String getContPreMatricula() {
        return contPreMatricula;
    }

    public  void setContPreMatricula(String contPreMatricula) {
        this.contPreMatricula = contPreMatricula;
    }

    public  BigDecimal getContVlBolsa() {
        return contVlBolsa;
    }

    public  void setContVlBolsa(BigDecimal contVlBolsa) {
        this.contVlBolsa = contVlBolsa;
    }

    @Deprecated
    public  BigDecimal getContVlDaParcelaAPagar() {
        return contVlDaParcelaAPagar;
    }

    @Deprecated
    public  void setContVlDaParcelaAPagar(BigDecimal contVlDaParcelaAPagar) {
        this.contVlDaParcelaAPagar = contVlDaParcelaAPagar;
    }

    public  BigDecimal getContVlDescComercial() {
        return contVlDescComercial;
    }

    public  void setContVlDescComercial(BigDecimal contVlDescComercial) {
        this.contVlDescComercial = contVlDescComercial;
    }

    public  Double getDescAteVencimento() {
        return descAteVencimento;
    }

    public  void setDescAteVencimento(Double descAteVencimento) {
        this.descAteVencimento = descAteVencimento;
    }

    public  Integer getRescisaoAntes() {
        return rescisaoAntes;
    }

    public  void setRescisaoAntes(Integer rescisaoAntes) {
        this.rescisaoAntes = rescisaoAntes;
    }

    public  Integer getRescisaoDurante() {
        return rescisaoDurante;
    }

    public  void setRescisaoDurante(Integer rescisaoDurante) {
        this.rescisaoDurante = rescisaoDurante;
    }

    public  String getStatusInterface() {
        return statusInterface;
    }

    public  void setStatusInterface(String statusInterface) {
        this.statusInterface = statusInterface;
    }

    public  String getObservacao5() {
        return observacao5;
    }

    public  void setObservacao5(String observacao5) {
        this.observacao5 = observacao5;
    }

    public  String getNumeroParcelas() {
        return numeroParcelas;
    }

    public  void setNumeroParcelas(String numeroParcelas) {
        this.numeroParcelas = numeroParcelas;
    }

    public  ClienteDTO getCliente() {
        return cliente;
    }

    public  void setCliente(ClienteDTO cliente) {
        this.cliente = cliente;
    }

    public  String getSacadoTipoDocIdent() {
        return sacadoTipoDocIdent;
    }

    public  void setSacadoTipoDocIdent(String sacadoTipoDocIdent) {
        this.sacadoTipoDocIdent = sacadoTipoDocIdent;
    }

    public  List<DescontoParcelaDTO> getDescontos() {
        return descontos;
    }

    public  void setDescontos(List<DescontoParcelaDTO> descontos) {
        this.descontos = descontos;
    }

    public  List<ParcelaPredefinidaDTO> getParcelasPredefinidas() {
        return parcelasPredefinidas;
    }

    public  void setParcelasPredefinidas(List<ParcelaPredefinidaDTO> parcelasPredefinidas) {
        this.parcelasPredefinidas = parcelasPredefinidas;
    }

    public  Date getDataInicioCobranca() {
        return dataInicioCobranca;
    }

    public  void setDataInicioCobranca(Date dataInicioCobranca) {
        this.dataInicioCobranca = dataInicioCobranca;
    }

    public  Date getDataFimCobranca() {
        return dataFimCobranca;
    }

    public  void setDataFimCobranca(Date dataFimCobranca) {
        this.dataFimCobranca = dataFimCobranca;
    }

    public  List<ItemContabilDTO> getListaItemContabil() {
        return listaItemContabil;
    }

    public  void setListaItemContabil(List<ItemContabilDTO> listaItemContabil) {
        this.listaItemContabil = listaItemContabil;
    }

    public  String getConvenioBancarioCodigo() {
        return convenioBancarioCodigo;
    }

    public  void setConvenioBancarioCodigo(String convenioBancarioCodigo) {
        this.convenioBancarioCodigo = convenioBancarioCodigo;
    }

    public  String getMotivoCancelamento() {
        return motivoCancelamento;
    }

    public  void setMotivoCancelamento(String motivoCancelamento) {
        this.motivoCancelamento = motivoCancelamento;
    }

    public  Integer getIdInterfaceOrigemCadin() {
        return idInterfaceOrigemCadin;
    }

    public  void setIdInterfaceOrigemCadin(Integer idInterfaceOrigemCadin) {
        this.idInterfaceOrigemCadin = idInterfaceOrigemCadin;
    }

    public  Boolean getIsEdicaoSneAutorizada() {
        return isEdicaoSneAutorizada;
    }

    public  void setIsEdicaoSneAutorizada(Boolean isEdicaoSneAutorizada) {
        this.isEdicaoSneAutorizada = isEdicaoSneAutorizada;
    }

    public  String getNossoNumeroExterno() {
        return nossoNumeroExterno;
    }

    public  void setNossoNumeroExterno(String nossoNumeroExterno) {
        this.nossoNumeroExterno = nossoNumeroExterno;
    }

    public  Integer getIdSistemaOrigem() {
        return idSistemaOrigem;
    }

    public  void setIdSistemaOrigem(Integer idSistemaOrigem) {
        this.idSistemaOrigem = idSistemaOrigem;
    }

    @Override
    public  String toString() {
        return "ContratoDTO{" + "contId=" + contId + ", sistemaId=" + sistemaId + ", operadorId=" + operadorId + ", contDtInicioVigencia=" + contDtInicioVigencia + ", contDtTerminoVigencia=" + contDtTerminoVigencia + ", dataInicioCobranca=" + dataInicioCobranca + ", dataFimCobranca=" + dataFimCobranca + ", unidadeId=" + unidadeId + ", idProduto=" + idProduto + ", ano=" + ano + ", statusInterface=" + statusInterface + ", descontos=" + descontos + '}';
    }

    public  Boolean getAtualizaPessoa() {
        return isAtualizaPessoa;
    }

    public  void setAtualizaPessoa(Boolean atualizaPessoa) {
        isAtualizaPessoa = atualizaPessoa;
    }

    public  String getConsumidorCpfCnpj() {
        return consumidorCpfCnpj;
    }

    public  void setConsumidorCpfCnpj(String consumidorCpfCnpj) {
        this.consumidorCpfCnpj = consumidorCpfCnpj;
    }

    public  Boolean getVendaRapidaDeIngresso() {
        return vendaRapidaDeIngresso;
    }

    public  void setVendaRapidaDeIngresso(Boolean vendaRapidaDeIngresso) {
        this.vendaRapidaDeIngresso = vendaRapidaDeIngresso;
    }

    public  Long getIdConsultor() {
        return idConsultor;
    }

    public  void setIdConsultor(Long idConsultor) {
        this.idConsultor = idConsultor;
    }

    public  String getContProtheus() {
        return contProtheus;
    }

    public  void setContProtheus(String contProtheus) {
        this.contProtheus = contProtheus;
    }

    public  String getCndMedicaoProtheus() {
        return cndMedicaoProtheus;
    }

    public  void setCndMedicaoProtheus(String cndMedicaoProtheus) {
        this.cndMedicaoProtheus = cndMedicaoProtheus;
    }

    public  String getIntegraProtheus() {
        return integraProtheus;
    }

    public  void setIntegraProtheus(String integraProtheus) {
        this.integraProtheus = integraProtheus;
    }

    public  Boolean getCancelamentoAutomatico() {
        return cancelamentoAutomatico;
    }

    public  void setCancelamentoAutomatico(Boolean cancelamentoAutomatico) {
        this.cancelamentoAutomatico = cancelamentoAutomatico;
    }

    public  List<ParcelaProteusDTO> getParcelasProteus() {
        return parcelasProteus;
    }

    public  void setParcelasProteus(List<ParcelaProteusDTO> parcelasProteus) {
        this.parcelasProteus = parcelasProteus;
    }

    public  ProtheusContratoDTO getProtheusContrato() {
        return protheusContrato;
    }

    public  void setProtheusContrato(ProtheusContratoDTO protheusContrato) {
        this.protheusContrato = protheusContrato;
    }

    public  Integer getRecno() {
        return recno;
    }

    public  void setRecno(Integer recno) {
        this.recno = recno;
    }

    public  Boolean getPagamentoRecorrente() {
        return pagamentoRecorrente;
    }

    public  void setPagamentoRecorrente(Boolean pagamentoRecorrente) {
        this.pagamentoRecorrente = pagamentoRecorrente;
    }
}