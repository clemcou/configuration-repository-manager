package fr.utbm.da50.configurationrepositorymanager.controller;

import fr.utbm.da50.configurationrepositorymanager.dto.ConfigurationDto;
import fr.utbm.da50.configurationrepositorymanager.entity.Configuration;
import fr.utbm.da50.configurationrepositorymanager.entity.Referentiel;
import fr.utbm.da50.configurationrepositorymanager.entity.Objet;
import fr.utbm.da50.configurationrepositorymanager.exception.ResourceNotFoundException;
import fr.utbm.da50.configurationrepositorymanager.service.ConfigurationService;
import fr.utbm.da50.configurationrepositorymanager.service.ReferentielService;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/")
public class ConfigurationManager {

    private final ReferentielService referentielService;
    private final ConfigurationService configurationService;

    private final ModelMapper modelMapper;

    public ConfigurationManager(ReferentielService referentielService, ConfigurationService configurationService, ModelMapper modelMapper) {
        Objects.requireNonNull(referentielService);
        Objects.requireNonNull(configurationService);
        Objects.requireNonNull(modelMapper);
        this.referentielService = referentielService;
        this.configurationService = configurationService;
        this.modelMapper = modelMapper;
    }

    /*
     *  CONFIGURATION API REST
     *
     */

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

    // create configuration
    @PostMapping("/configuration")
    public ConfigurationDto createConfiguration(@RequestBody ConfigurationDto configurationDto) {
        Configuration configuration = convertToEntity(configurationDto);
        Configuration configurationCreated = configurationService.saveConfiguration(configuration);
        return convertToDto(configurationCreated);
    }

    // get configuration by id
    @GetMapping("/configuration/{id}")
    public ConfigurationDto getConfigurationById(@PathVariable Long id) {
        return convertToDto(configurationService.getConfiguration(id)
                .orElseThrow(() -> new ResourceNotFoundException("Configuration not exist with id :" + id)));
    }

    // update configuration
    @PatchMapping("/configuration/{id}")
    public void updateConfiguration(@PathVariable Long id, @RequestBody Map<String, Object> patchValues){
        Configuration conf = configurationService.getConfiguration(id)
                .orElseThrow(() -> new ResourceNotFoundException("Configuration not exist with id :" + id));
        ConfigurationDto dto = convertToDto(conf);
        modelMapper.map(patchValues, dto);
        Configuration configuration = convertToEntity(dto);
        configurationService.updateConfig(id, configuration);
    }

    // delete configuration
    @DeleteMapping("/configuration/{id}")
    public void deleteConfiguration(@PathVariable Long id){
        configurationService.deleteWithAllRelatedObjets(id);
    }

    // get all configurations from a referentiel
    @GetMapping("/referentiel/{id}/configurations")
    public List<ConfigurationDto> getAllConfigurationsFromReferentiel(@PathVariable Long id){
        Iterable<Configuration> configurations = referentielService.getConfigurationsOfReferentiel(id);

        //Iterable<> to List<>
        List<Configuration> result = new ArrayList<Configuration>();
        for (Configuration c : configurations) {
            result.add(c);
        }

        return result.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // create configuration and link it to a referentiel
    @PostMapping("/referentiel/{id}/configuration")
    public ConfigurationDto createConfiguration(@PathVariable Long id, @RequestBody ConfigurationDto configurationDto) {
        Referentiel ref = referentielService.getReferentiel(id)
                .orElseThrow(() -> new ResourceNotFoundException("Referentiel not exist with id :" + id));

        Configuration configuration = convertToEntity(configurationDto);

        //duplicate objects from the referentiel to the fresh new configuration
        configuration.setObjets(new HashSet<Objet>());
        for(Objet o : ref.getObjets()){
            Objet newObj = new Objet(o.getNom(), o.getDescription(), o.getObjets(), o.getProprietes());
            configuration.getObjets().add(newObj);
        }

        Configuration configurationCreated = configurationService.saveConfiguration(configuration);

        //link the configuration to the referentiel
        ref.getConfigurations().add(configurationCreated);
        referentielService.saveReferentiel(ref);

        return convertToDto(configurationCreated);
    }

/*    @PostMapping("/configuration")
    public ConfigurationDto createConfigurationV2(@RequestBody Map<String, Object> payload) {

        Long id = (Long) payload.get("referentielId");
        ConfigurationDto configurationDto = (ConfigurationDto) payload.get("configuration");

        Referentiel ref = referentielService.getReferentiel(id)
                .orElseThrow(() -> new ResourceNotFoundException("Referentiel not exist with id :" + id));

        Configuration configuration = convertToEntity(configurationDto);
        Configuration configurationCreated = configurationService.saveConfiguration(configuration);

        ref.getConfigurations().add(configurationCreated);

        return convertToDto(configurationCreated);
    }*/

    // get configuration by id of referentiel and configuration
    @GetMapping("/referentiel/{refId}/configuration/{confId}")
    public ConfigurationDto getConfigurationByIds(@PathVariable Long refId, @PathVariable Long confId) {
        Referentiel ref = referentielService.getReferentiel(refId)
                .orElseThrow(() -> new ResourceNotFoundException("Referentiel not exist with id :" + refId));

        Configuration config = ref.getConfigurations().stream().filter(configuration -> confId.equals(configuration.getId()))
                .findAny()
                .orElseThrow(() -> new ResourceNotFoundException("Configuration not exist with id :" + confId));

        return convertToDto(config);
    }

    /*
     *  DTO CONVERSIONS
     *
     */

    // Configuration

    private ConfigurationDto convertToDto(Configuration c) {
        ConfigurationDto configurationDto = modelMapper.map(c, ConfigurationDto.class);

/*        if(c.getObjets() != null)
        {
            for(Objet o : c.getObjets()){
                configurationDto.getObjets().add(objetManager.convertToDto(o));
            }
        }*/

        return configurationDto;
    }
    
    private Configuration convertToEntity(ConfigurationDto configurationDto) {
        return modelMapper.map(configurationDto, Configuration.class);
    }
}
