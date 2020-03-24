package it.polito.tdp.artsmia.model;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.jgrapht.Graph;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.artsmia.db.ArtsmiaDAO;

public class Model {
	
	private ArtsmiaDAO dao ;
	private Map<Integer,String> mappaArtisti;
	private Graph<String,Arco> grafo ;
	private List<Arco> archi;
	
	
	public Model() {
		dao = new ArtsmiaDAO();
		archi = new LinkedList<Arco>();
	}
	
	public List<String> ruoli(){
		List<String> getRuoli = new LinkedList<String>(dao.getRuoli()) ;
		return getRuoli;
	}
	
	
	public Map<Integer,String> getNodi(String ruolo)
	{
		 this.mappaArtisti = new TreeMap<Integer,String>(this.dao.getArtista(ruolo));
		return mappaArtisti;
	}
	
	
	public void creaGrafo(String ruolo) {
		this.grafo = new SimpleWeightedGraph<String,Arco>(Arco.class);
		
		
	
		for(String s : mappaArtisti.values()) {
			this.grafo.addVertex(s);
			
		} 
	
		System.out.println(mappaArtisti.values());
		
		for(Arco a : this.dao.getArchi(ruolo)) {
			archi.add(a);
			
		}
		
		for(Arco b: archi) {
			this.grafo.addEdge(mappaArtisti.get(b.getArt1()), mappaArtisti.get(b.getArt2()), b);
		}
		
		System.out.println(this.grafo.toString());
		
	}

}
