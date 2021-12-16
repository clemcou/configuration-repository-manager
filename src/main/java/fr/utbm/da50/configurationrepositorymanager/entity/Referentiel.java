package fr.utbm.da50.configurationrepositorymanager.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.neo4j.core.schema.*;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Node("Referentiel")
public class Referentiel {
    @Id
    @GeneratedValue
    private Long id;
    @Property("nom")
    private String nom;
    @Property("description")
    private String description;

    @Relationship(type = "CREER", direction = Relationship.Direction.OUTGOING)
    private Set<Configuration> configurations = new HashSet<>();

    @Relationship(type = "CONTENIR", direction = Relationship.Direction.OUTGOING)
    private Set<Objet> objets = new HashSet<>();

    public Referentiel(String nom) {
        this.nom = nom;
    }

    public Referentiel(String nom, Set<Objet> objets) {
        this.nom = nom;
        this.objets = objets;
    }

    public Referentiel(String nom, String description) {
        this.nom = nom;
        this.description = description;
    }

    public Referentiel(String nom, String description, Set<Objet> objets) {
        this.nom = nom;
        this.description = description;
        this.objets = objets;
    }

}
