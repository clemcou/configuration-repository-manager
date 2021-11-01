package fr.utbm.da50.configurationrepositorymanager.dto;

import java.util.Set;

public class ObjetDto {

    private Long id;
    private String nom;
    private String description;
    private ObjetDto objetParent;
    private Set<ObjetDto> objetsEnfants;

    public ObjetDto(Long id, String nom, String description, ObjetDto objetParent, Set<ObjetDto> objetsEnfants) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.objetParent = objetParent;
        this.objetsEnfants = objetsEnfants;
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

    public ObjetDto getObjetParent() {
        return objetParent;
    }

    public void setObjetParent(ObjetDto objetParent) {
        this.objetParent = objetParent;
    }

    public Set<ObjetDto> getObjetsEnfants() {
        return objetsEnfants;
    }

    public void setObjetsEnfants(Set<ObjetDto> objetsEnfants) {
        this.objetsEnfants = objetsEnfants;
    }
}
