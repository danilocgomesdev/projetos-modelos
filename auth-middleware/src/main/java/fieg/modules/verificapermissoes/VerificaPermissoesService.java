package fieg.modules.verificapermissoes;

import fieg.modules.compartilhadoservice.dtos.OperadorDireitoDTO;

import java.util.List;

public interface VerificaPermissoesService {

    List<OperadorDireitoDTO> findDireitos(Integer idPessoa, Long expiresAt);

    void limpaCacheDireitos(Integer idPessoa);
}
