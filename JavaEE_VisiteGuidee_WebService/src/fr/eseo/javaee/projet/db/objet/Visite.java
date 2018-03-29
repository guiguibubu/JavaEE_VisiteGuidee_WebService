package fr.eseo.javaee.projet.db.objet;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import fr.eseo.javaee.projet.db.BaseDeDonnees;
import fr.eseo.javaee.projet.tools.ConvertisseurDate;

public class Visite implements Memorisable {

	public static final String NOM_TABLE = "visite";

	public static final String NOM_COL_ID 		= "idVisite";
	public static final String NOM_COL_TYPE 	= "typeVisite";
	public static final String NOM_COL_VILLE 	= "ville";
	public static final String NOM_COL_DATE 	= "dateVisite";
	public static final String NOM_COL_PRIX 	= "prixVisite";

	private static List<String> listeNomAttributsWithID = extractNomAttributs();
	private static List<String> listeNomAttributs = listeNomAttributsWithID.subList(1, listeNomAttributsWithID.size());

	public static final LocalDateTime dateVisiteParDefaut = ConvertisseurDate.dateTimeParDefaut;

	private int codeVisite;
	private String typeDeVisite;
	private String ville;
	private Date dateVisite;
	private float prix;

	public static List<String> extractNomAttributs() {
		List<String> listeNomAttribut = new ArrayList<>();
		listeNomAttribut.add(NOM_COL_ID);
		listeNomAttribut.add(NOM_COL_TYPE);
		listeNomAttribut.add(NOM_COL_VILLE);
		listeNomAttribut.add(NOM_COL_DATE);
		listeNomAttribut.add(NOM_COL_PRIX);
		return listeNomAttribut;
	}

	public static boolean isOuverte(Visite visite) {
		return LocalDateTime.now().isBefore(ConvertisseurDate.asLocalDateTime(visite.getDateVisite()));
	}

	public int getCodeVisite() {return this.codeVisite;}
	public void setCodeVisite(int codeVisite) {this.codeVisite = codeVisite;}

	public String getTypeDeVisite() {return this.typeDeVisite;}
	public void setTypeDeVisite(String typeDeVisite) {this.typeDeVisite = typeDeVisite;}

	public String getVille() {return this.ville;}
	public void setVille(String ville) {this.ville = ville;}

	public Date getDateVisite() {return (this.dateVisite == null) ? ConvertisseurDate.asUtilDate(dateVisiteParDefaut) : this.dateVisite;}
	public void setDateVisite(Date dateVisite) {this.dateVisite = dateVisite;}

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
	public List<String> extractListeAttributs() {
		List<String> listeAttributs = new ArrayList<>();
		listeAttributs.add(this.typeDeVisite);
		listeAttributs.add(this.ville);
		listeAttributs.add(BaseDeDonnees.convertForDB(ConvertisseurDate.asLocalDateTime(this.getDateVisite())));
		listeAttributs.add(BaseDeDonnees.convertForDB(this.prix));
		return listeAttributs;
	}

	@Override
	public void modifyListeAttributs(List<String> listeNouvellesValeurs) {
		this.codeVisite = Integer.parseInt(listeNouvellesValeurs.get(0));
		this.typeDeVisite = listeNouvellesValeurs.get(1);
		this.ville 		= listeNouvellesValeurs.get(2);
		this.dateVisite = ConvertisseurDate.asUtilDate(BaseDeDonnees.convertDateTimeFromDB(listeNouvellesValeurs.get(3)));
		this.prix 		= BaseDeDonnees.convertFloatFromDB(listeNouvellesValeurs.get(4));
	}

	@Override
	public List<String> getListeAttributsWithID() {
		List<String> listeAttributs = new ArrayList<>();
		listeAttributs.add(String.valueOf(this.codeVisite));
		listeAttributs.addAll(this.extractListeAttributs());
		return listeAttributs;
	}

	@Override
	public List<String> getListeNomAttributsWithID() {
		return listeNomAttributsWithID;
	}

}
