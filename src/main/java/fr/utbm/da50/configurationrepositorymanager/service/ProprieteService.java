package fr.utbm.da50.configurationrepositorymanager.service;

import fr.utbm.da50.configurationrepositorymanager.entity.Propriete;
import fr.utbm.da50.configurationrepositorymanager.repository.ProprieteRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Data
@Service
public class ProprieteService {

    @Autowired
    private ProprieteRepository proprieteRepository;
    
    // READ

    public Optional<Propriete> getPropriete(final Long id) {
        return proprieteRepository.findById(id);
    }

    public Iterable<Propriete> getProprietes() {
        return proprieteRepository.findAll();
    }
    
    public Iterable<Propriete> getProprietesByNom(String nom){
    	return proprieteRepository.findByNomLike(".*"+nom+".*");
    }
    
    //DELETE

    public void deletePropriete(final Long id) {
        proprieteRepository.deleteById(id);
    }
    
    //CREATE

    public Propriete savePropriete(Propriete propriete) {
        Propriete savedObjet = proprieteRepository.save(propriete);
        return savedObjet;
    }
    
    //UPDATE

	public void updatePropriete(final Long id, Propriete propriete) {
		if(proprieteRepository.findById(id).isPresent()){
			propriete.setId(id);
			proprieteRepository.save(propriete);
		}
	}

    public void updatePropNom(final Long id, String nom) {
    	Optional<Propriete> proprieteOpt = proprieteRepository.findById(id);
    	if(proprieteOpt.isPresent()){
    		Propriete propriete = proprieteOpt.get();
    		propriete.setNom(nom);
    		proprieteRepository.save(propriete);
    	}
	}
    
    public void updatePropType(final Long id, String type) {
    	Optional<Propriete> proprieteOpt = proprieteRepository.findById(id);
    	if(proprieteOpt.isPresent()){
    		Propriete propriete = proprieteOpt.get();
    		propriete.setType(type);
    		proprieteRepository.save(propriete);
    	}
	}
    
    public void updatePropValeur(final Long id, String valeur) {
    	Optional<Propriete> proprieteOpt = proprieteRepository.findById(id);
    	if(proprieteOpt.isPresent()){
    		Propriete propriete = proprieteOpt.get();
    		propriete.setValeur(valeur);
    		proprieteRepository.save(propriete);
    	}
	}

}
