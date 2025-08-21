package fieg.core.requests;

import fieg.core.exceptions.NegocioException;
import fieg.modulos.operadordireitos.dto.OperadorDireitoDTO;
import jakarta.enterprise.context.RequestScoped;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequestScoped
public class RequestInfoHolder {

    private UUID identificador;

    private Optional<Integer> idOperador = Optional.empty();

    private Optional<Integer> idPessoa = Optional.empty();

    private List<OperadorDireitoDTO> direitos;

    public UUID getIdentificador() {
        return identificador;
    }

    public void setIdentificador(UUID identificador) {
        this.identificador = identificador;
    }

    public Optional<Integer> getIdOperador() {
        return idOperador;
    }

    public void setIdOperador(Integer idOperador) {
        this.idOperador = Optional.of(idOperador);
    }

    public List<OperadorDireitoDTO> getDireitos() {
        return direitos;
    }

    public void setDireitos(List<OperadorDireitoDTO> direitos) {
        this.direitos = direitos;
    }

    public Optional<Integer> getIdPessoa() {
        return idPessoa;
    }

    public void setIdPessoa(Integer idPessoa) {
        this.idPessoa = Optional.of(idPessoa);
    }

    public int getIdOperadorOu(Integer idOperadorDefault) {
        return idOperador.orElseGet(() -> {
            if (idOperadorDefault == null) {
                throw new NegocioException("idOperador é obrigatório para essa request");
            }

            return idOperadorDefault;
        });
    }

    public int getIdPessoaOu(Integer idPessoaDefault) {
        return idPessoa.orElseGet(() -> {
            if (idPessoaDefault == null) {
                throw new NegocioException("idPessoa é obrigatório para essa request");
            }

            return idPessoaDefault;
        });
    }

}
