package fr.eseo.javaee.projet.visiteguidee;


import java.sql.SQLException;
import java.util.List;

import javax.jws.WebService;

import fr.eseo.javaee.projet.db.GestionDB;
import fr.eseo.javaee.projet.db.objet.Reservation;
import fr.eseo.javaee.projet.db.objet.Visite;

@WebService(targetNamespace = "http://visiteguidee.projet.javaee.eseo.fr/", endpointInterface = "fr.eseo.javaee.projet.visiteguidee.ReservationVisiteSEI", portName = "ReservationVisitePort", serviceName = "ReservationVisiteService")
public class ReservationVisite implements ReservationVisiteSEI {

	public Visite[] trouverVisite (Visite maVisite) throws SQLException {
		Visite[] listVisites = new Visite[0];
		listVisites = (Visite[])GestionDB.searchVisite(maVisite).toArray();
		return listVisites;
	}

	public int reserverVisite (Reservation maReservation) throws SQLException {
		int idReservation = 0;
		GestionDB.ajoutReservation(maReservation);
		List<Reservation> listReservation = GestionDB.searchReservation(maReservation);
		idReservation = listReservation.get(listReservation.size()-1).getCodeReservation();
		return idReservation;
	}

	public String payerVisite (int monCodeReservation) throws SQLException {
		String text = "";
		Reservation reservation = GestionDB.searchReservationById(monCodeReservation);
		reservation.setPaiementEffectue(true);
		GestionDB.updateReservationById(reservation);
		return text;
	}

	public boolean annulerVisite (int monCodeReservation) throws SQLException {
		GestionDB.supprimerReservationById(monCodeReservation);
		return GestionDB.existeReservationById(monCodeReservation);
	}
}
