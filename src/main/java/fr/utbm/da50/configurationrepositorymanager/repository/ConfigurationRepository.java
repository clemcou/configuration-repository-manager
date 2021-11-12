package fr.utbm.da50.configurationrepositorymanager.repository;

import fr.utbm.da50.configurationrepositorymanager.entity.Configuration;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
@Qualifier("ConfigurationRepository")
public interface ConfigurationRepository extends Neo4jRepository<Configuration, Long> {


}


