package fieg.modulos.estabelecimentocielo.model;

import fieg.modulos.estabelecimentocielo.enums.MeioPagamentoEstabelecimentoCielo;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "CR5_ESTABELECIMENTO_CIELO")
public class EstabelecimentoCielo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_ESTABELECIMENTO_CIELO", columnDefinition = "int", nullable = false)
    private Integer id;

    @Column(name = "ENTIDADE", columnDefinition = "int", nullable = false)
    private Integer entidade;

    @Size(max = 20, message = "Tamanho do Campo: numeroEstabelecimento")
    @Column(name = "ESC_NUMERO_ESTABELECIMENTO", length = 20, nullable = false)
    private String numeroEstabelecimento;

    @Column(name = "ESC_MEIO_PAGAMENTO", nullable = false)
    @Enumerated(EnumType.STRING)
    private MeioPagamentoEstabelecimentoCielo meioPagamento;

    @Size(max = 100, message = "Tamanho do Campo: merchantId")
    @Column(name = "ESC_MERCHANT_ID_CHECKOUT", length = 100)
    private String merchantIdCheckout;

    @Size(max = 255, message = "Tamanho do Campo: clientSecret")
    @Column(name = "CLIENT_SECRET_CHECKOUT")
    private String clientSecretCheckout;

    // Getters and setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer idEstabelecimentoCielo) {
        this.id = idEstabelecimentoCielo;
    }

    public Integer getEntidade() {
        return entidade;
    }

    public void setEntidade(Integer entidade) {
        this.entidade = entidade;
    }

    public String getNumeroEstabelecimento() {
        return numeroEstabelecimento;
    }

    public void setNumeroEstabelecimento(String escNumeroEstabelecimento) {
        this.numeroEstabelecimento = escNumeroEstabelecimento;
    }

    public MeioPagamentoEstabelecimentoCielo getMeioPagamento() {
        return meioPagamento;
    }

    public void setMeioPagamento(MeioPagamentoEstabelecimentoCielo escMeioPagamento) {
        this.meioPagamento = escMeioPagamento;
    }

    public String getMerchantIdCheckout() {
        return merchantIdCheckout;
    }

    public void setMerchantIdCheckout(String escMerchantIdCheckout) {
        this.merchantIdCheckout = escMerchantIdCheckout;
    }

    public String getClientSecretCheckout() {
        return clientSecretCheckout;
    }

    public void setClientSecretCheckout(String clientSecret) {
        this.clientSecretCheckout = clientSecret;
    }
}
