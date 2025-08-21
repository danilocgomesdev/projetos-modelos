import { SituacaoAgrupado } from "../CobrancasAgrupadas/SituacaoAgrupado";
import { SituacaoBoleto } from "./SituacaoBoleto";

export interface DadoBoletoCr5DTO {
    idBoleto: number;
    nossoNumero: string;
    bolSituacao: SituacaoBoleto;
    bolValor: number;
    bolDesconto: number | null;
    bolJuros: number | null;
    bolMulta: number | null;
    bolPago: number | null;
    bolVencimento: string; // formato ISO: 'YYYY-MM-DD'
    bolDataPagamento: string | null;
    bolDataCredito: string | null;
    bolEmissao: string | null;
    bolCancelamento: string | null;
    idCobrancasCliente: number;
    idCobrancasAgrupada: number;
    operadorInclusao: string;
    operadorAlteracao: string | null;
    operadorCancelamento: string | null;
    grupoStatus: SituacaoAgrupado;
    idUnidade: number;
    codUnidade: number;
    cnpjCedente: string;
    codigoBeneficiario: string;
    idArquivosRetornos: number | null;
    idArquivosDetalhes: number | null;
    bolRemessa: string | null;
}