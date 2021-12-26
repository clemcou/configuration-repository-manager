package fr.utbm.da50.configurationrepositorymanager.controller;

import fr.utbm.da50.configurationrepositorymanager.dto.ProprieteDto;
import fr.utbm.da50.configurationrepositorymanager.entity.Objet;
import fr.utbm.da50.configurationrepositorymanager.entity.Propriete;
import fr.utbm.da50.configurationrepositorymanager.exception.ResourceNotFoundException;
import fr.utbm.da50.configurationrepositorymanager.service.ObjetService;
import fr.utbm.da50.configurationrepositorymanager.service.ProprieteService;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@CrossOrigin
@RestController
@RequestMapping("/api/v1/")
public class ProprieteManager {

    private final ObjetService objetService;
    private final ProprieteService proprieteService;

    private final ModelMapper modelMapper;

    public ProprieteManager(ObjetService objetService, ProprieteService proprieteService, ModelMapper modelMapper) {
        Objects.requireNonNull(objetService);
        Objects.requireNonNull(modelMapper);
        this.objetService = objetService;
        this.proprieteService = proprieteService;
        this.modelMapper = modelMapper;
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
    @PatchMapping("/propriete/{id}")
    public void updatePropriete(@PathVariable Long id, @RequestBody Map<String, Object> patchValues){
        Propriete prop = proprieteService.getPropriete(id)
                .orElseThrow(() -> new ResourceNotFoundException("Propriete not exist with id :" + id));
        ProprieteDto dto = convertToDto(prop);
        modelMapper.map(patchValues, dto);
        Propriete propriete = convertToEntity(dto);
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

    // Propriete

    private ProprieteDto convertToDto(Propriete c) {
        return modelMapper.map(c, ProprieteDto.class);
    }

    private Propriete convertToEntity(ProprieteDto ProprieteDto) {
        return modelMapper.map(ProprieteDto, Propriete.class);
    }
}
