package fr.eseo.javaee.projet.visiteguidee;

import javax.jws.WebService;

@WebService(targetNamespace = "http://visiteguidee.projet.javaee.eseo.fr/", endpointInterface = "fr.eseo.javaee.projet.visiteguidee.ReservationVisiteSEI", portName = "ReservationVisitePort", serviceName = "ReservationVisiteService")
public class ReservationVisite implements ReservationVisiteSEI {

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


	public Visite[] trouverVisite (Visite maVisite) {return null;}

	public int reserverVisite (ReservationVisite maReservation) {return this.codeClient;	}

	public String payerVisite (int monCodeReservation) {return null;}

	public boolean annulerVisite (int monCodeReservation) {return this.paiementEffectue;}



}
