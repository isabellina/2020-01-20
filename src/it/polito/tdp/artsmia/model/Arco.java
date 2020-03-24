package it.polito.tdp.artsmia.model;

import org.jgrapht.graph.DefaultWeightedEdge;

public class Arco extends DefaultWeightedEdge {
	
	private int art1;
	private int art2;
	private int weight;
	
	public Arco(int art1, int art2, int weight) {
		super();
		this.art1 = art1;
		this.art2 = art2;
		this.weight = weight;
	}

	public int getArt1() {
		return art1;
	}

	public int getArt2() {
		return art2;
	}

	public double getWeight() {
		return super.getWeight();
	}
	
	
	
	

}
