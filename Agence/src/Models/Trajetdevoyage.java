package Models;

public class Trajetdevoyage {
	int id;
	String lieudedepart;
	String nomtrajetdevoyage;
	String destination;
	String categoriebus;
	int numerobus;
	public Trajetdevoyage(int id, String lieudedepart, String nomtrajetdevoyage, String destination,
			String categoriebus, int numerobus) {
		super();
		this.id = id;
		this.lieudedepart = lieudedepart;
		this.nomtrajetdevoyage = nomtrajetdevoyage;
		this.destination = destination;
		this.categoriebus = categoriebus;
		this.numerobus = numerobus;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getLieudedepart() {
		return lieudedepart;
	}
	public void setLieudedepart(String lieudedepart) {
		this.lieudedepart = lieudedepart;
	}
	public String getNomtrajetdevoyage() {
		return nomtrajetdevoyage;
	}
	public void setNomtrajetdevoyage(String nomtrajetdevoyage) {
		this.nomtrajetdevoyage = nomtrajetdevoyage;
	}
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	public String getCategoriebus() {
		return categoriebus;
	}
	public void setCategoriebus(String categoriebus) {
		this.categoriebus = categoriebus;
	}
	public int getNumerobus() {
		return numerobus;
	}
	public void setNumerobus(int numerobus) {
		this.numerobus = numerobus;
	}
	
	

}
