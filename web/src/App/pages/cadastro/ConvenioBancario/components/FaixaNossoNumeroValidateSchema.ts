import * as zod from "zod";

export const FaixaNossoNumeroValidateSchema = zod.object({
  nossoNumeroInicial: zod
    .string()
    .regex(/^\d{17}$/g, "Favor informar Nosso número inicial no formato correto"),
  nossoNumeroFinal: zod
    .string()
    .regex(/^\d{17}$/g, "Favor informar Nosso número final no formato correto"),
  nossoNumeroAtual: zod
    .string()
    .regex(/^\d{17}$/g, "Favor informar Nosso número atual no formato correto"),
});
