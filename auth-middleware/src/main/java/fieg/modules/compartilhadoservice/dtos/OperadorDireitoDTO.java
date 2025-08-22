package fieg.modules.compartilhadoservice.dtos;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
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

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    @EqualsAndHashCode
    public static class AcessoDTO {
        private Integer acesso;
        private String opcao;
    }
}
