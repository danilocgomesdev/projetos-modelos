import { EnderecoDTO } from "./EnderecoDTO";

export interface PagadorDTO {
  cpf: string;
  nome: string;
  endereco: EnderecoDTO;

  cnpj: string;
  razao_SOCIAL: string;
}