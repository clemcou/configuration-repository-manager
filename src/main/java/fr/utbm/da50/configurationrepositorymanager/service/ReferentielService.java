package fr.utbm.da50.configurationrepositorymanager.service;

import fr.utbm.da50.configurationrepositorymanager.entity.Configuration;
import fr.utbm.da50.configurationrepositorymanager.entity.Objet;
import fr.utbm.da50.configurationrepositorymanager.entity.Referentiel;
import fr.utbm.da50.configurationrepositorymanager.repository.ReferentielRepository;
import fr.utbm.da50.configurationrepositorymanager.service.ObjetService;
import lombok.Data;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Data
@Service
public class ReferentielService {

    @Autowired
    private ReferentielRepository referentielRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ObjetService objetService;

    //READ


    public Optional<Referentiel> getReferentiel(final Long id) {
        return referentielRepository.findById(id);
    }

    public Iterable<Referentiel> getReferentiels() {
        return referentielRepository.findAll();
    }

    public Iterable<Referentiel> getReferentielsByNom(String nom) {
        return referentielRepository.findByNomLike(".*" + nom + ".*");
    }

    public Iterable<Configuration> getConfigurationsOfReferentiel(final Long id) {
        Optional<Referentiel> referentielOpt = referentielRepository.findById(id);
        return referentielOpt.isPresent() ? referentielOpt.get().getConfigurations() : null;
    }

    public Iterable<Objet> getObjetsOfReferentiel(final Long id) {
        Optional<Referentiel> referentielOpt = referentielRepository.findById(id);
        return referentielOpt.isPresent() ? referentielOpt.get().getObjets() : null;
    }

    /*
    public Iterable<Referentiel> getReferentielsOrderById(){
    	return ReferentielRepository.findAllByOrderById();
    }
    */
    public Iterable<Referentiel> getReferentielsOrderByNomAsc() {
        return referentielRepository.findAllByOrderByNomAsc();
    }

    public Iterable<Referentiel> getReferentielsOrderByNomDesc() {
        return referentielRepository.findAllByOrderByNomDesc();
    }

    /*
     * order by id may not be used by user
    public List<Objet> getObjetsOrderByIdDesc(final Long id){
    	return int2ObjetList(ReferentielRepository.findObjetsOrderByIdDesc(id));
    }

    public List<Objet> getObjetsOrderByIdAsc(final Long id){
    	return int2ObjetList(ReferentielRepository.findObjetsOrderByIdAsc(id));
    }
    */
    public List<Objet> getObjetsOrderByNomDesc(final Long id) {
        return int2ObjetList(referentielRepository.findObjetsOrderByNomDesc(id));
    }

    public List<Objet> getObjetsOrderByNomAsc(final Long id) {
        return int2ObjetList(referentielRepository.findObjetsOrderByNomAsc(id));
    }

    public List<Objet> int2ObjetList(Iterable<Integer> idSet) {
        List<Objet> objetList = new ArrayList<Objet>();
        for (Integer i : idSet) {
            Optional<Objet> objetOpt = objetService.getObjet((long) i);
            objetOpt.ifPresent(objetList::add);
        }
        return objetList;
    }

    //DELETE

    /**
     * this method will only delete the Referentiel, but will not delete related entities
     *
     * @param id
     */
    public void deleteReferentiel(final Long id) {
        referentielRepository.deleteById(id);
    }

    public void deleteWithAllRelatedObjets(final Long id) {
        referentielRepository.deleteAllRelatedEntity(id);
    }

    //CREATE

    public Referentiel saveReferentiel(Referentiel Referentiel) {
        return referentielRepository.save(Referentiel);
    }

    //UPDATE

    public void updateReferentiel(final Long id, Referentiel referentiel) {
        if (referentielRepository.findById(id).isPresent()) {
            referentiel.setId(id);
            referentielRepository.save(referentiel);
        }
    }

    public void updateReferentielNom(final Long id, String name) {
        Optional<Referentiel> referentielOpt = referentielRepository.findById(id);
        if (referentielOpt.isPresent()) {
            Referentiel referentiel = referentielOpt.get();
            referentiel.setNom(name);
            referentielRepository.save(referentiel);
        }
    }

    public void updateReferentielDescription(final Long id, String description) {
        Optional<Referentiel> referentielOpt = referentielRepository.findById(id);
        if (referentielOpt.isPresent()) {
            Referentiel referentiel = referentielOpt.get();
            referentiel.setDescription(description);
            referentielRepository.save(referentiel);
        }
    }

    public void updateReferentielDescAndNom(final Long id, String description, String name) {
        Optional<Referentiel> referentielOpt = referentielRepository.findById(id);
        if (referentielOpt.isPresent()) {
            Referentiel referentiel = referentielOpt.get();
            referentiel.setNom(name);
            referentiel.setDescription(description);
            referentielRepository.save(referentiel);
        }
    }
}
