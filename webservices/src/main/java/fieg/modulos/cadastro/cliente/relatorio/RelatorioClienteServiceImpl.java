package fieg.modulos.cadastro.cliente.relatorio;

import fieg.modulos.cadastro.cliente.dto.ConsultaSituacaoClienteFilterDTO;
import fieg.modulos.cadastro.cliente.dto.SituacaoClienteDTO;

import fieg.modulos.entidade.enums.Entidade;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fieg.core.relatorios.AbstractRelatorioService;
import jakarta.enterprise.context.ApplicationScoped;


@ApplicationScoped
public class RelatorioClienteServiceImpl extends AbstractRelatorioService implements RelatorioClienteService{


    @Override
    public byte[] gerarRelatorioSituacaoClientePdf(List<SituacaoClienteDTO> situacaoCliente, Integer idEntidade, String operador, ConsultaSituacaoClienteFilterDTO dto) {
        Entidade entidade = Entidade.getByCodigo(idEntidade);
        String caminho = "relatorios/RelatorioSituacaoCliente.jrxml";

        Map<String, Object> parametros = new HashMap<>();
        parametros.put("OPERADOR", operador);
        parametros.put("UNIDADE", entidade.descDetalhada);
        parametros.put("IMAGEM_LOGO", entidade.urlLogoColorida);

        String filtro = "" ;
        filtro = buscarFiltro(dto) ;
        parametros.put("FILTRO", filtro);

        return gerarPdf(caminho, parametros, situacaoCliente);
    }

    private String buscarFiltro(ConsultaSituacaoClienteFilterDTO dto) {
        String filtro = "" ;
        if (dto.getCpfCnpj() != null) {filtro = "cpfCnpj: " + dto.getCpfCnpj(); }
        if (dto.getDtInicioCobranca() != null) {filtro = filtro + "  Datas: " + dto.getDtInicioCobranca()  + " - " + dto.getDtFimCobranca() ; }
        if (dto.getIdSistema() != null) {filtro = filtro + "  Sistema: " + dto.getIdSistema(); }
        if (dto.getIdUnidade() != null) {filtro = filtro + "  Unidade: " + dto.getIdUnidade(); }
        if (dto.getContrato() != null) {filtro = filtro + "  Contrato: " + dto.getContrato(); }
        if (dto.getParcela() != null) {filtro = filtro + "  Parcela: " + dto.getParcela(); }
        if (dto.getNossoNumero() != null && dto.getNossoNumero() != "") {filtro = filtro + "  Nosso Número: " + dto.getNossoNumero(); }
        if (dto.getProduto() != null && dto.getProduto() != "") {filtro = filtro + "  Produto: " + dto.getProduto(); }
        if (dto.getStatus().get(0) != "") {filtro = filtro + "  Status: " + dto.getStatus().get(0); }
        return filtro;
    }

    @Override
    public byte[] gerarRelatorioSituacaoClienteXls(List<SituacaoClienteDTO> situacaoCliente, Integer idEntidade, String operador, ConsultaSituacaoClienteFilterDTO dto) {
        Entidade entidade = Entidade.getByCodigo(idEntidade);

        String caminho = "relatorios/RelatorioSituacaoCliente.jrxml";

        Map<String, Object> parametros = new HashMap<>();
        parametros.put("OPERADOR", operador);
        parametros.put("UNIDADE", entidade.descDetalhada);
        parametros.put("IMAGEM_LOGO", entidade.urlLogoColorida);

        String filtro = "" ;
        filtro = buscarFiltro(dto) ;
        parametros.put("FILTRO", filtro);

        return gerarXls(caminho, parametros, situacaoCliente);
    }
}
