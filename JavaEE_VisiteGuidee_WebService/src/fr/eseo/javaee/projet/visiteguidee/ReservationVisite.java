package fr.eseo.javaee.projet.visiteguidee;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.jws.WebService;

import fr.eseo.javaee.projet.db.GestionDB;
import fr.eseo.javaee.projet.db.objet.ConstructorFactory;
import fr.eseo.javaee.projet.db.objet.Reservation;
import fr.eseo.javaee.projet.db.objet.Visite;

@WebService(targetNamespace = "http://visiteguidee.projet.javaee.eseo.fr/", endpointInterface = "fr.eseo.javaee.projet.visiteguidee.ReservationVisiteSEI", portName = "ReservationVisitePort", serviceName = "ReservationVisiteService")
public class ReservationVisite implements ReservationVisiteSEI {

	public List<Visite> trouverVisite (Visite maVisite) {
		List<Visite> listVisite = new ArrayList<>();
		try {
			listVisite = GestionDB.searchVisite(maVisite);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return listVisite;
	}

	public int reserverVisite (Reservation maReservation) {
		int codeReservation = 0;
		try {
			codeReservation = GestionDB.ajoutReservation(maReservation);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return codeReservation;
	}

	public boolean payerVisite (int monCodeReservation) {
		Reservation reservation = ConstructorFactory.createReservation();
		try {
			reservation = GestionDB.searchReservationById(monCodeReservation);
			if(reservation.getCodeReservation() != 0) {
				reservation.setPaiementEffectue(true);
				GestionDB.updateReservationById(reservation);
				reservation = GestionDB.searchReservationById(monCodeReservation);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return reservation.isPaiementEffectue();
	}

	public boolean annulerVisite (int monCodeReservation) {
		boolean existeVisite = true;
		try {
			GestionDB.supprimerReservationById(monCodeReservation);
			existeVisite = !GestionDB.existeReservationById(monCodeReservation);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return existeVisite;
	}
}
