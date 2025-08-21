import { useEffect, useRef } from "react";
import { useNavigate } from "react-router-dom";
import Acessos, { criaValidacaoTodas } from "../../utils/Acessos";
import { useFuncaoNotificacao } from "./useFuncaoNotificacao";
import useValidaPermissoes from "./useValidaPermissoes";

type Callback = () => void | Promise<void>;

export function useValidaAcessos(acessos: Acessos[], callback?: Callback) {
  const { validaPermissoes } = useValidaPermissoes();
  const navigate = useNavigate();
  const notificacao = useFuncaoNotificacao();
  const jaExecutou = useRef(false);

  useEffect(() => {
    if (jaExecutou.current) return;
    jaExecutou.current = true;

    if (validaPermissoes(criaValidacaoTodas(acessos))) {
      if (callback) callback(); // 🔹 roda o que você quiser dinamicamente
    } else {
      notificacao({
        tipo: "warning",
        titulo: "Aviso",
        message: "Você não tem permissão para acessar essa página",
      });
      navigate("/");
    }
  }, [acessos, callback, validaPermissoes, navigate, notificacao]);
}
