package fr.utbm.da50.configurationrepositorymanager.service;

import fr.utbm.da50.configurationrepositorymanager.entity.Objet;
import fr.utbm.da50.configurationrepositorymanager.entity.Propriete;
import fr.utbm.da50.configurationrepositorymanager.repository.ObjetRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Data
@Service
public class ObjetService {

    @Autowired
    private ObjetRepository objetRepository;
    
    @Autowired
    private ProprieteService proprieteService;
    
    //READ
    
    public Optional<Objet> getObjet(final Long id) {
    	return objetRepository.findById(id);
    }

    public Iterable<Objet> getObjets() {
        return objetRepository.findAll();
    }
    
    public Iterable<Objet> getObjetsByNom(String nom){
    	return objetRepository.findByNomLike(".*"+nom+".*");
    }
    
    public Iterable<Propriete> getProprietesOfObjet(final Long id){
    	Optional<Objet> objetOptional = objetRepository.findById(id);
    	return objetOptional.isPresent() ? objetOptional.get().getProprietes() : null;
    }
    
    public Iterable<Objet> getObjetsOfObjet(final Long id){
    	Optional<Objet> objetOptional = objetRepository.findById(id);
    	return objetOptional.isPresent() ? objetOptional.get().getObjets() : null;
    }
    
    //DELETE
    
    /**
     * this method will only delete the object, but will not delete related entities
     * @param id
     */
    public void deleteObjet(final Long id) {
        objetRepository.deleteById(id);
    }

	public void deleteWithAllRelatedEntity(final Long id) {
		objetRepository.deleteAllRelatedEntity(id);
	}
	
	//CREATE
	
    public Objet saveObjet(Objet objet) {
        Objet savedObjet = objetRepository.save(objet);
        return savedObjet;
    }
    
    //UPDATE
    public void updateObjetNom(final Long id, String nom) {
    	Optional<Objet> objetOpt = objetRepository.findById(id);
    	if(objetOpt.isPresent()){
    		Objet objet = objetOpt.get();
    		objet.setDescription(nom);
    		objetRepository.save(objet);
    	}
	}
    
    public void updateObjetDescription(final Long id, String description) {
    	Optional<Objet> objetOpt = objetRepository.findById(id);
    	if(objetOpt.isPresent()){
    		Objet objet = objetOpt.get();
    		objet.setDescription(description);
    		objetRepository.save(objet);
    	}
	}
    
    public void updateObjetNomAndDesc(final Long id, String nom, String description) {
    	Optional<Objet> objetOpt = objetRepository.findById(id);
    	if(objetOpt.isPresent()){
    		Objet objet = objetOpt.get();
    		objet.setDescription(description);
    		objet.setNom(nom);
    		objetRepository.save(objet);
    	}
	}
	

    
}
