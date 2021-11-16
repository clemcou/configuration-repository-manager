package fr.utbm.da50.configurationrepositorymanager.service;

import fr.utbm.da50.configurationrepositorymanager.entity.Configuration;
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
    
    public Optional<Configuration> getConfiguration(final Long id) {
        return configurationRepository.findById(id);
    }
    
    public Iterable<Configuration> getConfigurations() {
        return configurationRepository.findAll();
    }
    
    public void deleteConfiguration(final Long id) {
        configurationRepository.deleteById(id);
    }
    
    public Configuration saveConfiguration(Configuration configuration) {
        Configuration savedConfiguration = configurationRepository.save(configuration);
        return savedConfiguration;
    }
    
    //TODO
}
