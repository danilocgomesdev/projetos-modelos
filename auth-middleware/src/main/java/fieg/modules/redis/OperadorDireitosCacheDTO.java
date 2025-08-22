package fieg.modules.redis;

import fieg.modules.compartilhadoservice.dtos.OperadorDireitoDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OperadorDireitosCacheDTO {

    List<OperadorDireitoDTO> direitos;
}
