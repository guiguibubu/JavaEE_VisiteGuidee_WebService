package fr.eseo.javaee.projet.visiteguidee;

public class ReservationVisite {

	private int codeReservation;
	private int codeVisite;
	private int codeClient;
	private int nombrePersonnes;
	private boolean paiementEffectue;

	/**
	 * constructeur vide
	 */
	public ReservationVisite() {
		this.codeReservation = 0;
		this.codeVisite = 0;
		this.codeClient = 0;
		this.nombrePersonnes = 0;
		this.paiementEffectue = false;
	}

	/**
	 * constructeur non vide
	 */
	public ReservationVisite(int codeReservation, int codeVisite, int codeClient, int nombrePersonnes, boolean paiementEffectue) {
		this.codeReservation = codeReservation;
		this.codeVisite = codeVisite;
		this.codeClient = codeClient;
		this.nombrePersonnes = nombrePersonnes;
		this.paiementEffectue = paiementEffectue;
	}

	public int getCodeReservation() {return this.codeReservation;}
	public void setCodeReservation(int codeReservation) {this.codeReservation = codeReservation;}

	public int getCodeVisite() {return this.codeVisite;}
	public void setCodeVisite(int codeVisite) {this.codeVisite = codeVisite;}

	public int getCodeClient() {return this.codeClient;}
	public void setCodeClient(int codeClient) {this.codeClient = codeClient;}

	public int getNombrePersonnes() {return this.nombrePersonnes;}
	public void setNombrePersonnes(int nombrePersonnes) {this.nombrePersonnes = nombrePersonnes;}

	public boolean isPaiementEffectue() {return this.paiementEffectue;}
	public void setPaiementEffectue(boolean paiementEffectue) {this.paiementEffectue = paiementEffectue;}

}
