package fr.utbm.da50.configurationrepositorymanager.dto;

import lombok.Data;

@Data
public class ProprieteDto {

    private Long id;
    private String nom;
    private String type;
    private Object valeur;
    
    public ProprieteDto() {
    
    }
    
    public ProprieteDto(Long id, String nom, String type, Object valeur) {
        this.id = id;
        this.nom = nom;
        this.type = type;
        this.valeur = valeur;
    }

   
}
