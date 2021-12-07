package fr.utbm.da50.configurationrepositorymanager.repository;

import fr.utbm.da50.configurationrepositorymanager.entity.Objet;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
@Qualifier("ObjetRepository")
public interface ObjetRepository extends Neo4jRepository<Objet, Long> {
	
	@Query("match(n)-[*0..]->(x) where id(n)=$toId detach delete n,x")
	void deleteAllRelatedEntity(@Param(value = "toId") long toId);

	Iterable<Objet> findByNomLike(String nom);
	/*
	@Query("match(n:Objet)-[AVOIR]->(enf) where id(n)=$objetId return id(enf) order by id(enf) DESC")
	Iterable<Integer> findProprietesOrderByIdDesc(@Param(value = "objetId") long objetId);
	
	@Query("match(n:Objet)-[AVOIR]->(enf) where id(n)=$objetId return id(enf) order by id(enf)")
	Iterable<Integer> findProprietesOrderByIdAsc(@Param(value = "objetId") long objetId);
	*/
	@Query("match(n:Objet)-[AVOIR]->(enf) where id(n)=$objetId return id(enf) order by enf.nom DESC")
	Iterable<Integer> findProprietesOrderByNomDesc(@Param(value = "objetId") long objetId);
	
	@Query("match(n:Objet)-[AVOIR]->(enf) where id(n)=$objetId return id(enf) order by enf.nom")
	Iterable<Integer> findProprietesOrderByNomAsc(@Param(value = "objetId") long objetId);
	/*
	@Query("match(n:Objet)-[POSSEDER]->(enf) where id(n)=$objetId return id(enf) order by id(enf) DESC")
	Iterable<Integer> findObjetsOrderByIdDesc(@Param(value = "objetId") long objetId);
	
	@Query("match(n:Objet)-[POSSEDER]->(enf) where id(n)=$objetId return id(enf) order by id(enf)")
	Iterable<Integer> findObjetsOrderByIdAsc(@Param(value = "objetId") long objetId);
	*/
	@Query("match(n:Objet)-[POSSEDER]->(enf) where id(n)=$objetId return id(enf) order by enf.nom DESC")
	Iterable<Integer> findObjetsOrderByNomDesc(@Param(value = "objetId") long objetId);
	
	@Query("match(n:Objet)-[POSSEDER]->(enf) where id(n)=$objetId return id(enf) order by enf.nom")
	Iterable<Integer> findObjetsOrderByNomAsc(@Param(value = "objetId") long objetId);
}
