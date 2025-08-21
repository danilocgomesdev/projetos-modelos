import { useState } from "react";
import useCR5Axios from "../../../hooks/useCR5Axios";

export function useFetchRelatorio() {
  const [isLoading, setIsLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);
  const [relatorio, setRelatorio] = useState<Blob | null>(null);
  const { axios } = useCR5Axios();

  const fetchRelatorio = async (url: string, params?: Record<string, any>) => {
    setIsLoading(true);
    setError(null);
    setRelatorio(null);
    let fileType = "";

    try {
      const response = await axios.get<ArrayBuffer>(url, {
        params,
        responseType: "arraybuffer",
      });

      const contentType = response.headers["content-type"];
      fileType = "application/pdf";
      if (contentType && contentType.includes("excel")) {
        fileType = "application/excel";
      }

      const blob = new Blob([response.data], { type: fileType });
      setRelatorio(blob);
    } catch (err: any) {
      setError("Erro ao gerar o relatório: " + err.message);
    } finally {
      setIsLoading(false);
    }
  };

  return { fetchRelatorio, isLoading, error, relatorio };
}
