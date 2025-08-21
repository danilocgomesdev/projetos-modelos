import { Text } from "@chakra-ui/react";
import { useQuery } from "@tanstack/react-query";
import { useState } from "react";
import { ConvenioBancarioDTO } from "../../../../../models/DTOs/ConvenioBancario/ConvenioBancarioDTO.ts";
import { fetchConveniosBancariosInativosByUnidade } from "../../../../../requests/requestsConveniosBancarios.ts";
import { ModalBase } from "../../../../components/ModalBase";
import { TabelaCR5 } from "../../../../components/Tabelas/TabelaCR5.tsx";
import useCR5Axios from "../../../../hooks/useCR5Axios.ts";
import { useUnidadeStore } from "../../../../stores/UnidadeStore.tsx";

interface ModalPesquisaConvenioBancarioInativo {
  isOpen: boolean;
  onClose: () => void;
  textoBotaoConfirma: string;
  onClickBotaoConfirma: (convenioBancario: ConvenioBancarioDTO | undefined) => void;
  isLoading?: boolean;
}

export function ModalPesquisaConvenioBancarioInativo({
  isOpen,
  onClose,
  textoBotaoConfirma,
  onClickBotaoConfirma,
  isLoading,
}: ModalPesquisaConvenioBancarioInativo) {
  const { axios } = useCR5Axios();
  const { unidadeAtual } = useUnidadeStore();
  const [convenioBancario, setConvenioBancario] = useState<ConvenioBancarioDTO>();

  const { data, isFetching, isError, error } = useQuery({
    queryKey: ["fetchConveniosBancariosDesativados", unidadeAtual],
    keepPreviousData: true,
    queryFn: () => {
      return fetchConveniosBancariosInativosByUnidade(
        {
          idUnidade: unidadeAtual.id,
        },
        axios
      );
    },
  });
  console.log(data);
  return (
    <ModalBase
      isOpen={isOpen}
      onClose={onClose}
      titulo="Pesquisar Convênios Bancários Inativos"
      subtitulo={`Inativos da unidade ${unidadeAtual.nome}-${unidadeAtual.codigo}`}
      size="5xl"
      centralizado={true}
      isLoading={isLoading}
      loadingText="Reativando convênio..."
      botaoConfirma={{
        label: textoBotaoConfirma,
        onClick: () => onClickBotaoConfirma(convenioBancario),
      }}
    >
      <TabelaCR5
        cabecalhos={[
          { titulo: "NOME CEDENTE", dadoBuilder: (item) => item.nomeCedente },
          { titulo: "NÚMERO", dadoBuilder: (item) => item.numero },
          { titulo: "CARTEIRA", dadoBuilder: (item) => item.carteira },
          { titulo: "SISTEMA", dadoBuilder: (item) => item.sistemaBancario },
          { titulo: "BANCO", dadoBuilder: (item) => item.contaCorrente.agencia.banco.nome },
          { titulo: "AGÊNCIA", dadoBuilder: (item) => item.contaCorrente.agencia.nome },
          {
            titulo: "NÚMERO-DIGITO",
            dadoBuilder: (item) =>
              `${item.contaCorrente.numeroConta}-${item.contaCorrente.digitoConta}`,
          },
        ]}
        data={data?.result}
        keybuilder={(item) => item.id}
        isFetching={isFetching}
        isError={isError}
        error={error}
        onRowClick={(item) => setConvenioBancario(item)}
        alturaLoading="10vh"
      />
      <Text mt={4}>
        <strong>Convênio selecionado: </strong>
        {convenioBancario
          ? `${convenioBancario.nomeCedente} - ${convenioBancario.numero}`
          : "Nenhum selecionado"}
      </Text>
    </ModalBase>
  );
}
