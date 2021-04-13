package edu.unipampa.laboratoriovirologia.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * A Amostra.
 */
@Entity
@Table(name = "amostra")
public class Amostra implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "protocolo")
    private String protocolo;

    @Column(name = "forma_envio")
    private String formaEnvio;

    @Column(name = "numero_amostras")
    private Integer numeroAmostras;

    @Column(name = "especie")
    private String especie;

    @Column(name = "material_recebido")
    private String materialRecebido;

    @Column(name = "acondicionamento")
    private String acondicionamento;

    @Column(name = "condicao_material")
    private String condicaoMaterial;

    @Column(name = "status")
    private String status;

    @ManyToMany
    @JoinTable(
        name = "rel_amostra__user",
        joinColumns = @JoinColumn(name = "amostra_id"),
        inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> users = new HashSet<>();

    @OneToMany(mappedBy = "amostra")
    @JsonIgnoreProperties(value = { "amostra" }, allowSetters = true)
    private Set<Midia> midias = new HashSet<>();

    @OneToMany(mappedBy = "amostra")
    @JsonIgnoreProperties(value = { "amostra" }, allowSetters = true)
    private Set<Exame> exames = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "endereco", "propriedade" }, allowSetters = true)
    private Proprietario proprietario;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Amostra id(Long id) {
        this.id = id;
        return this;
    }

    public String getProtocolo() {
        return this.protocolo;
    }

    public Amostra protocolo(String protocolo) {
        this.protocolo = protocolo;
        return this;
    }

    public void setProtocolo(String protocolo) {
        this.protocolo = protocolo;
    }

    public String getFormaEnvio() {
        return this.formaEnvio;
    }

    public Amostra formaEnvio(String formaEnvio) {
        this.formaEnvio = formaEnvio;
        return this;
    }

    public void setFormaEnvio(String formaEnvio) {
        this.formaEnvio = formaEnvio;
    }

    public Integer getNumeroAmostras() {
        return this.numeroAmostras;
    }

    public Amostra numeroAmostras(Integer numeroAmostras) {
        this.numeroAmostras = numeroAmostras;
        return this;
    }

    public void setNumeroAmostras(Integer numeroAmostras) {
        this.numeroAmostras = numeroAmostras;
    }

    public String getEspecie() {
        return this.especie;
    }

    public Amostra especie(String especie) {
        this.especie = especie;
        return this;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public String getMaterialRecebido() {
        return this.materialRecebido;
    }

    public Amostra materialRecebido(String materialRecebido) {
        this.materialRecebido = materialRecebido;
        return this;
    }

    public void setMaterialRecebido(String materialRecebido) {
        this.materialRecebido = materialRecebido;
    }

    public String getAcondicionamento() {
        return this.acondicionamento;
    }

    public Amostra acondicionamento(String acondicionamento) {
        this.acondicionamento = acondicionamento;
        return this;
    }

    public void setAcondicionamento(String acondicionamento) {
        this.acondicionamento = acondicionamento;
    }

    public String getCondicaoMaterial() {
        return this.condicaoMaterial;
    }

    public Amostra condicaoMaterial(String condicaoMaterial) {
        this.condicaoMaterial = condicaoMaterial;
        return this;
    }

    public void setCondicaoMaterial(String condicaoMaterial) {
        this.condicaoMaterial = condicaoMaterial;
    }

    public String getStatus() {
        return this.status;
    }

    public Amostra status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Set<User> getUsers() {
        return this.users;
    }

    public Amostra users(Set<User> users) {
        this.setUsers(users);
        return this;
    }

    public Amostra addUser(User user) {
        this.users.add(user);
        return this;
    }

    public Amostra removeUser(User user) {
        this.users.remove(user);
        return this;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public Set<Midia> getMidias() {
        return this.midias;
    }

    public Amostra midias(Set<Midia> midias) {
        this.setMidias(midias);
        return this;
    }

    public Amostra addMidia(Midia midia) {
        this.midias.add(midia);
        midia.setAmostra(this);
        return this;
    }

    public Amostra removeMidia(Midia midia) {
        this.midias.remove(midia);
        midia.setAmostra(null);
        return this;
    }

    public void setMidias(Set<Midia> midias) {
        if (this.midias != null) {
            this.midias.forEach(i -> i.setAmostra(null));
        }
        if (midias != null) {
            midias.forEach(i -> i.setAmostra(this));
        }
        this.midias = midias;
    }

    public Set<Exame> getExames() {
        return this.exames;
    }

    public Amostra exames(Set<Exame> exames) {
        this.setExames(exames);
        return this;
    }

    public Amostra addExame(Exame exame) {
        this.exames.add(exame);
        exame.setAmostra(this);
        return this;
    }

    public Amostra removeExame(Exame exame) {
        this.exames.remove(exame);
        exame.setAmostra(null);
        return this;
    }

    public void setExames(Set<Exame> exames) {
        if (this.exames != null) {
            this.exames.forEach(i -> i.setAmostra(null));
        }
        if (exames != null) {
            exames.forEach(i -> i.setAmostra(this));
        }
        this.exames = exames;
    }

    public Proprietario getProprietario() {
        return this.proprietario;
    }

    public Amostra proprietario(Proprietario proprietario) {
        this.setProprietario(proprietario);
        return this;
    }

    public void setProprietario(Proprietario proprietario) {
        this.proprietario = proprietario;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Amostra)) {
            return false;
        }
        return id != null && id.equals(((Amostra) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Amostra{" +
            "id=" + getId() +
            ", protocolo='" + getProtocolo() + "'" +
            ", formaEnvio='" + getFormaEnvio() + "'" +
            ", numeroAmostras=" + getNumeroAmostras() +
            ", especie='" + getEspecie() + "'" +
            ", materialRecebido='" + getMaterialRecebido() + "'" +
            ", acondicionamento='" + getAcondicionamento() + "'" +
            ", condicaoMaterial='" + getCondicaoMaterial() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
