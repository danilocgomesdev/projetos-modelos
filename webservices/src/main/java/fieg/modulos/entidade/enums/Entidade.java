package fieg.modulos.entidade.enums;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public enum Entidade {

    SISTEMAFIEG(0, "SISTEMA FIEG", "SISTEMA FIEG", 2, Logos.LOGO_SISTEMAFIEG_COLORIDA, Logos.LOGO_SISTEMAFIEG_BRANCA),
    FIEG(1, "FIEG", "FIEG - Federação das Indústrias do Estado de Goiás", 2, Logos.LOGO_FIEG_COLORIDA, Logos.LOGO_FIEG_BRANCA),
    SESI(2, "SESI", "SESI - Serviço Social da Indústria", 6, Logos.LOGO_SESI_COLORIDA, Logos.LOGO_SESI_BRANCA),
    SENAI(3, "SENAI", "SENAI - Serviço Nacional de Aprendizagem Indústrial", 5, Logos.LOGO_SENAI_COLORIDA, Logos.LOGO_SENAI_BRANCA),
    IEL(4, "IEL", "IEL - Instituto Euvaldo Lodi", 4, Logos.LOGO_IEL_COLORIDA, Logos.LOGO_IEL_BRANCA),
    ICQ(5, "ICQ-Brasil", "ICQ-Brasil - Instituto de Certificação de Qualidade Brasil", 3, null, null),
    COAD(6, "COAD", "COAD - Coordenação Administrativa", 1, null, null),
    OUTROS(7, "OUTROS", "OUTROS", 7, null, null);

    public final Integer codigo; // O que normalmente pensamos como o id da entidade
    public final String descReduzida;
    public final String descDetalhada;
    public final Integer idEntidade;
    public final String urlLogoColorida;
    public final String urlLogoBranca;

    Entidade(int codigo, String descReduzida, String descDetalhada, int idEntidade, String urlLogoColorida, String urlLogoBranca) {
        this.codigo = codigo;
        this.descReduzida = descReduzida;
        this.descDetalhada = descDetalhada;
        this.idEntidade = idEntidade;
        this.urlLogoColorida = urlLogoColorida;
        this.urlLogoBranca = urlLogoBranca;
    }

    private static final Map<Integer, Entidade> CODIGO_PARA_ENTIDADE = new HashMap<>();
    private static final Map<Integer, Entidade> ID_PARA_ENTIDADE = new HashMap<>();

    static {
        for (Entidade entidade : values()) {
            CODIGO_PARA_ENTIDADE.put(entidade.codigo, entidade);
            ID_PARA_ENTIDADE.put(entidade.idEntidade, entidade);
        }
    }

    public static Entidade getByCodigo(String codigo) {
        return getByCodigo(Integer.parseInt(codigo));
    }

    public static Entidade getByCodigo(Integer codigo) {
        return Optional.ofNullable(CODIGO_PARA_ENTIDADE.get(codigo))
                .orElseThrow(() -> new IllegalArgumentException("Não existe entidade com código " + codigo));
    }

    public static Entidade getById(Integer id) {
        return Optional.ofNullable(ID_PARA_ENTIDADE.get(id))
                .orElseThrow(() -> new IllegalArgumentException("Não existe entidade com id " + id));
    }

    // Classe interna para as constantes de URL
    private static class Logos {
        public static final String LOGO_SISTEMAFIEG_COLORIDA =" https://www.fieg.com.br/portais/files/f1cad31b-3fb9-4b2e-b462-aa1c9ce86ca5.png?contentDisposition=inline";
        public static final String LOGO_SISTEMAFIEG_BRANCA = "https://www.fieg.com.br/portais/files/c41394d4-8c97-4ce2-a9c0-14e0648a133c.png?contentDisposition=inline";
        public static final String LOGO_FIEG_COLORIDA ="https://www.fieg.com.br/portais/files/30288cad-7718-46e9-903d-49da577bc5db.png?contentDisposition=inline";
        public static final String LOGO_FIEG_BRANCA = "https://www.fieg.com.br/portais/files/d0bdbe87-ec5d-47ff-b393-a1c48c93628b.png?contentDisposition=inline";
        public static final String LOGO_SESI_COLORIDA = "https://www.fieg.com.br/portais/files/22e7933b-270e-4f36-84d9-1de057e8397d.png?contentDisposition=inline";
        public static final String LOGO_SESI_BRANCA = "https://www.fieg.com.br/portais/files/603233fc-5355-4684-9f2c-ab1ea6dc5fb3.png?contentDisposition=inline";
        public static final String LOGO_SENAI_COLORIDA = "https://www.fieg.com.br/portais/files/31244154-93d0-4bd4-bddb-ad7ee7171adc.png?contentDisposition=inline";
        public static final String LOGO_SENAI_BRANCA = " https://www.fieg.com.br/portais/files/0f8edf9e-b5c7-4c11-9da1-7756c9766136.png?contentDisposition=inline";
        public static final String LOGO_IEL_COLORIDA = "https://www.fieg.com.br/portais/files/3d74a8ba-2553-41f0-8b91-5ba3d25eca79.png?contentDisposition=inline";
        public static final String LOGO_IEL_BRANCA = "https://www.fieg.com.br/portais/files/1b54defa-63ab-401e-9fd7-34105e37a476.png?contentDisposition=inline";
    }
}
