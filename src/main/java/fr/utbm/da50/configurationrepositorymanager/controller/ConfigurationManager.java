package fr.utbm.da50.configurationrepositorymanager.controller;

import fr.utbm.da50.configurationrepositorymanager.dto.ConfigurationDto;
import fr.utbm.da50.configurationrepositorymanager.dto.ObjetDto;
import fr.utbm.da50.configurationrepositorymanager.dto.ProprieteDto;
import fr.utbm.da50.configurationrepositorymanager.dto.ReferentielDto;
import fr.utbm.da50.configurationrepositorymanager.entity.Configuration;
import fr.utbm.da50.configurationrepositorymanager.entity.Objet;
import fr.utbm.da50.configurationrepositorymanager.entity.Propriete;
import fr.utbm.da50.configurationrepositorymanager.entity.Referentiel;
import fr.utbm.da50.configurationrepositorymanager.exception.ResourceNotFoundException;
import fr.utbm.da50.configurationrepositorymanager.service.ConfigurationService;
import fr.utbm.da50.configurationrepositorymanager.service.ObjetService;
import fr.utbm.da50.configurationrepositorymanager.service.ProprieteService;
import fr.utbm.da50.configurationrepositorymanager.service.ReferentielService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/")
public class ConfigurationManager {

    @Autowired
    private ReferentielService referentielService;
    @Autowired
    private ConfigurationService configurationService;
    @Autowired
    private ObjetService objetService;
    @Autowired
    private ProprieteService proprieteService;
     
    @Autowired
    private ModelMapper modelMapper;

    /*
     *  REFERENTIEL API REST
     *
     */

    // get all referentiels
    @GetMapping("/referentiels")
    public List<ReferentielDto> getAllReferentiels(){
        Iterable<Referentiel> referentiels = referentielService.getReferentiels();

        //Iterable<> to List<>
        List<Referentiel> result = new ArrayList<Referentiel>();
        for (Referentiel c : referentiels) {
            result.add(c);
        }

        return result.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // create referentiel
    @PostMapping("/referentiel")
    public ReferentielDto createReferentiel(@RequestBody ReferentielDto referentielDto) {
        Referentiel referentiel = convertToEntity(referentielDto);
        Referentiel referentielCreated = referentielService.saveReferentiel(referentiel);
        return convertToDto(referentielCreated);
    }

    // get referentiel by id
    @GetMapping("/referentiel/{id}")
    public ReferentielDto getReferentielById(@PathVariable Long id) {
        return convertToDto(referentielService.getReferentiel(id)
                .orElseThrow(() -> new ResourceNotFoundException("Referentiel not exist with id :" + id)));
    }

    // update referentiel
    @PutMapping("/referentiel/{id}")
    public void updateReferentiel(@PathVariable Long id, @RequestBody ReferentielDto referentielDto){
        Referentiel referentiel = convertToEntity(referentielDto);
        referentielService.updateReferentiel(id, referentiel);
    }

    // delete referentiel
    @DeleteMapping("/referentiel/{id}")
    public void deleteReferentiel(@PathVariable Long id){
        referentielService.deleteWithAllRelatedObjets(id);
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
    @PutMapping("/configuration/{id}")
    public void updateConfiguration(@PathVariable Long id, @RequestBody ConfigurationDto configurationDto){
        Configuration configuration = convertToEntity(configurationDto);
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
        Configuration configurationCreated = configurationService.saveConfiguration(configuration);

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

    // get configuration by id of regerentiel and configuration
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
     *  OBJET API REST
     * 
     */

    // create objet and link it to an objet
    @PostMapping("/objet/{id}/objet")
    public ObjetDto createObjetInObjet(@PathVariable Long id, @RequestBody ObjetDto objetDto) {
        Objet objParent = objetService.getObjet(id)
                .orElseThrow(() -> new ResourceNotFoundException("Objet not exist with id :" + id));

        Objet objet = convertToEntity(objetDto);
        Objet objetCreated = objetService.saveObjet(objet);

        objParent.getObjets().add(objetCreated);
        objetService.saveObjet(objParent);

        return convertToDto(objetCreated);
    }

    // create objet and link it to a referentiel
    @PostMapping("/referentiel/{id}/objet")
    public ObjetDto createObjetInReferentiel(@PathVariable Long id, @RequestBody ObjetDto objetDto) {
        Referentiel ref = referentielService.getReferentiel(id)
                .orElseThrow(() -> new ResourceNotFoundException("Referentiel not exist with id :" + id));

        Objet objet = convertToEntity(objetDto);
        Objet objetCreated = objetService.saveObjet(objet);

        ref.getObjets().add(objetCreated);
        referentielService.saveReferentiel(ref);

        return convertToDto(objetCreated);
    }

    // create objet and link it to a configuration
    @PostMapping("/configuration/{id}/objet")
    public ObjetDto createObjetInConfig(@PathVariable Long id, @RequestBody ObjetDto objetDto) {
        Configuration configParent = configurationService.getConfiguration(id)
                .orElseThrow(() -> new ResourceNotFoundException("Configuration not exist with id :" + id));

        Objet objet = convertToEntity(objetDto);
        Objet objetCreated = objetService.saveObjet(objet);

        configParent.getObjets().add(objetCreated);
        configurationService.saveConfiguration(configParent);

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
    public void updateObjet(@PathVariable Long id, @RequestBody ObjetDto objetDto){
        Objet objet = convertToEntity(objetDto);
        objetService.updateObjet(id, objet);
    }

    // delete objet
    @DeleteMapping("/objet/{id}")
    public void deleteObjet(@PathVariable Long id){
        objetService.deleteWithAllRelatedEntity(id);
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

    // create propriete and link it to an objet
    @PostMapping("/objet/{id}/propriete")
    public ProprieteDto createProprieteInObjet(@PathVariable Long id, @RequestBody ProprieteDto proprieteDto) {
        Objet objParent = objetService.getObjet(id)
                .orElseThrow(() -> new ResourceNotFoundException("Objet not exist with id :" + id));

        Propriete propriete = convertToEntity(proprieteDto);
        Propriete proprieteCreated = proprieteService.savePropriete(propriete);

        objParent.getProprietes().add(proprieteCreated);
        objetService.saveObjet(objParent);

        return convertToDto(proprieteCreated);
    }

    // get propriete by id
    @GetMapping("/propriete/{id}")
    public ProprieteDto getProprieteById(@PathVariable Long id) {
        return convertToDto(proprieteService.getPropriete(id)
                .orElseThrow(() -> new ResourceNotFoundException("Propriete not exist with id :" + id)));
    }

    // update propriete
    @PutMapping("/propriete/{id}")
    public void updatePropriete(@PathVariable Long id, @RequestBody ProprieteDto proprieteDto){
        Propriete propriete = convertToEntity(proprieteDto);
        proprieteService.updatePropriete(id, propriete);
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


    // Referentiel

    private ReferentielDto convertToDto(Referentiel r) {
        ReferentielDto referentielDto = modelMapper.map(r, ReferentielDto.class);

        if(r.getObjets() != null)
        {
            for(Objet o : r.getObjets()){
                referentielDto.getObjets().add(convertToDto(o));
            }
        }

        if(r.getConfigurations() != null)
        {
            for(Configuration c : r.getConfigurations()){
                referentielDto.getConfigurations().add(convertToDto(c));
            }
        }

        return referentielDto;
    }

    private Referentiel convertToEntity(ReferentielDto referentielDto) {
        return modelMapper.map(referentielDto, Referentiel.class);
    }

    // Configuration

    private ConfigurationDto convertToDto(Configuration c) {
        ConfigurationDto configurationDto = modelMapper.map(c, ConfigurationDto.class);


        if(c.getObjets() != null)
        {
            for(Objet o : c.getObjets()){
                configurationDto.getObjets().add(convertToDto(o));
            }
        }

        return configurationDto;
    }
    
    private Configuration convertToEntity(ConfigurationDto configurationDto) {
        return modelMapper.map(configurationDto, Configuration.class);
    }


    // Objet

    private ObjetDto convertToDto(Objet o) {
        ObjetDto objetDto = modelMapper.map(o, ObjetDto.class);

        if(o.getObjets() != null)
        {
            for(Objet enfant : o.getObjets()){
                if(objetDto.getObjetsEnfants() == null){
                    objetDto.setObjetsEnfants(new HashSet<ObjetDto>());
                }
                objetDto.getObjetsEnfants().add(convertToDto(enfant));
            }
        }

        return objetDto;
    }

    private Objet convertToEntity(ObjetDto objetDto) {
        Objet o = modelMapper.map(objetDto, Objet.class);

        if(objetDto.getObjetsEnfants() != null)
        {
            for(ObjetDto enfant : objetDto.getObjetsEnfants()){
                if(o.getObjets() == null){
                    o.setObjets(new HashSet<Objet>());
                }
                o.getObjets().add(convertToEntity(enfant));
            }
        }

        return o;
    }


    // Propriete

    private ProprieteDto convertToDto(Propriete c) {
        return modelMapper.map(c, ProprieteDto.class);
    }

    private Propriete convertToEntity(ProprieteDto ProprieteDto) {
        return modelMapper.map(ProprieteDto, Propriete.class);
    }
}
