
/**
* Apresentação da Interface 
*/

export interface EntidadeDTO {
  /** 
   * Código da Entidade da Tabela CI_ENTIDADE
   * @type {number} 
  */
  codigo: number;
  descReduzida: string;
  descDetalhada: string;
  /** 
   * Código da Entidade da Tabela SF_ENTIDADE
   * @type {number} 
  */
  idEntidade: number;
}

export interface EntidadesType {
  FIEG: EntidadeDTO;
  SESI: EntidadeDTO;
  SENAI: EntidadeDTO;
  IEL: EntidadeDTO;
}

export type EntidadeEnum = "SISTEMAFIEG" | "FIEG" | "SESI" | "SENAI" | "IEL" | "ICQ";

// SISTEMAFIEG e ICQ não são usados no CR5
export const Entidades: EntidadesType = {
  //   SISTEMAFIEG: {
  //     codigo: 0, entidade
  //     descReduzida: "SISTEMA FIEG",
  //     descDetalhada: "SISTEMA FIEG",
  //     idEntidade: 1, entidade acesso
  //   },
  FIEG: {
    codigo: 1,
    descReduzida: "FIEG",
    descDetalhada: "FIEG - Federação das Indústrias do Estado de Goiás",
    idEntidade: 2,
  },
  SESI: {
    codigo: 2,
    descReduzida: "SESI",
    descDetalhada: "SESI - Serviço Social da Indústria",
    idEntidade: 6,
  },
  SENAI: {
    codigo: 3,
    descReduzida: "SENAI",
    descDetalhada: "SENAI - Serviço Nacional de Aprendizagem Indústrial",
    idEntidade: 5,
  },
  IEL: {
    codigo: 4,
    descReduzida: "IEL",
    descDetalhada: "IEL - Instituto Euvaldo Lodi",
    idEntidade: 4,
  },
  //   ICQ: {
  //     codigo: 5,
  //     descReduzida: "ICQ-Brasil",
  //     descDetalhada: "ICQ-Brasil - Instituto de Certificação de Qualidade Brasil",
  //     idEntidade: 3,
  //   },
};
