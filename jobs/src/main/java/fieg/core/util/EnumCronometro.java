package fieg.core.util;

public enum EnumCronometro {

    MINUTOS {
        public String mostrarTempo(long tempoInicial, Object threadName) {
            long tempoFinal = System.currentTimeMillis();
            long tempoTotal = tempoFinal - tempoInicial;
            int milesegundos = (int) (tempoTotal % 1000L);
            tempoTotal /= 1000L;
            int segundos = (int) tempoTotal % 60;
            tempoTotal /= 60L;
            int minutos = (int) (tempoTotal % 60L);
            tempoTotal /= 60L;
            segundos %= 60;
            String msg = String.format("\n%s -> Minutos:%d\t Segundos:%d\t milisegundos:%d\n", threadName, minutos, segundos, milesegundos);
            return msg;
        }
    },
    COMPLETO {
        public String mostrarTempo(long tempoInicial, Object threadName) {
            long tempoFinal = System.currentTimeMillis();
            long tempoTotal = tempoFinal - tempoInicial;
            int milesegundos = (int) (tempoTotal % 1000L);
            tempoTotal /= 1000L;
            int segundos = (int) tempoTotal % 60;
            tempoTotal /= 60L;
            int minutos = (int) (tempoTotal % 60L);
            tempoTotal /= 60L;
            segundos %= 60;
            int hora = (int) tempoTotal;
            String msg = String.format("\n%s \t Hora:%d\t\t Minutos:%d\t Segundos:%d\t milisegundos:%d\n", threadName, hora, minutos, segundos, milesegundos);
            return msg;
        }
    };

    abstract String mostrarTempo(long var1, Object var3);

    public String calcularTempo(long tempoInicial, Object threadName) {
        return this.mostrarTempo(tempoInicial, threadName);
    }

    public static long tempoTotal(long tempoTotal) {
        int milesegundos = (int) (tempoTotal % 1000L);
        tempoTotal /= 1000L;
        int segundos = (int) tempoTotal % 60;
        tempoTotal /= 60L;
        int minutos = (int) (tempoTotal % 60L);
        tempoTotal /= 60L;
        segundos %= 60;
        int hora = (int) tempoTotal;
        System.out.printf(" Hora:%d\t\t Minutos:%d\t Segundos:%d\t milisegundos:%d\n", hora, minutos, segundos, milesegundos);
        return tempoTotal;
    }

    public static int obterSegundos(long tempoInicial) {
        long tempoFinal = System.currentTimeMillis();
        long tempoTotal = tempoFinal - tempoInicial;
        int milesegundos = (int) (tempoTotal % 1000L);
        tempoTotal /= 1000L;
        int segundos = (int) tempoTotal % 60;
        tempoTotal /= 60L;
        int minutos = (int) (tempoTotal % 60L);
        tempoTotal /= 60L;
        segundos %= 60;
        System.out.printf(" Minutos:%d\t Segundos:%d\t milisegundos:%d\n", minutos, segundos, milesegundos);
        return segundos;
    }
}