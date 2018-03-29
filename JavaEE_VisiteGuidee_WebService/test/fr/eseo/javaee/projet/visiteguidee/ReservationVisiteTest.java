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
import fr.eseo.javaee.projet.tools.ConvertisseurDate;

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
	void testTrouverClientDejaEnBase() {
		Client client = ConstructorFactory.createClient("Buchle", "Guillaume");
		Client clientTrouve = ConstructorFactory.createClient();
		try {
			BaseDeDonnees.cleanTable(Client.NOM_TABLE);
			client.setIdClient(GestionDB.ajoutClient(client));
			ReservationVisite reservation = new ReservationVisite();
			clientTrouve = reservation.trouverClient(client.getNom(), client.getPrenom());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		Assertions.assertEquals(client.getIdClient(), clientTrouve.getIdClient());
		Assertions.assertEquals(client.getNom(), clientTrouve.getNom());
		Assertions.assertEquals(client.getPrenom(), clientTrouve.getPrenom());
	}

	@Test
	void testTrouverClientNonEnBase() {
		Client client = ConstructorFactory.createClient("Buchle", "Guillaume");
		Client clientTrouve = ConstructorFactory.createClient();
		try {
			BaseDeDonnees.cleanTable(Client.NOM_TABLE);
			ReservationVisite reservation = new ReservationVisite();
			clientTrouve = reservation.trouverClient(client.getNom(), client.getPrenom());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		Assertions.assertNotEquals(0, clientTrouve.getIdClient());
		Assertions.assertEquals(client.getNom(), clientTrouve.getNom());
		Assertions.assertEquals(client.getPrenom(), clientTrouve.getPrenom());
	}

	@Test
	void testTrouverVisitePresente() {
		LocalDate dateDate = LocalDate.of(2018,02, 2);
		LocalTime dateTime = LocalTime.of(11,22,33,00);
		LocalDateTime date = LocalDateTime.of(dateDate,dateTime);
		Visite visiteTest = ConstructorFactory.createVisite("guidee", "AnGers", date, 99);
		ReservationVisite reservation = new ReservationVisite();
		List<Visite> visite_trouvee = null;
		try {
			BaseDeDonnees.cleanTable(Visite.NOM_TABLE);
			GestionDB.ajoutVisite(visiteTest);
			visite_trouvee = reservation.trouverVisite(visiteTest);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		Assertions.assertEquals(1, visite_trouvee.size());
		Assertions.assertEquals("Angers".toUpperCase(), visite_trouvee.get(0).getVille().toUpperCase());
	}

	@Test
	void testTrouverVisitePresente2() {
		LocalDateTime date = Visite.dateVisiteParDefaut;
		Visite visiteTest = ConstructorFactory.createVisite("guIdee", "AnGers", date, 0);
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
		Assertions.assertEquals("Angers".toUpperCase(), visite_trouvee.get(0).getVille().toUpperCase());
		Assertions.assertEquals("guidee".toUpperCase(), visite_trouvee.get(0).getTypeDeVisite().toUpperCase());
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
	void testTrouverToutesLesVisites() {
		LocalDateTime date = ConvertisseurDate.dateTimeParDefaut;
		Visite visiteRecherche = ConstructorFactory.createVisite("", null, date, -1);
		Visite visiteTest = ConstructorFactory.createVisite("guidee", "Lille", LocalDateTime.now(), 999);
		Visite visiteTest2 = ConstructorFactory.createVisite("guidee", "Nantes", date.plusDays(5), 999);
		Visite visiteTest3 = ConstructorFactory.createVisite("guidee", "Paris", date.plusHours(2), 600);
		Visite visiteTest4 = ConstructorFactory.createVisite("libre", "Angers", date.minusMonths(3), 500);
		Visite visiteTest5 = ConstructorFactory.createVisite("guidee", "Lille", date.plusYears(1), 400);
		ReservationVisite reservation = new ReservationVisite();
		List<Visite> visite_trouvee = null;
		try {
			BaseDeDonnees.cleanTable(Visite.NOM_TABLE);
			GestionDB.ajoutVisite(visiteTest);
			GestionDB.ajoutVisite(visiteTest2);
			GestionDB.ajoutVisite(visiteTest3);
			GestionDB.ajoutVisite(visiteTest4);
			GestionDB.ajoutVisite(visiteTest5);
			visite_trouvee = reservation.trouverVisite(visiteRecherche);
		}catch (SQLException e) {
			e.printStackTrace();
		}
		Assertions.assertEquals(5, visite_trouvee.size());
	}

	@Test
	void testReserverVisite() {
		LocalDate dateDate = LocalDate.of(2018,02, 2);
		LocalTime dateTime = LocalTime.of(11,22,33,00);
		LocalDateTime date = LocalDateTime.of(dateDate,dateTime);
		Visite visiteTest = ConstructorFactory.createVisite("guidee", "Angers", date, 99);
		ReservationVisite reservation = new ReservationVisite();
		Client clientTest = ConstructorFactory.createClient("Buchle", "Guillaume");
		Reservation reservationTest = ConstructorFactory.createReservation(visiteTest, clientTest, 20, false);
		try {
			BaseDeDonnees.cleanTable(Visite.NOM_TABLE);
			BaseDeDonnees.cleanTable(Client.NOM_TABLE);
			BaseDeDonnees.cleanTable(Reservation.NOM_TABLE);
			visiteTest.setCodeVisite(GestionDB.ajoutVisite(visiteTest));
			clientTest.setIdClient(GestionDB.ajoutClient(clientTest));
			reservationTest.setCodeReservation(reservation.reserverVisite(reservationTest));
		}catch (SQLException e) {
			e.printStackTrace();
		}
		Assertions.assertNotEquals(0, reservationTest.getCodeReservation());

	}

	@Test
	void testReserverVisiteMemeID() {
		LocalDate dateDate = LocalDate.of(2018,02, 2);
		LocalTime dateTime = LocalTime.of(11,22,33,00);
		LocalDateTime date = LocalDateTime.of(dateDate,dateTime);
		Visite visiteTest = ConstructorFactory.createVisite("guidee", "Angers", date, 99);
		ReservationVisite reservation = new ReservationVisite();
		Client clientTest = ConstructorFactory.createClient("Buchle", "Guillaume");
		Reservation reservationTest = ConstructorFactory.createReservation(visiteTest, clientTest, 20, false);
		Reservation reservationTest2 = ConstructorFactory.createReservation(visiteTest, clientTest, 20, false);
		try {
			BaseDeDonnees.cleanTable(Visite.NOM_TABLE);
			BaseDeDonnees.cleanTable(Client.NOM_TABLE);
			BaseDeDonnees.cleanTable(Reservation.NOM_TABLE);
			visiteTest.setCodeVisite(GestionDB.ajoutVisite(visiteTest));
			clientTest.setIdClient(GestionDB.ajoutClient(clientTest));
			reservationTest.setCodeReservation(reservation.reserverVisite(reservationTest));
			reservationTest2.setCodeReservation(reservation.reserverVisite(reservationTest2));
		}catch (SQLException e) {
			e.printStackTrace();
		}
		Assertions.assertNotEquals(reservationTest.getCodeReservation(), reservationTest2.getCodeReservation());
		Assertions.assertEquals(reservationTest.getVisite().getCodeVisite(), reservationTest.getVisite().getCodeVisite());
		Assertions.assertEquals(reservationTest.getClient().getIdClient(), reservationTest.getClient().getIdClient());
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

	@Test
	void testPayerVisitePayee() {
		LocalDate dateDate = LocalDate.of(2018,02, 2);
		LocalTime dateTime = LocalTime.of(11,22,33,00);
		LocalDateTime date = LocalDateTime.of(dateDate,dateTime);
		Visite visiteTest = ConstructorFactory.createVisite("guidee", "Angers", date, 99);
		Client clientTest = ConstructorFactory.createClient("Buchle", "Guillaume");
		Reservation reservationTest = ConstructorFactory.createReservation(visiteTest, clientTest, 20, false);
		ReservationVisite reservation = new ReservationVisite();
		boolean isPayed = false;
		try {
			BaseDeDonnees.cleanTable(Visite.NOM_TABLE);
			BaseDeDonnees.cleanTable(Client.NOM_TABLE);
			clientTest.setIdClient(GestionDB.ajoutClient(clientTest));
			visiteTest.setCodeVisite(GestionDB.ajoutVisite(visiteTest));
			reservationTest.setCodeReservation(GestionDB.ajoutReservation(reservationTest));
			isPayed = reservation.payerVisite(reservationTest.getCodeReservation());
		}catch (SQLException e) {
			e.printStackTrace();
		}
		Assertions.assertTrue(isPayed);
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
		Reservation reservationTest = ConstructorFactory.createReservation(visiteTest, clientTest, 20, false);
		ReservationVisite reservation = new ReservationVisite();
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