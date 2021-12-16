package fr.utbm.da50.configurationrepositorymanager.dto;

import lombok.Data;

import java.util.Set;

@Data
public class ReferentielDto {

    private Long id;
    private String nom;
    private String description;
    private Set<ObjetDto> objets;
    private Set<ConfigurationDto> configurations;

    public ReferentielDto(){
    }

    public ReferentielDto(Long id, String nom, String description, Set<ObjetDto> objets, Set<ConfigurationDto> configurations) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.objets = objets;
        this.configurations = configurations;
    }

}
