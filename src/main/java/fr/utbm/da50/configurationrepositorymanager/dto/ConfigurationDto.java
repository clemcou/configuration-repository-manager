package fr.utbm.da50.configurationrepositorymanager.dto;

import lombok.Data;

import java.util.Set;

@Data
public class ConfigurationDto {

    private Long id;
    private String nom;
    private String description;
    private Set<ObjetDto> objets;

    public ConfigurationDto(){
    }
    
    public ConfigurationDto(Long id, String nom, String description, Set<ObjetDto> objets) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.objets = objets;
    }

    
}
