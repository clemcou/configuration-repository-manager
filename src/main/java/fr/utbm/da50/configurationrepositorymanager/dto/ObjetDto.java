package fr.utbm.da50.configurationrepositorymanager.dto;

import lombok.Data;

import java.util.Set;

@Data
public class ObjetDto {

    private Long id;
    private String nom;
    private String description;
    private Set<ObjetDto> objets;
    private Set<ProprieteDto> proprietes;
    
    public ObjetDto(){
    
    }

    public ObjetDto(Long id, String nom, String description, Set<ObjetDto> objetsEnfants) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.objets = objetsEnfants;
    }
    
}
