package fr.utbm.da50.configurationrepositorymanager.dto;

public class ProprieteDto {

    private Long id;
    private String nom;
    private String type;
    private Object valeur;

    public ProprieteDto(Long id, String nom, String type, Object valeur) {
        this.id = id;
        this.nom = nom;
        this.type = type;
        this.valeur = valeur;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Object getValeur() {
        return valeur;
    }

    public void setValeur(Object valeur) {
        this.valeur = valeur;
    }
}
