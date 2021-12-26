package fr.utbm.da50.configurationrepositorymanager.service;

import fr.utbm.da50.configurationrepositorymanager.entity.Configuration;
import fr.utbm.da50.configurationrepositorymanager.entity.Objet;

import fr.utbm.da50.configurationrepositorymanager.repository.ConfigurationRepository;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Data
@Service
public class ConfigurationService {
    
    private ConfigurationRepository configurationRepository;
    
    private ObjetService objetService;

    public ConfigurationService(ConfigurationRepository configurationRepository, ObjetService objetService){
        Objects.requireNonNull(configurationRepository);
        Objects.requireNonNull(objetService);
        this.configurationRepository = configurationRepository;
        this.objetService = objetService;
    }
    
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
    /*
    public Iterable<Configuration> getConfigurationsOrderById(){
    	return configurationRepository.findAllByOrderById();
    }
    */
    public Iterable<Configuration> getConfigurationsOrderByNomAsc(){
    	return configurationRepository.findAllByOrderByNomAsc();
    }
    
    public Iterable<Configuration> getConfigurationsOrderByNomDesc(){
    	return configurationRepository.findAllByOrderByNomDesc();
    }
    /*
     * order by id may not be used by user
    public List<Objet> getObjetsOrderByIdDesc(final Long id){
    	return int2ObjetList(configurationRepository.findObjetsOrderByIdDesc(id));
    }
    
    public List<Objet> getObjetsOrderByIdAsc(final Long id){
    	return int2ObjetList(configurationRepository.findObjetsOrderByIdAsc(id));
    }
    */
    public List<Objet> getObjetsOrderByNomDesc(final Long id){
    	return int2ObjetList(configurationRepository.findObjetsOrderByNomDesc(id));
    }
    
    public List<Objet> getObjetsOrderByNomAsc(final Long id){
    	return int2ObjetList(configurationRepository.findObjetsOrderByNomAsc(id));
    }
    
    public List<Objet> int2ObjetList(Iterable<Integer> idSet){
    	List<Objet> objetList = new ArrayList<Objet>();
    	for(Integer i : idSet) {
    		Optional<Objet> objetOpt = objetService.getObjet((long)i);
    		if(objetOpt.isPresent()) {
    			objetList.add(objetOpt.get());
    		}
    	}
    	return objetList;
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
        return configurationRepository.save(configuration);
    }
    
    //UPDATE

    public void updateConfig(final Long id, Configuration configuration) {
        if(configurationRepository.findById(id).isPresent()){
            configuration.setId(id);
            configurationRepository.save(configuration);
        }
    }
    
    public void updateConfigNom(final Long id, String name) {
    	Optional<Configuration> confOpt = configurationRepository.findById(id);
    	if(configurationRepository.findById(id).isPresent()){
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
