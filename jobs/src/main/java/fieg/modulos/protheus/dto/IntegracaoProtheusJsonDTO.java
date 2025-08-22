package fieg.modulos.protheus.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Data
@NoArgsConstructor
@Builder
public class IntegracaoProtheusJsonDTO implements Serializable {

    public String titulos;

    @JsonProperty("FILIAL")
    public String filial;

    @JsonProperty(value = "compartilhar", required = false)
    public List<String> compartilhar;

    @JsonProperty("SA1_TAB")
    public List<List<Object>> sa1Tab;

    @JsonProperty("CN9_TAB")
    public List<List<Object>> cn9Tab;

    @JsonProperty("CNN_TAB")
    public List<List<List<Object>>> cnnTab;

    @JsonProperty("CNC_TAB")
    public List<List<Object>> cncTab;

    @JsonProperty("CNA_TAB")
    public List<List<Object>> cnaTab;

    @JsonProperty("CNB_TAB")
    public List<List<List<Object>>> cnbTab;

    @JsonProperty("financeiro")
    public List<List<Object>> financeiro;

    @JsonProperty("Z11_TAB")
    public List<List<Object>> z11;

    @JsonProperty("parcela")
    public List<ParcelasDTO> parcela;

    @JsonProperty("execucao")
    public List<List<Object>> execucao;

    IntegracaoProtheusJsonDTO(String titulos, String filial, List<String> compartilhar,
                              List<List<Object>> sa1Tab, List<List<Object>> cn9Tab, List<List<List<Object>>> cnnTab,
                              List<List<Object>> cncTab, List<List<Object>> cnaTab, List<List<List<Object>>> cnbTab,
                              List<List<Object>> financeiro, List<List<Object>> z11, List<ParcelasDTO> parcela,
                              List<List<Object>> execucao) {
        this.titulos = titulos;
        this.filial = filial;
        this.compartilhar = compartilhar;
        this.sa1Tab = sa1Tab;
        this.cn9Tab = cn9Tab;
        this.cnnTab = cnnTab;
        this.cncTab = cncTab;
        this.cnaTab = cnaTab;
        this.cnbTab = cnbTab;
        this.financeiro = financeiro;
        this.z11 = z11;
        this.parcela = parcela;
        this.execucao = execucao;
    }

    public static InclusaoContratoProtheusJsonDTOBuilder builder() {
        return new InclusaoContratoProtheusJsonDTOBuilder();
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IntegracaoProtheusJsonDTO that = (IntegracaoProtheusJsonDTO) o;
        return titulos.equals(that.titulos) && filial.equals(that.filial) && compartilhar.equals(that.compartilhar) && sa1Tab.equals(that.sa1Tab) && cn9Tab.equals(that.cn9Tab) && cnnTab.equals(that.cnnTab) && cncTab.equals(that.cncTab) && cnaTab.equals(that.cnaTab) && cnbTab.equals(that.cnbTab) && financeiro.equals(that.financeiro) && z11.equals(that.z11) && parcela.equals(that.parcela) && execucao.equals(that.execucao);
    }

    @Override
    public int hashCode() {
        return Objects.hash(titulos, filial, compartilhar, sa1Tab, cn9Tab, cnnTab, cncTab, cnaTab,
                cnbTab, financeiro, z11, parcela, execucao);
    }

    public String toString() {
        return "InclusaoContratoProtheusJsonDTO(titulos=" + this.getTitulos() +
                ", filial=" + this.getFilial() + ", compartilhar=" + this.getCompartilhar() +
                ", sa1Tab=" + this.getSa1Tab() + ", cn9Tab=" + this.getCn9Tab() + ", cnnTab=" + this.getCnnTab() +
                ", cncTab=" + this.getCncTab() + ", cnaTab=" + this.getCnaTab() + ", cnbTab=" + this.getCnbTab() +
                ", financeiro=" + this.getFinanceiro() + ", z11=" + this.getZ11() + ", parcela=" + this.getParcela() +
                ", execucao=" + this.getExecucao() +")";
    }

    public static class InclusaoContratoProtheusJsonDTOBuilder {
        public String titulos;
        public String filial;
        public List<String> compartilhar;
        public List<List<Object>> sa1Tab;
        public List<List<Object>> cn9Tab;
        public List<List<List<Object>>> cnnTab;
        public List<List<Object>> cncTab;
        public List<List<Object>> cnaTab;
        public List<List<List<Object>>> cnbTab;
        public List<List<Object>> financeiro;
        public List<List<Object>> z11;
        public List<ParcelasDTO> parcela;
        public List<List<Object>> execucao;

        InclusaoContratoProtheusJsonDTOBuilder() {
        }

        public InclusaoContratoProtheusJsonDTOBuilder titulos(String titulos) {
            this.titulos = titulos;
            return this;
        }

        public InclusaoContratoProtheusJsonDTOBuilder filial(String filial) {
            this.filial = filial;
            return this;
        }

        public InclusaoContratoProtheusJsonDTOBuilder compartilhar(List<String> compartilhar) {
            this.compartilhar = compartilhar;
            return this;
        }

        public InclusaoContratoProtheusJsonDTOBuilder sa1Tab(List<List<Object>> sa1Tab) {
            this.sa1Tab = sa1Tab;
            return this;
        }

        public InclusaoContratoProtheusJsonDTOBuilder cn9Tab(List<List<Object>> cn9Tab) {
            this.cn9Tab = cn9Tab;
            return this;
        }

        public InclusaoContratoProtheusJsonDTOBuilder cnnTab(List<List<List<Object>>> cnnTab) {
            this.cnnTab = cnnTab;
            return this;
        }

        public InclusaoContratoProtheusJsonDTOBuilder cncTab(List<List<Object>> cncTab) {
            this.cncTab = cncTab;
            return this;
        }

        public InclusaoContratoProtheusJsonDTOBuilder cnaTab(List<List<Object>> cnaTab) {
            this.cnaTab = cnaTab;
            return this;
        }

        public InclusaoContratoProtheusJsonDTOBuilder cnbTab(List<List<List<Object>>> cnbTab) {
            this.cnbTab = cnbTab;
            return this;
        }

        public InclusaoContratoProtheusJsonDTOBuilder financeiro(List<List<Object>> financeiro) {
            this.financeiro = financeiro;
            return this;
        }

        public InclusaoContratoProtheusJsonDTOBuilder z11(List<List<Object>> z11) {
            this.z11 = z11;
            return this;
        }

        public InclusaoContratoProtheusJsonDTOBuilder parcela(List<ParcelasDTO> parcela) {
            this.parcela = parcela;
            return this;
        }


        public InclusaoContratoProtheusJsonDTOBuilder execucao(List<List<Object>> exec) {
            this.execucao = exec;
            return this;
        }
        public IntegracaoProtheusJsonDTO build() {
            return new IntegracaoProtheusJsonDTO(titulos, filial, compartilhar, sa1Tab, cn9Tab, cnnTab, cncTab, cnaTab, cnbTab, financeiro, z11, parcela, execucao);
        }

        public String toString() {
            return "InclusaoContratoProtheusJsonDTO.InclusaoContratoProtheusJsonDTOBuilder(titulos=" + this.titulos +
                    ", filial=" + this.filial + ", compartilhar=" + this.compartilhar +
                    ", sa1Tab=" + this.sa1Tab + ", cn9Tab=" + this.cn9Tab + ", cnnTab=" + this.cnnTab +
                    ", cncTab=" + this.cncTab + ", cnaTab=" + this.cnaTab + ", cnbTab=" + this.cnbTab +
                    ", financeiro=" + this.financeiro + ", z11=" + this.z11 + ", parcela=" + this.parcela + "execucao=" + this.execucao + ")";
        }
    }
}