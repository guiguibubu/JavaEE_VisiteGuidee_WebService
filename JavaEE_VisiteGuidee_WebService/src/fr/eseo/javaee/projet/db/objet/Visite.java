package fr.eseo.javaee.projet.db.objet;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import fr.eseo.javaee.projet.db.BaseDeDonnees;

public class Visite implements Memorisable {

	public static final String NOM_TABLE = "visite";

	public static final String NOM_COL_ID 		= "idVisite";
	public static final String NOM_COL_TYPE 	= "typeVisite";
	public static final String NOM_COL_VILLE 	= "ville";
	public static final String NOM_COL_DATE 	= "dateVisite";
	public static final String NOM_COL_PRIX 	= "prixVisite";

	private static List<String> listeNomAttributs = extractNomAttributs();

	private int codeVisite;
	private String typeDeVisite;
	private String ville;
	private LocalDateTime dateVisite;
	private float prix;

	public Visite() {
		this(0);
	}

	public Visite(int codeVisite) {
		this(codeVisite,"","",LocalDateTime.now(),0);
	}

	public Visite(String typeDeVisite, String ville, LocalDateTime dateVisite, float prix) {
		this(0, typeDeVisite, ville, dateVisite, prix);
	}
	public Visite(int codeVisite, String typeDeVisite, String ville, LocalDateTime dateVisite, float prix) {
		this.codeVisite = codeVisite;
		this.typeDeVisite = typeDeVisite;
		this.ville = ville;
		this.dateVisite = dateVisite;
		this.prix = prix;
	}

	public static List<String> extractNomAttributs() {
		List<String> listeNomAttribut = new ArrayList<>();
		listeNomAttribut.add(NOM_COL_TYPE);
		listeNomAttribut.add(NOM_COL_VILLE);
		listeNomAttribut.add(NOM_COL_DATE);
		listeNomAttribut.add(NOM_COL_PRIX);
		return listeNomAttribut;
	}

	public boolean isOuverte() {
		return LocalDateTime.now().isBefore(this.dateVisite);
	}

	public int getCodeVisite() {return this.codeVisite;}
	public void setCodeVisite(int codeVisite) {this.codeVisite = codeVisite;}

	public String getTypeDeVisite() {return this.typeDeVisite;}
	public void setTypeDeVisite(String typeDeVisite) {this.typeDeVisite = typeDeVisite;}

	public String getVille() {return this.ville;}
	public void setVille(String ville) {this.ville = ville;}

	public LocalDateTime getDateVisite() {return this.dateVisite;}
	public void setDateVisite(LocalDateTime dateVisite) {this.dateVisite = dateVisite;}

	public float getPrix() {return this.prix;}
	public void setPrix(float prix) {this.prix = prix;}

	@Override
	public String getNomTable() {
		return NOM_TABLE;
	}

	@Override
	public List<String> getListeNomAttributs() {
		return listeNomAttributs;
	}

	@Override
	public List<String> getListeAttributs() {
		List<String> listeAttributs = new ArrayList<>();
		listeAttributs.add(this.typeDeVisite);
		listeAttributs.add(this.ville);
		listeAttributs.add(BaseDeDonnees.convertForDB(this.dateVisite));
		listeAttributs.add(BaseDeDonnees.convertForDB(this.prix));
		return listeAttributs;
	}

	@Override
	public void setListeAttributs(List<String> listeNouvellesValeurs) {
		this.codeVisite = Integer.parseInt(listeNouvellesValeurs.get(0));
		this.typeDeVisite = listeNouvellesValeurs.get(1);
		this.ville 		= listeNouvellesValeurs.get(2);
		this.dateVisite = BaseDeDonnees.convertDateTimeFromDB(listeNouvellesValeurs.get(3));
		this.prix 		= BaseDeDonnees.convertFloatFromDB(listeNouvellesValeurs.get(4));
	}

}
