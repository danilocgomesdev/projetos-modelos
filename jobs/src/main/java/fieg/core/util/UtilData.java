

package fieg.core.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.*;

public final class UtilData {
    public static final String REGEX_DATA_BANCO = "[1-2]\\d{3}-\\d{2}-\\d{2}";
    public static final Integer JANEIRO = 0;
    public static final String PADRAO_DATA_BANCO = "yyyy-MM-dd";
    private static SimpleDateFormat formatter = (SimpleDateFormat) DateFormat.getDateTimeInstance(1, 1);
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
        return frmtDt.format(new Date());
    }

    public static List<List<Object>> mapToList(Map<String, Object> map) {
        List<List<Object>> list = new ArrayList<>();
        map.forEach((k, v) -> {
            list.add(Arrays.asList(k, v));
        });
        return list;
    }

    public static String stringLimit(String str, int max) {
        if (str == null) return null;
        return str.substring(0, Math.min(str.length(), max));
    }

    public static List<List<Object>> mapToList(List<Map<String, Object>> mapList) {
        List<List<Object>> list = new ArrayList<>();
        mapList.forEach(map -> {
            map.forEach((k, v) -> {
                list.add(Arrays.asList(k, v));
            });
        });
        return list;
    }

    public static int intervalodeDatas(Date dataAtual, Date data) {
        Calendar ant = Calendar.getInstance();
        Calendar dep = Calendar.getInstance();
        int dias = 0;
        ant.setTime(data);
        dep.setTime(dataAtual);

        while (ant.before(dep)) {
            ++dias;
            ant.add(Calendar.DATE, 1);
        }

        return dias;
    }

    public static int calcularIntervaloMeses(Date dateInicial, Date dateFinal) {
        Calendar calIni = Calendar.getInstance();
        calIni.setTime(dateInicial);
        Calendar calFim = Calendar.getInstance();
        calFim.setTime(dateFinal);
        int qtdMesAno = calFim.get(Calendar.YEAR) - calIni.get(Calendar.YEAR);
        int qtdMes = calFim.get(Calendar.MONTH) - calIni.get(Calendar.MONTH);
        return qtdMesAno * 12 + qtdMes;
    }

    public static Boolean betweenMonth(Date date, int mesInicial, int mesfinal) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        boolean isMesesValido = mesInicial >= 1 && mesInicial <= 12 && mesfinal >= 1 && mesfinal <= 12;
        boolean isDataDentroDoPeriodo = cal.get(Calendar.MONTH) + 1 >= mesInicial && cal.get(Calendar.MONTH) + 1 <= mesfinal;
        return isMesesValido && isDataDentroDoPeriodo;
    }

    public static Boolean betweenYear(Date date, int anoInicial, int anofinal) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.YEAR) >= anoInicial && cal.get(Calendar.YEAR) <= anofinal;
    }

    public static int calcularIntervaloDias(Date dateInicial, Date dateFinal) {
        LocalDate dtInicio = dateInicial.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate dtFim = dateFinal.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        long dias = ChronoUnit.DAYS.between(dtInicio, dtFim);
        return (int) dias;
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
            saida = Integer.parseInt(ano.format(data)) + "-" + Integer.parseInt(mes.format(data)) + "-" + Integer.parseInt(dia.format(data));
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
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    public static Date retornaAntsOntemEmDateZerado() {
        int numeroDiasParaSubtrair = 2;
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        cal.add(Calendar.DATE, numeroDiasParaSubtrair);
        return cal.getTime();
    }

    public static boolean verificaSeEstaVencido(Date dataVencimento) {
        try {
            LocalDate dataAtual = LocalDate.now();
            LocalDate dtVenc;
            if (dataVencimento instanceof java.sql.Date) {
                dtVenc = ((java.sql.Date) dataVencimento).toLocalDate();
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
                dtVenc = ((java.sql.Date) dataVencimento).toLocalDate();
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

    public static String dataPorExtenso(String cidade, Date dt) {
        int d = dt.getDate();
        int m = dt.getMonth() + 1;
        int a = dt.getYear() + 1900;
        Calendar data = new GregorianCalendar(a, m - 1, d);
        int ds = data.get(Calendar.DAY_OF_WEEK);
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

            try {
                return frmtDtMesAno.parse(data);
            } catch (ParseException var3) {
                throw new RuntimeException(var3);
            }
        } else {
            return null;
        }
    }

    public static Integer retornaIdade(Date dtnNascimento) {
        Calendar dataNascimento = Calendar.getInstance();
        dataNascimento.setTime(dtnNascimento);
        Calendar hoje = Calendar.getInstance();
        int idade = hoje.get(Calendar.YEAR) - dataNascimento.get(Calendar.YEAR);
        if (hoje.get(Calendar.MONTH) < dataNascimento.get(Calendar.MONTH)) {
            --idade;
        } else if (hoje.get(Calendar.MONTH) == dataNascimento.get(Calendar.MONTH) && hoje.get(Calendar.DATE) < dataNascimento.get(Calendar.DATE)) {
            --idade;
        }

        return idade;
    }

    public static String formatarData(Date data) {
        return data != null ? frmtDt.format(data) : null;
    }

    public static String formatarDatayyyyMMdd(Date data) {
        return data != null ? frmtDtyyyyMMdd.format(data) : null;
    }

    public static String formatarDataHoraBD(Date data) {
        return data != null ? frmDtHrsInicialBd.format(data) : null;
    }

    public static String formatarDataMM_yyyy(Date data) {
        return data != null ? frmtDtMM_dd.format(data) : null;
    }

    public static String formatarDataHora(Date data) {
        return data != null ? frmDtHrsPort.format(data) : null;
    }

    public static String formatarDataBanco(Date data) {
        return data != null ? frmtDtBanco.format(data) : null;
    }

    public static String formatarDataHoraBanco(Date data) {
        return data != null ? frmDtHrs.format(data) : null;
    }

    public static Date adicionarMes(Date data, int qtdMeses) {
        Calendar calendar = Calendar.getInstance(new Locale("pt", "BR"));
        calendar.setTime(data);
        calendar.add(Calendar.MONTH, qtdMeses);
        return calendar.getTime();
    }

    public static Date adicionarDias(Date data, int qtdDias) {
        Calendar calendar = Calendar.getInstance(new Locale("pt", "BR"));
        calendar.setTime(data);
        calendar.add(Calendar.DATE, qtdDias);
        return calendar.getTime();
    }

    public static int obterDiaDoMes(Date data) {
        Calendar calendar = Calendar.getInstance(new Locale("pt", "BR"));
        return calendar.get(Calendar.DATE);
    }

    public static int obterAnoAtual() {
        Calendar calendar = Calendar.getInstance(new Locale("pt", "BR"));
        return calendar.get(Calendar.YEAR);
    }

    public static int obterAno(Date data) {
        Calendar calendar = Calendar.getInstance(new Locale("pt", "BR"));
        calendar.setTime(data);
        return calendar.get(Calendar.YEAR);
    }

    public static boolean mesAnterior(Date inicioVigencia, Date hoje) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(inicioVigencia);
        int anoVig = cal1.get(Calendar.YEAR);
        int mes1 = cal1.get(Calendar.MONTH);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(hoje);
        int ano2 = cal2.get(Calendar.YEAR);
        int mes2 = cal2.get(Calendar.MONTH);
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
        return calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY;
    }

    public static boolean isDomingo(Date data) {
        Calendar calendar = Calendar.getInstance(new Locale("pt", "BR"));
        calendar.setTime(data);
        return calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY;
    }

    public static boolean verificarFimDeSemanaEstendido(Date data) {
        Calendar calendar = Calendar.getInstance(new Locale("pt", "BR"));
        calendar.setTime(data);
        int diaDaSemana = calendar.get(Calendar.DAY_OF_WEEK);
        return diaDaSemana == Calendar.FRIDAY || diaDaSemana == Calendar.SATURDAY || diaDaSemana == Calendar.SUNDAY;
    }

    public static int getHoraDoDia(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.HOUR_OF_DAY);
    }

    public static Date getPrimeiroDiaDoMes(Date data) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(data);
        cal1.set(Calendar.DATE, 1);
        return cal1.getTime();
    }

    public static Date getUltimoDiaDoMes(Date data) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(data);
        cal1.set(Calendar.DAY_OF_MONTH, cal1.getActualMaximum(Calendar.DAY_OF_MONTH));
        return cal1.getTime();
    }

    public static Date alterDia(Date data, int dia) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(data);
        cal.set(Calendar.DATE, dia);
        return cal.getTime();
    }

    public static Date alterarDataParaProximoDiaUtil(Date data) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(data);
        if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            calendar.add(Calendar.DATE, 1);
        } else if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
            calendar.add(Calendar.DATE, 2);
        }

        return calendar.getTime();
    }

    public static Date alterarMes(Date data, int dia) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(data);
        cal.set(Calendar.MONTH, dia);
        return cal.getTime();
    }

    public static Date alterarMesEAno(Date data, int dia, int ano) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(data);
        cal.set(Calendar.MONTH, dia);
        cal.set(Calendar.YEAR, ano);
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
                return formatter.parse(data);
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
                return formatter.parse(data);
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
        gc.set(Calendar.HOUR_OF_DAY, hourOfDay);
        gc.set(Calendar.MINUTE, minute);
        gc.set(Calendar.SECOND, second);
        gc.set(Calendar.MILLISECOND, ms);
        return gc.getTime();
    }

    public static Date definirUltimoDiaMes(Date data) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(data);
        calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));
        return calendar.getTime();
    }

    public static Date localDateParaDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    public static LocalDate dateParaLocalDate(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public static Date definirSomentaData(Date date) {
        LocalDate localDate = dateParaLocalDate(date);
        return localDateParaDate(localDate);
    }

}
