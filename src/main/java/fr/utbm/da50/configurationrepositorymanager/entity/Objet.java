package fr.utbm.da50.configurationrepositorymanager.entity;

import java.util.HashSet;
import java.util.Set;

import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;
import org.springframework.data.neo4j.core.schema.Relationship;
import org.springframework.data.neo4j.core.schema.Relationship.Direction;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Node("Objet")
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Objet {
	@Id
	@GeneratedValue
	private Long id;
	@Property("nom")
	private String nom;
	@Property("description")
	private String description;
	
	@Relationship(type = "POSSEDER", direction = Direction.OUTGOING)
	private Set<Objet> objets = new HashSet<>();
	
	@Relationship(type = "AVOIR", direction = Direction.OUTGOING)
	private HashSet<Propriete> proprietes = new HashSet<>();

	public Objet(String nom, String description) {
		this.nom = nom;
		this.description = description;
	}
	
	public Objet(String nom, String description, Set<Objet> objets, HashSet<Propriete> proprietes) {
		this.nom = nom;
		this.description = description;

		this.objets = new HashSet<Objet>();
		for(Objet o : objets){
			Objet newObj = new Objet(o.getNom(), o.getDescription(), o.getObjets(), o.getProprietes());
			this.objets.add(newObj);
		}

		this.proprietes = new HashSet<Propriete>();
		for(Propriete p : proprietes){
			Propriete newProp = new Propriete(p.getNom(), p.getType(), p.getValeur());
			this.proprietes.add(newProp);
		}
	}

	public Objet(String nom, String description, HashSet<Propriete> proprietes) {
		this.nom = nom;
		this.description = description;

		this.proprietes = new HashSet<Propriete>();
		for(Propriete p : proprietes){
			Propriete newProp = new Propriete(p.getNom(), p.getType(), p.getValeur());
			this.proprietes.add(newProp);
		}
	}

	public Objet(String nom, String description, Set<Objet> objets) {
		this.nom = nom;
		this.description = description;

		this.objets = new HashSet<Objet>();
		for(Objet o : objets){
			Objet newObj = new Objet(o.getNom(), o.getDescription(), o.getObjets(), o.getProprietes());
			this.objets.add(newObj);
		}
	}

	public Objet(String nom) {
		this.nom = nom;
	}
	
	

}
