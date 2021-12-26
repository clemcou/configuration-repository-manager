package fr.utbm.da50.configurationrepositorymanager.service;

import fr.utbm.da50.configurationrepositorymanager.entity.Objet;
import fr.utbm.da50.configurationrepositorymanager.entity.Propriete;
import fr.utbm.da50.configurationrepositorymanager.repository.ObjetRepository;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Data
@Service
public class ObjetService {

    private ObjetRepository objetRepository;
    
    private ProprieteService proprieteService;

	public ObjetService(ObjetRepository objetRepository, ProprieteService proprieteService){
		Objects.requireNonNull(objetRepository);
		Objects.requireNonNull(proprieteService);
		this.objetRepository = objetRepository;
		this.proprieteService = proprieteService;
	}
    
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
    /*
     * order by id may not used by user
    public List<Propriete> getProprietesOrderByIdDesc(final Long id){
    	return int2ProprieteList(objetRepository.findProprietesOrderByIdDesc(id));
    }
    
    public List<Propriete> getProprietesOrderByIdAsc(final Long id){
    	return int2ProprieteList(objetRepository.findProprietesOrderByIdAsc(id));
    }
    */
    public List<Propriete> getProprietesOrderByNomDesc(final Long id){
    	return int2ProprieteList(objetRepository.findProprietesOrderByNomDesc(id));
    }
    
    public List<Propriete> getProprietesOrderByNomAsc(final Long id){
    	return int2ProprieteList(objetRepository.findProprietesOrderByNomAsc(id));
    }
    /*
    public List<Objet> getObjetsOrderByIdDesc(final Long id){
    	return int2ObjetList(objetRepository.findObjetsOrderByIdDesc(id));
    }
    
    public List<Objet> getObjetsOrderByIdAsc(final Long id){
    	return int2ObjetList(objetRepository.findObjetsOrderByIdAsc(id));
    }
    */
    public List<Objet> getObjetsOrderByNomDesc(final Long id){
    	return int2ObjetList(objetRepository.findObjetsOrderByNomDesc(id));
    }
    
    public List<Objet> getObjetsOrderByNomAsc(final Long id){
    	return int2ObjetList(objetRepository.findObjetsOrderByNomAsc(id));
    }
    
    public List<Propriete> int2ProprieteList(Iterable<Integer> idSet){
    	List<Propriete> proprieteList = new ArrayList<Propriete>();
    	for(Integer i : idSet) {
    		Optional<Propriete> proprieteOpt = proprieteService.getPropriete((long)i);
    		if(proprieteOpt.isPresent()) {
    			proprieteList.add(proprieteOpt.get());
    		}
    	}
    	return proprieteList;
    }
    
    public List<Objet> int2ObjetList(Iterable<Integer> idSet){
    	List<Objet> objetList = new ArrayList<Objet>();
    	for(Integer i : idSet) {
    		Optional<Objet> objetOpt = objetRepository.findById((long)i);
    		if(objetOpt.isPresent()) {
    			objetList.add(objetOpt.get());
    		}
    	}
    	return objetList;
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
		return objetRepository.save(objet);
    }
    
    //UPDATE

	public void updateObjet(final Long id, Objet objet) {
		if(objetRepository.findById(id).isPresent()){
			objet.setId(id);
			objetRepository.save(objet);
		}
	}

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
