package fr.utbm.da50.configurationrepositorymanager.repository;

import fr.utbm.da50.configurationrepositorymanager.entity.Objet;
import fr.utbm.da50.configurationrepositorymanager.entity.Propriete;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
@Qualifier("ProprieteRepository")
public interface ProprieteRepository extends CrudRepository<Propriete, Long> {


}
