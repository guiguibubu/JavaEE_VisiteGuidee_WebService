package fr.eseo.javaee.projet.db.objet;

public class Coordonnee {

	private String adresse;
	private int codePostal;
	private String pays;
	private int numTelephone;
	private String mail;

	public Coordonnee () {
		this("",0,"",0,"");
	}

	public Coordonnee (String adresse, int codePostal, String pays, int numTelephone, String mail) {
		this.adresse = adresse;
		this.codePostal = codePostal;
		this.pays = pays;
		this.numTelephone = numTelephone;
		this.mail = mail;
	}

	//GETTER - SETTER
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
