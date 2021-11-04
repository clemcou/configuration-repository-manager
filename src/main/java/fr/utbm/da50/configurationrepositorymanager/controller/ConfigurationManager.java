package fr.utbm.da50.configurationrepositorymanager.controller;

import fr.utbm.da50.configurationrepositorymanager.dto.ConfigurationDto;
import fr.utbm.da50.configurationrepositorymanager.entity.Configuration;
import fr.utbm.da50.configurationrepositorymanager.exception.ResourceNotFoundException;
import fr.utbm.da50.configurationrepositorymanager.service.ConfigurationService;
import fr.utbm.da50.configurationrepositorymanager.service.ObjetService;
import fr.utbm.da50.configurationrepositorymanager.service.ProprieteService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api/v1/")
public class ConfigurationManager {
    @Autowired
    private ConfigurationService configurationService;
    @Autowired
    private ObjetService objetService;
    @Autowired
    private ProprieteService proprieteService;
    
    @Autowired
    private ModelMapper modelMapper;

    // get all configurations
    @GetMapping("/configurations")
    public List<ConfigurationDto> getAllConfigurations(){
        Iterable<Configuration> configurations = configurationService.getConfigurations();
        
        //Iterable<> to List<>
        List<Configuration> result = new ArrayList<Configuration>();
        for (Configuration c : configurations) {
            result.add(c);
        }
        return result.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // create configuration rest api
    @PostMapping("/configurations")
    public ConfigurationDto createConfiguration(@RequestBody ConfigurationDto configurationDto) {
        Configuration configuration = convertToEntity(configurationDto);
        Configuration configurationCreated = configurationService.saveConfiguration(configuration);
        return convertToDto(configurationCreated);
    }

    // get configuration by id rest api
    @GetMapping("/configurations/{id}")
    public ConfigurationDto getConfigurationById(@PathVariable Long id) {
        return convertToDto(configurationService.getConfiguration(id)
                .orElseThrow(() -> new ResourceNotFoundException("Configuration not exist with id :" + id)));
    }

    // update configuration rest api
    @PutMapping("/configurations/{id}")
    public void updateConfiguration(@RequestBody ConfigurationDto configurationDto){
        Configuration configuration = convertToEntity(configurationDto);
        configurationService.saveConfiguration(configuration);
    }

    // delete configuration rest api
    @DeleteMapping("/configurations/{id}")
    public void deleteConfiguration(@PathVariable Long id){
        configurationService.deleteConfiguration(id);
    }
    
    private ConfigurationDto convertToDto(Configuration c) {
        ConfigurationDto configurationDto = modelMapper.map(c, ConfigurationDto.class);
        
        //TODO
        
        return configurationDto;
    }
    
    private Configuration convertToEntity(ConfigurationDto configurationDto) {
        Configuration c = modelMapper.map(configurationDto, Configuration.class);
        
        //TODO
        
        return c;
    }
    
}
