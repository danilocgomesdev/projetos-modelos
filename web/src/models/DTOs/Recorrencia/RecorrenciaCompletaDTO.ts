

export interface RecorrenciaCompletaDTO {

  idEntidade: number;
  entidade: string;
  unidade: string;
  responsavelFinanceiro: string;
  cpfCnpj: string;

  idRecorrencia: number;
  statusRecorrencia: string;
  dataInicioRecorrencia: string;
  dataFimRecorrencia: string;

  contrato: number;
  parcela: number;
  situacao: string;
  idSistema: number;
  valorCobranca: number;
  valorPago: number;
  dataVencimento: string;
  dataPagamento: string;
  idCobrancasClientes: number;

  dataVendaCielo: string;
  tidCielo: string;
  autorizacaoCielo: string;
  nsuCielo: string;
  valorVendaCielo: number;

}