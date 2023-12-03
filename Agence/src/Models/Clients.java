package Models;

import java.sql.Date;

public class Clients {
	int id;
	String nomprenom;
	Date dateNaiss;
	String tele;
	String cni;
	public Clients() {
		super();
	}
	public Clients(int id, String nomprenom, Date dateNaiss, String tele, String cni) {
		super();
		this.id = id;
		this.nomprenom = nomprenom;
		this.dateNaiss = dateNaiss;
		this.tele = tele;
		this.cni = cni;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNomprenom() {
		return nomprenom;
	}
	public void setNomprenom(String nomprenom) {
		this.nomprenom = nomprenom;
	}
	public Date getDateNaiss() {
		return dateNaiss;
	}
	public void setDateNaiss(Date dateNaiss) {
		this.dateNaiss = dateNaiss;
	}
	public String getTele() {
		return tele;
	}
	public void setTele(String tele) {
		this.tele = tele;
	}
	public String getCni() {
		return cni;
	}
	public void setCni(String cni) {
		this.cni = cni;
	}
	

}
