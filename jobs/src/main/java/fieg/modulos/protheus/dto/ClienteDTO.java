package fieg.modulos.protheus.dto;


import fieg.modulos.cr5.model.PessoaCr5;
import java.util.Date;

public class ClienteDTO {

    private Integer sacadoId;
    private String sacadoNome;
    private String sacadoCpfCnpj;
    private String sacadoComplemento;
    private String sacadoFone;
    private String sacadoCep;
    private String sacadoLogradouro;
    private String sacadoBairro;
    private String sacadoCidade;
    private String sacadoEstado;

    //**************** CAMPOS OPCIONAIS ****************
    private Date sacadoDtNascimento;
    private String sacadoNumDocIdent;
    private String sacadoFone2;
    private String sacadoCelFone;
    private String sacadoCelFone2;
    private String sacadoNumero;
    private String sacadoEmail;
    private String inscricaoEstadual;
    private boolean emancipado;

    public ClienteDTO() {
    }

    public ClienteDTO(PessoaCr5 cliente) {
        this.setSacadoId(cliente.getId());
        this.setSacadoNome(cliente.getPesDescricao());
        this.setSacadoCpfCnpj(cliente.getCpfCnpj());
        this.setSacadoComplemento(cliente.getPesComplemento());
        this.setSacadoFone(cliente.getPesTelefone());
        this.setSacadoCep(cliente.getPesCep());
        this.setSacadoLogradouro(cliente.getPesLogradouro());
        this.setSacadoBairro(cliente.getPesBairro());
        this.setSacadoCidade(cliente.getPesCidade());
        this.setSacadoEstado(cliente.getPesEstado());
        this.setSacadoDtNascimento(cliente.getPesDtNascimento());
        this.setSacadoNumDocIdent(cliente.getPesRg());
        this.setSacadoFone2(cliente.getPesTelefone2());
        this.setSacadoCelFone(cliente.getPesCelular());
        this.setSacadoCelFone2(cliente.getPesCelular2());
        this.setSacadoNumero(cliente.getPesNumero());
        this.setSacadoEmail(cliente.getPesEmail());
    }

    public Integer getSacadoId() {
        return sacadoId;
    }

    public void setSacadoId(Integer sacadoId) {
        this.sacadoId = sacadoId;
    }

    public String getSacadoNome() {
        return sacadoNome;
    }

    public void setSacadoNome(String sacadoNome) {
        this.sacadoNome = sacadoNome;
    }

    public String getSacadoCpfCnpj() {
        return sacadoCpfCnpj;
    }

    public void setSacadoCpfCnpj(String sacadoCpfCnpj) {
        this.sacadoCpfCnpj = sacadoCpfCnpj;
    }

    public String getSacadoComplemento() {
        return sacadoComplemento;
    }

    public void setSacadoComplemento(String sacadoComplemento) {
        this.sacadoComplemento = sacadoComplemento;
    }

    public String getSacadoFone() {
        return sacadoFone;
    }

    public void setSacadoFone(String sacadoFone) {
        this.sacadoFone = sacadoFone;
    }

    public String getSacadoNumDocIdent() {
        return sacadoNumDocIdent;
    }

    public void setSacadoNumDocIdent(String sacadoNumDocIdent) {
        this.sacadoNumDocIdent = sacadoNumDocIdent;
    }

    public String getSacadoCep() {
        return sacadoCep;
    }

    public void setSacadoCep(String sacadoCep) {
        this.sacadoCep = sacadoCep;
    }

    public String getSacadoLogradouro() {
        return sacadoLogradouro;
    }

    public void setSacadoLogradouro(String sacadoLogradouro) {
        this.sacadoLogradouro = sacadoLogradouro;
    }

    public String getSacadoBairro() {
        return sacadoBairro != null ? sacadoBairro.toUpperCase() : null;
    }

    public void setSacadoBairro(String sacadoBairro) {
        this.sacadoBairro = sacadoBairro != null ? sacadoBairro.toUpperCase() : null;
    }

    public String getSacadoCidade() {
        return sacadoCidade != null ? sacadoCidade.toUpperCase() : null;
    }

    public void setSacadoCidade(String sacadoCidade) {
        this.sacadoCidade = sacadoCidade != null ? sacadoCidade.toUpperCase() : null;
    }

    public String getSacadoEstado() {
        return sacadoEstado != null ? sacadoEstado.toUpperCase() : null;
    }

    public void setSacadoEstado(String sacadoEstado) {
        this.sacadoEstado = sacadoEstado != null ? sacadoEstado.toUpperCase() : null;
    }

    public Date getSacadoDtNascimento() {
        return sacadoDtNascimento;
    }

    public void setSacadoDtNascimento(Date sacadoDtNascimento) {
        this.sacadoDtNascimento = sacadoDtNascimento;
    }

    public String getSacadoFone2() {
        return sacadoFone2;
    }

    public void setSacadoFone2(String sacadoFone2) {
        this.sacadoFone2 = sacadoFone2;
    }

    public String getSacadoCelFone() {
        return sacadoCelFone;
    }

    public void setSacadoCelFone(String sacadoCelFone) {
        this.sacadoCelFone = sacadoCelFone;
    }

    public String getSacadoCelFone2() {
        return sacadoCelFone2;
    }

    public void setSacadoCelFone2(String sacadoCelFone2) {
        this.sacadoCelFone2 = sacadoCelFone2;
    }

    @Override
    public String toString() {
        return "ClienteDTO{" + "sacadoId=" + sacadoId + ", sacadoNome=" + sacadoNome + ", sacadoCpfCnpj=" + sacadoCpfCnpj + ", sacadoComplemento=" + sacadoComplemento + ", sacadoFone=" + sacadoFone + ", sacadoNumDocIdent=" + sacadoNumDocIdent + ", sacadoCep=" + sacadoCep + ", sacadoLogradouro=" + sacadoLogradouro + ", sacadoBairro=" + sacadoBairro + ", sacadoCidade=" + sacadoCidade + ", sacadoEstado=" + sacadoEstado + ", sacadoDtNascimento=" + sacadoDtNascimento + ", sacadoFone2=" + sacadoFone2 + '}';
    }

    public String getInscricaoEstadual() {
        return inscricaoEstadual;
    }

    public void setInscricaoEstadual(String inscricaoEstadual) {
        this.inscricaoEstadual = inscricaoEstadual;
    }

    public boolean isEmancipado() {
        return emancipado;
    }

    public void setEmancipado(boolean emancipado) {
        this.emancipado = emancipado;
    }

    public String getSacadoNumero() {
        return sacadoNumero;
    }

    public void setSacadoNumero(String sacadoNumero) {
        this.sacadoNumero = sacadoNumero;
    }

    public String getSacadoEmail() {
        return sacadoEmail;
    }

    public void setSacadoEmail(String sacadoEmail) {
        this.sacadoEmail = sacadoEmail;
    }
}
