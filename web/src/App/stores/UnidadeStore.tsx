import { create } from "zustand";
import { persist } from "zustand/middleware";
import { UnidadeDTO } from "../../models/DTOs/Outros/UnidadeDTO";

interface UnidadeState {
  unidadeAtual: UnidadeDTO;
  setUnidade: (novaUnidade: UnidadeDTO) => void;
}

export const useUnidadeStore = create(
  persist<UnidadeState>(
    (set) => ({
      // Isso é feito para que o tipo de unidadeAtual seja not null, sendo preenchido com uma unidade real ao realizar login
      unidadeAtual: {
        id: -1,
        codigo: -1,
        nome: "VALIDANDO PERMISSOES",
        cidade: "",
        filialERP: "",
        centroCustoErp: "",
        entidade: "FIEG",
        idLocal: -1,
        uf: "",
        descricaoUnidade: "",
      },
      setUnidade: (novaUnidade) =>
        set(() => ({
          unidadeAtual: novaUnidade,
        })),
    }),
    {
      name: "unidadeStore",
    }
  )
);
