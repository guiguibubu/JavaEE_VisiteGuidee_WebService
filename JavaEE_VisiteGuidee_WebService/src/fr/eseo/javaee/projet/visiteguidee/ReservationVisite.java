package fr.eseo.javaee.projet.visiteguidee;

import javax.jws.WebService;

import fr.eseo.javaee.projet.db.objet.Visite;

@WebService(targetNamespace = "http://visiteguidee.projet.javaee.eseo.fr/", endpointInterface = "fr.eseo.javaee.projet.visiteguidee.ReservationVisiteSEI", portName = "ReservationVisitePort", serviceName = "ReservationVisiteService")
public class ReservationVisite implements ReservationVisiteSEI {

	@Override
	public Visite[] trouverVisite (Visite maVisite) {return null;}

	@Override
	public int reserverVisite (ReservationVisite maReservation) {return 0;}

	@Override
	public String payerVisite (int monCodeReservation) {return null;}

	@Override
	public boolean annulerVisite (int monCodeReservation) {return true;}



}
