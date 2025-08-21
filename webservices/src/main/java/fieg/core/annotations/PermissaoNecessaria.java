package fieg.core.annotations;

import fieg.core.annotations.enums.TipoValidacaoPermissao;
import fieg.externos.compartilhadoservice.acesso.Acessos;
import jakarta.enterprise.util.Nonbinding;
import jakarta.interceptor.InterceptorBinding;

import java.lang.annotation.*;

/**
 * Interceptada por {@link fieg.core.annotations.interceptors.PermissaoNecessariaInterceptor}
 * uso:
 * <pre>
 * &#064;Authenticated
 * &#064;Path("/bancos")
 * &#064;PermissaoNecessaria({Acessos.BANCOS})
 * public class BancoRest {
 *     ...
 *     // Métodos aqui exigem que o operador tenha acesso aos Bancos
 *     ...
 * }
 * </pre>
 */
@InterceptorBinding
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface PermissaoNecessaria {

    @Nonbinding
    Acessos[] value() default {};

    @Nonbinding
    TipoValidacaoPermissao tipo() default TipoValidacaoPermissao.TEM_TODAS_AS_PERMISSOES;
}
