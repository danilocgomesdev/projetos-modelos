import { ParcelaDTO } from "../Cobrancas/ParcelaDTO";
import { ContasAReceberProtheusDTO } from "../Protheus/ContasAReceberProtheusDTO";
import { AcordoRateadoDTO } from "./AcordoRateadoDTO";

export interface ParcelaDeOrigem {
  parcelaDTO: ParcelaDTO;
  contasAReceberProtheus: ContasAReceberProtheusDTO;
}

export interface InformacoesOrigemDTO {
  parcelaDeOrigem: ParcelaDeOrigem;
  acordoRateado: AcordoRateadoDTO | null;
}

export interface ParcelaCadinDTO {
  parcelaCadin: {
    parcelaDTO: ParcelaDTO;
    contasAReceberProtheus: ContasAReceberProtheusDTO;
  };
  informacoesOrigem: InformacoesOrigemDTO[];
}
