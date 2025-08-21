export interface PessoaCIDTO {
  id: number;
  nome: string;
  gerencia: {
    id: number;
    descricao: string;
    idUnidade: number;
  };
  entidade: string;
  email: string;
  matricula: string;
  status: string;
  idOperador: number;
}

export interface OperadorDireitoDTO {
  acesso: {
    acesso: number;
    opcao: string;
  };
  unico: boolean;
  consultar: boolean;
  cadastrar: boolean;
  alterar: boolean;
  excluir: boolean;
  liberado: boolean;
  idPerfil: number;
  idSistema: number;
  idMenu: number;
}

export interface OperadorEDireitosDTO {
  pessoa: PessoaCIDTO;
  direitos: OperadorDireitoDTO[];
}
