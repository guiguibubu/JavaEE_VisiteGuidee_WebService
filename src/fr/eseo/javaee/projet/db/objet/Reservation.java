package fr.eseo.javaee.projet.db.objet;

import java.util.ArrayList;
import java.util.List;

import fr.eseo.javaee.projet.db.BaseDeDonnees;

public class Reservation implements Memorisable {

	public static final String NOM_TABLE = "reservation";

	public static final String NOM_COL_ID 		= "idReservation";
	public static final String NOM_COL_VISITE 	= "idVisite";
	public static final String NOM_COL_CLIENT 	= "idClient";
	public static final String NOM_COL_PLACE 	= "nombrePlaces";
	public static final String NOM_COL_PAIEMENT	= "booleanPaiementEffectue";

	private static List<String> listeNomAttributsWithID = extractNomAttributs();
	private static List<String> listeNomAttributs = listeNomAttributsWithID.subList(1, listeNomAttributsWithID.size());


	private int codeReservation;
	private Visite visite;
	private Client client;
	private int nombrePersonnes;
	private boolean paiementEffectue;

	public static List<String> extractNomAttributs() {
		List<String> listeNomAttribut = new ArrayList<>();
		listeNomAttribut.add(NOM_COL_ID);
		listeNomAttribut.add(NOM_COL_VISITE);
		listeNomAttribut.add(NOM_COL_CLIENT);
		listeNomAttribut.add(NOM_COL_PLACE);
		listeNomAttribut.add(NOM_COL_PAIEMENT);
		return listeNomAttribut;
	}

	public int getCodeReservation() {return this.codeReservation;}
	public void setCodeReservation(int codeReservation) {this.codeReservation = codeReservation;}

	public Visite getVisite() {return this.visite;}
	public void setVisite(Visite visite) {this.visite = visite;}

	public Client getClient() {return this.client;}
	public void setClient(Client client) {this.client = client;}

	public int getNombrePersonnes() {return this.nombrePersonnes;}
	public void setNombrePersonnes(int nombrePersonnes) {this.nombrePersonnes = nombrePersonnes;}

	public boolean isPaiementEffectue() {return this.paiementEffectue;}
	public void setPaiementEffectue(boolean paiementEffectue) {this.paiementEffectue = paiementEffectue;}

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
		listeAttributs.add(String.valueOf(this.visite.getCodeVisite()));
		listeAttributs.add(String.valueOf(this.client.getIdClient()));
		listeAttributs.add(String.valueOf(this.nombrePersonnes));
		listeAttributs.add(BaseDeDonnees.convertForDB(this.paiementEffectue));
		return listeAttributs;
	}

	@Override
	public List<String> getListeAttributsWithID() {
		List<String> listeAttributs = new ArrayList<>();
		listeAttributs.add(String.valueOf(this.visite.getCodeVisite()));
		listeAttributs.addAll(this.extractListeAttributs());
		return listeAttributs;
	}

	@Override
	public void modifyListeAttributs(List<String> listeNouvellesValeurs) {
		this.codeReservation = Integer.parseInt(listeNouvellesValeurs.get(0));
		this.visite = ConstructorFactory.createVisite(Integer.parseInt(listeNouvellesValeurs.get(1)));
		this.client = ConstructorFactory.createClient(Integer.parseInt(listeNouvellesValeurs.get(2)));
		this.nombrePersonnes = Integer.parseInt(listeNouvellesValeurs.get(3));
		this.paiementEffectue = BaseDeDonnees.convertBooleanFromDB(listeNouvellesValeurs.get(4));
	}

	@Override
	public List<String> getListeNomAttributsWithID() {
		return listeNomAttributsWithID;
	}

}
