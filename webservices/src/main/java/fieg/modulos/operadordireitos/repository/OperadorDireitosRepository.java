package fieg.modulos.operadordireitos.repository;

import fieg.core.enums.Sistemas;
import fieg.modulos.operadordireitos.dto.OperadorDireitoDTO;
import fieg.modulos.operadordireitos.dto.OperadorDireitoFilterDTO;

import java.util.List;

public interface OperadorDireitosRepository {
    void set(OperadorDireitoFilterDTO filterDTO, List<OperadorDireitoDTO> direitos, Long expiresAt);

    List<OperadorDireitoDTO> get(OperadorDireitoFilterDTO filterDTO);

    boolean exists(OperadorDireitoFilterDTO filterDTO);

    default List<OperadorDireitoDTO> getCr5(Integer operadorId) {
        return this.get(new OperadorDireitoFilterDTO(operadorId, Sistemas.CR5.idSistema));
    }
}
