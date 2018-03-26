package fr.eseo.javaee.projet.visiteguidee;


import java.sql.SQLException;
import java.util.List;

import javax.jws.WebService;

import fr.eseo.javaee.projet.db.GestionDB;
import fr.eseo.javaee.projet.db.objet.Reservation;
import fr.eseo.javaee.projet.db.objet.Visite;

@WebService(targetNamespace = "http://visiteguidee.projet.javaee.eseo.fr/", endpointInterface = "fr.eseo.javaee.projet.visiteguidee.ReservationVisiteSEI", portName = "ReservationVisitePort", serviceName = "ReservationVisiteService")
public class ReservationVisite implements ReservationVisiteSEI {

	public List<Visite> trouverVisite (Visite maVisite) throws SQLException {
		return GestionDB.searchVisite(maVisite);
	}

	public int reserverVisite (Reservation maReservation) throws SQLException {
		return GestionDB.ajoutReservation(maReservation);
	}

	public boolean payerVisite (int monCodeReservation) throws SQLException {
		Reservation reservation = GestionDB.searchReservationById(monCodeReservation);
		if(reservation.getCodeReservation() != 0) {
			reservation.setPaiementEffectue(true);
			GestionDB.updateReservationById(reservation);
			reservation = GestionDB.searchReservationById(monCodeReservation);
		}
		return reservation.isPaiementEffectue();
	}

	public boolean annulerVisite (int monCodeReservation) throws SQLException {
		GestionDB.supprimerReservationById(monCodeReservation);
		return !GestionDB.existeReservationById(monCodeReservation);
	}
}
