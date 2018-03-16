package fr.eseo.javaee.projet.db.objet;

import java.time.LocalDate;

public class Client {

	private int idClient;
	private String nom;
	private String prenom;
	private String dateNaissance;
	private String adresse;
	private int codePostal;
	private String pays;
	private int numTelephone;
	private String mail;

	public Client (int idClient, String nom, String prenom) {
		this(idClient, nom, prenom, "","",0,"",0,"");
	}

	public Client (int idClient, String nom, String prenom, String dateNaissance, String adresse, int codePostal, String pays, int numTelephone, String mail) {
		this.idClient = idClient;
		this.nom = nom;
		this.prenom = prenom;
		this.dateNaissance = dateNaissance;
		this.adresse = adresse;
		this.codePostal = codePostal;
		this.pays = pays;
		this.numTelephone = numTelephone;
		this.mail = mail;
	}

	public boolean isAnniversaire() {
		return LocalDate.now().equals(this.dateNaissance);
	}

	//GETTER - SETTER
	public int getIdClient() {return this.idClient;}
	public void setIdClient(int idClient) {this.idClient = idClient;}

	public String getNom() {return this.nom;}
	public void setNom(String nom) {this.nom = nom;}

	public String getPrenom() {return this.prenom;}
	public void setPrenom(String prenom) {this.prenom = prenom;}

	public String getDateNaissance() {return this.dateNaissance;}
	public void setDateNaissance(String dateNaissance) {this.dateNaissance = dateNaissance;}

	public String getAdresse() {return this.adresse;}
	public void setAdresse(String adresse) {this.adresse = adresse;}

	public int getCodePostal() {return this.codePostal;}
	public void setCodePostal(int codePostal) {this.codePostal = codePostal;}

	public String getPays() {return this.pays;}
	public void setPays(String pays) {this.pays = pays;}

	public int getNumTelephone() {return this.numTelephone;}
	public void setNumTelephone(int numTelephone) {this.numTelephone = numTelephone;}

	public String getMail() {return this.mail;}
	public void setMail(String mail) {this.mail = mail;}
}
