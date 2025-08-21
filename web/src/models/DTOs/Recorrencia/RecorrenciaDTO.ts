
export interface RecorrenciaDTO {

  idEntidade: number;
  entidade: string;
  unidade: string;
  responsavelFinanceiro: string;
  cpfCnpj: string;

  idRecorrencia: number;
  statusRecorrencia: string;
  dataInicioRecorrencia: string;
  dataFimRecorrencia: string;

  idSistema: number;

}