import { PageQuery } from "../request/PageQuery";

export interface PageResult<T> extends PageQuery {
  total: number;
  pageTotal: number;
  result: T[];
}
