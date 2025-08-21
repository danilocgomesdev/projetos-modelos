import * as zod from "zod";

export function stringValid(errorMessage: string): zod.ZodEffects<zod.ZodString, string, string> {
  return zod
    .string({
      errorMap: () => {
        return { message: errorMessage };
      },
    })
    .refine(
      (value) => {
        return value.trim() !== "";
      },
      {
        message: errorMessage,
      }
    );
}

interface ExtraValidationParams {
  min?: number;
  max?: number;
}

export function checkString(
  nomeCampo: string,
  params?: ExtraValidationParams
): zod.ZodEffects<zod.ZodString, string, string> {
  let zodString = zod.string({
    errorMap: (err) => {
      return { message: err?.message ?? `Informe ${nomeCampo}` };
    },
  });
  zodString = adicionaValidacoes(zodString, nomeCampo, params);

  return zodString.refine(
    (value) => {
      return value.trim() !== "";
    },
    {
      message: `Informe ${nomeCampo}`,
    }
  );
}

export function checkOptionalString(
  nomeCampo: string,
  params?: ExtraValidationParams
): zod.ZodOptional<zod.ZodString> {
  let zodString = zod.string();
  zodString = adicionaValidacoes(zodString, nomeCampo, params);

  return zodString.optional();
}

function adicionaValidacoes(
  zodString: zod.ZodString,
  nomeCampo: string,
  params: ExtraValidationParams | undefined
): zod.ZodString {
  const nomeCampoMaiusculo = nomeCampo.charAt(0).toUpperCase() + nomeCampo.slice(1);
  if (params?.min) {
    zodString = zodString.min(
      params.min,
      `${nomeCampoMaiusculo} deve ter no mínimo ${params.min} caracteres`
    );
  }
  if (params?.max) {
    zodString = zodString.max(
      params.max,
      `${nomeCampoMaiusculo} deve ter no máximo ${params.max} caracteres`
    );
  }

  return zodString;
}

export function numberValid(errorMessage: string): zod.ZodEffects<zod.ZodString, string, string> {
  return zod
    .string({
      errorMap: () => {
        return { message: errorMessage };
      },
    })
    .refine(
      (v) => {
        const n = Number(v);
        return !isNaN(n) && v?.length > 0;
      },
      { message: errorMessage }
    );
}

export function validaSubconjuntoString<T extends string>(
  errorMessage: string,
  stringEhDoSubconjunto: (str: string) => str is T
): zod.ZodEffects<zod.ZodEffects<zod.ZodString, string, string>, T, string> {
  return zod
    .string({
      errorMap: () => {
        return { message: errorMessage };
      },
    })
    .refine(
      (value) => {
        const trimmedValue = value.trim();
        return stringEhDoSubconjunto(trimmedValue);
      },
      { message: errorMessage }
    )
    .transform((value) => {
      return value as T;
    });
}

export function optionalString(): zod.ZodOptional<zod.ZodString> {
  return zod.string().optional();
}

export function emailValid(errorMessage: string): zod.ZodEffects<zod.ZodString, string, string> {
  const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

  return zod
    .string({
      errorMap: () => {
        return { message: errorMessage };
      },
    })
    .refine(
      (v) => {
        return emailRegex.test(v);
      },
      { message: errorMessage }
    );
}

export function dateValid(errorMessage: string): zod.ZodEffects<zod.ZodString, string, string> {
  return zod
    .string({
      errorMap: () => {
        return { message: errorMessage };
      },
    })
    .refine(
      (v) => {
        // Verifica se a string é uma data válida
        const date = new Date(v);
        return !isNaN(date.getTime());
      },
      { message: errorMessage }
    );
}
