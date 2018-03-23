package fr.eseo.javaee.projet.db.objet;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import fr.eseo.javaee.projet.db.BaseDeDonnees;

public class Client implements Memorisable {

	public static final String NOM_TABLE 		= "client";

	public static final String NOM_COL_ID 		= "idClient";
	public static final String NOM_COL_NOM 		= "nom";
	public static final String NOM_COL_PRENOM 	= "prenom";
	public static final String NOM_COL_NAISSANCE = "dateNaissance";
	public static final String NOM_COL_ADRESSE 		= "adresse";
	public static final String NOM_COL_CODE_POSTAL 	= "codePostal";
	public static final String NOM_COL_PAYS 		= "pays";
	public static final String NOM_COL_TELEPHONE 	= "numTelephone";
	public static final String NOM_COL_MAIL 		= "mail";

	private static List<String> listeNomAttributs = extractNomAttributs();

	private int idClient;
	private String nom;
	private String prenom;
	private LocalDate dateNaissance;
	private String adresse;
	private int codePostal;
	private String pays;
	private int numTelephone;
	private String mail;

	//	public boolean isAnniversaire() {
	//		return LocalDate.now().equals(this.dateNaissance);
	//	}

	public static List<String> extractNomAttributs() {
		List<String> listeNomAttribut = new ArrayList<>();
		listeNomAttribut.add(NOM_COL_NOM);
		listeNomAttribut.add(NOM_COL_PRENOM);
		listeNomAttribut.add(NOM_COL_NAISSANCE);
		listeNomAttribut.add(NOM_COL_ADRESSE);
		listeNomAttribut.add(NOM_COL_CODE_POSTAL);
		listeNomAttribut.add(NOM_COL_PAYS);
		listeNomAttribut.add(NOM_COL_TELEPHONE);
		listeNomAttribut.add(NOM_COL_MAIL);
		return listeNomAttribut;
	}

	//GETTER - SETTER
	public int getIdClient() {return idClient;}
	public void setIdClient(int idClient) {this.idClient = idClient;}

	public String getNom() {return nom;}
	public void setNom(String nom) {this.nom = nom;}

	public String getPrenom() {return prenom;}
	public void setPrenom(String prenom) {this.prenom = prenom;}

	//public LocalDate getDateNaissance() {return this.dateNaissance;}
	LocalDate getDatenaissance() {return (dateNaissance == null) ? LocalDate.MIN : dateNaissance;}
	public void setDateNaissance(LocalDate dateNaissance) {this.dateNaissance = dateNaissance;}

	public String getAdresse() {return adresse;}
	public void setAdresse(String adresse) {this.adresse = adresse;}

	public int getCodePostal() {return codePostal;}
	public void setCodePostal(int codePostal) {this.codePostal = codePostal;}

	public String getPays() {return pays;}
	public void setPays(String pays) {this.pays = pays;}

	public int getNumTelephone() {return numTelephone;}
	public void setNumTelephone(int numTelephone) {this.numTelephone = numTelephone;}

	public String getMail() {return mail;}
	public void setMail(String mail) {this.mail = mail;}

	@Override
	public List<String> getListeNomAttributs() {
		return listeNomAttributs;
	}

	@Override
	public List<String> getListeAttributs() {
		List<String> listeAttributs = new ArrayList<>();
		listeAttributs.add(nom);
		listeAttributs.add(prenom);
		listeAttributs.add(BaseDeDonnees.convertForDB(dateNaissance));
		listeAttributs.add(adresse);
		listeAttributs.add(String.valueOf(codePostal));
		listeAttributs.add(pays);
		listeAttributs.add(String.valueOf(numTelephone));
		listeAttributs.add(mail);
		return listeAttributs;
	}

	@Override
	public void setListeAttributs(List<String> listeNouvellesValeurs) {
		idClient 	= Integer.parseInt(listeNouvellesValeurs.get(0));
		nom 		= listeNouvellesValeurs.get(1);
		prenom 	= listeNouvellesValeurs.get(2);
		dateNaissance = BaseDeDonnees.convertDateFromDB(listeNouvellesValeurs.get(3));
		adresse 	= listeNouvellesValeurs.get(4);
		codePostal = Integer.parseInt(listeNouvellesValeurs.get(5));
		pays 		= listeNouvellesValeurs.get(6);
		numTelephone = Integer.parseInt(listeNouvellesValeurs.get(7));
		mail 		= listeNouvellesValeurs.get(8);
	}

	@Override
	public String getNomTable() {
		return NOM_TABLE;
	}
}
