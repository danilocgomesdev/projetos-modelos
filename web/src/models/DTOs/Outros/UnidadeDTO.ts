import { EntidadeEnum } from "./EntidadeDTO";

export interface UnidadeDTO {
  id: number;
  idLocal: number;
  codigo: number;
  filialERP: string;
  centroCustoErp: string;
  descricaoUnidade: string;
  entidade: EntidadeEnum;
  nome: string;
  cidade: string;
  uf: string;
}
