package fr.utbm.da50.configurationrepositorymanager.controller;

import fr.utbm.da50.configurationrepositorymanager.dto.ConfigurationDto;
import fr.utbm.da50.configurationrepositorymanager.dto.ObjetDto;
import fr.utbm.da50.configurationrepositorymanager.dto.ProprieteDto;
import fr.utbm.da50.configurationrepositorymanager.entity.Configuration;
import fr.utbm.da50.configurationrepositorymanager.entity.Objet;
import fr.utbm.da50.configurationrepositorymanager.entity.Propriete;
import fr.utbm.da50.configurationrepositorymanager.exception.ResourceNotFoundException;
import fr.utbm.da50.configurationrepositorymanager.service.ConfigurationService;
import fr.utbm.da50.configurationrepositorymanager.service.ObjetService;
import fr.utbm.da50.configurationrepositorymanager.service.ProprieteService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
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
    @PutMapping("/configuration/{id}")
    public void updateConfiguration(@RequestBody ConfigurationDto configurationDto){
        Configuration configuration = convertToEntity(configurationDto);
        configurationService.saveConfiguration(configuration);
    }

    // delete configuration
    @DeleteMapping("/configuration/{id}")
    public void deleteConfiguration(@PathVariable Long id){
        configurationService.deleteConfiguration(id);
    }

    /*
     *  OBJET API REST
     * 
     */

    // create objet
    @PostMapping("/objet")
    public ObjetDto createObjet(@RequestBody ObjetDto objetDto) {
        Objet objet = convertToEntity(objetDto);
        Objet objetCreated = objetService.saveObjet(objet);
        return convertToDto(objetCreated);
    }

    // get object by id
    @GetMapping("/objet/{id}")
    public ObjetDto getObjetById(@PathVariable Long id) {
        return convertToDto(objetService.getObjet(id)
                .orElseThrow(() -> new ResourceNotFoundException("Objet not exist with id :" + id)));
    }

    // update objet
    @PutMapping("/objet/{id}")
    public void updateObjet(@RequestBody ObjetDto objetDto){
        Objet objet = convertToEntity(objetDto);
        objetService.saveObjet(objet);
    }

    // delete objet
    @DeleteMapping("/objet/{id}")
    public void deleteObjet(@PathVariable Long id){
        objetService.deleteObjet(id);
    }

    /*
     *  PROPRIETE API REST
     * 
     */

    // create propriete
    @PostMapping("/propriete")
    public ProprieteDto createPropriete(@RequestBody ProprieteDto proprieteDto) {
        Propriete propriete = convertToEntity(proprieteDto);
        Propriete proprieteCreated = proprieteService.savePropriete(propriete);
        return convertToDto(proprieteCreated);
    }

    // get object by id
    @GetMapping("/propriete/{id}")
    public ProprieteDto getProprieteById(@PathVariable Long id) {
        return convertToDto(proprieteService.getPropriete(id)
                .orElseThrow(() -> new ResourceNotFoundException("Propriete not exist with id :" + id)));
    }

    // update propriete
    @PutMapping("/propriete/{id}")
    public void updatePropriete(@RequestBody ProprieteDto proprieteDto){
        Propriete propriete = convertToEntity(proprieteDto);
        proprieteService.savePropriete(propriete);
    }

    // delete propriete
    @DeleteMapping("/propriete/{id}")
    public void deletePropriete(@PathVariable Long id){
        proprieteService.deletePropriete(id);
    }

    /*
     *  DTO CONVERSIONS
     *
     */


    // Configuration

    private ConfigurationDto convertToDto(Configuration c) {
        ConfigurationDto configurationDto = modelMapper.map(c, ConfigurationDto.class);
        

        for(Objet o : c.getObjets()){
            configurationDto.getObjets().add(convertToDto(o));
        }

        return configurationDto;
    }
    
    private Configuration convertToEntity(ConfigurationDto configurationDto) {
        Configuration c = modelMapper.map(configurationDto, Configuration.class);
        
        return c;
    }


    // Objet

    private ObjetDto convertToDto(Objet o) {
        ObjetDto objetDto = modelMapper.map(o, ObjetDto.class);

        for(Objet enfant : o.getObjets()){
            if(objetDto.getObjetsEnfants() == null){
                objetDto.setObjetsEnfants(new HashSet<ObjetDto>());
            }
            objetDto.getObjetsEnfants().add(convertToDto(enfant));
        }

        return objetDto;
    }

    private Objet convertToEntity(ObjetDto objetDto) {
        Objet o = modelMapper.map(objetDto, Objet.class);

        for(ObjetDto enfant : objetDto.getObjetsEnfants()){
            if(o.getObjets() == null){
                o.setObjets(new HashSet<Objet>());
            }
            o.getObjets().add(convertToEntity(enfant));
        }

        return o;
    }


    // Propriete

    private ProprieteDto convertToDto(Propriete c) {
        ProprieteDto ProprieteDto = modelMapper.map(c, ProprieteDto.class);

        return ProprieteDto;
    }

    private Propriete convertToEntity(ProprieteDto ProprieteDto) {
        Propriete c = modelMapper.map(ProprieteDto, Propriete.class);

        return c;
    }
}
