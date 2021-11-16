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
	private Set<Propriete> proprietes = new HashSet<>();

}
