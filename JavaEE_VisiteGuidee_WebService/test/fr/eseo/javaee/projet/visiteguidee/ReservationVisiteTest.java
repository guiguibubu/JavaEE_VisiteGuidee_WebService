package fr.eseo.javaee.projet.visiteguidee;

public class ReservationVisiteTest {

}

package fr.eseo.javaee.projet.visiteguidee;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import fr.eseo.javaee.projet.db.GestionDB;
import fr.eseo.javaee.projet.db.objet.Client;
import fr.eseo.javaee.projet.db.objet.ConstructorFactory;
import fr.eseo.javaee.projet.db.objet.Reservation;
import fr.eseo.javaee.projet.db.objet.Visite;

class ReservationVisiteTest {

	@Test
	void testTrouverVisitePresente() {
		LocalDate dateDate = LocalDate.of(2018,02, 2);
		LocalTime dateTime = LocalTime.of(11,22,33,00);
		LocalDateTime date = LocalDateTime.of(dateDate,dateTime);
		Visite visiteTest = new Visite();
		visiteTest = ConstructorFactory.createVisite("guidee", "Angers", date, 99);
		ReservationVisite reservation = new ReservationVisite();
		Visite[] visite_trouvee = null;
		try {
			GestionDB.ajoutVisite(visiteTest);
			visite_trouvee = reservation.trouverVisite(visiteTest);
		}catch (SQLException e) {
			e.printStackTrace();
		}
		Assertions.assertEquals(visiteTest.toString(), visite_trouvee.toString());
	}

	@Test
	void testTrouverVisitePresentes() {
		LocalDate dateDate = LocalDate.of(2018,02, 2);
		LocalTime dateTime = LocalTime.of(11,22,33,00);
		LocalDateTime date = LocalDateTime.of(dateDate,dateTime);
		Visite visiteTest = ConstructorFactory.createVisite("guidee", "Angers", date, 99);
		Visite[] listAttendu = {visiteTest, visiteTest};
		ReservationVisite reservation = new ReservationVisite();
		Visite[] visite_trouvee = null;
		try {
			GestionDB.ajoutVisite(visiteTest);
			GestionDB.ajoutVisite(visiteTest);
			visite_trouvee = reservation.trouverVisite(visiteTest);
		}catch (SQLException e) {
			e.printStackTrace();
		}
		Assertions.assertEquals(listAttendu, visite_trouvee);
	}

	@Test
	void testTrouverVisiteVide() {
		Visite visiteTest = new Visite();
		ReservationVisite reservation = new ReservationVisite();
		Visite[] visite_trouvee = null;
		try {
			GestionDB.ajoutVisite(visiteTest);
			visite_trouvee = reservation.trouverVisite(visiteTest);
		}catch (SQLException e) {
			e.printStackTrace();
		}
		Assertions.assertEquals(visiteTest.toString(), visite_trouvee.toString());
	}

	@Test
	void testTrouverVisiteAbsente() {
		LocalDate dateDate = LocalDate.of(2018,02, 2);
		LocalTime dateTime = LocalTime.of(11,22,33,00);
		LocalDateTime date = LocalDateTime.of(dateDate,dateTime);
		Visite visiteTest = new Visite();
		visiteTest = ConstructorFactory.createVisite("guidee", "Lille", date, 999);
		ReservationVisite reservation = new ReservationVisite();
		Visite[] visite_trouvee = null;
		try {
			visite_trouvee = reservation.trouverVisite(visiteTest);
		}catch (SQLException e) {
			e.printStackTrace();
		}
		Assertions.assertEquals(visiteTest.toString(), visite_trouvee.toString());
	}

	@Test
	void testTrouverVisiteAbsente2() {
		LocalDate dateDate = LocalDate.of(2018,02, 2);
		LocalTime dateTime = LocalTime.of(11,22,33,00);
		LocalDateTime date = LocalDateTime.of(dateDate,dateTime);
		Visite visiteTest = new Visite();
		visiteTest = ConstructorFactory.createVisite("guidee", "Lille", date, 999);
		ReservationVisite reservation = new ReservationVisite();
		try {
			GestionDB.ajoutVisite(visiteTest);
			GestionDB.supprimerVisiteById(visiteTest);
			reservation.trouverVisite(visiteTest);
		}catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@Test
	void testReserverVisite() {
		LocalDate dateDate = LocalDate.of(2018,02, 2);
		LocalTime dateTime = LocalTime.of(11,22,33,00);
		LocalDateTime date = LocalDateTime.of(dateDate,dateTime);
		Visite visiteTest = new Visite();
		visiteTest = ConstructorFactory.createVisite("guidee", "Angers", date, 99);
		ReservationVisite reservation = new ReservationVisite();
		Client clientTest = new Client();
		int reservationID = 1;
		Reservation reservationTest = new Reservation();
		reservationTest = ConstructorFactory.createReservation(reservationID, visiteTest, clientTest, 20, false);
		try {
			reservation.reserverVisite(reservationTest);
		}catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@Test
	void testReserverVisiteMemeID() {
		LocalDate dateDate = LocalDate.of(2018,02, 2);
		LocalTime dateTime = LocalTime.of(11,22,33,00);
		LocalDateTime date = LocalDateTime.of(dateDate,dateTime);
		Visite visiteTest = new Visite();
		visiteTest = ConstructorFactory.createVisite("guidee", "Angers", date, 99);
		ReservationVisite reservation = new ReservationVisite();
		Client clientTest = new Client();
		int reservationID = 1;
		Reservation reservationTest = new Reservation();
		Reservation reservationTest2 = new Reservation();
		reservationTest = ConstructorFactory.createReservation(reservationID, visiteTest, clientTest, 20, false);
		reservationTest2 = ConstructorFactory.createReservation(reservationID, visiteTest, clientTest, 20, false);
		try {
			reservation.reserverVisite(reservationTest);
			reservation.reserverVisite(reservationTest2);
		}catch (SQLException e) {
			e.printStackTrace();
		}
		Assertions.assertEquals(clientTest.toString(), reservationTest.getClient().toString());
	}

	@Test
	void testReserverVisiteIDNull() {
		LocalDate dateDate = LocalDate.of(2018,02, 2);
		LocalTime dateTime = LocalTime.of(11,22,33,00);
		LocalDateTime date = LocalDateTime.of(dateDate,dateTime);
		Visite visiteTest = new Visite();
		visiteTest = ConstructorFactory.createVisite("guidee", "Angers", date, 99);
		ReservationVisite reservation = new ReservationVisite();
		Client clientTest = new Client();
		Reservation reservationTest = new Reservation();
		reservationTest = ConstructorFactory.createReservation(visiteTest, clientTest, 20, false);
		try {
			reservation.reserverVisite(reservationTest);
		}catch (SQLException e) {
			e.printStackTrace();
		}
		Assertions.assertEquals(clientTest.toString(), reservationTest.getClient().toString());
	}


	@Test
	void testPayerVisite() {
		LocalDate dateDate = LocalDate.of(2018,02, 2);
		LocalTime dateTime = LocalTime.of(11,22,33,00);
		LocalDateTime date = LocalDateTime.of(dateDate,dateTime);
		Visite visiteTest = new Visite();
		visiteTest = ConstructorFactory.createVisite("guidee", "Angers", date, 99);
		Client clientTest = new Client();
		int idReservation = 0;
		Reservation reservationTest = new Reservation();
		reservationTest = ConstructorFactory.createReservation(visiteTest, clientTest, 20, false);
		ReservationVisite reservation = new ReservationVisite();
		try {
			GestionDB.ajoutReservation(reservationTest);
			reservation.payerVisite(idReservation);
		}catch (SQLException e) {
			e.printStackTrace();
		}
		Assertions.assertTrue(reservationTest.isPaiementEffectue());
	}

	@Test
	void testPayerVisiteReservationVide() {
		LocalDate dateDate = LocalDate.of(2018,02, 2);
		LocalTime dateTime = LocalTime.of(11,22,33,00);
		LocalDateTime date = LocalDateTime.of(dateDate,dateTime);
		Visite visiteTest = new Visite();
		visiteTest = ConstructorFactory.createVisite("guidee", "Angers", date, 99);
		Client clientTest = new Client();
		Reservation reservationTest = new Reservation();
		ReservationVisite reservation = new ReservationVisite();
		try {
			GestionDB.ajoutReservation(reservationTest);
			reservation.payerVisite(reservationTest.getCodeReservation());
		}catch (SQLException e) {
			e.printStackTrace();
		}
		Assertions.assertTrue(reservationTest.isPaiementEffectue());
	}

	void testPayerVisitePayee() {
		LocalDate dateDate = LocalDate.of(2018,02, 2);
		LocalTime dateTime = LocalTime.of(11,22,33,00);
		LocalDateTime date = LocalDateTime.of(dateDate,dateTime);
		Visite visiteTest = new Visite();
		visiteTest = ConstructorFactory.createVisite("guidee", "Angers", date, 99);
		Client clientTest = new Client();
		int idReservation = 0;
		Reservation reservationTest = new Reservation();
		reservationTest = ConstructorFactory.createReservation(visiteTest, clientTest, 20, false);
		ReservationVisite reservation = new ReservationVisite();
		try {
			GestionDB.ajoutReservation(reservationTest);
			reservation.payerVisite(idReservation);
		}catch (SQLException e) {
			e.printStackTrace();
		}
		Assertions.assertTrue(reservationTest.isPaiementEffectue());
	}

	@Test
	void testAnnulerVisite() {
		LocalDate dateDate = LocalDate.of(2018,02, 2);
		LocalTime dateTime = LocalTime.of(11,22,33,00);
		LocalDateTime date = LocalDateTime.of(dateDate,dateTime);
		Visite visiteTest = new Visite();
		visiteTest = ConstructorFactory.createVisite("guidee", "Angers", date, 99);
		Client clientTest = new Client();
		int idReservation = 0;
		boolean visiteAnnulee = false;
		ReservationVisite reservation = new ReservationVisite();
		Reservation reservationTest = new Reservation();
		reservationTest = ConstructorFactory.createReservation(visiteTest, clientTest, 20, false);
		try {
			GestionDB.ajoutReservation(reservationTest);
			reservation.annulerVisite(idReservation);
		}catch (SQLException e) {
			e.printStackTrace();
		}
		Assertions.assertTrue(visiteAnnulee);
	}

	@Test
	void testAnnulerVisiteIDNull() {
		LocalDate dateDate = LocalDate.of(2018,02, 2);
		LocalTime dateTime = LocalTime.of(11,22,33,00);
		LocalDateTime date = LocalDateTime.of(dateDate,dateTime);
		Visite visiteTest = new Visite();
		visiteTest = ConstructorFactory.createVisite("guidee", "Angers", date, 99);
		Client clientTest = new Client();
		ReservationVisite reservation = new ReservationVisite();
		Reservation reservationTest = new Reservation();
		reservationTest = ConstructorFactory.createReservation(visiteTest, clientTest, 20, false);
		boolean visiteAnnulee = false;
		try {
			GestionDB.ajoutReservation(reservationTest);
			visiteAnnulee = reservation.annulerVisite(reservationTest.getCodeReservation());
		}catch (SQLException e) {
			e.printStackTrace();
		}
		Assertions.assertTrue(visiteAnnulee);
	}
}