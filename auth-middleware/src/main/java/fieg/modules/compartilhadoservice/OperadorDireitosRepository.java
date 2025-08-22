package fieg.modules.compartilhadoservice;

import fieg.modules.compartilhadoservice.dtos.OperadorDireitoDTO;
import fieg.modules.compartilhadoservice.dtos.OperadorDireitoFilterDTO;

import java.util.List;

public interface OperadorDireitosRepository {

    void set(OperadorDireitoFilterDTO filterDTO, List<OperadorDireitoDTO> direitos, Long expiresAt);

    List<OperadorDireitoDTO> get(OperadorDireitoFilterDTO filterDTO);

    boolean exists(OperadorDireitoFilterDTO filterDTO);

    void delete(OperadorDireitoFilterDTO filterDTO);
}
