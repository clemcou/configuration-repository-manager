package fr.utbm.da50.configurationrepositorymanager.controller;

import fr.utbm.da50.configurationrepositorymanager.dto.ObjetDto;
import fr.utbm.da50.configurationrepositorymanager.entity.Configuration;
import fr.utbm.da50.configurationrepositorymanager.entity.Objet;
import fr.utbm.da50.configurationrepositorymanager.entity.Referentiel;
import fr.utbm.da50.configurationrepositorymanager.exception.ResourceNotFoundException;
import fr.utbm.da50.configurationrepositorymanager.service.ConfigurationService;
import fr.utbm.da50.configurationrepositorymanager.service.ObjetService;
import fr.utbm.da50.configurationrepositorymanager.service.ReferentielService;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;


@CrossOrigin
@RestController
@RequestMapping("/api/v1/")
public class ObjetManager {

    private final ReferentielService referentielService;
    private final ConfigurationService configurationService;
    private final ObjetService objetService;

    private final ModelMapper modelMapper;

    public ObjetManager(ReferentielService referentielService, ConfigurationService configurationService, ObjetService objetService, ModelMapper modelMapper) {
        Objects.requireNonNull(referentielService);
        Objects.requireNonNull(configurationService);
        Objects.requireNonNull(objetService);
        Objects.requireNonNull(modelMapper);
        this.referentielService = referentielService;
        this.configurationService = configurationService;
        this.objetService = objetService;
        this.modelMapper = modelMapper;
    }


    /*
     *  OBJET API REST
     *
     */

    // get all objets from a referentiel
    @GetMapping("/referentiel/{id}/objets")
    public List<ObjetDto> getAllObjetsFromReferentiel(@PathVariable Long id){
        Iterable<Objet> objets = referentielService.getObjetsOfReferentiel(id);

        //Iterable<> to List<>
        List<Objet> result = new ArrayList<Objet>();
        for (Objet o : objets) {
            result.add(o);
        }

        return result.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // get all objets from a configuration
    @GetMapping("/configuration/{id}/objets")
    public List<ObjetDto> getAllObjetsFromConfiguration(@PathVariable Long id){
        Iterable<Objet> objets = configurationService.getObjetsOfConfig(id);

        //Iterable<> to List<>
        List<Objet> result = new ArrayList<Objet>();
        for (Objet o : objets) {
            result.add(o);
        }

        return result.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // get all objets from a objet
    @GetMapping("/objet/{id}/objets")
    public List<ObjetDto> getAllObjetsFromObjet(@PathVariable Long id){
        Iterable<Objet> objets = objetService.getObjetsOfObjet(id);

        //Iterable<> to List<>
        List<Objet> result = new ArrayList<Objet>();
        for (Objet o : objets) {
            result.add(o);
        }

        return result.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

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
    @PatchMapping("/objet/{id}")
    public void updateObjet(@PathVariable Long id, @RequestBody Map<String, Object> patchValues){
        Objet obj = objetService.getObjet(id)
                .orElseThrow(() -> new ResourceNotFoundException("Objet not exist with id :" + id));
        ObjetDto dto = convertToDto(obj);
        modelMapper.map(patchValues, dto);
        Objet objet = convertToEntity(dto);
        objetService.updateObjet(id, objet);
    }

    // delete objet
    @DeleteMapping("/objet/{id}")
    public void deleteObjet(@PathVariable Long id){
        objetService.deleteWithAllRelatedEntity(id);
    }


    /*
     *  DTO CONVERSIONS
     *
     */

    // Objet

    public ObjetDto convertToDto(Objet o) {
        ObjetDto objetDto = modelMapper.map(o, ObjetDto.class);

        if(o.getObjets() != null)
        {
            for(Objet enfant : o.getObjets()){
                if(objetDto.getObjets() == null){
                    objetDto.setObjets(new HashSet<ObjetDto>());
                }
                objetDto.getObjets().add(convertToDto(enfant));
            }
        }

        return objetDto;
    }

    public Objet convertToEntity(ObjetDto objetDto) {
        Objet o = modelMapper.map(objetDto, Objet.class);

        if(objetDto.getObjets() != null)
        {
            for(ObjetDto enfant : objetDto.getObjets()){
                if(o.getObjets() == null){
                    o.setObjets(new HashSet<Objet>());
                }
                o.getObjets().add(convertToEntity(enfant));
            }
        }

        return o;
    }
}
