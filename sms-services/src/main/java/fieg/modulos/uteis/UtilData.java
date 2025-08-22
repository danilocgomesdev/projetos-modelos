

package fieg.modulos.uteis;


import io.micrometer.core.instrument.util.StringUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public final class UtilData {
    public static final String REGEX_DATA_BANCO = "[1-2]\\d{3}-\\d{2}-\\d{2}";
    public static final Integer JANEIRO = 0;
    public static final String PADRAO_DATA_BANCO = "yyyy-MM-dd";
    private static SimpleDateFormat formatter = (SimpleDateFormat)DateFormat.getDateTimeInstance(1, 1);
    public static final SimpleDateFormat frmDtHrs = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    public static final SimpleDateFormat frmtDt = new SimpleDateFormat("dd/MM/yyyy");
    public static final SimpleDateFormat frmtDtyyyyMMdd = new SimpleDateFormat("yyyyMMdd");
    public static final SimpleDateFormat frmtDtMM_dd = new SimpleDateFormat("MM/yyyy");
    public static final SimpleDateFormat frmtDtMesAno = new SimpleDateFormat("MM/yyyy");
    public static final SimpleDateFormat frmtDtEn = new SimpleDateFormat("MM/dd/yyyy");
    public static final SimpleDateFormat frmtDtBanco = new SimpleDateFormat("yyyy-MM-dd");
    public static final SimpleDateFormat frmtHrs = new SimpleDateFormat("HH:mm");
    public static final SimpleDateFormat frmDtHrsPort = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    public static final SimpleDateFormat frmDtHrsInicial = new SimpleDateFormat("yyyy/MM/dd 00:00:00");
    public static final SimpleDateFormat frmDtHrsInicialBd = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
    public static final SimpleDateFormat frmDtHrsFinal = new SimpleDateFormat("yyyy/MM/dd 23:59:59");
    public static final SimpleDateFormat frmDtHrsFinalBD = new SimpleDateFormat("yyyy-MM-dd 23:59:59");
    public static final SimpleDateFormat retornaDia = new SimpleDateFormat("dd");
    public static final SimpleDateFormat retornaMes = new SimpleDateFormat("MM");
    public static final SimpleDateFormat retornaAno = new SimpleDateFormat("yyyy");

    public UtilData() {
    }

    public static String getDataAtual() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        return formatter.format(new Date());
    }

    public static Date hoje() {
        return Calendar.getInstance().getTime();
    }


    public static int intervalodeDatas(Date dataAtual, Date data) {
        Calendar ant = Calendar.getInstance();
        Calendar dep = Calendar.getInstance();
        int dias = 0;
        ant.setTime(data);
        dep.setTime(dataAtual);

        while(ant.before(dep)) {
            ++dias;
            ant.add(5, 1);
        }

        return dias;
    }

    public static int calcularIntervaloMes(Date dateInicial, Date dateFinal) {
        Calendar calIni = Calendar.getInstance();
        calIni.setTime(dateInicial);
        Calendar calFim = Calendar.getInstance();
        calFim.setTime(dateFinal);
        int qtdMesAno = calFim.get(1) - calIni.get(1);
        int qtdMes = calFim.get(2) - calIni.get(2);
        int qtde = qtdMesAno * 12 + qtdMes;
        return qtde;
    }

    public static Boolean betweenMonth(Date date, int mesInicial, int mesfinal) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        boolean isMesesValido = mesInicial >= 1 && mesInicial <= 12 && mesfinal >= 1 && mesfinal <= 12;
        boolean isDataDentroDoPeriodo = cal.get(2) + 1 >= mesInicial && cal.get(2) + 1 <= mesfinal;
        return isMesesValido && isDataDentroDoPeriodo ? true : false;
    }

    public static Boolean betweenYear(Date date, int anoInicial, int anofinal) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        boolean isDataDentroDoPeriodo = cal.get(1) >= anoInicial && cal.get(1) <= anofinal;
        return !isDataDentroDoPeriodo ? false : true;
    }

    public static int calcularIntervaloParcelas(Date dateInicial, Date dateFinal) {
        return calcularIntervaloMes(dateInicial, dateFinal) + 1;
    }

    public static int calcularIntervaloDias(Date dateInicial, Date dateFinal) {
        LocalDate dtInicio = dateInicial.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate dtFim = dateFinal.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        long dias = ChronoUnit.DAYS.between(dtInicio, dtFim);
        return (int)dias;
    }

    public static String getDataPorExtenso() {
        DateFormat df = DateFormat.getDateInstance(1, new Locale("pt", "BR"));
        return df.format(new Date());
    }

    public static Integer getAnoAtual() {
        return getAno(new Date());
    }

    public static int getMesAtual() {
        SimpleDateFormat formatter = new SimpleDateFormat("MM");
        return Integer.parseInt(formatter.format(new Date()));
    }

    public static int getDiaAtual() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd");
        return Integer.parseInt(formatter.format(new Date()));
    }

    public static String FormatarStrData(String entrada) {
        SimpleDateFormat formatoEntrada = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat ano = new SimpleDateFormat("yyyy");
        SimpleDateFormat mes = new SimpleDateFormat("MM");
        SimpleDateFormat dia = new SimpleDateFormat("dd");
        String saida = "";

        try {
            Date data = formatoEntrada.parse(entrada);
            saida = "" + Integer.parseInt(ano.format(data)) + "-" + Integer.parseInt(mes.format(data)) + "-" + Integer.parseInt(dia.format(data));
            return saida;
        } catch (ParseException var8) {
            var8.printStackTrace();
            return "";
        }
    }

    public static int retornaDia(Date data) {
        return Integer.parseInt(retornaDia.format(data));
    }

    public static int retornaMes(Date data) {
        return Integer.parseInt(retornaMes.format(data));
    }

    public static Integer retornaAno(Date data) {
        return Integer.parseInt(retornaAno.format(data));
    }

    public static Date retornaHojeEmDateZerado() {
        Calendar cal = Calendar.getInstance();
        cal.set(11, 0);
        cal.set(12, 0);
        cal.set(13, 0);
        cal.set(14, 0);
        Date today = cal.getTime();
        return today;
    }

    public static Date retornaAntsOntemEmDateZerado() {
        int numeroDiasParaSubtrair = 2;
        Calendar cal = Calendar.getInstance();
        cal.set(11, 0);
        cal.set(12, 0);
        cal.set(13, 0);
        cal.set(14, 0);
        cal.add(5, numeroDiasParaSubtrair);
        Date antesDeOntem = cal.getTime();
        return antesDeOntem;
    }

    public static boolean verificaSeEstaVencido(Date dataVencimento) {
        try {
            LocalDate dataAtual = LocalDate.now();
            LocalDate dtVenc;
            if (dataVencimento instanceof java.sql.Date) {
                dtVenc = ((java.sql.Date)dataVencimento).toLocalDate();
            } else {
                dtVenc = dataVencimento.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            }

            return dtVenc.isBefore(dataAtual);
        } catch (Exception var3) {
            throw new RuntimeException("Erro ao converter data. Detalhe: " + var3.getMessage());
        }
    }


    public static boolean verificaVencidoDtPagamento(Date dataVencimento, Date dataPagamento) {
        try {
            LocalDate dtVenc;
            if (dataVencimento instanceof java.sql.Date) {
                dtVenc = ((java.sql.Date)dataVencimento).toLocalDate();
            } else {
                dtVenc = dataVencimento.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            }

            LocalDate dtPagamento = dataPagamento.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            return dtVenc.isBefore(dtPagamento);
        } catch (Exception var4) {
            throw new RuntimeException("Erro ao converter data. Detalhe: " + var4.getMessage());
        }
    }

    public static String NomeDoMes(int i, int tipo) {
        String[] mes = new String[]{"janeiro", "fevereiro", "março", "abril", "maio", "junho", "julho", "agosto", "setembro", "outubro", "novembro", "dezembro"};
        return tipo == 0 ? mes[i - 1] : mes[i - 1].substring(0, 3);
    }

    public static String DiaDaSemana(int i, int tipo) {
        String[] diasem = new String[]{"domingo", "segunda-feira", "terça-feira", "quarta-feira", "quinta-feira", "sexta-feira", "sábado"};
        return tipo == 0 ? diasem[i - 1] : diasem[i - 1].substring(0, 3);
    }

    public static String DataPorExtenso(String cidade, Date dt) {
        int d = dt.getDate();
        int m = dt.getMonth() + 1;
        int a = dt.getYear() + 1900;
        Calendar data = new GregorianCalendar(a, m - 1, d);
        int ds = data.get(7);
        return cidade + ", " + d + " de " + NomeDoMes(m, 0) + " de " + a + " (" + DiaDaSemana(ds, 1) + ").";
    }

    public static Date converteStringEmData(String entrada) {
        String dia = entrada.substring(0, 2);
        String mes = entrada.substring(2, 4);
        String ano = entrada.substring(4, 8);
        String retorno = dia + "/" + mes + "/" + ano;

        try {
            return formataData(retorno);
        } catch (Exception var6) {
            var6.printStackTrace();
            return null;
        }
    }

    public static Date formataData(String data) {
        if (data != null && !data.equals("")) {
            Date date = null;

            try {
                DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                date = formatter.parse(data);
            } catch (ParseException var3) {
                date = formatarDataBancoString(data);
            }

            return date;
        } else {
            return null;
        }
    }

    public static Date formatarDataBancoString(String data) {
        if (data != null && data.trim().length() != 0) {
            Date date = null;

            try {
                date = frmtDtBanco.parse(data);
                return date;
            } catch (ParseException var3) {
                throw new RuntimeException(var3);
            }
        } else {
            return null;
        }
    }

    public static Date formatarDataMesAnoString(String data) {
        if (data != null && !data.equals("")) {
            Date date = null;

            try {
                date = frmtDtMesAno.parse(data);
                return date;
            } catch (ParseException var3) {
                throw new RuntimeException(var3);
            }
        } else {
            return null;
        }
    }

    public static Date operacoesData(Date data, String tipoCampo, Integer valor) {
        Calendar gc = Calendar.getInstance();
        gc.setTime(data);
        Date retorno = null;
        if (tipoCampo.equals("dias")) {
            gc.add(6, valor);
            return gc.getTime();
        } else if (tipoCampo.equals("meses")) {
            gc.add(2, valor);
            return gc.getTime();
        } else if (tipoCampo.equals("ano")) {
            gc.add(1, valor);
            return gc.getTime();
        } else {
            return null;
        }
    }



    public static Integer retornaIdade(Date dtnNascimento) {
        Calendar dataNascimento = Calendar.getInstance();
        dataNascimento.setTime(dtnNascimento);
        Calendar hoje = Calendar.getInstance();
        int idade = hoje.get(1) - dataNascimento.get(1);
        if (hoje.get(2) < dataNascimento.get(2)) {
            --idade;
        } else if (hoje.get(2) == dataNascimento.get(2) && hoje.get(5) < dataNascimento.get(5)) {
            --idade;
        }

        return idade;
    }

    public static String formatarData(Date data) {
        return data != null && !data.equals("") ? frmtDt.format(data) : null;
    }

    public static String formatarDatayyyyMMdd(Date data) {
        return data != null && !data.equals("") ? frmtDtyyyyMMdd.format(data) : null;
    }

    public static String formatarDataMM_yyyy(Date data) {
        return data != null && !data.equals("") ? frmtDtMM_dd.format(data) : null;
    }

    public static String formatarDataHora(Date data) {
        return data != null && !data.equals("") ? frmDtHrsPort.format(data) : null;
    }

    public static String formatarDataBanco(Date data) {
        return data != null && !data.equals("") ? frmtDtBanco.format(data) : null;
    }

    public static String formatarDataHoraBanco(Date data) {
        return data != null && !data.equals("") ? frmDtHrs.format(data) : null;
    }

    public static Date adicionarMes(Date data, int qtdMeses) {
        Calendar calendar = Calendar.getInstance(new Locale("pt", "BR"));
        calendar.setTime(data);
        calendar.add(2, qtdMeses);
        return calendar.getTime();
    }

    public static Date adicionarDias(Date data, int qtdDias) {
        Calendar calendar = Calendar.getInstance(new Locale("pt", "BR"));
        calendar.setTime(data);
        calendar.add(5, qtdDias);
        return calendar.getTime();
    }

    public static int obterDiaDoMes(Date data) {
        Calendar calendar = Calendar.getInstance(new Locale("pt", "BR"));
        int dia = calendar.get(5);
        return dia;
    }

    public static int obterAnoAtual() {
        Calendar calendar = Calendar.getInstance(new Locale("pt", "BR"));
        int ano = calendar.get(1);
        return ano;
    }

    public static int obterAno(Date data) {
        Calendar calendar = Calendar.getInstance(new Locale("pt", "BR"));
        calendar.setTime(data);
        int ano = calendar.get(1);
        return ano;
    }

    public static boolean mesAnterior(Date inicioVigencia, Date hoje) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(inicioVigencia);
        int anoVig = cal1.get(1);
        int mes1 = cal1.get(2);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(hoje);
        int ano2 = cal2.get(1);
        int mes2 = cal2.get(2);
        boolean mesmoAno = anoVig == ano2 && mes1 < mes2;
        boolean anoAnterior = anoVig < ano2;
        return mesmoAno || anoAnterior;
    }

    public static boolean verificarFimDeSemana(Date data) {
        Calendar calendar = Calendar.getInstance(new Locale("pt", "BR"));
        calendar.setTime(data);
        return isSabado(data) || isDomingo(data);
    }

    public static boolean isSabado(Date data) {
        Calendar calendar = Calendar.getInstance(new Locale("pt", "BR"));
        calendar.setTime(data);
        return calendar.get(7) == 7;
    }

    public static boolean isDomingo(Date data) {
        Calendar calendar = Calendar.getInstance(new Locale("pt", "BR"));
        calendar.setTime(data);
        return calendar.get(7) == 1;
    }

    public static boolean verificarFimDeSemanaEstendido(Date data) {
        Calendar calendar = Calendar.getInstance(new Locale("pt", "BR"));
        calendar.setTime(data);
        return calendar.get(7) == 6 || calendar.get(7) == 7 || calendar.get(7) == 1;
    }

    public static int getHoraDoDia(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int hora = cal.get(11);
        return hora;
    }

    public static Date getPrimeiroDiaDoMes(Date data) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(data);
        cal1.set(5, 1);
        return cal1.getTime();
    }

    public static Date getUltimoDiaDoMes(Date data) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(data);
        cal1.set(5, cal1.getActualMaximum(5));
        return cal1.getTime();
    }

    public static Date alterDia(Date data, int dia) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(data);
        cal.set(5, dia);
        return cal.getTime();
    }

    public static Date alterarDataParaProximoDiaUtil(Date data) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(data);
        if (calendar.get(7) == 1) {
            calendar.add(5, 1);
        } else if (calendar.get(7) == 7) {
            calendar.add(5, 2);
        }

        return calendar.getTime();
    }

    public static Date alterarMes(Date data, int dia) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(data);
        cal.set(2, dia);
        return cal.getTime();
    }

    public static Date alterarMesEAno(Date data, int dia, int ano) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(data);
        cal.set(2, dia);
        cal.set(1, ano);
        return cal.getTime();
    }

    public static Date reduzirAnos(Date data, int qtdAnos) {
        return reduzir(data, qtdAnos, 1);
    }

    public static Date reduzirMes(Date data, int qtdMeses) {
        return reduzir(data, qtdMeses, 2);
    }

    private static Date reduzir(Date data, int qtdMeses, int periodo) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(data);
        cal.add(periodo, -qtdMeses);
        return cal.getTime();
    }

    public static Integer getAno(Date data) {
        if (data == null) {
            throw new IllegalArgumentException("O parâmetro data não pode ser nulo.");
        } else {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
            return Integer.parseInt(formatter.format(data));
        }
    }

    public static boolean isDataValida(Date data) {
        if (data == null) {
            return false;
        } else {
            int ano = obterAno(data);
            return ano > 1753;
        }
    }

    public static Date definirPrimeiroDiaMes(Date data) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(data);
        cal.set(5, 1);
        return cal.getTime();
    }

    public static Date converterData(String data) {
        if (StringUtils.isBlank(data)) {
            return null;
        } else {
            try {
                DateFormat formatter = new SimpleDateFormat("yyyyMMdd");
                Date date = formatter.parse(data);
                return date;
            } catch (ParseException var3) {
                throw new RuntimeException("Erro ao converter data: " + data);
            }
        }
    }

    public static Date converterDataReduzida(String data) {
        if (StringUtils.isBlank(data)) {
            return null;
        } else {
            try {
                DateFormat formatter = new SimpleDateFormat("yyMMdd");
                Date date = formatter.parse(data);
                return date;
            } catch (ParseException var3) {
                throw new RuntimeException("Erro ao converter data: " + data);
            }
        }
    }

    public static Date zeroTime(Date date) {
        return setTime(date, 0, 0, 0, 0);
    }

    public static Date LastDateTime(Date date) {
        return setTime(date, 23, 59, 59, 59);
    }

    public static Date setTime(Date date, int hourOfDay, int minute, int second, int ms) {
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(date);
        gc.set(11, hourOfDay);
        gc.set(12, minute);
        gc.set(13, second);
        gc.set(14, ms);
        return gc.getTime();
    }

    public static Date definirUltimoDiaMes(Date data) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(data);
        calendar.set(5, calendar.getActualMaximum(5));
        return calendar.getTime();
    }
    public static Integer diasDeAtraso(Date dataVencimentoCobranca, Date dataPagementoCobranca) {
        Integer dias = intervalodeDatas(dataPagementoCobranca, dataVencimentoCobranca) < 0 ? 0 : intervalodeDatas(dataPagementoCobranca, dataVencimentoCobranca);
        return dias;
    }

    public static boolean depois(Date date1, Date date2)  {
        Date dt1 = definirSomentaData(date1);
        Date dt2 = definirSomentaData(date2);
        return dt1.after(dt2);
    }
    private static Date definirSomentaData(Date date) {
        try {
            SimpleDateFormat  parserData = new SimpleDateFormat("ddMMyyyy");
            return DateFormat.getDateInstance().parse(String.valueOf(parserData));
        } catch (ParseException e) {
            return date;
        }

    }
}
