package fr.utbm.da50.configurationrepositorymanager.repository;

import fr.utbm.da50.configurationrepositorymanager.entity.Configuration;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
@Qualifier("ConfigurationRepository")
public interface ConfigurationRepository extends CrudRepository<Configuration, Long> {


}


