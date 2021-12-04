package fr.utbm.da50.configurationrepositorymanager.service;

import fr.utbm.da50.configurationrepositorymanager.entity.Configuration;
import fr.utbm.da50.configurationrepositorymanager.entity.Objet;

import fr.utbm.da50.configurationrepositorymanager.repository.ConfigurationRepository;
import lombok.Data;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Data
@Service
public class ConfigurationService {
    
    @Autowired
    private ConfigurationRepository configurationRepository;
    
    @Autowired
    private ModelMapper modelMapper;
    
    @Autowired
    private ObjetService objetService;
    
    //READ
    
    public Optional<Configuration> getConfiguration(final Long id) {
        return configurationRepository.findById(id);
    }
    
    public Iterable<Configuration> getConfigurations() {
        return configurationRepository.findAll();
    }
    
    public Iterable<Configuration> getConfigurationsByNom(String nom) {
        return configurationRepository.findByNomLike(".*"+nom+".*");
    }
    
    public Iterable<Objet> getObjetsOfConfig(final Long id){
    	Optional<Configuration> confOpt = configurationRepository.findById(id);
    	return confOpt.isPresent() ? confOpt.get().getObjets() : null;
    }
    
    //DELETE
    
    /**
     * this method will only delete the configuration, but will not delete related entities
     * @param id
     */
    public void deleteConfiguration(final Long id) {
        configurationRepository.deleteById(id);
    }
    
    public void deleteWithAllRelatedObjets(final Long id) {
    	configurationRepository.deleteAllRelatedEntity(id);
    }
    
    //CREATE
    
    public Configuration saveConfiguration(Configuration configuration) {
        Configuration savedConfiguration = configurationRepository.save(configuration);
        return savedConfiguration;
    }
    
    //UPDATE
    
    public void updateConfigNom(final Long id, String name) {
    	Optional<Configuration> confOpt = configurationRepository.findById(id);
    	if(confOpt.isPresent()){
    		Configuration configuration = confOpt.get();
    		configuration.setNom(name);
    		configurationRepository.save(configuration);
    	}
	}
    
    public void updateConfigDescription(final Long id, String description) {
    	Optional<Configuration> confOpt = configurationRepository.findById(id);
    	if(confOpt.isPresent()){
    		Configuration configuration = confOpt.get();
    		configuration.setDescription(description);
    		configurationRepository.save(configuration);
    	}
	}
    
    public void updateConfigDescAndNom(final Long id, String description, String name) {
    	Optional<Configuration> confOpt = configurationRepository.findById(id);
    	if(confOpt.isPresent()){
    		Configuration configuration = confOpt.get();
    		configuration.setNom(name);
    		configuration.setDescription(description);
    		configurationRepository.save(configuration);
    	}
    }
    


}
