import * as zod from "zod";
import {
  emailValid,
  numberValid,
  optionalString,
  stringValid,
  validaSubconjuntoString,
} from "../../../../../utils/zodUtil";

export const PessoaFisicaValidateSchema = zod.object({
  cpf: stringValid("Informe o CPF"),
  rg: stringValid("Informe o RG"),
  descricao: stringValid("Informe o Nome"),
  dataNascimento: stringValid("Informe a Data de Nascimento"),
  logradouro: stringValid("Informe o logradouro"),
  complemento: optionalString(),
  bairro: stringValid("Informe o bairro"),
  cidade: stringValid("Informe a cidade"),
  estado: stringValid("Informe o estado"),
  cep: stringValid("Informe o CEP"),
  telefone: stringValid("Informe o telefone"),
  telefone2: optionalString(),
  idResponsavelFinanceiro: optionalString(),
  emancipado: validaSubconjuntoString("Emancipado deve ser S (Sim) ou N (Não)", isAOrF),
  celular: stringValid("Informe o celular"),
  celular2: optionalString(),
  numeroResidencia: optionalString(),
  email: emailValid("Informe um e-mail válido"),
});

export const PessoaJuridicaValidateSchema = zod.object({
  razaoSocial: stringValid("Informe a Razão Social"),
  cnpj: stringValid("Informe o CNPJ"),
  logradouro: stringValid("Informe o logradouro"),
  complemento: optionalString(),
  bairro: stringValid("Informe o bairro"),
  cidade: stringValid("Informe a cidade"),
  estado: stringValid("Informe o estado"),
  cep: stringValid("Informe o CEP"),
  telefone: stringValid("Informe o telefone"),
  telefone2: optionalString(),
  inscricaoEstadual: stringValid("Informe a Inscrição Estadual"),
  celular: stringValid("Informe o celular"),
  celular2: optionalString(),
  numeroResidencia: optionalString(),
  email: emailValid("Informe um e-mail válido"),
});

export const EstrangeiroValidateSchema = zod.object({
  idEstrangeiro: stringValid("Informe o Identificador Estrangeiro"),
  dataNascimento: optionalString(),
  descricao: stringValid("Informe o Nome"),
  logradouro: stringValid("Informe o logradouro"),
  complemento: optionalString(),
  bairro: stringValid("Informe o bairro"),
  estado: optionalString(),
  cidade: stringValid("Informe a cidade"),
  telefone: stringValid("Informe o telefone"),
  telefone2: optionalString(),
  celular: stringValid("Informe o celular"),
  celular2: optionalString(),
  numeroResidencia: optionalString(),
  email: emailValid("Informe um e-mail válido"),
  pais: stringValid("Informe o País"),
  codigoPais: numberValid("Informe o Código País"),
  codigoPostal: stringValid("Informe o Código Postal"),
});

function isAOrF(value: string): value is "S" | "N" {
  return value === "S" || value === "N";
}
