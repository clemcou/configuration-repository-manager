package fr.utbm.da50.configurationrepositorymanager.repository;

import fr.utbm.da50.configurationrepositorymanager.entity.Configuration;
import fr.utbm.da50.configurationrepositorymanager.entity.Referentiel;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
@Qualifier("ReferentielRepository")
public interface ReferentielRepository extends Neo4jRepository<Referentiel, Long> {
    @Query("match(n)-[*0..]->(x) where id(n)=$toId detach delete n,x")
    void deleteAllRelatedEntity(@Param(value = "toId") long toId);

    Iterable<Referentiel> findByNomLike(String nom);

    //Iterable<Referentiel> findAllByOrderById();

    Iterable<Referentiel> findAllByOrderByNomAsc();

    Iterable<Referentiel> findAllByOrderByNomDesc();
    /*
    @Query("match(n:Configuration)-[CONTENIR]->(enf) where id(n)=$configId return id(enf) order by id(enf) DESC")
    Iterable<Integer> findObjetsOrderByIdDesc(@Param(value = "configId") long configId);

    @Query("match(n:Configuration)-[CONTENIR]->(enf) where id(n)=$configId return id(enf) order by id(enf)")
    Iterable<Integer> findObjetsOrderByIdAsc(@Param(value = "configId") long configId);
    */
    @Query("match(n:Referentiel)-[CONTENIR]->(enf) where id(n)=$referentielId return id(enf) order by enf.nom DESC")
    Iterable<Integer> findObjetsOrderByNomDesc(@Param(value = "referentielId") long referentielId);

    @Query("match(n:Referentiel)-[CONTENIR]->(enf) where id(n)=$referentielId return id(enf) order by enf.nom")
    Iterable<Integer> findObjetsOrderByNomAsc(@Param(value = "referentielId") long referentielId);
}
