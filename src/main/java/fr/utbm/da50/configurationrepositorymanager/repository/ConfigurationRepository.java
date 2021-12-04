package fr.utbm.da50.configurationrepositorymanager.repository;

import fr.utbm.da50.configurationrepositorymanager.entity.Configuration;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
@Qualifier("ConfigurationRepository")
public interface ConfigurationRepository extends Neo4jRepository<Configuration, Long> {
	
	@Query("match(n)-[*0..]->(x) where id(n)=$toId detach delete n,x")
	void deleteAllRelatedEntity(@Param(value = "toId") long toId);
	/*
	@Query(MATCH (n) WHERE n.nom CONTAINS 'test' RETURN n)
	void findByNameLike()
	
	@Query("MATCH (n) WHERE n.name =~ ('.*'+$name+'.*') RETURN n")
    List<BaseNode> findBaseNodeByNameLike(@Param("name") String name);
    */
	Iterable<Configuration> findByNomLike(String nom);
}


