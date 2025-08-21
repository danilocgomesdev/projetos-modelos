import { AmortizaBoletoPagoDTO } from "./AmortizaBoletoPagoDTO";
import { RateioOrigemCadinDTO } from "./RateioOrigemCadinDTO";

export interface AcordoRateadoDTO {
  amortizaBoletoPago: AmortizaBoletoPagoDTO;
  rateios: RateioOrigemCadinDTO[];
}
