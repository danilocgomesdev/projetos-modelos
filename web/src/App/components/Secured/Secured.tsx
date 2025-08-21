import { useQuery } from "@tanstack/react-query";
import { ReactNode, useEffect, useState } from "react";
import { UnidadeDTO } from "../../../models/DTOs/Outros/UnidadeDTO";
import { fetchAcessos } from "../../../requests/fetchAcessos";
import { fetchAllSistemas } from "../../../requests/requestsSistema";
import { fetchUnidade, fetchUnidadesPaginadas } from "../../../requests/requestsUnidades";
import { TemposCachePadrao } from "../../../utils/constantes";
import { ehErroDoAxios } from "../../../utils/errors";
import useCR5Axios from "../../hooks/useCR5Axios";
import { useKeycloak } from "../../hooks/useKeyCloak";
import { useOperadorEDireitosStore } from "../../stores/OperadorEDireitosStore";
import { useUnidadeStore } from "../../stores/UnidadeStore";
import { DadosNaoEncontrados } from "../DadosNaoEncontrados";
import { Loading } from "../Loading";

export const Secured = ({ children }: { children: ReactNode }) => {
  const { keycloak, initialized } = useKeycloak();
  const [authenticated, setAuthenticated] = useState<boolean | null>(null);
  const [temAcessoAUnidade, setTemAcessoAUnidade] = useState<boolean | null>(null);
  const { axios } = useCR5Axios();
  const [operador, direitos, setOperadorEDireitosStore] = useOperadorEDireitosStore((state) => [
    state.operador,
    state.direitos,
    state.setOperadorEDireitos,
  ]);
  const { unidadeAtual, setUnidade } = useUnidadeStore();

  useEffect(() => {
    if (!initialized) {
      return;
    }
    setAuthenticated(!!keycloak.authenticated);

    // TODO talvez seja melhor somente recarregar o token ao fazer requisições
    const intervalId = setInterval(() => {
      if (!keycloak.isTokenExpired(30)) {
        return;
      }

      keycloak
        .updateToken(30)
        .success((auth) => {
          setAuthenticated(auth);
          if (auth) {
            console.log("Token recarregado");
          } else {
            console.log("Token não mais válido. Redirecionando ao login");
            keycloak.login();
          }
        })
        .error((error) => {
          setAuthenticated(false);
          console.log("Erro ao atualizar token. Redirecionando ao login", error);
          keycloak.login();
        });
    }, 10 * 1000);

    return () => {
      clearInterval(intervalId);
    };
  }, [initialized]);

  const {
    data: operadorEDireitos,
    isLoading: carregandoPessoa,
    isError: erroAoCarregarPessoa,
    error: erroPessoa,
  } = useQuery({
    queryKey: ["fetchAcessos"],
    enabled: !!authenticated && (!operador || !direitos),
    queryFn: () => {
      return fetchAcessos(axios);
    },
  });

  const handleFetchUnidade = (
    requisicao: Promise<UnidadeDTO>,
    callbackFalha: (e: Error) => void
  ): void => {
    requisicao
      .then((unidade) => {
        setTemAcessoAUnidade(true);
        setUnidade(unidade);
      })
      .catch(callbackFalha);
  };

  const marcaSemAcesso = (erro: Error): void => {
    setTemAcessoAUnidade(false);
    console.error(erro);
  };

  const encontraUnidadeQualquer = async (): Promise<UnidadeDTO> => {
    const unidades = await fetchUnidadesPaginadas({ page: 0, pageSize: 1 }, axios);
    if (!unidades.result[0]) {
      throw new Error("Operador não tem acesso à nenhuma unidade do CR5");
    }
    return unidades.result[0];
  };

  useEffect(() => {
    if (!operadorEDireitos) {
      return;
    }

    // Não podemos usar reactQuery aqui, já que estamos dentro de um useEffect
    // Tentamos logar na unidade do logal storege, se não der certo em uma unidade qualquer
    handleFetchUnidade(fetchUnidade(axios, unidadeAtual.id), () =>
      handleFetchUnidade(encontraUnidadeQualquer(), marcaSemAcesso)
    );

    setOperadorEDireitosStore(operadorEDireitos);
  }, [operadorEDireitos]);

  useEffect(() => {
    if (!ehErroDoAxios(erroPessoa)) {
      return;
    }

    if (erroPessoa.response?.status === 403) {
      console.log("Erro ao atualizar token. Redirecionando ao login", erroPessoa);
      keycloak.login();
    }
  }, [erroPessoa]);

  useQuery({
    queryKey: ["todasOsSistemas"],
    staleTime: TemposCachePadrao.ESTATICO,
    cacheTime: TemposCachePadrao.ESTATICO,
    queryFn: () => {
      return fetchAllSistemas(axios);
    },
  });

  return (
    <>
      {!keycloak || authenticated === null ? (
        <Loading mensagem="Verificando autenticação" altura="100vh" />
      ) : !authenticated ? (
        <DadosNaoEncontrados altura="100vh" isError mensagem="Impossível autenticar-se." />
      ) : carregandoPessoa ? (
        <Loading mensagem="Carregando os Dados" altura="100vh" />
      ) : erroAoCarregarPessoa ? (
        <DadosNaoEncontrados
          altura="100vh"
          isError
          mensagem="Não foi possível carregar a aplicação. Procure o suporte."
        />
      ) : !operador || direitos.length == 0 ? (
        <DadosNaoEncontrados
          altura="100vh"
          isError
          mensagem="Seu operador não está cadastrado corretamente. Procure o suporte."
        />
      ) : temAcessoAUnidade === null ? (
        <Loading mensagem="Verificando acesso à unidade" altura="100vh" />
      ) : temAcessoAUnidade === false ? (
        <DadosNaoEncontrados
          altura="100vh"
          isError
          mensagem="Seu operador não está cadastrado corretamente, ele não tem acesso à nenhuma unidade do CR5. Procure a Gefin."
        />
      ) : (
        <>{children}</>
      )}
    </>
  );
};
