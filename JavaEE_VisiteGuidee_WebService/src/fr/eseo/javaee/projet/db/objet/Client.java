package fr.eseo.javaee.projet.db.objet;

import java.time.LocalDate;

public class Client {

	private int idClient;
	private String nom;
	private String prenom;
	private LocalDate dateNaissance;
	private final Coordonnee coordonnee;

	public Client (int idClient, String nom, String prenom) {
		this(idClient, nom, prenom, null, new Coordonnee());
	}

	public Client (int idClient, String nom, String prenom, LocalDate dateNaissance, Coordonnee coordonnee) {
		this.idClient = idClient;
		this.nom = nom;
		this.prenom = prenom;
		this.dateNaissance = dateNaissance;
		this.coordonnee = coordonnee;
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

	public LocalDate getDateNaissance() {return this.dateNaissance;}
	public void setDateNaissance(LocalDate dateNaissance) {this.dateNaissance = dateNaissance;}

	public String getAdresse() {return this.coordonnee.getAdresse();}
	public void setAdresse(String adresse) {this.coordonnee.setAdresse(adresse);}

	public int getCodePostal() {return this.coordonnee.getCodePostal();}
	public void setCodePostal(int codePostal) {this.coordonnee.setCodePostal(codePostal);}

	public String getPays() {return this.coordonnee.getPays();}
	public void setPays(String pays) {this.coordonnee.setPays(pays);}

	public int getNumTelephone() {return this.coordonnee.getNumTelephone();}
	public void setNumTelephone(int numTelephone) {this.coordonnee.setNumTelephone(numTelephone);}

	public String getMail() {return this.coordonnee.getMail();}
	public void setMail(String mail) {this.coordonnee.setMail(mail);}
}
