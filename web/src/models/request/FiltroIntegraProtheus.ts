import { IntegraProtheus } from "../DTOs/Protheus/IntegraProtheus";

export type FiltroIntegraProtheus =
  | { isProtheus: boolean }
  | { integraProtheus: IntegraProtheus[] };
