package fr.utbm.da50.configurationrepositorymanager.controller;

import fr.utbm.da50.configurationrepositorymanager.dto.ReferentielDto;
import fr.utbm.da50.configurationrepositorymanager.entity.Referentiel;
import fr.utbm.da50.configurationrepositorymanager.exception.ResourceNotFoundException;
import fr.utbm.da50.configurationrepositorymanager.service.ReferentielService;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;


@CrossOrigin
@RestController
@RequestMapping("/api/v1/")
public class ReferentielManager {

    private final ReferentielService referentielService;
    private final ModelMapper modelMapper;

    public ReferentielManager(ReferentielService referentielService, ModelMapper modelMapper) {
        Objects.requireNonNull(referentielService);
        Objects.requireNonNull(modelMapper);
        this.referentielService = referentielService;
        this.modelMapper = modelMapper;
    }

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
    @PatchMapping("/referentiel/{id}")
    public void updateReferentiel(@PathVariable Long id, @RequestBody Map<String, Object> patchValues){
        Referentiel ref = referentielService.getReferentiel(id)
                .orElseThrow(() -> new ResourceNotFoundException("Referentiel not exist with id :" + id));
        ReferentielDto dto = convertToDto(ref);
        modelMapper.map(patchValues, dto);
        Referentiel referentiel = convertToEntity(dto);
        referentielService.updateReferentiel(id, referentiel);
    }

    // delete referentiel
    @DeleteMapping("/referentiel/{id}")
    public void deleteReferentiel(@PathVariable Long id){
        referentielService.deleteWithAllRelatedObjets(id);
    }


    /*
     *  DTO CONVERSIONS
     *
     */


    // Referentiel

    private ReferentielDto convertToDto(Referentiel r) {
        ReferentielDto referentielDto = modelMapper.map(r, ReferentielDto.class);

/*        if(r.getObjets() != null)
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
        }*/

        return referentielDto;
    }

    private Referentiel convertToEntity(ReferentielDto referentielDto) {
        return modelMapper.map(referentielDto, Referentiel.class);
    }
}
