package fr.eseo.javaee.projet.db.objet;

public class Reservation {
	private int codeReservation;
	private Visite visite;
	private Client client;
	private int nombrePersonnes;
	private boolean paiementEffectue;

	/**
	 * constructeur vide
	 */
	public Reservation() {
		this(0,null,null,0,false);
	}

	/**
	 * constructeur non vide
	 */
	public Reservation(int codeReservation, Visite visite, Client client, int nombrePersonnes, boolean paiementEffectue) {
		this.codeReservation = codeReservation;
		this.visite = visite;
		this.client = client;
		this.nombrePersonnes = nombrePersonnes;
		this.paiementEffectue = paiementEffectue;
	}

	public int getCodeReservation() {return this.codeReservation;}
	public void setCodeReservation(int codeReservation) {this.codeReservation = codeReservation;}

	public Visite getCodeVisite() {return this.visite;}
	public void setCodeVisite(Visite visite) {this.visite = visite;}

	public Client getCodeClient() {return this.client;}
	public void setCodeClient(Client client) {this.client = client;}

	public int getNombrePersonnes() {return this.nombrePersonnes;}
	public void setNombrePersonnes(int nombrePersonnes) {this.nombrePersonnes = nombrePersonnes;}

	public boolean isPaiementEffectue() {return this.paiementEffectue;}
	public void setPaiementEffectue(boolean paiementEffectue) {this.paiementEffectue = paiementEffectue;}

}
