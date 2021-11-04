package fr.utbm.da50.configurationrepositorymanager.service;

import fr.utbm.da50.configurationrepositorymanager.entity.Objet;
import fr.utbm.da50.configurationrepositorymanager.repository.ObjetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ObjetService {

    @Autowired
    private ObjetRepository objetRepository;

    public Optional<Objet> getObjet(final Long id) {
        return objetRepository.findById(id);
    }

    public Iterable<Objet> getObjets() {
        return objetRepository.findAll();
    }

    public void deleteObjet(final Long id) {
        objetRepository.deleteById(id);
    }

    public Objet saveObjet(Objet objet) {
        Objet savedObjet = objetRepository.save(objet);
        return savedObjet;
    }


    public ObjetRepository getObjetRepository() {
        return objetRepository;
    }

    public void setObjetRepository(ObjetRepository objetRepository) {
        this.objetRepository = objetRepository;
    }
}
