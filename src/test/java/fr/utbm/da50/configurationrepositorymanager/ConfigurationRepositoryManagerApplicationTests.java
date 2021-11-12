package fr.utbm.da50.configurationrepositorymanager;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import fr.utbm.da50.configurationrepositorymanager.entity.Objet;
import fr.utbm.da50.configurationrepositorymanager.service.ObjetService;

@SpringBootTest
class ConfigurationRepositoryManagerApplicationTests {
	
	@Autowired
	ObjetService objServ;
	
    @Test
    void contextLoads() {
    	/*
    	Objet testObj = new Objet();
    	testObj.setNom("backend test");
    	testObj.setDescription("no propriete");
    	objServ.saveObjet(testObj);
    	*/
    	objServ.deleteObjet(23L);
    }

}
