package edu.unipampa.laboratoriovirologia.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A DTO for the {@link edu.unipampa.laboratoriovirologia.domain.Amostra} entity.
 */
public class AmostraDTO implements Serializable {

    private Long id;

    private String protocolo;

    private String formaEnvio;

    private Integer numeroAmostras;

    private String especie;

    private Integer numeroAnimais;

    private String acometidos;

    private String pricipalSuspeita;

    private LocalDate dataInicial;

    private String materialRecebido;

    private String acondicionamento;

    private String condicaoMaterial;

    private String status;

    private String tipoMedVet;

    private BigDecimal valorTotal;

    private String tipoPagamento;

    private String tipo;

    private String situacao;

    private Set<UserDTO> users = new HashSet<>();

    private PropriedadeDTO propriedade;

    private MedicoveterinarioDTO medicoveterinario;

    private SubamostraDTO subamostra;

    private VacinaDTO vacina;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProtocolo() {
        return protocolo;
    }

    public void setProtocolo(String protocolo) {
        this.protocolo = protocolo;
    }

    public String getFormaEnvio() {
        return formaEnvio;
    }

    public void setFormaEnvio(String formaEnvio) {
        this.formaEnvio = formaEnvio;
    }

    public Integer getNumeroAmostras() {
        return numeroAmostras;
    }

    public void setNumeroAmostras(Integer numeroAmostras) {
        this.numeroAmostras = numeroAmostras;
    }

    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public Integer getNumeroAnimais() {
        return numeroAnimais;
    }

    public void setNumeroAnimais(Integer numeroAnimais) {
        this.numeroAnimais = numeroAnimais;
    }

    public String getAcometidos() {
        return acometidos;
    }

    public void setAcometidos(String acometidos) {
        this.acometidos = acometidos;
    }

    public String getPricipalSuspeita() {
        return pricipalSuspeita;
    }

    public void setPricipalSuspeita(String pricipalSuspeita) {
        this.pricipalSuspeita = pricipalSuspeita;
    }

    public LocalDate getDataInicial() {
        return dataInicial;
    }

    public void setDataInicial(LocalDate dataInicial) {
        this.dataInicial = dataInicial;
    }

    public String getMaterialRecebido() {
        return materialRecebido;
    }

    public void setMaterialRecebido(String materialRecebido) {
        this.materialRecebido = materialRecebido;
    }

    public String getAcondicionamento() {
        return acondicionamento;
    }

    public void setAcondicionamento(String acondicionamento) {
        this.acondicionamento = acondicionamento;
    }

    public String getCondicaoMaterial() {
        return condicaoMaterial;
    }

    public void setCondicaoMaterial(String condicaoMaterial) {
        this.condicaoMaterial = condicaoMaterial;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTipoMedVet() {
        return tipoMedVet;
    }

    public void setTipoMedVet(String tipoMedVet) {
        this.tipoMedVet = tipoMedVet;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    public String getTipoPagamento() {
        return tipoPagamento;
    }

    public void setTipoPagamento(String tipoPagamento) {
        this.tipoPagamento = tipoPagamento;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }

    public Set<UserDTO> getUsers() {
        return users;
    }

    public void setUsers(Set<UserDTO> users) {
        this.users = users;
    }

    public PropriedadeDTO getPropriedade() {
        return propriedade;
    }

    public void setPropriedade(PropriedadeDTO propriedade) {
        this.propriedade = propriedade;
    }

    public MedicoveterinarioDTO getMedicoveterinario() {
        return medicoveterinario;
    }

    public void setMedicoveterinario(MedicoveterinarioDTO medicoveterinario) {
        this.medicoveterinario = medicoveterinario;
    }

    public SubamostraDTO getSubamostra() {
        return subamostra;
    }

    public void setSubamostra(SubamostraDTO subamostra) {
        this.subamostra = subamostra;
    }

    public VacinaDTO getVacina() {
        return vacina;
    }

    public void setVacina(VacinaDTO vacina) {
        this.vacina = vacina;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AmostraDTO)) {
            return false;
        }

        AmostraDTO amostraDTO = (AmostraDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, amostraDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AmostraDTO{" +
            "id=" + getId() +
            ", protocolo='" + getProtocolo() + "'" +
            ", formaEnvio='" + getFormaEnvio() + "'" +
            ", numeroAmostras=" + getNumeroAmostras() +
            ", especie='" + getEspecie() + "'" +
            ", numeroAnimais=" + getNumeroAnimais() +
            ", acometidos='" + getAcometidos() + "'" +
            ", pricipalSuspeita='" + getPricipalSuspeita() + "'" +
            ", dataInicial='" + getDataInicial() + "'" +
            ", materialRecebido='" + getMaterialRecebido() + "'" +
            ", acondicionamento='" + getAcondicionamento() + "'" +
            ", condicaoMaterial='" + getCondicaoMaterial() + "'" +
            ", status='" + getStatus() + "'" +
            ", tipoMedVet='" + getTipoMedVet() + "'" +
            ", valorTotal=" + getValorTotal() +
            ", tipoPagamento='" + getTipoPagamento() + "'" +
            ", tipo='" + getTipo() + "'" +
            ", situacao='" + getSituacao() + "'" +
            ", users=" + getUsers() +
            ", propriedade=" + getPropriedade() +
            ", medicoveterinario=" + getMedicoveterinario() +
            ", subamostra=" + getSubamostra() +
            ", vacina=" + getVacina() +
            "}";
    }
}
