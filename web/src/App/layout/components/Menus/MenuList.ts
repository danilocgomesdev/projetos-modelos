import { IconType } from "react-icons";
import {
  FaBusinessTime,
  FaCog,
  FaCreditCard,
  FaEdit,
  FaFileAlt,
  FaGoogleWallet,
  FaHandsHelping,
  FaHome,
  FaInfoCircle,
  FaQrcode,
  FaSwatchbook,
} from "react-icons/fa";
import Acessos, { ValidacaoAcesso, criaValidacaoTodas } from "../../../../utils/Acessos";

interface SubItemsProps {
  name: string;
  divider?: boolean;
  path: string;
  validacaoAcesso?: ValidacaoAcesso;
}

interface LinkItemProps {
  name: string;
  icon: IconType;
  subItems?: SubItemsProps[];
  path: string;
  validacaoAcesso?: ValidacaoAcesso;
}

export const MenuList: Array<LinkItemProps> = [
  { name: "Home", icon: FaHome, path: "/" },
  {
    name: "Cobranças",
    icon: FaBusinessTime,
    path: "/cobrancas",
    subItems: [
      { name: "Cobrança", path: "/cobranca" },
      { name: "Recebimento", path: "/recebimento" },
      { name: "Agrupar Parcelas", path: "/agrupar-parcelas" },
      { name: "Exportar Para Cadin", path: "/exportar-para-cadin" },
      { name: "Situação do Cliente", path: "/situacaoCliente" },
    ],
  },
  {
    name: "Movimento",
    icon: FaGoogleWallet,
    path: "/movimento",
    subItems: [
      {
        name: "Consulta Boleto",
        path: "/consulta-boleto-caixa",
      },
    ],
  },
  { name: "Relatórios", icon: FaFileAlt, path: "/relatorios" },
  {
    name: "Cadastro",
    icon: FaEdit,
    path: "/cadastro",
    subItems: [
      {
        name: "Agências",
        path: "/agencias",
        validacaoAcesso: criaValidacaoTodas([Acessos.AGENCIAS]),
      },
      { name: "Bancos", path: "/bancos", validacaoAcesso: criaValidacaoTodas([Acessos.BANCOS]) },
      {
        name: "Contas Corrente",
        path: "/contas-corrente",
        validacaoAcesso: criaValidacaoTodas([Acessos.CONTAS_CORRENTES]),
      },
      {
        name: "Convênios Bancários",
        path: "/convenio-bancario",
        validacaoAcesso: criaValidacaoTodas([Acessos.CONVENIOS_BANCARIOS]),
      },
      {
        name: "Gestor",
        path: "/gestor",
        validacaoAcesso: criaValidacaoTodas([Acessos.ADMINISTRADOR_CR5]),
      },
      {
        name: "Clientes",
        divider: true,
        path: "/clientes",
        validacaoAcesso: criaValidacaoTodas([Acessos.PESSOAS]),
      },
      { name: "Vínculo Dependentes", path: "/vinculo-dependentes" },
      {
        name: "Produto / Serviço",
        path: "/produto-servico",
        validacaoAcesso: criaValidacaoTodas([Acessos.SECRETARIA_PRODUTO_AVULSO]),
      },
      {
        name: "Dado Contábil",
        path: "/dado-contabil",
        validacaoAcesso: criaValidacaoTodas([Acessos.PRODUTO_CONTA_CONTABIL]),
      },
      {
        name: "Produto / Dado Contábil",
        path: "/produto-dado-contabil",
        validacaoAcesso: criaValidacaoTodas([Acessos.SECRETARIA_PRODUTO_AVULSO]),
      },
      {
        name: "Impressora",
        divider: true,
        path: "/impressoras",
        validacaoAcesso: criaValidacaoTodas([Acessos.CADASTRO_DE_IMPRESSORAS]),
      },
    ],
  },
  {
    name: "Administrativo",
    icon: FaCog,
    path: "/administrativo",
    // TODO validar permissão
    subItems: [
      { name: "Pagamentos não Baixados", path: "/pagamento-nao-baixado" },
      { name: "Terminais TEF", path: "/terminais-tef" },
      { name: "Adiciona Parcela", path: "/adiciona-parcela" },
      { name: "Enviar Notificação", path: "/notificacao" },
      { name: "Cancelamento Automático", path: "/cancelamento-automatico" },
    ],
  },
  {
    name: "Conciliação",
    icon: FaHandsHelping,
    path: "/conciliacao",
    subItems: [
      { name: "Concilia Hits", path: "/concilia-hits" },
      { name: "Concilia Protheus", path: "/concilia-protheus" },
      { name: "Concilia Protheus / Cadin", path: "/concilia-protheus-cadin" },
    ],
  },
  {
    name: "Cartões",
    icon: FaCreditCard,
    path: "/cartoes",
    subItems: [{ name: "Pagamentos Recorrentes", path: "/pagamento-recorrente" }],
  },
  {
    name: "Protheus",
    icon: FaSwatchbook,
    path: "/protheus",
    subItems: [{ name: "Criar Contratos em Rede", path: "/contrato-rede" }],
  },
  { name: "Pix", icon: FaQrcode, path: "/pix" },
  { name: "Sobre", icon: FaInfoCircle, path: "/sobre" },
];
