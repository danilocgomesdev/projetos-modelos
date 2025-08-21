package fieg.core.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Por padrão, o auth-middleware loga os bodys de todas as repostas que não são GET. Porém, algumas requisições que deveriam
 * ser GET acabam sendo declaradas como POST, na maioria das vezes por ter muitos filtros que devem ser enviados num body.
 * Por isso, para não poluir o log do middleware com respostas de métodos de listagem, os métodos dos controllers que não
 * devem ter suas respostas logadas devem ser marcados com essa anotação.
 * <br><br>
 * Ver {@link fieg.core.filters.ResponseFilters} onde essa anotação é usada para adicionar um Header que demostra que a
 * resposta não deve ser loggada.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface NaoLoggarResposta {
}
