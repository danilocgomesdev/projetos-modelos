import * as zod from "zod";

export const EnviarNotificacaoValidateSchema = zod.object({
  mensagem: zod
    .string({
      errorMap: () => {
        return { message: "Informe a mensagem" };
      },
    })
    .max(200, "A mensagem deve ter no máximo 200 caracteres")
    .refine(
      (value) => {
        return value.trim() !== "";
      },
      {
        message: "Informe a mensagem",
      }
    ),
});
