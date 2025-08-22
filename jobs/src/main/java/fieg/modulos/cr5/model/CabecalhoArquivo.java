
package fieg.modulos.cr5.model;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "CR5_HEADER_ARQUIVO_CIELO")
public class CabecalhoArquivo extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_HEADER_ARQUIVO_CIELO")
    private Integer id;

    @Column(name = "HAC_CAIXA_POSTAL")
    private String caixaPostal;

    @Column(name = "HAC_DATA_PROCESSAMENTO")
    private Date dataProcessamento;

    @Column(name = "HAC_EMPRESA_ADQUIRENTE")
    private String empresaAdquirente;

    @Column(name = "HAC_ESTEBELECIMENTO_MATRIZ")
    private String estabelecimentoMatriz;
    @Column(name = "HAC_NOME_ARQUIVO")
    private String nomeArquivo;

    @Column(name = "HAC_OPCAO_EXTRATO")
    private String opcaoExtrato;

    @Column(name = "HAC_PERIODO_FINAL")
    private Date periodoFinal;

    @Column(name = "HAC_PERIODO_INICIAL")
    private Date periodoInicial;

    @Column(name = "HAC_SEQUENCIA")
    private String sequencia;

    @Column(name = "HAC_VAN")
    private String van;

    @Column(name = "HAC_VERSAO_LAYOUT")
    private String versaoLayout;

    public CabecalhoArquivo() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCaixaPostal() {
        return caixaPostal;
    }

    public void setCaixaPostal(String caixaPostal) {
        this.caixaPostal = caixaPostal;
    }

    public Date getDataProcessamento() {
        return dataProcessamento;
    }

    public void setDataProcessamento(Date dataProcessamento) {
        this.dataProcessamento = dataProcessamento;
    }

    public String getEmpresaAdquirente() {
        return empresaAdquirente;
    }

    public void setEmpresaAdquirente(String empresaAdquirente) {
        this.empresaAdquirente = empresaAdquirente;
    }

    public String getEstabelecimentoMatriz() {
        return estabelecimentoMatriz;
    }

    public void setEstabelecimentoMatriz(String estabelecimentoMatriz) {
        this.estabelecimentoMatriz = estabelecimentoMatriz;
    }

    public String getNomeArquivo() {
        return nomeArquivo;
    }

    public void setNomeArquivo(String nomeArquivo) {
        this.nomeArquivo = nomeArquivo;
    }

    public String getOpcaoExtrato() {
        return opcaoExtrato;
    }

    public void setOpcaoExtrato(String opcaoExtrato) {
        this.opcaoExtrato = opcaoExtrato;
    }

    public Date getPeriodoFinal() {
        return periodoFinal;
    }

    public void setPeriodoFinal(Date periodoFinal) {
        this.periodoFinal = periodoFinal;
    }

    public Date getPeriodoInicial() {
        return periodoInicial;
    }

    public void setPeriodoInicial(Date periodoInicial) {
        this.periodoInicial = periodoInicial;
    }

    public String getSequencia() {
        return sequencia;
    }

    public void setSequencia(String sequencia) {
        this.sequencia = sequencia;
    }

    public String getVan() {
        return van;
    }

    public void setVan(String van) {
        this.van = van;
    }

    public String getVersaoLayout() {
        return versaoLayout;
    }

    public void setVersaoLayout(String versaoLayout) {
        this.versaoLayout = versaoLayout;
    }
}
