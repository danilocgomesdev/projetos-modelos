import { useRef } from "react";
import { ValidacaoAcesso, validaPermissoes } from "../../utils/Acessos";
import { useOperadorEDireitosStore } from "../stores/OperadorEDireitosStore";

class ValidacaoDeUsuario {
  public constructor(
    public readonly validacao: ValidacaoAcesso,
    public readonly idOperador: number
  ) {}
}

export default function useValidaPermissoes(): {
  validaPermissoes: (validacaoNecessaria: ValidacaoAcesso) => boolean;
} {
  const cache = useRef(new Map<string, boolean>());
  const { operadorEDireitos } = useOperadorEDireitosStore();

  return {
    validaPermissoes: (validacaoNecessaria: ValidacaoAcesso) => {
      if (!operadorEDireitos) {
        return false;
      }

      const validacaoDeUsuario = new ValidacaoDeUsuario(
        validacaoNecessaria,
        operadorEDireitos.pessoa.idOperador
      );
      const chave = JSON.stringify(validacaoDeUsuario);

      if (cache.current.has(chave)) {
        return cache.current.get(chave) as boolean;
      }

      const temPermissao = validaPermissoes(validacaoNecessaria, operadorEDireitos.direitos);
      cache.current.set(chave, temPermissao);

      return temPermissao;
    },
  };
}
