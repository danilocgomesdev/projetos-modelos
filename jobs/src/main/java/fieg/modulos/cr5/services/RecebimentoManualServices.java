package fieg.modulos.cr5.services;

import fieg.modulos.cieloJobs.dto.TransacaoCieloDTO;
import fieg.modulos.cr5.model.ItemPagamentoContabil;
import fieg.modulos.cr5.model.TransacaoTEF;
import fieg.modulos.cr5.model.TransacaoTefParc;

import java.util.Collection;
import java.util.List;

public interface RecebimentoManualServices {

     void atualizarConciliacao(Collection<TransacaoTefParc> parcelas, List<ItemPagamentoContabil> itensPagamento, TransacaoCieloDTO dto);

     List<ItemPagamentoContabil> atualizarItemPagamento(TransacaoTEF transacao, TransacaoCieloDTO dto);

}
