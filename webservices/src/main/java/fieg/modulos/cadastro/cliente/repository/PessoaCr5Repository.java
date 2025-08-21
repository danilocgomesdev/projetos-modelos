package fieg.modulos.cadastro.cliente.repository;

import fieg.core.pagination.PageResult;
import fieg.modulos.cadastro.cliente.dto.ConsultaSituacaoClienteFilterDTO;
import fieg.modulos.cadastro.cliente.dto.PessoaCr5FilterDTO;
import fieg.modulos.cadastro.cliente.dto.SituacaoClienteDTO;
import fieg.modulos.cadastro.cliente.model.PessoaCr5;

import java.util.List;
import java.util.Optional;

public interface PessoaCr5Repository {

    Optional<PessoaCr5> getByIdOptional(Integer idPessoa);

    Optional<PessoaCr5> getPessoaCr5ByCpf(String cpfCnpj);

    PageResult<PessoaCr5> getAllPessoaCr5Paginado(
            PessoaCr5FilterDTO dto
    );

    void persistPessoaCr5(PessoaCr5 pessoaCr5);

    void deletePessoaCr5(PessoaCr5 pessoaCr5);

    PageResult<SituacaoClienteDTO> pesquisaSituacaoCliente(ConsultaSituacaoClienteFilterDTO consultaSituacaoClienteFilterDTO) ;

    public List<SituacaoClienteDTO> pesquisaSituacaoClienteLista(ConsultaSituacaoClienteFilterDTO dto);
}
