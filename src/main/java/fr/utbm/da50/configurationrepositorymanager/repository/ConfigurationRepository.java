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

	Iterable<Configuration> findByNomLike(String nom);
	
	//Iterable<Configuration> findAllByOrderById();
	
	Iterable<Configuration> findAllByOrderByNomAsc();
	
	Iterable<Configuration> findAllByOrderByNomDesc();
	/*
	@Query("match(n:Configuration)-[CONTENIR]->(enf) where id(n)=$configId return id(enf) order by id(enf) DESC")
	Iterable<Integer> findObjetsOrderByIdDesc(@Param(value = "configId") long configId);
	
	@Query("match(n:Configuration)-[CONTENIR]->(enf) where id(n)=$configId return id(enf) order by id(enf)")
	Iterable<Integer> findObjetsOrderByIdAsc(@Param(value = "configId") long configId);
	*/
	@Query("match(n:Configuration)-[CONTENIR]->(enf) where id(n)=$configId return id(enf) order by enf.nom DESC")
	Iterable<Integer> findObjetsOrderByNomDesc(@Param(value = "configId") long configId);
	
	@Query("match(n:Configuration)-[CONTENIR]->(enf) where id(n)=$configId return id(enf) order by enf.nom")
	Iterable<Integer> findObjetsOrderByNomAsc(@Param(value = "configId") long configId);

}


