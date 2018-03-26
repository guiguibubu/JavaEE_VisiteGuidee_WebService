package fr.eseo.javaee.projet.visiteguidee;


import java.sql.SQLException;

import javax.jws.WebService;

import fr.eseo.javaee.projet.db.BaseDeDonnees;
import fr.eseo.javaee.projet.db.GestionDB;
import fr.eseo.javaee.projet.db.objet.Reservation;
import fr.eseo.javaee.projet.db.objet.Visite;

@WebService(targetNamespace = "http://visiteguidee.projet.javaee.eseo.fr/", endpointInterface = "fr.eseo.javaee.projet.visiteguidee.ReservationVisiteSEI", portName = "ReservationVisitePort", serviceName = "ReservationVisiteService")
public class ReservationVisite implements ReservationVisiteSEI {

	@Override
	public Visite[] trouverVisite (Visite maVisite) throws SQLException {
		return GestionDB.searchVisite(maVisite).toArray(new Visite[0]);
	}

	@Override
	public int reserverVisite (Reservation maReservation) throws SQLException {
		return GestionDB.ajoutReservation(maReservation);
	}

	@Override
	public String payerVisite (int monCodeReservation) throws SQLException {
		Reservation reservation = GestionDB.searchReservationById(monCodeReservation);
		reservation.setPaiementEffectue(true);
		GestionDB.updateReservationById(reservation);
		return BaseDeDonnees.convertForDB(reservation.getCodeReservation() != 0);
	}

	@Override
	public boolean annulerVisite (int monCodeReservation) throws SQLException {
		GestionDB.supprimerReservationById(monCodeReservation);
		return !GestionDB.existeReservationById(monCodeReservation);
	}
}
