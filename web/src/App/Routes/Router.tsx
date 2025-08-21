import { useRoutes } from "react-router-dom";
import { Home } from "../pages/Home";
import { NotFound } from "../pages/NotFound";
import { Sobre } from "../pages/Sobre";
import { AdicionaParcela } from "../pages/administrativo/AdicionaParcela";
import { CancelamentoAutomatico } from "../pages/administrativo/CancelamentoAutomatico";
import { Notificacao } from "../pages/administrativo/Notificacao";
import { PagamentosNaoBaixados } from "../pages/administrativo/PagamentosNaoBaixados";
import { TerminaisTEF } from "../pages/administrativo/TerminaisTEF";
import { Agencias } from "../pages/cadastro/Agencias";
import { CriarAgencia } from "../pages/cadastro/Agencias/CriarAgencia";
import { EditarAgencia } from "../pages/cadastro/Agencias/EditarAgencia";
import { Bancos } from "../pages/cadastro/Bancos";
import { CriarBanco } from "../pages/cadastro/Bancos/CriarBanco.tsx";
import { EditarBanco } from "../pages/cadastro/Bancos/EditarBanco.tsx";
import { Clientes } from "../pages/cadastro/Clientes";
import { CriarClienteEstangeiro } from "../pages/cadastro/Clientes/CriarClienteEstangeiro.tsx";
import { CriarClientePessoaFisica } from "../pages/cadastro/Clientes/CriarClientePessoaFisica.tsx";
import { CriarClientePessoaJuridica } from "../pages/cadastro/Clientes/CriarClientePessoaJuridica.tsx";
import { EditarClienteEstrangeiro } from "../pages/cadastro/Clientes/EditarClienteEstrangeiro.tsx";
import { EditarClientePessoaFisica } from "../pages/cadastro/Clientes/EditarClientePessoaFisica.tsx";
import { EditarClientePessoaJuridica } from "../pages/cadastro/Clientes/EditarClientePessoaJuridica.tsx";
import { ContasCorrente } from "../pages/cadastro/ContaCorrente";
import { CriarContaCorrente } from "../pages/cadastro/ContaCorrente/CriarContaCorrente.tsx";
import { EditarContaCorrente } from "../pages/cadastro/ContaCorrente/EditarContaCorrente.tsx";
import { ConvenioBancario } from "../pages/cadastro/ConvenioBancario";
import { CriarConvenioBancario } from "../pages/cadastro/ConvenioBancario/CriarConvenioBancario.tsx";
import { EditarConvenioBancario } from "../pages/cadastro/ConvenioBancario/EditarConvenioBancario.tsx";
import { DadoContabil } from "../pages/cadastro/DadoContabil";
import { CriarDadoContabil } from "../pages/cadastro/DadoContabil/CriarDadoContabil.tsx";
import { EditarDadoContabil } from "../pages/cadastro/DadoContabil/EditarDadoContabil.tsx";
import { Gestores } from "../pages/cadastro/Gestor";
import { CriarGestor } from "../pages/cadastro/Gestor/CriarGestor.tsx";
import { EditarGestor } from "../pages/cadastro/Gestor/EditarGestor.tsx";
import { Impressoras } from "../pages/cadastro/Impressoras";
import { CriarImpressora } from "../pages/cadastro/Impressoras/CriarImpressora.tsx";
import { EditarImpressora } from "../pages/cadastro/Impressoras/EditarImpressora.tsx";
import { CriarProdutoDadosContabil } from "../pages/cadastro/ProdutoDadosContabil/CriarProdutoDadoContabil.tsx";
import { EditarProdutoDadosContabil } from "../pages/cadastro/ProdutoDadosContabil/EditarProdutoDadoContabil.tsx";
import { ProdutoDadoContabil } from "../pages/cadastro/ProdutoDadosContabil/ProdutoDadoContabil.tsx";
import { ProdutoServico } from "../pages/cadastro/ProdutoServico";
import { CriarProdutoServico } from "../pages/cadastro/ProdutoServico/CriarProdutoServico.tsx";
import { EditarProdutoServico } from "../pages/cadastro/ProdutoServico/EditarProdutoServico.tsx";
import { VinculoDependente } from "../pages/cadastro/VinculoDependentes";
import { PagamentoRecorrente } from "../pages/cartoes/PagamentoRecorrente";
import { EditarPagamentoRecorrente } from "../pages/cartoes/PagamentoRecorrente/EditarPagamentoRecorrente.tsx";
import { AgruparParcelas } from "../pages/cobrancas/AgruparParcelas";
import { Cobranca } from "../pages/cobrancas/Cobranca/Cobranca.tsx";
import { ExportaParaCadin } from "../pages/cobrancas/ExportaParaCadin";
import { SituacaoCliente } from "../pages/cobrancas/SituacaoCliente";
import { ConciliaHits } from "../pages/conciliacao/ConciliaHits/index.tsx";
import { ConciliaProtheus } from "../pages/conciliacao/ConciliaProtheus/index.tsx";
import { ConciliaProtheusCadin } from "../pages/conciliacao/ConciliaProtheusCadin/index.ts";
import { ConsultaBoletoCaixa } from "../pages/movimento/ConsultaBoletoCaixa";
import { ContratoRede } from "../pages/protheus/ContratoRede";
export const routes = [
  {
    id: "Home",
    path: "/",
    element: <Home />,
  },
  {
    id: "Cadastro",
    path: "/cadastro",
  },
  {
    id: "Agências",
    path: "/cadastro/agencias",
    element: <Agencias />,
  },
  {
    id: "Nova Agência",
    path: "/cadastro/agencias/novo",
    element: <CriarAgencia />,
  },
  {
    id: "Editar Agência",
    path: "/cadastro/agencias/editar",
    element: <EditarAgencia />,
  },
  {
    id: "Bancos",
    path: "/cadastro/bancos",
    element: <Bancos />,
  },
  {
    id: "Novo Banco",
    path: "/cadastro/bancos/novo",
    element: <CriarBanco />,
  },
  {
    id: "Editar Banco",
    path: "/cadastro/bancos/editar",
    element: <EditarBanco />,
  },
  {
    id: "Contas Corrente",
    path: "/cadastro/contas-corrente",
    element: <ContasCorrente />,
  },
  {
    id: "Nova Conta Corrente",
    path: "/cadastro/contas-corrente/novo",
    element: <CriarContaCorrente />,
  },
  {
    id: "Editar Conta Corrente",
    path: "/cadastro/contas-corrente/editar",
    element: <EditarContaCorrente />,
  },
  {
    id: "Convênios Bancários",
    path: "/cadastro/convenio-bancario",
    element: <ConvenioBancario />,
  },
  {
    id: "Novo Convênio Bancário",
    path: "/cadastro/convenio-bancario/novo",
    element: <CriarConvenioBancario />,
  },
  {
    id: "Editar Convênio Bancário",
    path: "/cadastro/convenio-bancario/editar",
    element: <EditarConvenioBancario />,
  },
  {
    id: "Produto Serviço ",
    path: "/cadastro/produto-servico",
    element: <ProdutoServico />,
  },
  {
    id: "Novo Produto Serviço ",
    path: "/cadastro/produto-servico/novo",
    element: <CriarProdutoServico />,
  },
  {
    id: "Editar Produto Serviço ",
    path: "/cadastro/produto-servico/editar",
    element: <EditarProdutoServico />,
  },
  {
    id: "Dado Contábil",
    path: "/cadastro/dado-contabil",
    element: <DadoContabil />,
  },
  {
    id: "Novo Dado Contábil",
    path: "/cadastro/dado-contabil/novo",
    element: <CriarDadoContabil />,
  },
  {
    id: "Editar Dado Contábil",
    path: "/cadastro/dado-contabil/editar",
    element: <EditarDadoContabil />,
  },
  {
    id: "Produto / Dado Contábil",
    path: "/cadastro/produto-dado-contabil",
    element: <ProdutoDadoContabil />,
  },
  {
    id: "Novo Produto / Dado Contábil",
    path: "/cadastro/produto-dado-contabil/novo",
    element: <CriarProdutoDadosContabil />,
  },
  {
    id: "Editar Produto / Dado Contábil",
    path: "/cadastro/produto-dado-contabil/editar",
    element: <EditarProdutoDadosContabil />,
  },
  {
    id: "Clientes",
    path: "/cadastro/clientes",
    element: <Clientes />,
  },
  {
    id: "Nova Pessoa Física",
    path: "/cadastro/clientes/pessoa-fisica-novo",
    element: <CriarClientePessoaFisica />,
  },
  {
    id: "Nova Pessoa Jurídica",
    path: "/cadastro/clientes/pessoa-juridica-novo",
    element: <CriarClientePessoaJuridica />,
  },
  {
    id: "Novo Cliente Estangeiro",
    path: "/cadastro/clientes/estrangeiro-novo",
    element: <CriarClienteEstangeiro />,
  },
  {
    id: "Editar Cliente Estangeiro",
    path: "/cadastro/clientes/estrangeiro-editar",
    element: <EditarClienteEstrangeiro />,
  },
  {
    id: "Editar Cliente Pessoa Física",
    path: "/cadastro/clientes/pessoa-fisica-editar",
    element: <EditarClientePessoaFisica />,
  },
  {
    id: "Editar Pessoa Jurídica",
    path: "/cadastro/clientes/pessoa-juridica-editar",
    element: <EditarClientePessoaJuridica />,
  },
  {
    id: "Vínculo Dependentes",
    path: "/cadastro/vinculo-dependentes",
    element: <VinculoDependente />,
  },
  {
    id: "Impressoras",
    path: "/cadastro/impressoras",
    element: <Impressoras />,
  },
  {
    id: "Nova Impressora",
    path: "/cadastro/impressoras/novo",
    element: <CriarImpressora />,
  },
  {
    id: "Editar Impressora",
    path: "/cadastro/impressoras/editar",
    element: <EditarImpressora />,
  },
  {
    id: "Gestor",
    path: "/cadastro/gestor",
    element: <Gestores />,
  },
  {
    id: "Nova Gestor",
    path: "/cadastro/gestor/novo",
    element: <CriarGestor />,
  },
  {
    id: "Editar Gestor",
    path: "/cadastro/gestor/editar",
    element: <EditarGestor />,
  },
  {
    id: "Administrativo",
    path: "/administrativo",
  },
  {
    id: "Pagamentos não Baixados",
    path: "/administrativo/pagamento-nao-baixado",
    element: <PagamentosNaoBaixados />,
  },
  {
    id: "Terminais TEF",
    path: "/administrativo/terminais-tef",
    element: <TerminaisTEF />,
  },
  {
    id: "Adiciona Parcela",
    path: "/administrativo/adiciona-parcela",
    element: <AdicionaParcela />,
  },
  {
    id: "Enviar Notificação",
    path: "/administrativo/notificacao",
    element: <Notificacao />,
  },
  {
    id: "Cancelamento Automático",
    path: "/administrativo/cancelamento-automatico",
    element: <CancelamentoAutomatico />,
  },
  {
    id: "Conciliação",
    path: "/conciliacao",
  },
  {
    id: "Concilia Hits",
    path: "/conciliacao/concilia-hits",
    element: <ConciliaHits />,
  },
  {
    id: "Concilia Protheus",
    path: "/conciliacao/concilia-protheus",
    element: <ConciliaProtheus />,
  },
  {
    id: "Concilia Protheus Cadin",
    path: "/conciliacao/concilia-protheus-cadin",
    element: <ConciliaProtheusCadin />,
  },
  {
    id: "Cartões",
    path: "/cartoes",
  },
  {
    id: "Pagamentos Recorrentes",
    path: "/cartoes/pagamento-recorrente",
    element: <PagamentoRecorrente />,
  },
  {
    id: "Editar Conta Corrente",
    path: "/cartoes/pagamento-recorrente/editar",
    element: <EditarPagamentoRecorrente />,
  },
  {
    id: "Contrato Rede",
    path: "/protheus/contrato-rede",
    element: <ContratoRede />,
  },
  {
    id: "Sobre",
    path: "/sobre",
    element: <Sobre />,
  },
  {
    id: "Movimento",
    path: "/movimento",
  },
  {
    id: "Consulta Boleto",
    path: "/movimento/consulta-boleto-caixa",
    element: <ConsultaBoletoCaixa />,
  },

  { id: undefined, path: "*", element: <NotFound /> },
  {
    id: "Cobrancas",
    path: "/cobrancas",
  },
  { id: "Agrupa Parcela", path: "/cobrancas/agrupar-parcelas", element: <AgruparParcelas /> },
  {
    id: "Cobrança",
    path: "/cobrancas/cobranca",
    element: <Cobranca />,
  },
  {
    id: "Exportar Para Cadin",
    path: "/cobrancas/exportar-para-cadin",
    element: <ExportaParaCadin />,
  },
  {
    id: "Situação do Cliente",
    path: "/cobrancas/situacaoCliente",
    element: <SituacaoCliente />,
  },
];

export function Router() {
  return useRoutes(routes);
}
