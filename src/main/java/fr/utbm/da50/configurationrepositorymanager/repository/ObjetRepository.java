package fr.utbm.da50.configurationrepositorymanager.repository;

import fr.utbm.da50.configurationrepositorymanager.entity.Objet;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
@Qualifier("ObjetRepository")
public interface ObjetRepository extends CrudRepository<Objet, Long> {


}
