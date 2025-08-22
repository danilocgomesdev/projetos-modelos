package fieg.modulos.cr5.model;


import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "CR5_CABECALHO_ARQ_PAGAMENTO")
public class CabecalhoArquivoPagamento extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_CABECALHO_ARQ_PAGAMENTO")
    private Integer id;

    @Column(name = "CVP_CAIXA_POSTAL")
    private String caixaPostal;

    @Column(name = "CVP_DATA_PROCESSAMENTO")
    private Date dataProcessamento;

    @Column(name = "CVP_EMPRESA_ADQUIRENTE")
    private String empresaAdquirente;

    @Column(name = "CVP_ESTABELECIMENTO_MATRIZ")
    private String estabelecimentoMatriz;

    @Column(name = "CVP_NOME_ARQUIVO")
    private String nomeArquivo;

    //03 = Arquivo de retorno de vendas(conciliação)
    //04 = Pagamentos
    @Column(name = "CVP_OPCAO_EXTRATO")
    private String opcaoExtrato;

    @Column(name = "CVP_PERIODO_FINAL")
    private Date periodoFinal;

    @Column(name = "CVP_PERIODO_INICIAL")
    private Date periodoInicial;

    @Column(name = "CVP_SEQUENCIA")
    private String sequencia;

    @Column(name = "CVP_VAN")
    private String van;

    @Column(name = "CVP_VERSAO_LAYOUT")
    private String versaoLayout;

    public  CabecalhoArquivoPagamento(){
    }

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

    @Override
    public String toString() {
        return "CabecalhoArquivoPagamento{" +
                "id=" + id +
                ", caixaPostal='" + caixaPostal + '\'' +
                ", dataProcessamento=" + dataProcessamento +
                ", empresaAdquirente='" + empresaAdquirente + '\'' +
                ", estabelecimentoMatriz='" + estabelecimentoMatriz + '\'' +
                ", nomeArquivo='" + nomeArquivo + '\'' +
                ", opcaoExtrato='" + opcaoExtrato + '\'' +
                ", periodoFinal=" + periodoFinal +
                ", periodoInicial=" + periodoInicial +
                ", sequencia='" + sequencia + '\'' +
                ", van='" + van + '\'' +
                ", versaoLayout='" + versaoLayout + '\'' +
                '}';
    }
}
