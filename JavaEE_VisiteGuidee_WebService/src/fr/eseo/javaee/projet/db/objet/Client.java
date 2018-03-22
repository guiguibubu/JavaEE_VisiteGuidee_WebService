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

	private static List<String> listeNomAttributs = extractNomAttributs();

	private int idClient;
	private String nom;
	private String prenom;
	private LocalDate dateNaissance;
	private Coordonnee coordonnee;

	public Client() {
		this(0);
	}

	public Client(int idClient) {
		this(idClient, "", "");
	}

	public Client (String nom, String prenom) {
		this(0, nom, prenom);
	}

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

	public static List<String> extractNomAttributs() {
		List<String> listeNomAttribut = new ArrayList<>();
		listeNomAttribut.add(NOM_COL_NOM);
		listeNomAttribut.add(NOM_COL_PRENOM);
		listeNomAttribut.add(NOM_COL_NAISSANCE);
		listeNomAttribut.addAll(Coordonnee.extractNomAttributs());
		return listeNomAttribut;
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

	public Coordonnee getCoordonnee() {return this.coordonnee;}
	public void setCoordonnee(Coordonnee coordonnee) {this.coordonnee = coordonnee;}

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

	@Override
	public List<String> getListeNomAttributs() {
		return listeNomAttributs;
	}

	@Override
	public List<String> getListeAttributs() {
		List<String> listeAttributs = new ArrayList<>();
		listeAttributs.add(this.nom);
		listeAttributs.add(this.prenom);
		listeAttributs.add(BaseDeDonnees.convertForDB(this.dateNaissance));
		listeAttributs.addAll(this.coordonnee.getListeAttributs());
		return listeAttributs;
	}

	@Override
	public void setListeAttributs(List<String> listeNouvellesValeurs) {
		this.idClient 	= Integer.parseInt(listeNouvellesValeurs.get(0));
		this.nom 		= listeNouvellesValeurs.get(1);
		this.prenom 	= listeNouvellesValeurs.get(2);
		this.dateNaissance = BaseDeDonnees.convertDateFromDB(listeNouvellesValeurs.get(3));
		this.coordonnee.setListeAttributs(listeNouvellesValeurs);
	}

	@Override
	public String getNomTable() {
		return NOM_TABLE;
	}
}
