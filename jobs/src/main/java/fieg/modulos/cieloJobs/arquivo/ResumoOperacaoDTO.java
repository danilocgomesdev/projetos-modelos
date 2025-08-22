package fieg.modulos.cieloJobs.arquivo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResumoOperacaoDTO {

    public String numeroUnicoRO;
    public String estabelecimentoSubmissor;
    public String numeroResumoOperacao;
    public String plano;
    public String meioCaptura;
    public List<DetalheComprovanteVendaDTO> detalhesComprovantesVenda;
    public List<DetalheResumoOperacaoDTO> detalhesResumoOperacao;

    public ResumoOperacaoDTO(DetalheComprovanteVendaDTO detalheComprovanteVendaDTO, String meioCaptura) {
        this.numeroUnicoRO = detalheComprovanteVendaDTO.getNumeroUnicoRO();
        this.estabelecimentoSubmissor = detalheComprovanteVendaDTO.getEstabelecimentoSubmissor();
        this.numeroResumoOperacao = detalheComprovanteVendaDTO.getNumeroResumoOperacao();
        this.plano = detalheComprovanteVendaDTO.getQtdeParcelas();
    }

    public ResumoOperacaoDTO(DetalheResumoOperacaoDTO detalheResumoOperacaoDTO) {
        this.numeroUnicoRO = detalheResumoOperacaoDTO.getNumeroUnicoRO();
        this.estabelecimentoSubmissor = detalheResumoOperacaoDTO.getEstabelecimentoSubmissor();
        this.numeroResumoOperacao = detalheResumoOperacaoDTO.getNumeroResumoOperacao();
        this.plano = detalheResumoOperacaoDTO.getPlano();
        this.meioCaptura = detalheResumoOperacaoDTO.getMeioCaptura();
    }


}
