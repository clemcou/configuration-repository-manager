package fr.utbm.da50.configurationrepositorymanager.entity;

import org.springframework.data.neo4j.core.schema.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Node("Propriete")
public class Propriete {
	@Id @GeneratedValue
	private Long id;
	@Property("nom")
	private String nom;
	@Property("type")
	private String type;
	@Property("valeur")
	private String valeur;	
}
