package edu.unipampa.laboratoriovirologia.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import javax.persistence.*;
import org.hibernate.annotations.Type;
import edu.unipampa.laboratoriovirologia.domain.enumeration.TipoExame;
import edu.unipampa.laboratoriovirologia.domain.enumeration.TipoVirus;

/**
 * A Exame.
 */
@Entity
@Table(name = "exame")
public class Exame implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "nome")
    private TipoExame nome;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo")
    private TipoVirus tipo;

    @Column(name = "resultado")
    private String resultado;

    @Column(name = "data_teste")
    private LocalDate dataTeste;

    @Column(name = "data_leitura")
    private LocalDate dataLeitura;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "preenchimento_espelho")
    private String preenchimentoEspelho;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "observacoes")
    private String observacoes;

    @Column(name = "valor", precision = 21, scale = 2)
    private BigDecimal valor;

    @ManyToOne
    @JsonIgnoreProperties(value = { "users", "midias", "exames", "propriedade", "medicoveterinario" }, allowSetters = true)
    private Amostra amostra;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Exame id(Long id) {
        this.id = id;
        return this;
    }

    public TipoExame getNome() {
        return this.nome;
    }

    public Exame nome(TipoExame nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(TipoExame nome) {
        this.nome = nome;
    }

    public TipoVirus getTipo() {
        return this.tipo;
    }

    public Exame tipo(TipoVirus tipo) {
        this.tipo = tipo;
        return this;
    }

    public void setTipo(TipoVirus tipo) {
        this.tipo = tipo;
    }

    public String getResultado() {
        return this.resultado;
    }

    public Exame resultado(String resultado) {
        this.resultado = resultado;
        return this;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }

    public LocalDate getDataTeste() {
        return this.dataTeste;
    }

    public Exame dataTeste(LocalDate dataTeste) {
        this.dataTeste = dataTeste;
        return this;
    }

    public void setDataTeste(LocalDate dataTeste) {
        this.dataTeste = dataTeste;
    }

    public LocalDate getDataLeitura() {
        return this.dataLeitura;
    }

    public Exame dataLeitura(LocalDate dataLeitura) {
        this.dataLeitura = dataLeitura;
        return this;
    }

    public void setDataLeitura(LocalDate dataLeitura) {
        this.dataLeitura = dataLeitura;
    }

    public String getPreenchimentoEspelho() {
        return this.preenchimentoEspelho;
    }

    public Exame preenchimentoEspelho(String preenchimentoEspelho) {
        this.preenchimentoEspelho = preenchimentoEspelho;
        return this;
    }

    public void setPreenchimentoEspelho(String preenchimentoEspelho) {
        this.preenchimentoEspelho = preenchimentoEspelho;
    }

    public String getObservacoes() {
        return this.observacoes;
    }

    public Exame observacoes(String observacoes) {
        this.observacoes = observacoes;
        return this;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public BigDecimal getValor() {
        return this.valor;
    }

    public Exame valor(BigDecimal valor) {
        this.valor = valor;
        return this;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public Amostra getAmostra() {
        return this.amostra;
    }

    public Exame amostra(Amostra amostra) {
        this.setAmostra(amostra);
        return this;
    }

    public void setAmostra(Amostra amostra) {
        this.amostra = amostra;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Exame)) {
            return false;
        }
        return id != null && id.equals(((Exame) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Exame{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", tipo='" + getTipo() + "'" +
            ", resultado='" + getResultado() + "'" +
            ", dataTeste='" + getDataTeste() + "'" +
            ", dataLeitura='" + getDataLeitura() + "'" +
            ", preenchimentoEspelho='" + getPreenchimentoEspelho() + "'" +
            ", observacoes='" + getObservacoes() + "'" +
            ", valor=" + getValor() +
            "}";
    }
}
