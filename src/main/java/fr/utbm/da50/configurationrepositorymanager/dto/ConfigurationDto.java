package fr.utbm.da50.configurationrepositorymanager.dto;

import java.util.Set;

public class ConfigurationDto {

    private Long id;
    private String nom;
    private String description;
    private boolean isReferentiel;
    private Set<ObjetDto> objets;

    public ConfigurationDto(Long id, String nom, String description, boolean isReferentiel, Set<ObjetDto> objets) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.isReferentiel = isReferentiel;
        this.objets = objets;
    }

    public ConfigurationDto(boolean isReferentiel) {
        this.isReferentiel = isReferentiel;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isReferentiel() {
        return isReferentiel;
    }

    public void setReferentiel(boolean referentiel) {
        isReferentiel = referentiel;
    }

    public Set<ObjetDto> getObjets() {
        return objets;
    }

    public void setObjets(Set<ObjetDto> objets) {
        this.objets = objets;
    }
}
