package fr.utbm.da50.configurationrepositorymanager;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import fr.utbm.da50.configurationrepositorymanager.entity.Configuration;
import fr.utbm.da50.configurationrepositorymanager.entity.Objet;
import fr.utbm.da50.configurationrepositorymanager.entity.Propriete;
import fr.utbm.da50.configurationrepositorymanager.service.*;
@SpringBootTest
class ConfigurationRepositoryManagerApplicationTests {
	
	@Autowired
	ObjetService objServ;
	
	@Autowired
	ProprieteService pServ;
	
	@Autowired
	ConfigurationService conServ;
	
    @Test
    void contextLoads() {
    	/*
    	Objet testObj = new Objet();
    	testObj.setNom("backend test");
    	testObj.setDescription("no propriete");
    	objServ.saveObjet(testObj);
    	objServ.deleteObjet(23L);
    	
    	
    	//test for create entities and the relationships
    	 
    	Propriete p1 = new Propriete("propriete1", "Stirng", "aaa");
    	Propriete p2 = new Propriete("propriete2", "Stirng", "aaa");
    	Propriete p3 = new Propriete("propriete2", "Stirng", "aaa");
    	HashSet<Propriete> pSet = new HashSet<Propriete>();
    	pSet.add(p1);
    	pSet.add(p2);
    	pServ.savePropriete(p3);
    	
    	Objet testObj4 = new Objet("backend test4","no propriete");
    	Set<Objet> oSet4 = new HashSet<Objet>();
    	objServ.saveObjet(testObj4);
    	oSet4.add(testObj4);
    	Objet testObj5 = new Objet("backend test5","no propriete",oSet4,pSet);
    	objServ.saveObjet(testObj5);
    	Set<Objet> oSet5 = new HashSet<Objet>();
    	oSet5.add(testObj5);
    	Objet testObj6 = new Objet("backend test6","no propriete",oSet5);
    	objServ.saveObjet(testObj6);
    	Set<Objet> oSet6 = new HashSet<Objet>();
    	oSet6.add(testObj6);
    	Objet testObj7 = new Objet("backend test7","no propriete",oSet6);
    	objServ.saveObjet(testObj7);
    	Set<Objet> oSet7 = new HashSet<Objet>();
    	oSet7.add(testObj7);
    	
    	Configuration config = new Configuration("delete test","111 delete delete delete");
    	config.setObjets(oSet7);
    	conServ.saveConfiguration(config);
    	
    	
    	//test for finding by keyword
    	 
    	Iterable<Configuration> config = conServ.getConfigurationsByNom("o");
    	for(Configuration c : config) {
    		System.out.println(c.getId()+c.getNom());
    	}
    	
    	Iterable<Objet> objets = objServ.getObjetsByNom("a");
    	for(Objet o : objets) {
    		System.out.println(o.getId()+" "+o.getNom());
    	}
    	
    	//test for getSet from configuration and Objet
    	
    	Iterable<Objet> objetsOfConfig = conServ.getObjetsOfConfig(2L);
    	for(Objet o : objetsOfConfig) {
    		System.out.println(o.getId()+" "+o.getNom());
    	}
    	
    	Iterable<Objet> objets = objServ.getObjetsOfObjet(25L);
    	for(Objet o : objets) {
    		System.out.println(o.getId()+" "+o.getNom());
    	}
    	
    	System.out.println("proprietes");
    	
    	Iterable<Propriete> proprietes = objServ.getProprietesOfObjet(25L);
    	for(Propriete o : proprietes) {
    		System.out.println(o.getId()+" "+o.getNom());
    	}
    	

    	//test for null getSet from configuration and Objet
    	
    	Objet testObj4 = new Objet("backend test4","no propriete");
    	Configuration config = new Configuration("delete test","111 delete delete delete");
    	conServ.saveConfiguration(config);
    	objServ.saveObjet(testObj4);
    	Iterable<Objet> objets = objServ.getObjetsOfObjet(testObj4.getId());
    	if(objets == null) {
    		System.out.println("null");
    	}else {
    		for(Objet o : objets) {
        		System.out.println(o.getId()+" "+o.getNom());
        	}
    	}
    	
    	Iterable<Propriete> proprietes = objServ.getProprietesOfObjet(testObj4.getId());
    	if(proprietes == null) {
    		System.out.println("null");
    	}else {
    		for(Propriete o : proprietes) {
        		System.out.println(o.getId()+" "+o.getNom());
        	}
    	}
    	
    	Iterable<Objet> objetsOfConfig = conServ.getObjetsOfConfig(config.getId());
    	if(objetsOfConfig == null) {
    		System.out.println("null");
    	}else {
    		for(Objet o : objetsOfConfig) {
        		System.out.println(o.getId()+" "+o.getNom());
        	}
    	}
    	
    	//update test
    	objServ.updateObjetNomAndDesc(143L, "update nom", "update desc");
    	pServ.updatePropNom(141L, "update nom");
    	pServ.updatePropType(141L, "change type");
    	pServ.updatePropValeur(141L, "change valeur");
    	conServ.updateConfigDescAndNom(144L, "update desc", "update nom");
    	
    	
    	//delete test
    	pServ.deletePropriete(140L);
    	Iterable<Propriete> proprietes = objServ.getProprietesOfObjet(139L);
    	if(proprietes == null) {
    		System.out.println("null");
    	}else {
    		for(Propriete o : proprietes) {
        		System.out.println(o.getId()+" "+o.getNom());
        	}
    	}
    	
    	objServ.deleteObjet(138L);
    	Iterable<Objet> objets = objServ.getObjetsOfObjet(139L);
    	if(objets == null) {
    		System.out.println("null");
    	}else {
    		for(Objet o : objets) {
        		System.out.println(o.getId()+" "+o.getNom());
        	}
    	}
    	
    	objServ.deleteWithAllRelatedEntity(143L);
    	Configuration config = new Configuration("delete test","111 delete delete delete");
    	conServ.saveConfiguration(config);
    	Iterable<Objet> objetsOfConfig = conServ.getObjetsOfConfig(144L);
    	if(objetsOfConfig == null) {
    		System.out.println("no related objet");
    	}else {
    		for(Objet o : objetsOfConfig) {
        		System.out.println(o.getId()+" "+o.getNom());
        	}
    	}
    	
    	System.out.println(conServ.getConfiguration(144L).get().getObjets().size() );
    	
    	List<Propriete> pList = objServ.getProprietesOrderByIdDesc(33L);
    	for(Propriete i : pList) {
    		System.out.println(i.toString());
    	}
    	
    	List<Propriete> pList1 = objServ.getProprietesOrderByIdAsc(33L);
    	for(Propriete i : pList1) {
    		System.out.println(i.toString());
    	}
    	
    	List<Propriete> pList2 = objServ.getProprietesOrderByNomAsc(33L);
    	for(Propriete i : pList2) {
    		System.out.println(i.toString());
    	}
    	for(Propriete i : objServ.getProprietesOrderByNomDesc(33L)) {
    		System.out.println(i.toString());
    	}
    	for(Objet i : objServ.getObjetsOrderByIdDesc(25L)) {
    		System.out.println(i.toString());
    	}
    	for(Objet i : conServ.getObjetsOrderByIdAsc(2L)) {
    		System.out.println(i.toString());
    	}
    	*/
    	
    	for(Configuration c : conServ.getConfigurationsOrderByNomDesc()) {
    		System.out.println(c.toString());
    	}
    }
}
