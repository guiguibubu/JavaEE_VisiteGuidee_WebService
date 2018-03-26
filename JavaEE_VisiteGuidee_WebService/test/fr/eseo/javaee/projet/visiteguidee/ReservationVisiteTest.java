
package fr.eseo.javaee.projet.visiteguidee;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.eseo.javaee.projet.db.BaseDeDonnees;
import fr.eseo.javaee.projet.db.GestionDB;
import fr.eseo.javaee.projet.db.objet.Client;
import fr.eseo.javaee.projet.db.objet.ConstructorFactory;
import fr.eseo.javaee.projet.db.objet.Reservation;
import fr.eseo.javaee.projet.db.objet.Visite;

class ReservationVisiteTest {

	private static int nbBefore = 0;
	private static int nbAfter = 0;

	private static List<String> sqlReset;

	private static void setDonneesDeTests() {
		nbBefore++;
		BufferedReader bufferedReader = null;
		try {
			if(sqlReset == null) {
				sqlReset = new ArrayList<>();
				bufferedReader = new BufferedReader(new FileReader("SQL/gestionVisiteInsertData.sql"));
				String sql = "";
				while((sql = bufferedReader.readLine()) !=null){
					if(!sql.trim().isEmpty() || sql.startsWith("/*")) {
						sqlReset.add(sql);
					}
				}
			}

			BaseDeDonnees.openConnection();
			for(String sql : sqlReset) {
				if(!sql.trim().isEmpty() || sql.startsWith("/*")) {
					BaseDeDonnees.executeSQL(sql, false);
				}
			}
			BaseDeDonnees.closeStatement();
		} catch (IOException | SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (bufferedReader != null) {
					bufferedReader.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}

	@BeforeEach
	void resetDonneesDeTestsBefore() {
		nbBefore++;
		setDonneesDeTests();
	}

	@AfterAll
	static void resetDonneesDeTestsAfter() {
		nbAfter++;
		setDonneesDeTests();
	}

	@Test
	void testResetDonnesDeTests() {
		setDonneesDeTests();
	}

	@Test
	void testTrouverVisitePresente() {
		LocalDate dateDate = LocalDate.of(2018,02, 2);
		LocalTime dateTime = LocalTime.of(11,22,33,00);
		LocalDateTime date = LocalDateTime.of(dateDate,dateTime);
		Visite visiteTest = new Visite();
		visiteTest = ConstructorFactory.createVisite("guidee", "Angers", date, 99);
		ReservationVisite reservation = new ReservationVisite();
		List<Visite> visite_trouvee = null;
		try {
			BaseDeDonnees.cleanTable(Visite.NOM_TABLE);
			GestionDB.ajoutVisite(visiteTest);
			visite_trouvee = reservation.trouverVisite(visiteTest);
		}catch (SQLException e) {
			e.printStackTrace();
		}
		Assertions.assertEquals(1, visite_trouvee.size());
		Assertions.assertEquals("Angers", visite_trouvee.get(0).getVille());
	}

	@Test
	void testTrouverVisitePresentes() {
		LocalDate dateDate = LocalDate.of(2018,02, 2);
		LocalTime dateTime = LocalTime.of(11,22,33,00);
		LocalDateTime date = LocalDateTime.of(dateDate,dateTime);
		Visite visiteTest1 = ConstructorFactory.createVisite("guidee", "Angers", date, 99);
		Visite visiteTest2 = ConstructorFactory.createVisite("guidee", "Nantes", date, 99);
		Visite visiteTestRecherche = ConstructorFactory.createVisite("guidee", "", date, 99);
		//		Visite[] listAttendu = {visiteTest1, visiteTest2};
		ReservationVisite reservation = new ReservationVisite();
		List<Visite> visite_trouvee = null;
		try {
			BaseDeDonnees.cleanTable(Visite.NOM_TABLE);
			visiteTest1.setCodeVisite(GestionDB.ajoutVisite(visiteTest1));
			visiteTest2.setCodeVisite(GestionDB.ajoutVisite(visiteTest2));
			visite_trouvee = reservation.trouverVisite(visiteTestRecherche);
		}catch (SQLException e) {
			e.printStackTrace();
		}
		Assertions.assertEquals(2, visite_trouvee.size());
	}

	@Test
	void testTrouverVisiteAbsente() {
		LocalDate dateDate = LocalDate.of(2018,02, 2);
		LocalTime dateTime = LocalTime.of(11,22,33,00);
		LocalDateTime date = LocalDateTime.of(dateDate,dateTime);
		Visite visiteTest = ConstructorFactory.createVisite("guidee", "Lille", date, 999);
		ReservationVisite reservation = new ReservationVisite();
		List<Visite> visite_trouvee = null;
		try {
			BaseDeDonnees.cleanTable(Visite.NOM_TABLE);
			GestionDB.ajoutVisite("", "Nantes", LocalDateTime.now(), 0);
			visite_trouvee = reservation.trouverVisite(visiteTest);
		}catch (SQLException e) {
			e.printStackTrace();
		}
		Assertions.assertEquals(0, visite_trouvee.size());
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
		Visite visiteTest = ConstructorFactory.createVisite("guidee", "Angers", date, 99);
		Client clientTest = ConstructorFactory.createClient("Buchle", "Guillaume");
		Reservation reservationTest = new Reservation();
		ReservationVisite reservation = new ReservationVisite();
		try {
			BaseDeDonnees.cleanTable(Visite.NOM_TABLE);
			BaseDeDonnees.cleanTable(Client.NOM_TABLE);
			visiteTest.setCodeVisite(GestionDB.ajoutVisite(visiteTest));
			clientTest.setIdClient(GestionDB.ajoutClient(clientTest));
			reservationTest.setClient(clientTest);
			reservationTest.setVisite(visiteTest);
			reservationTest.setCodeReservation(GestionDB.ajoutReservation(reservationTest));
			boolean isPayed = reservation.payerVisite(reservationTest.getCodeReservation());
			reservationTest.setPaiementEffectue(isPayed);
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
		Visite visiteTest = ConstructorFactory.createVisite("guidee", "Angers", date, 99);
		Client clientTest = ConstructorFactory.createClient("Buchle", "Guillaume");
		Reservation reservationTest = new Reservation();
		ReservationVisite reservation = new ReservationVisite();
		try {
			BaseDeDonnees.cleanTable(Visite.NOM_TABLE);
			BaseDeDonnees.cleanTable(Client.NOM_TABLE);
			visiteTest.setCodeVisite(GestionDB.ajoutVisite(visiteTest));
			clientTest.setIdClient(GestionDB.ajoutClient(clientTest));
			reservationTest.setClient(clientTest);
			reservationTest.setVisite(visiteTest);
			reservationTest.setCodeReservation(GestionDB.ajoutReservation(reservationTest));
			GestionDB.supprimerReservationById(reservationTest);
			reservation.payerVisite(reservationTest.getCodeReservation());
		}catch (SQLException e) {
			e.printStackTrace();
		}
		Assertions.assertFalse(reservationTest.isPaiementEffectue());
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
		Visite visiteTest = ConstructorFactory.createVisite("guidee", "Angers", date, 99);
		Client clientTest = ConstructorFactory.createClient("Buchle", "Guillaume");
		boolean visiteAnnulee = false;
		ReservationVisite reservation = new ReservationVisite();
		Reservation reservationTest = ConstructorFactory.createReservation(visiteTest, clientTest, 20, false);
		try {
			reservationTest.setCodeReservation(GestionDB.ajoutReservation(reservationTest));
			visiteAnnulee = reservation.annulerVisite(reservationTest.getCodeReservation());
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
		Visite visiteTest = ConstructorFactory.createVisite("guidee", "Angers", date, 99);
		Client clientTest = ConstructorFactory.createClient("Buchle", "Guillaume");
		ReservationVisite reservation = new ReservationVisite();
		Reservation reservationTest = ConstructorFactory.createReservation(visiteTest, clientTest, 20, false);
		boolean visiteAnnulee = false;
		try {
			BaseDeDonnees.cleanTable(Visite.NOM_TABLE);
			BaseDeDonnees.cleanTable(Client.NOM_TABLE);
			clientTest.setIdClient(GestionDB.ajoutClient(clientTest));
			visiteTest.setCodeVisite(GestionDB.ajoutVisite(visiteTest));
			reservationTest.setCodeReservation(GestionDB.ajoutReservation(reservationTest));
			visiteAnnulee = reservation.annulerVisite(reservationTest.getCodeReservation());
		}catch (SQLException e) {
			e.printStackTrace();
		}
		Assertions.assertTrue(visiteAnnulee);
	}
}