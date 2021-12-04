package fr.utbm.da50.configurationrepositorymanager.repository;

import fr.utbm.da50.configurationrepositorymanager.entity.Objet;
import fr.utbm.da50.configurationrepositorymanager.entity.Propriete;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
@Qualifier("ProprieteRepository")
public interface ProprieteRepository extends Neo4jRepository<Propriete, Long> {

	Iterable<Objet> findByNomLike(String nom);


}
