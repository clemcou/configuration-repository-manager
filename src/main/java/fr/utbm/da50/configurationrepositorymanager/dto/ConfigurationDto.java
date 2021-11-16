package fr.utbm.da50.configurationrepositorymanager.dto;

import lombok.Data;

import java.util.Set;

@Data
public class ConfigurationDto {

    private Long id;
    private String nom;
    private String description;
    private boolean isReferentiel;
    private Set<ObjetDto> objets;

    public ConfigurationDto(){
    
    }
    
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

    
}
