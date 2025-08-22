package fieg.modulos.cieloJobs;

import fieg.core.util.StringUtils;
import fieg.core.util.UtilValorMonetario;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;

public class CieloUtil {

    private static final DateFormat formatoDDMMAAAA = new SimpleDateFormat("ddMMyyyy");
    private static final DateTimeFormatter formatoHoraHHMMSS = DateTimeFormatter.ofPattern("HHmmss");

    /**
     * Pela documentação da versão 15: Devem ser considerados com duas casas decimais, sem vírgulas, pontos ou qualquer
     * outro caractere
     *
     * @param sinalEValor uma String com um sinal e valor em centavos. Ex: +002312
     * @return um BigDecimal que coresponde ao valor. Ex: 23.12
     */
    public static BigDecimal leDinheiroComSinal(String sinalEValor) {
        char sinal = sinalEValor.charAt(0);

        BigDecimal valor = UtilValorMonetario.converterDeCentavos(sinalEValor.substring(1));

        return switch (sinal) {
            case '+' -> valor;
            case '-' -> valor.negate();
            default -> throw new IllegalArgumentException("Sinal " + sinal + " inválido");
        };
    }

    public static Date leDataDDMMAAAA(String dataString) {
        if (StringUtils.isBlank(dataString)) {
            return null;
        }
        try {
            return formatoDDMMAAAA.parse(dataString);
        } catch (ParseException e) {
            throw new IllegalArgumentException("String " + dataString + " não é uma data no formato DDMMAAAA", e);
        }
    }

    public static LocalTime leHoraHHMMSS(String horaString) {
        if (StringUtils.isBlank(horaString)) {
            return null;
        }
        try {
            return LocalTime.parse(horaString, formatoHoraHHMMSS);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("String " + horaString + " não é uma data no formato DDMMAAAA", e);
        }
    }

    public static boolean converteSN(Character sOuN) {
        return switch (sOuN) {
            case 'S' -> true;
            case 'N' -> false;
            default -> throw new IllegalArgumentException("Caractere " + sOuN + " deveria ser S (true) ou N (false)");
        };
    }

    public static String getMeioDeCapturav15(String codigoMeioCaptura) {
        return switch (codigoMeioCaptura) {
            case "000" -> "CIELO_LIO";
            case "001" -> "POS";
            case "002", "008", "099" -> "TEF";
            case "003" -> "MANUAL";
            case "004" -> "URA/CVA";
            case "005" -> "EDI";
            case "006" -> "GDS/IATA";
            case "007" -> "ECOMMERCE";
            case "009" -> "MER";
            case "010", "011" -> "CENTRAL_ATENDIMENTO";
            case "012" -> "CHAREBACK";
            case "013" -> "OUVIDORIA";
            case "014" -> "MASSIVO";
            case "015" -> "SUPERLINK";
            default -> {
                if (StringUtils.isBlank(codigoMeioCaptura)) {
                    yield "NADA";
                } else {
                    yield codigoMeioCaptura;
                }
            }
        };
    }

    public static boolean ecommerceTefOuCieloLio(String meioCaptura) {
        return meioCaptura.equals("CIELO_LIO") || meioCaptura.equals("ECOMMERCE") || meioCaptura.equals("TEF");
    }

    public static int stringParaNumeroSeVazioMenosUm(String numero) throws NumberFormatException {
        if (StringUtils.isBlank(numero)) {
            return -1;
        }

        return Integer.parseInt(numero);
    }
}
