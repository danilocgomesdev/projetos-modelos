package fieg.core.annotations.interceptors;

import fieg.core.annotations.PermissaoNecessaria;
import fieg.core.annotations.enums.TipoValidacaoPermissao;
import fieg.core.exceptions.ApplicationExceptionBase;
import fieg.core.exceptions.CodigoErroCr5;
import fieg.core.requests.RequestInfoHolder;
import fieg.modulos.operadordireitos.dto.OperadorDireitoDTO;
import fieg.modulos.operadordireitos.repository.OperadorDireitosRepository;
import jakarta.annotation.Priority;
import jakarta.inject.Inject;
import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;
import org.apache.http.HttpStatus;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@SuppressWarnings("unused")
@PermissaoNecessaria
@Priority(Interceptor.Priority.APPLICATION)
@Interceptor
public class PermissaoNecessariaInterceptor {

    @Inject
    RequestInfoHolder requestInfoHolder;

    @Inject
    OperadorDireitosRepository operadorDireitosRepository;

    @AroundInvoke
    public Object checkPermissao(InvocationContext ctx) throws Exception {
        // Se não tem idOperador, não veio do front end (Auth-Middleware)
        if (requestInfoHolder.getIdOperador().isEmpty()) {
            return ctx.proceed();
        }

        // A partir do id do operador, obtém as suas permissões
        List<OperadorDireitoDTO> permissoesDoUsuario = operadorDireitosRepository.getCr5(requestInfoHolder.getIdOperador().get());

        PermissaoNecessaria anotacao = getAnotacao(ctx);

        Set<Integer> permissoesNecessarias = Arrays.stream(anotacao.value()).map(it -> it.idAcesso).collect(Collectors.toSet());
        // Não são necessárias quaisquer permissões
        if (permissoesNecessarias.isEmpty()) {
            return ctx.proceed();
        }

        Set<Integer> permissoesEncontradas = permissoesDoUsuario
                .stream()
                .filter(dto -> permissoesNecessarias.contains(dto.getAcesso().getAcesso()) && dto.isLiberado())
                .map(dto -> dto.getAcesso().getAcesso())
                .collect(Collectors.toSet());

        var excecao = new ApplicationExceptionBase("Usuário não tem permissões necessárias", HttpStatus.SC_FORBIDDEN, CodigoErroCr5.SEM_PERMISSAO);

        // Verifica se o usuário tem todas as permissões necessárias
        if (anotacao.tipo() == TipoValidacaoPermissao.TEM_TODAS_AS_PERMISSOES) {
            if (permissoesEncontradas.size() < permissoesNecessarias.size()) {
                throw excecao;
            }

        } else {
            if (permissoesEncontradas.isEmpty()) {
                throw excecao;
            }
        }

        return ctx.proceed();
    }

    private PermissaoNecessaria getAnotacao(InvocationContext ctx) {
        Method method = ctx.getMethod();
        PermissaoNecessaria permissaoNecessaria = method.getAnnotation(PermissaoNecessaria.class);

        if (permissaoNecessaria != null) {
            return permissaoNecessaria;
        }

        // Nesse caso a classe foi anotada
        Class<?> aClass = ctx.getTarget().getClass();

        while ((permissaoNecessaria = aClass.getAnnotation(PermissaoNecessaria.class)) == null) {
            aClass = aClass.getSuperclass();
        }

        return permissaoNecessaria;
    }
}
