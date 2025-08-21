import { IMask } from "react-imask";

import {
  FormaPagamentoSimplificado,
  formasDePagamentoValues,
} from "../models/DTOs/Outros/FormasDePagamentoSimplificas";
import { IntegraProtheus, integraProtheusValues } from "../models/DTOs/Protheus/IntegraProtheus";

export function mascaraCNPJ(texto: string): string {
  const masked = IMask.createMask({
    mask: "00.000.000/0000-00",
  });
  masked.resolve(texto);
  return masked.value;
}

export function mascaraCPF(texto: string): string {
  const masked = IMask.createMask({
    mask: "000.000.000-00",
  });
  masked.resolve(texto);
  return masked.value;
}

export function mascaraCpfCnpj(texto: string | null): string {
  if (!texto) {
    return "";
  }
  const numeroLimpo = texto.replace(/[^\d]/g, "");

  if (numeroLimpo.length === 11) {
    return mascaraCPF(numeroLimpo);
  } else if (numeroLimpo.length === 14) {
    return mascaraCNPJ(numeroLimpo);
  } else {
    return "-";
  }
}

export function mascaraFone(texto: string): string {
  const masked = IMask.createMask({
    mask: "(00)0000-0000",
  });
  masked.resolve(texto);
  return masked.value;
}

export function formatarDataHoraBrasil(dataHora: string | Date | number | null): string {
  if (dataHora == null) {
    return "-";
  }
  if (typeof dataHora === "string" || typeof dataHora === "number") {
    dataHora = new Date(dataHora);
  }

  const options: Intl.DateTimeFormatOptions = {
    year: "numeric",
    month: "2-digit",
    day: "2-digit",
    hour: "2-digit",
    minute: "2-digit",
    second: "2-digit",
    timeZone: "America/Sao_Paulo",
  };

  return dataHora.toLocaleDateString("pt-BR", options);
}

export function formatarDataBrasil(dataHora: string | Date | number | null | undefined): string {
  if (dataHora == null) {
    return "";
  }
  if (typeof dataHora === "string" || typeof dataHora === "number") {
    dataHora = new Date(dataHora);
  }

  const options: Intl.DateTimeFormatOptions = {
    year: "numeric",
    month: "2-digit",
    day: "2-digit",
    timeZone: "America/Sao_Paulo",
  };
  return dataHora.toLocaleDateString("pt-BR", options);
}

export function formatarDataBrasil_2(dataHora: string | null | undefined): string {
  if (dataHora == null || dataHora == undefined || dataHora == "") {
    return "";
  }

  let varData = "";
  const dataArray = dataHora.split("-");
  varData = dataArray[2] + "/" + dataArray[1] + "/" + dataArray[0];

  return varData;
}

export function formatarDataBrasilTimestamp(
  dataHora: string | Date | number | null | undefined
): string {
  if (!dataHora) return "";

  const d = new Date(dataHora);

  const dia = String(d.getDate()).padStart(2, "0");
  const mes = String(d.getMonth() + 1).padStart(2, "0");
  const ano = d.getFullYear();

  return `${dia}/${mes}/${ano}`;
}

export function formatarMoedaBrasil(valor: string | number): string {
  const valorNumerico = typeof valor === "string" ? parseFloat(valor) : valor;

  return new Intl.NumberFormat("pt-BR", {
    style: "currency",
    currency: "BRL",
  }).format(valorNumerico);
}

export function formatarDataHttp(data: Date | null): string {
  if (data == null) {
    return "";
  }

  const year = data.getFullYear();
  const month = data.getMonth() + 1;
  const day = data.getDate();

  let monthString = month.toString();
  let dayString = day.toString();

  if (month < 10) {
    monthString = "0" + month;
  }

  if (day < 10) {
    dayString = "0" + day;
  }

  return `${year}-${monthString}-${dayString}`;
}

const maskCurrency = (valor: number, locale = "pt-BR", currency = "BRL") => {
  return new Intl.NumberFormat(locale, {
    style: "currency",
    currency,
  }).format(valor);
};

export function mascaraMoeda(event: React.ChangeEvent<HTMLInputElement>): string | undefined {
  const inputValue = event.target.value;
  if (inputValue === "") {
    return undefined; // Se o valor estiver vazio, retorna uma string vazia
  }
  const onlyDigits = inputValue
    .split("")
    .filter((s) => /\d/.test(s))
    .join("")
    .padStart(3, "0");

  const digitsFloat = onlyDigits.slice(0, -2) + "." + onlyDigits.slice(-2);
  const valorFormatado = maskCurrency(Number(digitsFloat));
  return valorFormatado;
}

export function mascaraCEP(texto: string): string {
  const masked = IMask.createMask({
    mask: "00000-000",
  });
  masked.resolve(texto);
  return masked.value;
}

export function getDescricaoIntegraProtheus(integraProtheus: string | null): string {
  if (integraProtheus && integraProtheus in integraProtheusValues) {
    return integraProtheusValues[integraProtheus as IntegraProtheus].descricao;
  }
  return "";
}

export function getDescricaoFormaPagamentoSimplificado(formaPagamento: string | null): string {
  if (formaPagamento && formaPagamento in formasDePagamentoValues) {
    return formasDePagamentoValues[formaPagamento as FormaPagamentoSimplificado].descricao;
  }
  return "";
}
