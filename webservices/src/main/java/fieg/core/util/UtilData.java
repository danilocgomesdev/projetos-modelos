package fieg.core.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.zone.ZoneRules;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import static java.time.temporal.ChronoUnit.DAYS;

public class UtilData {
    public static final String REGEX_DATA_BANCO = "[1-2]\\d{3}-\\d{2}-\\d{2}";

    public static final Integer JANEIRO = 0;
    public static final String PADRAO_DATA_BANCO = "yyyy-MM-dd";

    private static SimpleDateFormat formatter = (SimpleDateFormat) DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG);

    public final static SimpleDateFormat frmDtHrs = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    public final static SimpleDateFormat frmtDt = new SimpleDateFormat("dd/MM/yyyy");

    public final static SimpleDateFormat frmtDtyyyyMMdd = new SimpleDateFormat("yyyyMMdd");

    public final static SimpleDateFormat frmtDtMM_dd = new SimpleDateFormat("MM/yyyy");

    public final static SimpleDateFormat frmtDtMesAno = new SimpleDateFormat("MM/yyyy");

    public final static SimpleDateFormat frmtDtEn = new SimpleDateFormat("MM/dd/yyyy");

    public final static SimpleDateFormat frmtDtBanco = new SimpleDateFormat(PADRAO_DATA_BANCO);

    public final static SimpleDateFormat frmtHrs = new SimpleDateFormat("HH:mm");

    public final static SimpleDateFormat frmDtHrsPort = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    public final static SimpleDateFormat frmDtHrsInicial = new SimpleDateFormat("yyyy/MM/dd 00:00:00");

    public final static SimpleDateFormat frmDtHrsInicialBd = new SimpleDateFormat("yyyy-MM-dd 00:00:00");

    public final static SimpleDateFormat frmDtHrsFinal = new SimpleDateFormat("yyyy/MM/dd 23:59:59");

    public final static SimpleDateFormat frmDtHrsFinalBD = new SimpleDateFormat("yyyy-MM-dd 23:59:59");

    public final static SimpleDateFormat retornaDia = new SimpleDateFormat("dd");

    public final static SimpleDateFormat retornaMes = new SimpleDateFormat("MM");

    public final static SimpleDateFormat retornaAno = new SimpleDateFormat("yyyy");

    public static String getDataAtual() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        return formatter.format(new Date());
    }

    public static int intervalodeDatas(Date dataAtual, Date data) {
        Calendar ant = Calendar.getInstance();
        Calendar dep = Calendar.getInstance();
        int dias = 0;

        ant.setTime(data);
        dep.setTime(dataAtual);

        while (ant.before(dep)) {
            dias++;
            ant.add(Calendar.DATE, 1);
        }

        return dias;
    }

    /**
     * Ex: Para o periodo de agosto a novembro o intervalo será de 3 meses
     * <p>
     * dateInicial dateFinal
     *
     * @return Retorna a quantidade de meses entre o período informado
     */
    public static int calcularIntervaloMes(Date dateInicial, Date dateFinal) {
        Calendar calIni = Calendar.getInstance();
        calIni.setTime(dateInicial);

        Calendar calFim = Calendar.getInstance();
        calFim.setTime(dateFinal);
        int qtdMesAno = calFim.get(Calendar.YEAR) - calIni.get(Calendar.YEAR);
        int qtdMes = calFim.get(Calendar.MONTH) - calIni.get(Calendar.MONTH);
        int qtde = (qtdMesAno * 12) + qtdMes;
        return qtde;
    }

    /**
     * Ex: 05-05-2019 esta entes os meses de Janeiro e julho
     *
     * @param date       data para verificar
     * @param mesInicial INT mes inicial
     * @param mesfinal   INT mes final
     * @return True se data dentro do periodo, False se data fora do periodo
     */
    public static Boolean betweenMonth(Date date, int mesInicial, int mesfinal) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        boolean isMesesValido = (mesInicial >= 1 && mesInicial <= 12) && (mesfinal >= 1 && mesfinal <= 12);
        boolean isDataDentroDoPeriodo = (cal.get(Calendar.MONTH) + 1) >= mesInicial && (cal.get(Calendar.MONTH) + 1) <= mesfinal;
        if (!isMesesValido || !isDataDentroDoPeriodo) {
            return false;
        }
        return true;
    }

    /**
     * Ex: 05-05-2019 esta entes os anos de 2018 e 2020
     *
     * @param date       data para verificar
     * @param anoInicial INT ano inicial
     * @param anofinal   INT ano final
     * @return True se data dentro do periodo, False se data fora do periodo
     */
    public static Boolean betweenYear(Date date, int anoInicial, int anofinal) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        boolean isDataDentroDoPeriodo = cal.get(Calendar.YEAR) >= anoInicial && cal.get(Calendar.YEAR) <= anofinal;
        if (!isDataDentroDoPeriodo) {
            return false;
        }
        return true;
    }


    /**
     * Ex: Para o periodo de agosto a novembro o intervalo será de 4 meses
     * <p>
     * dateInicial dateFinal
     *
     * @return Retorna a quantidade de meses entre o período informado
     * considerando o período Fechado
     */
    public static int calcularIntervaloParcelas(Date dateInicial, Date dateFinal) {
        return calcularIntervaloMes(dateInicial, dateFinal) + 1;
    }

    public static int calcularIntervaloDias(LocalDateTime dataInicial, LocalDateTime dataFinal) {
        // Converte LocalDateTime diretamente para LocalDate
        LocalDate dtInicio = dataInicial.toLocalDate();
        LocalDate dtFim = dataFinal.toLocalDate();

        // Calcula a diferença em dias usando ChronoUnit
        return (int) ChronoUnit.DAYS.between(dtInicio, dtFim);
    }

    public static String getDataPorExtenso() {
        DateFormat df = DateFormat.getDateInstance(DateFormat.LONG, new Locale("pt", "BR"));
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

        Date data;
        String saida = "";

        try {
            data = formatoEntrada.parse(entrada);
            saida = "" + Integer.parseInt(ano.format(data)) + "-" + Integer.parseInt(mes.format(data)) + "-" + Integer.parseInt(dia.format(data));
            return saida;

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return "";

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
        Date today = cal.getTime();
        return today;

    }

    public static Date retornaAntsOntemEmDateZerado() {
        int numeroDiasParaSubtrair = 2;
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        cal.add(Calendar.DATE, numeroDiasParaSubtrair);
        Date antesDeOntem = cal.getTime();
        return antesDeOntem;
    }

    public static boolean verificaSeEstaVencido(LocalDateTime dataVencimento) {
        LocalDate dataAtual = LocalDate.now();
        LocalDate dtVenc = dataVencimento.toLocalDate();

        return dtVenc.isBefore(dataAtual);
    }

    public static boolean verificaSeEstaVencidoEmRelacaoPagamento(Date dataVencimento, Date dataPagamento) {
        dataVencimento = definirSomentaData(dataVencimento);
        dataPagamento = definirSomentaData(dataPagamento);
        return dataPagamento.after(dataVencimento);
    }


    public static boolean verificaVencidoDtPagamento(Date dataVencimento, Date dataPagamento) {
        LocalDate dtVenc;
        if (dataVencimento instanceof java.sql.Date) {
            dtVenc = ((java.sql.Date) dataVencimento).toLocalDate();
        } else {
            dtVenc = dataVencimento.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        }

        LocalDate dtPagamento = dataPagamento.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        return dtVenc.isBefore(dtPagamento);
    }

    public static String NomeDoMes(int i, int tipo) {

        String mes[] = {"janeiro", "fevereiro", "março", "abril", "maio", "junho", "julho", "agosto", "setembro", "outubro", "novembro", "dezembro"};
        if (tipo == 0) {
            return (mes[i - 1]); // extenso
        } else {
            return (mes[i - 1].substring(0, 3)); // abreviado
        }
    }

    public static String DiaDaSemana(int i, int tipo) {
        String diasem[] = {"domingo", "segunda-feira", "terça-feira", "quarta-feira", "quinta-feira", "sexta-feira", "sábado"};
        if (tipo == 0) {
            return (diasem[i - 1]);
        } else {
            return (diasem[i - 1].substring(0, 3));
        }
    }

    public static String DataPorExtenso(String cidade, java.util.Date dt) {
        // retorna os valores ano, mês e dia da variável "dt"
        int d = dt.getDate();
        int m = dt.getMonth() + 1;
        int a = dt.getYear() + 1900;
        // retorna o dia da semana: 1=domingo, 2=segunda-feira, ..., 7=sábado
        Calendar data = new GregorianCalendar(a, m - 1, d);
        int ds = data.get(Calendar.DAY_OF_WEEK);
        return (cidade + ", " + d + " de " + NomeDoMes(m, 0) + " de " + a + " (" + DiaDaSemana(ds, 1) + ").");
    }

    public static Date converteStringEmData(String entrada) {
        String dia = entrada.substring(0, 2);
        String mes = entrada.substring(2, 4);
        String ano = entrada.substring(4, 8);
        String retorno = dia + "/" + mes + "/" + ano;

        try {
            return formataData(retorno);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Formato dd/MM/yyyy
     * <p>
     * data
     *
     * @return
     */
    public static Date formataData(String data) {
        if (data == null || data.equals("")) {
            return null;
        }

        Date date = null;
        try {
            DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            date = (java.util.Date) formatter.parse(data);
        } catch (ParseException e) {
            date = formatarDataBancoString(data);
        }
        return date;
    }

    public static Date formatarDataBancoString(String data) {
        if (data == null || data.trim().length() == 0) {
            return null;
        }

        Date date = null;
        try {
            date = (java.util.Date) frmtDtBanco.parse(data);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return date;
    }

    public static Date formatarDataMesAnoString(String data) {
        if (data == null || data.equals("")) {
            return null;
        }

        Date date = null;
        try {
            date = (java.util.Date) frmtDtMesAno.parse(data);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return date;
    }

    public static Date operacoesData(Date data, String tipoCampo, Integer valor) {
        Calendar gc = Calendar.getInstance();
        gc.setTime(data);
        Date retorno = null;
        if (tipoCampo.equals("dias")) {
            gc.add(Calendar.DAY_OF_YEAR, valor);
            return gc.getTime();
        }
        if (tipoCampo.equals("meses")) {
            gc.add(Calendar.MONTH, valor);
            return gc.getTime();
        }
        if (tipoCampo.equals("ano")) {
            gc.add(Calendar.YEAR, valor);
            return gc.getTime();
        }
        return null;
    }

    /**
     * Verifica se a data1 vem antes da data2
     * <p>
     * date1 date2
     *
     * @return
     */
    public static boolean antes(Date date1, Date date2) {
        Date dt1 = definirSomentaData(date1);
        Date dt2 = definirSomentaData(date2);
        return dt1.before(dt2);
    }

    public static boolean antes(LocalDateTime date1, LocalDateTime date2) {
        Date dt1 = definirSomentaData(date1);
        Date dt2 = definirSomentaData(date2);
        return dt1.before(dt2);
    }

    public static Date definirSomentaData(LocalDateTime date) {
        LocalDate localDate = date.toLocalDate();

        // Converte de volta para Date
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    public static Date definirSomentaData(Date date) {
        LocalDate localDate = date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();

        // Converte de volta para Date
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    public static Date definirSomentaData(String date) {
        return definirSomentaData(formataData(date));
    }

    /**
     * Verifica se a data1 é posterior a data2
     * <p>
     * date1 date2
     *
     * @return
     */
    public static boolean depois(Date date1, Date date2) {
        Date dt1 = definirSomentaData(date1);
        Date dt2 = definirSomentaData(date2);
        return dt1.after(dt2);
    }

    public static boolean iguais(Date date1, Date date2) {
        Date dt1 = definirSomentaData(date1);
        Date dt2 = definirSomentaData(date2);
        return dt1.equals(dt2);
    }

    public static boolean entre(Date dataComparar, Date dataInicio, Date dataFim) {
        Date dtComp = definirSomentaData(dataComparar);
        Date dataIni = definirSomentaData(dataInicio);
        Date dtFim = definirSomentaData(dataFim);

        boolean igualInicio = dtComp.equals(dataIni);
        boolean igualFim = dtComp.equals(dtFim);
        boolean aposInicio = dtComp.after(dataIni);
        boolean antesFim = dtComp.before(dtFim);
        return (igualInicio || igualFim) || (aposInicio && antesFim);
    }

    public static Integer retornaIdade(Date dtnNascimento) {
        Calendar dataNascimento = Calendar.getInstance();
        dataNascimento.setTime(dtnNascimento);
        Calendar hoje = Calendar.getInstance();

        int idade = hoje.get(Calendar.YEAR) - dataNascimento.get(Calendar.YEAR);

        if (hoje.get(Calendar.MONTH) < dataNascimento.get(Calendar.MONTH)) {
            idade--;
        } else if (hoje.get(Calendar.MONTH) == dataNascimento.get(Calendar.MONTH) && hoje.get(Calendar.DAY_OF_MONTH) < dataNascimento.get(Calendar.DAY_OF_MONTH)) {
            idade--;
        }

        return idade;
    }

    public static String formatarData(Date data) {
        if (data == null || data.equals("")) {
            return null;
        }
        return frmtDt.format(data);
    }

    public static String formatarDatayyyyMMdd(Date data) {
        if (data == null || data.equals("")) {
            return null;
        }
        return frmtDtyyyyMMdd.format(data);
    }

    public static String formatarDataMM_yyyy(Date data) {
        if (data == null || data.equals("")) {
            return null;
        }
        return frmtDtMM_dd.format(data);
    }

    public static String formatarDataHora(Date data) {
        if (data == null || data.equals("")) {
            return null;
        }
        return frmDtHrsPort.format(data);
    }

    public static String formatarDataBanco(Date data) {
        if (data == null || data.equals("")) {
            return null;
        }
        return frmtDtBanco.format(data);
    }

    public static String formatarDataHoraBanco(Date data) {
        if (data == null || data.equals("")) {
            return null;
        }
        return frmDtHrs.format(data);
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

        calendar.add(Calendar.DAY_OF_MONTH, qtdDias);
        return calendar.getTime();
    }

    public static Date adicionarMinutos(Date data, int qtdDias) {
        Calendar calendar = Calendar.getInstance(new Locale("pt", "BR"));
        calendar.setTime(data);

        calendar.add(Calendar.MINUTE, qtdDias);
        return calendar.getTime();
    }


    public static int obterDiaDoMes(Date data) {
        Calendar calendar = Calendar.getInstance(new Locale("pt", "BR"));

        int dia = calendar.get(Calendar.DAY_OF_MONTH);

        return dia;
    }

    public static int obterAnoAtual() {
        Calendar calendar = Calendar.getInstance(new Locale("pt", "BR"));

        int ano = calendar.get(Calendar.YEAR);

        return ano;
    }

    public static int obterAno(Date data) {
        Calendar calendar = Calendar.getInstance(new Locale("pt", "BR"));
        calendar.setTime(data);
        int ano = calendar.get(Calendar.YEAR);
        return ano;
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

    public static boolean datasDiferentes(Date date1, Date date2) {
        return !datasIguais(date1, date2);
    }

    public static boolean datasIguais(Date date1, Date date2) {
        Date dt1 = definirSomentaData(date1);
        Date dt2 = definirSomentaData(date2);
        return dt1.compareTo(dt2) == 0;
    }

    public static boolean verificarFimDeSemana(LocalDateTime data) {
        DayOfWeek diaDaSemana = data.getDayOfWeek();
        return isSabado(data)
                || isDomingo(data);
    }

    public static boolean isSabado(LocalDateTime data) {
        return data.toLocalDate().getDayOfWeek() == DayOfWeek.SATURDAY;
    }

    public static boolean isDomingo(LocalDateTime data) {
        return data.toLocalDate().getDayOfWeek() == DayOfWeek.SUNDAY;
    }

    /**
     * Verifica se é sexta, sabado ou domingo
     * <p>
     * data
     *
     * @return
     */
    public static boolean verificarFimDeSemanaEstendido(Date data) {
        Calendar calendar = Calendar.getInstance(new Locale("pt", "BR"));
        calendar.setTime(data);
        return calendar.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY
                || calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY
                || calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY;
    }

    public static int getHoraDoDia(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int hora = cal.get(Calendar.HOUR_OF_DAY);
        return hora;
    }

    public static Date getPrimeiroDiaDoMes(Date data) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(data);
        cal1.set(Calendar.DAY_OF_MONTH, 1);
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
        cal.set(Calendar.DAY_OF_MONTH, dia);
        return cal.getTime();
    }

    public static LocalDateTime alterarDataParaProximoDiaUtil(LocalDateTime data) {
        DayOfWeek diaDaSemana = data.getDayOfWeek();

        if (diaDaSemana == DayOfWeek.SATURDAY) {
            return data.plusDays(2); // Sábado, adicionar 2 dias
        } else if (diaDaSemana == DayOfWeek.SUNDAY) {
            return data.plusDays(1); // Domingo, adicionar 1 dia
        }

        // Se for dia útil, retorna a mesma data
        return data;
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
        return reduzir(data, qtdAnos, Calendar.YEAR);
    }

    public static Date reduzirMes(Date data, int qtdMeses) {
        return reduzir(data, qtdMeses, Calendar.MONTH);
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
        }
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
        return Integer.parseInt(formatter.format(data));
    }

    public static Date definirPrimeiroDiaMes(Date data) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(data);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        return cal.getTime();
    }

    public static Date converterData(String data) {
        if (UtilString.isBlank(data)) {
            return null;
        }

        try {
            DateFormat formatter = new SimpleDateFormat("yyyyMMdd");
            Date date = (java.util.Date) formatter.parse(data);
            return date;
        } catch (ParseException e) {
            throw new RuntimeException("Erro ao converter data: " + data);
        }
    }

    public static Date converterDataReduzida(String data) {
        if (UtilString.isBlank(data)) {
            return null;
        }

        try {
            DateFormat formatter = new SimpleDateFormat("yyMMdd");
            Date date = (java.util.Date) formatter.parse(data);
            return date;
        } catch (ParseException e) {
            throw new RuntimeException("Erro ao converter data: " + data);
        }
    }


    /**
     * coloca Zerro na data passada
     * <p>
     * set 0 nos minutos, horas e dias
     *
     * @param date
     * @return set 0 nos minutos, horas e dias
     */
    public static Date zeroTime(Date date) {
        return setTime(date, 0, 0, 0, 0);
    }


    /**
     * pega a data e adicona a ultima hora do dia 23:59:59, e retorna o date
     *
     * @param date
     * @return Date atualizado time
     */
    public static Date LastDateTime(Date date) {
        return setTime(date, 23, 59, 59, 59);
    }


    /**
     * Set time para a data
     *
     * @param date
     * @param hourOfDay
     * @param minute
     * @param second
     * @param ms
     * @return new instance of java.util.Date with the time set
     */
    public static Date setTime(Date date, int hourOfDay, int minute, int second, int ms) {
        final GregorianCalendar gc = new GregorianCalendar();
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
        calendar.set(Calendar.DAY_OF_MONTH,
                calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        return calendar.getTime();
    }

    public static Integer diasDeAtraso(LocalDateTime dataVencimentoCobranca) {
        LocalDate dataCorrente = LocalDate.now();
        LocalDate dataVencimento = dataVencimentoCobranca.toLocalDate();
        return Math.max(0, (int) ChronoUnit.DAYS.between(dataVencimento, dataCorrente));
    }

    public static Date getPrimeiroDiaDoMesAnterior(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, -1);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        return cal.getTime();
    }

    public static Date getUltimoDiaDoMesAnterior(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, -1);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        return cal.getTime();
    }

    public static Date corrigeHorarioDeVerao(Date data) {
        ZoneRules zoneRulesBrasilia = ZoneId.of("America/Sao_Paulo").getRules();
        ZoneRules zoneRulesBahia = ZoneId.of("America/Bahia").getRules();

        int offsetSegundosBrasilia = zoneRulesBrasilia.getOffset(data.toInstant()).getTotalSeconds();
        int offsetSegundosBahia = zoneRulesBahia.getOffset(data.toInstant()).getTotalSeconds();

        int diferenca = offsetSegundosBrasilia - offsetSegundosBahia;

        if (diferenca > 0) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(data);
            calendar.add(Calendar.HOUR, diferenca / 3600);

            return calendar.getTime();
        } else {
            return data;
        }
    }

    public static boolean isMesmoMes(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(date1);
        cal2.setTime(date2);

        // Retorna verdadeiro se o ano e o mês de ambas as datas forem iguais
        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH);
    }

    public static LocalDateTime getLocalDateTime(Object value) {
        if (value == null) {
            return null; // Retorna null se não houver data
        }
        return ((Timestamp) value).toLocalDateTime();
    }

    public static String converteLocalDateTimeToString(LocalDateTime dataVencimento) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"); // ou outro formato desejado
        return dataVencimento.format(formatter);
    }

    public static LocalDateTime convertStringToLocalDateTime(String dataString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(dataString, formatter);
        return localDate.atStartOfDay();
    }

    public static boolean isFirstDateBeforeSecond(LocalDateTime firstDate, LocalDateTime secondDate) {
        // Converter LocalDateTime para Date
        Date firstDateConverted = Date.from(firstDate.atZone(ZoneId.systemDefault()).toInstant());
        Date secondDateConverted = Date.from(secondDate.atZone(ZoneId.systemDefault()).toInstant());

        // Validar se a primeira data é anterior à segunda
        return firstDateConverted.before(secondDateConverted);

    }

    public static boolean isFirstDateAfterSecond(LocalDateTime firstDate, LocalDateTime secondDate) {
        // Converter LocalDateTime para Date
        Date firstDateConverted = Date.from(firstDate.atZone(ZoneId.systemDefault()).toInstant());
        Date secondDateConverted = Date.from(secondDate.atZone(ZoneId.systemDefault()).toInstant());

        // Validar se a primeira data é anterior à segunda
        return firstDateConverted.after(secondDateConverted);

    }

    public static LocalDateTime validaSabadoDomingoDiaUtil(LocalDateTime data) {
        LocalDateTime novaDataVencimento = data;
        if (isSabado(data) || isDomingo(data)) {
            novaDataVencimento = alterarDataParaProximoDiaUtil(data);
        }

        return novaDataVencimento;
    }
 }
