package fieg.modulos.cieloJobs.arquivo;

import fieg.modulos.cr5.model.CabecalhoArquivoVenda;

import javax.persistence.Transient;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

// TODO Cielo v15 não mudou nada, é reutilizado
public class CabecalhoDTO extends CabecalhoArquivoVenda {

    private String nomeArquivo;
    private String estabelecimentoMatriz;
    private Date dataProcessamento;
    private Date periodoInicial;
    private Date periodoFinal;
    private String sequencia;
    private String empresaAdquirente;

    //03 = Arquivo de retorno de vendas(conciliação)
    //04 = Pagamentos
    private String opcaoExtrato;

    private String van;
    private String caixaPostal;
    private String versaoLayout;

    public CabecalhoDTO(String linhaHead) {
        this.estabelecimentoMatriz = linhaHead.substring(1, 11);

        this.dataProcessamento = conveterData(linhaHead.substring(11, 19));
        this.periodoInicial = conveterData(linhaHead.substring(19, 27));
        this.periodoFinal = conveterData(linhaHead.substring(27, 35));

        this.sequencia = linhaHead.substring(35, 42);
        this.empresaAdquirente = linhaHead.substring(42, 47);
        this.opcaoExtrato = linhaHead.substring(47, 49);
        this.van = linhaHead.substring(49, 50);
        this.caixaPostal = linhaHead.substring(50, 70);
        this.versaoLayout = linhaHead.substring(70, 73);
    }

    public String getEstabelecimentoMatriz() {
        return estabelecimentoMatriz;
    }

    public void setEstabelecimentoMatriz(String estabelecimentoMatriz) {
        this.estabelecimentoMatriz = estabelecimentoMatriz;
    }

    public Date getDataProcessamento() {
        return dataProcessamento;
    }

    public void setDataProcessamento(Date dataProcessamento) {
        this.dataProcessamento = dataProcessamento;
    }

    public Date getPeriodoInicial() {
        return periodoInicial;
    }

    public void setPeriodoInicial(Date periodoInicial) {
        this.periodoInicial = periodoInicial;
    }

    public Date getPeriodoFinal() {
        return periodoFinal;
    }

    public void setPeriodoFinal(Date periodoFinal) {
        this.periodoFinal = periodoFinal;
    }

    public String getSequencia() {
        return sequencia;
    }

    public void setSequencia(String sequencia) {
        this.sequencia = sequencia;
    }

    public String getEmpresaAdquirente() {
        return empresaAdquirente;
    }

    public void setEmpresaAdquirente(String empresaAdquirente) {
        this.empresaAdquirente = empresaAdquirente;
    }

    public String getOpcaoExtrato() {
        return opcaoExtrato;
    }

    public void setOpcaoExtrato(String opcaoExtrato) {
        this.opcaoExtrato = opcaoExtrato;
    }

    public String getVan() {
        return van;
    }

    public void setVan(String van) {
        this.van = van;
    }

    public String getCaixaPostal() {
        return caixaPostal;
    }

    public void setCaixaPostal(String caixaPostal) {
        this.caixaPostal = caixaPostal;
    }

    public String getVersaoLayout() {
        return versaoLayout;
    }

    public void setVersaoLayout(String versaoLayout) {
        this.versaoLayout = versaoLayout;
    }

    public String getNomeArquivo() {
        return nomeArquivo;
    }

    public void setNomeArquivo(String nomeArquivo) {
        this.nomeArquivo = nomeArquivo;
    }

    @Transient
    public boolean isPagamento() {
        return "04".equals(opcaoExtrato);
    }

    @Transient
    public boolean isVenda() {
        return "03".equals(opcaoExtrato);
    }

    @Override
    public String toString() {
        return "Header{" + "estabelecimentoMatriz=" + estabelecimentoMatriz + ", dataProcessamento=" + dataProcessamento + ", periodoInicial=" + periodoInicial + ", periodoFinal=" + periodoFinal + ", sequencia=" + sequencia + ", empresaAdquirente=" + empresaAdquirente + ", opcaoExtrato=" + opcaoExtrato + ", van=" + van + ", caixaPostal=" + caixaPostal + ", versaoLayout=" + versaoLayout + '}';
    }

    private Date conveterData(String dataConciliacao) {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyyMMdd");
        LocalDate localDate = LocalDate.parse(dataConciliacao, format);
        Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        return date;
    }
}
