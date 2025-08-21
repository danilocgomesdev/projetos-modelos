export interface CobrancasDTO {
  entidade: string;
  codUnidade: string;
  unidade: string;
  filial: string;
  responsavelFinanceiro: string;
  cpfCnpj: string;
  nascimento: number;
  consumidor: string;
  idSistema: number;
  sistema: string;
  ano: number;
  produtoServico: string;
  protheus: string;
  proposta: string | null;
  contrato: number;
  idCobrancaAutomatica: number | null;
  idProduto: number;
  idOperadorConsultor: number | null;
  consultor: string | null;
}
