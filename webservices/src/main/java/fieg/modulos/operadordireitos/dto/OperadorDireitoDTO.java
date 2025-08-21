package fieg.modulos.operadordireitos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OperadorDireitoDTO {

    private Integer idOperador;
    private AcessoDTO acesso;
    private boolean unico;
    private boolean consultar;
    private boolean cadastrar;
    private boolean alterar;
    private boolean excluir;
    private boolean liberado;
    private Integer idPerfil;
    private Integer idSistema;
    private Integer idMenu;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class AcessoDTO {
        private Integer acesso;
        private String opcao;
    }
}
