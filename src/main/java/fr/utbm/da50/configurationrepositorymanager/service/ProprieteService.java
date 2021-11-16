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

    public Optional<Propriete> getPropriete(final Long id) {
        return proprieteRepository.findById(id);
    }

    public Iterable<Propriete> getProprietes() {
        return proprieteRepository.findAll();
    }

    public void deletePropriete(final Long id) {
        proprieteRepository.deleteById(id);
    }

    public Propriete savePropriete(Propriete propriete) {
        Propriete savedObjet = proprieteRepository.save(propriete);
        return savedObjet;
    }

}
