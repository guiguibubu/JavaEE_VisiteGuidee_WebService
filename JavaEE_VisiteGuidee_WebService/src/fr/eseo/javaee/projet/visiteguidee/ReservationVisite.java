package fr.eseo.javaee.projet.visiteguidee;

public class ReservationVisite {

	int codeReservation;
	int codeVisite;
	int codeClient;
	int nombrePersonnes;
	boolean payementEffectue;

	/**
	 * constructeur vide
	 */
	public ReservationVisite() {
		this.codeReservation = 0;
		this.codeVisite = 0;
		this.codeClient = 0;
		this.nombrePersonnes = 0;
		this.payementEffectue = false;
	}

	/**
	 * constructeur non vide
	 */
	public ReservationVisite(int codeReservation, int codeVisite, int codeClient, int nombrePersonnes, boolean payementEffectue) {
		this.codeReservation = codeReservation;
		this.codeVisite = codeVisite;
		this.codeClient = codeClient;
		this.nombrePersonnes = nombrePersonnes;
		this.payementEffectue = payementEffectue;
	}

}
