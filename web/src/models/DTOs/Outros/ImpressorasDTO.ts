export interface ImpressorasDTO {
  idImpressora: number;
  dataCadastro: Date;
  idUnidade: number;
  descricao: string;
  modelo: "Térmica";
  ipMaquina: string;
  gaveta: boolean;
  guilhotina: boolean;
  tipoPorta: "COM";
  porta: number;
}
