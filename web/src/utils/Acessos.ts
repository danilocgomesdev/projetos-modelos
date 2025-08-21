import { OperadorDireitoDTO } from "../models/DTOs/Acessos/AcessosDTOs";

export default class Acessos {
  private constructor(private readonly _idAcesso: number, private readonly _nomeAcesso: string) {}

  static SECRETARIA_CONTRATO = new Acessos(455, "Secretaria / Contrato");
  static TESOURARIA = new Acessos(456, "Tesouraria");
  static BANCOS = new Acessos(457, "Bancos");
  static AGENCIAS = new Acessos(458, "Agências");
  static CONTAS_CORRENTES = new Acessos(459, "Contas Correntes");
  static CONVENIOS_BANCARIOS = new Acessos(460, "Convênios Bancários");
  static EFETUAR_RECEBIMENTO = new Acessos(461, "Efetuar Recebimento");
  static MOVIMENTO_DIARIO = new Acessos(462, "Movimento Diário");
  static RETIRAR_MULTAS_E_JUROS = new Acessos(464, "Retirar Multas e Juros");
  static CANCELAR_COBRANCA = new Acessos(465, "Cancelar Cobrança");
  static DESATIVAR_COBRANCA = new Acessos(466, "Desativar Cobrança");
  static RETORNO_DO_BANCO = new Acessos(471, "Retorno do Banco");
  static GERAR_COBRANCA_DE_CONTRATOS = new Acessos(472, "Gerar Cobrança de Contratos");
  static IMPRIMIR_BOLETO = new Acessos(473, "Imprimir Boleto");
  static PROMOCAO = new Acessos(486, "Promoção");
  static CONCEDER_PROMOCAO = new Acessos(487, "Conceder Promoção");
  static CONFIGURACAO_DO_BANCO_DE_DADOS = new Acessos(488, "Configuração do Banco de Dados");
  static MOTIVO_DE_CANCELAMENTO_DA_COBRANCA = new Acessos(
    496,
    "Motivo de Cancelamento da Cobrança"
  );
  static DEVOLUCAO_NUMERARIA = new Acessos(498, "Devolução Numerária");
  static PESSOAS = new Acessos(551, "Pessoas");
  static PRODUTO_SERVICO_AVULSO = new Acessos(553, "Produto/Serviço Avulso");
  static DESATIVAR_PRODUTO_SERVICO_AVULSO = new Acessos(554, "Desativar produto/serviço avulso");
  static GERAR_COBRANCA_DE_SERVICOS_PRODUTOS_AVULSOS = new Acessos(
    559,
    "Gerar cobrança de serviços/produtos avulsos"
  );
  static ESTORNAR_COBRANCA = new Acessos(595, "Estornar cobrança");
  static RELATORIO_MOVIMENTO_DIARIO = new Acessos(601, "Relatório movimento diário");
  static RELATORIO_RECEBIMENTOS_POR_EVENTO = new Acessos(602, "Relatório recebimentos por evento");
  static RELATORIO_DE_COBRANCAS_EM_ABERTO = new Acessos(603, "Relatório de cobranças em aberto");
  static RELATORIO_DE_COBRANCAS_VENCIDAS = new Acessos(604, "Relatório de cobranças vencidas");
  static RELATORIO_DE_COBRANCAS_ESTORNADAS = new Acessos(605, "Relatório de cobranças estornadas");
  static RELATORIO_DE_PROMOCOES_CONCEDIDAS = new Acessos(606, "Relatório de promoções concedidas");
  static RELATORIO_DE_COBRANCAS_POR_CENTRO_DE_RESP = new Acessos(
    607,
    "Relatório de cobranças por centro de resp."
  );
  static RELATORIO_DE_RETIRADAS_DE_MULTAS_E_JUROS = new Acessos(
    608,
    "Relatório de retiradas de multas e juros"
  );
  static RELATORIO_DE_DEVOLUCAO_NUMERARIA = new Acessos(609, "Relatório de devolução numerária");
  static RELATORIO_DE_COBRANCAS_NAO_ADM_CR5 = new Acessos(
    617,
    "Relatório de cobranças Não Adm CR5"
  );
  static RELATORIO_DE_COBRANCAS_CANCELADAS = new Acessos(618, "Relatório de cobranças Canceladas");
  static SECRETARIA_PRODUTO_AVULSO = new Acessos(633, "Secretaria \\ Produto Avulso");
  static GERAR_CONTRATOS_AVULSOS = new Acessos(637, "Gerar contratos avulsos");
  static GERAR_COBRANCA_DE_CONTRATOS_NAO_ADMINISTRADOS_CR5 = new Acessos(
    644,
    "Gerar Cobrança de contratos não administrados CR5"
  );
  static CANCELAR_CONTRATO_PRODUTO_AVULSO = new Acessos(646, "Cancelar Contrato Produto Avulso");
  static AGRUPAR_PARCELAS = new Acessos(746, "Agrupar parcelas");
  static CANCELAMENTO_DE_BOLETOS = new Acessos(748, "Cancelamento de boletos");
  static RELATORIO_DEMONSTRATIVO_DE_COBRANCAS_EM_ABERTO = new Acessos(
    755,
    "Relatório Demonstrativo de cobrancas em aberto"
  );
  static ALTERAR_DESCONTO_ANTES_DO_VENCIMENTO_NO_BOLETO = new Acessos(
    773,
    "Alterar Desconto antes do vencimento no boleto"
  );
  static RELATORIO_DE_SALDO_CONTRATUAL_POR_PERIODO = new Acessos(
    774,
    "Relatório de saldo contratual por periodo"
  );
  static RELATORIO_DE_CONTRATOS_ABERTOS = new Acessos(775, "Relatório de Contratos Abertos");
  static RELATORIO_DE_CONTRATOS_CANCELADOS = new Acessos(776, "Relatório de Contratos Cancelados");
  static RELATORIO_DE_CONTRATOS_A_CANCELAR = new Acessos(777, "Relatório de Contratos À Cancelar");
  static RELATORIO_DE_CONTRATOS_COBRADOS = new Acessos(778, "Relatório de Contratos Cobrados");
  static RELATORIO_DE_SALDO_POR_UNIDADE = new Acessos(779, "Relatório de Saldo por unidade");
  static RELATORIO_DE_BOLETOS_CANCELADOS_BAIXADOS = new Acessos(
    780,
    "Relatório de Boletos Cancelados Baixados"
  );
  static CANCELAR_COBRANCAS_DO_CONTRATO = new Acessos(789, "Cancelar cobrancas do contrato");
  static RELATORIO_DE_SALDO_POR_PRODUTO = new Acessos(799, "Relatório de Saldo por Produto");
  static PRODUTO_CONTA_CONTABIL = new Acessos(816, "Produto Conta Contabil");
  static RELATORIO_DE_COBRANCAS_PAGAS_E_A_RECEBER = new Acessos(
    821,
    "Relatório de Cobranças Pagas e A Receber"
  );
  static EXPORTAR_CADIN = new Acessos(827, "Exportar Cadin");
  static MUDAR_STATUS_ADM_CADIN = new Acessos(937, "Mudar Status Adm. Cadin");
  static RELATORIOD_DE_COBRANCAS_EXPORTADAS_CADIN = new Acessos(
    938,
    "Relatóriod de Cobranças exportadas Cadin"
  );
  static CANCELAMENTO_DE_PAGAMENTO = new Acessos(998, "Cancelamento de pagamento");
  static DESCANCELAR_COBRANCAS = new Acessos(1011, "Descancelar Cobranças");
  static SUBSTITUICAO_DE_CONTRATOS = new Acessos(1046, "Substituição de Contratos");
  static EXTINCAO_DE_CONTRATOS = new Acessos(1047, "Extinção de Contratos");
  static VISUALIZAR_SITUACAO_DO_CLIENTE = new Acessos(1048, "Visualizar situação do cliente");
  static FAZER_LOGOFF_NO_SISTEMA = new Acessos(1097, "Fazer logoff no sistema");
  static DEPARA_PRODUTO_CONTA_CONTABIL = new Acessos(1129, "Depara Produto Conta Contabil");
  static INATIVAR_PRODUTO_CONTA_CONTABIL = new Acessos(1130, "Inativar Produto conta contabil");
  static INFORMAR_NUMERO_E_DATA_DE_EMISSAO_NOTA_FISCAL = new Acessos(
    1158,
    "Informar Número e data de emissão nota fiscal"
  );
  static CANCELAR_PAGAMENTO_DEPOIS_DO_DIA_DE_RECEBIMENTO = new Acessos(
    1159,
    "Cancelar pagamento depois do dia de recebimento"
  );
  static REALIZAR_RATEIO_DO_FINANCIAMENTO_EDUCACIONAL = new Acessos(
    1174,
    "Realizar Rateio do financiamento educacional"
  );
  static BAIXAR_FINANCIAMENTO_EDUCACIONAL = new Acessos(1272, "Baixar Financiamento Educacional");
  static RELATORIO_DE_POSICAO_FINANCEIRA = new Acessos(1307, "Relatório de posição financeira");
  static IMPOSTOS = new Acessos(1321, "Impostos");
  static RELATORIO_DE_DESCONTOS = new Acessos(1323, "Relatório de descontos");
  static RELATORIO_ESTATISTICO_DE_BOLETOS_POR_CENTRO_E_CONT = new Acessos(
    1329,
    "Relatório estatístico de boletos por centro e cont"
  );
  static VERIFICA_ARQUIVO_DE_RETORNO_DETALHES = new Acessos(
    1406,
    "Verifica arquivo de retorno detalhes"
  );
  static AUDITORIA = new Acessos(1413, "Auditoria");
  static ADMINISTRADOR_CR5 = new Acessos(1429, "Administrador CR5");
  static RELATORIOS_GRAFICOS = new Acessos(1540, "Relatórios Gráficos");
  static CONCESSAO_DE_DESCONTO = new Acessos(1680, "Concessão de Desconto");
  static ESTORNAR_PARCELA_PARA_CONTRATO_COBRADO = new Acessos(
    1743,
    "Estornar parcela para contrato COBRADO"
  );
  static CORRIGIR_CONVENIO_DE_PARCELA = new Acessos(1745, "Corrigir convênio de parcela");
  static DESC_ESPECIAL_PARA_EMPRESA_ASSOCIADA_A_SINDICATO = new Acessos(
    1754,
    "Desc. Especial para Empresa Associada a Sindicato"
  );
  static DEPARA = new Acessos(1755, "DePara");
  static VISUALIZAR_GRAFICO_DE_PAGO_A_MENOR = new Acessos(
    1774,
    "Visualizar Gráfico de Pago a Menor"
  );
  static OLYMPIA_CONCEDE_DESC_PARA_CATEIRA_SOCIAL = new Acessos(
    1775,
    "Olympia - Concede desc. para Cateira Social"
  );
  static NEGOCIACAO_CIN = new Acessos(1777, "Negociação CIN");
  static CADASTRO_DE_IMPRESSORAS = new Acessos(1792, "Cadastro de impressoras");
  static GERAR_REMESSA_CNBA_240 = new Acessos(1793, "Gerar remessa CNBA 240");
  static GERAR_REMESSA_DE_SMS = new Acessos(1797, "Gerar Remessa de SMS");
  static VISUALIZAR_DASHBOARD = new Acessos(1813, "Visualizar Dashboard");
  static GERACAO_DE_COBRANCAS_EM_LOTE = new Acessos(1816, "Geração de Cobranças em Lote");
  static ADMNISTRADOR_CADIN = new Acessos(1855, "Admnistrador CADIN");
  static EXPORTAR_PARA_CADIN = new Acessos(1858, "Exportar para Cadin");
  static CANCELAR_CONTRATO_NO_7o_DIA_APOS_VENCIMENTO = new Acessos(
    1860,
    "Cancelar contrato no 7º dia após vencimento"
  );
  static TERMINAIS_TEF = new Acessos(1864, "Terminais TEF");
  static DESCONTO_COVID = new Acessos(1866, "Desconto covid");
  static INFORMACOES_E_COMMERCE = new Acessos(1868, "Informações E-commerce");
  static ALTERAR_DATA_DE_ESTORNO = new Acessos(1869, "Alterar data de estorno");
  static TEF_ESTORNO = new Acessos(1870, "TEF Estorno");
  static MUDAR_STATUS_CONTRATO_E_PARCELAS = new Acessos(1872, "Mudar status Contrato e Parcelas");
  static ADMINISTRAR_FINANCIAMENTO = new Acessos(1873, "Administrar Financiamento");
  static MUDAR_DATA_DE_VIGENCIA_DA_COBRANCA = new Acessos(
    1874,
    "Mudar Data de Vigência da Cobrança"
  );
  static DESVINCULAR_RESPONSAVEL_FINANCEIRO = new Acessos(
    1875,
    "Desvincular Responsável Financeiro"
  );
  static ALTERAR_PORCENTAGEM_DO_ESTORNO_DA_MULTA_RESCISORIA = new Acessos(
    1876,
    "Alterar Porcentagem do Estorno da Multa Rescisoria"
  );
  static ALTERAR_O_NOME_OU_RAZAO_SOCIAL_DO_CLIENTE = new Acessos(
    1877,
    "Alterar o nome ou razão social do cliente"
  );
  static CANCELAR_CONTRATO = new Acessos(1880, "Cancelar Contrato");
  static BAIXA_PROTHEUS = new Acessos(1882, "Baixa Protheus");
  static CONSULTAR_PIX = new Acessos(1889, "Consultar PIX");
  static ADMINISTRADOR_NOTA_FISCAL = new Acessos(1891, "Administrador Nota Fiscal");
  static CRIACAO_DE_CONTRATO_EM_REDE = new Acessos(1892, "Criação de Contrato em Rede");
  static CONCILIACAO_CR5_X_PROTHEUS = new Acessos(1893, "Conciliação CR5 X Protheus");
  static CANCELAR_TITULO_NO_PROTHEUS = new Acessos(2060, "Cancelar Titulo no Protheus");
  static DADOS_TICKETLOG = new Acessos(2062, "Dados Ticketlog");
  static RECORRENCIA = new Acessos(2092, "Recorrência");
  static ADICIONAR_PARCELA = new Acessos(2099, "Adicionar Parcela");
  static REMOVER_VINCULO = new Acessos(2106, "Boleto Remover Vinculo");

  get idAcesso() {
    return this._idAcesso;
  }

  get nomeAcesso() {
    return this._nomeAcesso;
  }
}

export type TipoValidacaoPermissao = "TEM_TODAS_AS_PERMISSOES" | "TEM_UMA_PERMISSAO";

export interface ValidacaoAcesso {
  tipo: TipoValidacaoPermissao;
  acessos: Acessos[];
}

export function validaPermissoes(
  validacaoNecessaria: ValidacaoAcesso | undefined,
  direitos: OperadorDireitoDTO[]
): boolean {
  if (!validacaoNecessaria) {
    return true;
  }

  const acessosNecessarios = validacaoNecessaria.acessos;

  // Procura quais permissões necessárias o usuário em questão possui
  const permissoesEncontradas = acessosNecessarios.filter((acesso) => {
    const acessoDoOperador = direitos.find((direito) => direito.acesso.acesso === acesso.idAcesso);

    return acessoDoOperador?.liberado;
  });

  // Se o operador precisa de todas as permissões, o tamanho das listas deve ser igual
  if (validacaoNecessaria.tipo === "TEM_TODAS_AS_PERMISSOES") {
    return permissoesEncontradas.length === acessosNecessarios.length;
  }

  // Se o operador precisa de somente uma permissão, basta a lista não estar vazia
  return permissoesEncontradas.length > 0;
}

export function criaValidacaoTodas(acessos: Acessos[]): ValidacaoAcesso {
  return {
    tipo: "TEM_TODAS_AS_PERMISSOES",
    acessos,
  };
}

export function criaValidacaoUma(acessos: Acessos[]): ValidacaoAcesso {
  return {
    tipo: "TEM_UMA_PERMISSAO",
    acessos,
  };
}
