package fr.eseo.javaee.projet.db.objet;

import java.util.ArrayList;
import java.util.List;

public class Coordonnee implements Memorisable {

	public static final String NOM_TABLE 		= "client";

	public static final String NOM_COL_ADRESSE 		= "adresse";
	public static final String NOM_COL_CODE_POSTAL 	= "codePostal";
	public static final String NOM_COL_PAYS 		= "pays";
	public static final String NOM_COL_TELEPHONE 	= "numTelephone";
	public static final String NOM_COL_MAIL 		= "mail";

	private static List<String> listeNomAttributs;

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

		if(listeNomAttributs ==  null) {
			listeNomAttributs = extractNomAttributs();
		}
	}

	private static List<String> extractNomAttributs() {
		List<String> listeNomAttribut = new ArrayList<String>();
		listeNomAttribut.add(NOM_COL_ADRESSE);
		listeNomAttribut.add(NOM_COL_CODE_POSTAL);
		listeNomAttribut.add(NOM_COL_PAYS);
		listeNomAttribut.add(NOM_COL_TELEPHONE);
		listeNomAttribut.add(NOM_COL_MAIL);
		return listeNomAttribut;
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

	@Override
	public String getNomTable() {
		return NOM_TABLE;
	}

	@Override
	public List<String> getListeNomAttributs() {
		if(listeNomAttributs ==  null) {
			listeNomAttributs = extractNomAttributs();
		}
		return listeNomAttributs;
	}

	@Override
	public List<String> getListeAttributs() {
		List<String> listeAttributs = new ArrayList<String>();
		listeAttributs.add(this.adresse);
		listeAttributs.add(String.valueOf(this.codePostal));
		listeAttributs.add(this.pays);
		listeAttributs.add(String.valueOf(this.numTelephone));
		listeAttributs.add(this.mail);
		return listeAttributs;
	}

	@Override
	public void setListeAttributs(List<String> listeNouvellesValeurs) {
		this.adresse 	= listeNouvellesValeurs.get(0);
		this.codePostal = Integer.parseInt(listeNouvellesValeurs.get(1));
		this.pays 		= listeNouvellesValeurs.get(2);
		this.numTelephone = Integer.parseInt(listeNouvellesValeurs.get(3));
		this.mail 		= listeNouvellesValeurs.get(4);
	}
}
