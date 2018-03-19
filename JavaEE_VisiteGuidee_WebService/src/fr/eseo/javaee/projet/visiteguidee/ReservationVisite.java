package fr.eseo.javaee.projet.visiteguidee;


import javax.jws.WebService;

import fr.eseo.javaee.projet.db.objet.Reservation;
import fr.eseo.javaee.projet.db.objet.Visite;

@WebService(targetNamespace = "http://visiteguidee.projet.javaee.eseo.fr/", endpointInterface = "fr.eseo.javaee.projet.visiteguidee.ReservationVisiteSEI", portName = "ReservationVisitePort", serviceName = "ReservationVisiteService")
public class ReservationVisite implements ReservationVisiteSEI {

	public Visite[] trouverVisite (Visite maVisite) {return null;}

	public int reserverVisite (Reservation maReservation) {return 0;}

	public String payerVisite (int monCodeReservation) {return null;}

	public boolean annulerVisite (int monCodeReservation) {return true;}
}
