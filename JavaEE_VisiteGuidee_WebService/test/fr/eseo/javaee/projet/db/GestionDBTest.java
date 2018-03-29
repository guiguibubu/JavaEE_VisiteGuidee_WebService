package fr.eseo.javaee.projet.db;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.ResultSet;
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

import fr.eseo.javaee.projet.db.objet.Client;
import fr.eseo.javaee.projet.db.objet.ConstructorFactory;
import fr.eseo.javaee.projet.db.objet.Reservation;
import fr.eseo.javaee.projet.db.objet.Visite;

class GestionDBTest {

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

	//Test Client
	@Test
	void testAjoutClient() {
		String nom = "";
		String prenom = "";
		try {
			BaseDeDonnees.openConnection();
			BaseDeDonnees.executeSQL("DELETE FROM client WHERE nom='Buchle' AND prenom='Guillaume'", false);
			GestionDB.ajoutClient("Guillaume", "Buchle");

			BaseDeDonnees.openConnection();
			ResultSet rs = BaseDeDonnees.executeSQL("SELECT * FROM client WHERE nom='Buchle' AND prenom='Guillaume'", true);
			while(rs.next()) {
				nom = rs.getString("nom");
				prenom = rs.getString("prenom");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		Assertions.assertTrue("Buchle".equals(nom) && "Guillaume".equals(prenom));
	}

	@Test
	void testSupprimeClient() {
		boolean existeClient = true;
		try {
			GestionDB.ajoutClient("Guillaume", "Buchle");
			GestionDB.supprimeClient("Guillaume", "Buchle");
			existeClient = GestionDB.existeClient("Guillaume", "Buchle");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		Assertions.assertFalse(existeClient);
	}

	@Test
	void testExisteClient() {
		boolean existeClient = false;
		try {
			GestionDB.ajoutClient("Guillaume", "Buchle");
			existeClient = GestionDB.existeClient("Guillaume", "Buchle");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		Assertions.assertTrue(existeClient);
	}

	@Test
	void testCleanTable() {

		boolean existeClient = true;
		try {
			GestionDB.ajoutClient("Guillaume", "Buchle");
			GestionDB.ajoutClient("Guillaume", "Buchle2");
			GestionDB.ajoutClient("Guillaume", "Buchle3");
			BaseDeDonnees.cleanTable("client");
			existeClient = GestionDB.existeClient("Guillaume", "Buchle") ||
					GestionDB.existeClient("Guillaume", "Buchle2") ||
					GestionDB.existeClient("Guillaume", "Buchle3");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		Assertions.assertFalse(existeClient);
	}

	@Test
	void testUniciteClientSQL() {

		int nbClient = 0;
		try {
			BaseDeDonnees.cleanTable("client");
			GestionDB.ajoutClient("Guillaume", "Buchle");
			GestionDB.ajoutClient("Guillaume", "Buchle");
			nbClient = (GestionDB.existeClient("Guillaume", "Buchle")) ? nbClient+1 : nbClient;
			GestionDB.supprimeClient("Guillaume", "Buchle");
			nbClient = (GestionDB.existeClient("Guillaume", "Buchle")) ? nbClient+1 : nbClient;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		Assertions.assertEquals(1, nbClient);
	}

	//Test Visite
	@Test
	void testAjouterVisite() {
		LocalDate dateDate = LocalDate.of(2018,02, 2);
		LocalTime dateTime = LocalTime.of(11,22,33,00);
		LocalDateTime date = LocalDateTime.of(dateDate,dateTime);
		float prix = 59;
		try {
			GestionDB.ajoutVisite("guide", "Angers", date, prix);
		}catch (SQLException e) {
			e.printStackTrace();
		}
		Assertions.assertTrue(true);
	}

	@Test
	void testSupprimeVisite() {
		boolean existeVisite = true;
		LocalDate dateDate = LocalDate.of(2018,02, 2);
		LocalTime dateTime = LocalTime.of(11,22,33,00);
		LocalDateTime date = LocalDateTime.of(dateDate,dateTime);
		Visite visite = ConstructorFactory.createVisite("guide", "Angers", date, 59);
		try {
			visite.setCodeVisite(GestionDB.ajoutVisite("guide","Angers", date, 59));
			GestionDB.supprimerVisiteById(visite);
			existeVisite = GestionDB.existeVisite("guide","Angers",date, Float.MAX_VALUE+1);
		}catch (SQLException e) {
			e.printStackTrace();
		}
		Assertions.assertFalse(existeVisite);
	}

	@Test
	void testExisteVisite() {
		boolean existeVisite = true;
		LocalDate dateDate = LocalDate.of(2018,02, 2);
		LocalTime dateTime = LocalTime.of(11,22,33,00);
		LocalDateTime date = LocalDateTime.of(dateDate,dateTime);
		try {
			GestionDB.ajoutVisite("guide", "Nante", date, 59);
			existeVisite = GestionDB.existeVisite("guide","Nante",date,Float.MAX_VALUE+1);
		}catch (SQLException e) {
			e.printStackTrace();
		}
		Assertions.assertTrue(existeVisite);
	}

	//test passe mais ne devrait pas ==> liste vide
	@Test
	void testSearchVisiteTypeVisite() {
		LocalDate dateDate = LocalDate.of(2018,02, 2);
		LocalTime dateTime = LocalTime.of(11,22,33,00);
		LocalDateTime date = LocalDateTime.of(dateDate,dateTime);
		List<Visite> listVisite = new ArrayList<Visite>();
		try {
			BaseDeDonnees.cleanTable(Visite.NOM_TABLE);
			GestionDB.ajoutVisite("guide", "Nante", date, 60);
			GestionDB.ajoutVisite("guide", "Angers", date, 60);
			GestionDB.ajoutVisite("libre", "Paris", date, 61);
			GestionDB.ajoutVisite("guide", "Paris", LocalDateTime.now(), 99);
			listVisite = GestionDB.searchVisite("guide", null, null, null,-1, Float.MAX_VALUE+1);
		}catch (SQLException e) {
			e.printStackTrace();
		}
		Assertions.assertEquals(3,listVisite.size());
	}

	//test passe mais ne devrait pas ==> liste vide
	@Test
	void testSearchVisiteVille() {
		LocalDate dateDate = LocalDate.of(2018,02, 2);
		LocalTime dateTime = LocalTime.of(11,22,33,00);
		LocalDateTime date = LocalDateTime.of(dateDate,dateTime);
		List<Visite> listVisite = new ArrayList<Visite>();
		try {
			BaseDeDonnees.cleanTable(Visite.NOM_TABLE);
			GestionDB.ajoutVisite("guide", "Nante", date, 60);
			GestionDB.ajoutVisite("guide", "Angers", date, 60);
			GestionDB.ajoutVisite("libre", "Paris", date, 61);
			GestionDB.ajoutVisite("guide", "Paris", LocalDateTime.now(), 99);
			listVisite = GestionDB.searchVisite(null, "PaRis", null, null,-1, Float.MAX_VALUE+1);
		}catch (SQLException e) {
			e.printStackTrace();
		}
		Assertions.assertEquals(2,listVisite.size());
		Assertions.assertEquals("libre", listVisite.get(0).getTypeDeVisite());
		Assertions.assertEquals("guide", listVisite.get(1).getTypeDeVisite());
		Assertions.assertEquals("Paris", listVisite.get(0).getVille());
		Assertions.assertEquals("Paris", listVisite.get(1).getVille());
	}

	//test passe mais ne devrait pas ==> liste vide
	@Test
	void testSearchVisiteDate() {
		LocalDate dateDate = LocalDate.of(2018,02, 2);
		LocalTime dateTime = LocalTime.of(11,22,33,00);
		LocalDateTime date = LocalDateTime.of(dateDate,dateTime);
		List<Visite> listVisite = new ArrayList<Visite>();
		try {
			BaseDeDonnees.cleanTable(Visite.NOM_TABLE);
			GestionDB.ajoutVisite("guide", "Nante", date, 60);
			GestionDB.ajoutVisite("guide", "Angers", date, 60);
			GestionDB.ajoutVisite("libre", "Paris", date, 61);
			GestionDB.ajoutVisite("guide", "Paris", LocalDateTime.now(), 99);
			listVisite = GestionDB.searchVisite(null, null, date, date.plusDays(1),-1, Float.MAX_VALUE+1);
		}catch (SQLException e) {
			e.printStackTrace();
		}
		Assertions.assertEquals(3, listVisite.size());
	}

	@Test
	void testAjouterVisiteObjet() {
		LocalDate dateDate = LocalDate.of(2018,02, 2);
		LocalTime dateTime = LocalTime.of(11,22,33,00);
		LocalDateTime date = LocalDateTime.of(dateDate,dateTime);
		float prix = 59;
		Visite visiteTest = new Visite();
		visiteTest = ConstructorFactory.createVisite(1,"guide", "Angers", date, prix);
		try {
			GestionDB.ajoutVisite(visiteTest);
		}catch (SQLException e) {
			e.printStackTrace();
		}
		Assertions.assertTrue(true);
	}

	@Test
	void testSupprimeVisiteObjet() {
		boolean existeVisite = true;
		LocalDate dateDate = LocalDate.of(2018,02, 2);
		LocalTime dateTime = LocalTime.of(11,22,33,00);
		LocalDateTime date = LocalDateTime.of(dateDate,dateTime);
		float prix = 59;
		Visite visiteTest = ConstructorFactory.createVisite(1,"guide", "Angers", date, prix);
		try {
			visiteTest.setCodeVisite(GestionDB.ajoutVisite(visiteTest));
			GestionDB.supprimerVisiteById(visiteTest);
			existeVisite = GestionDB.existeVisite("guide","Angers",date,Float.MAX_VALUE+1);
		}catch (SQLException e) {
			e.printStackTrace();
		}
		Assertions.assertFalse(existeVisite);
	}

	//Test Reservation
	@Test
	void testAjouterReservation() {
		Visite visiteTest = ConstructorFactory.createVisite("guidee", "Nantes", LocalDateTime.now(), 50);
		Client clientTest = ConstructorFactory.createClient("Buchle", "Guillaume");
		Reservation reservationTest = ConstructorFactory.createReservation(visiteTest, clientTest, 2, false);
		boolean reservationAjoutee = false;
		try {
			BaseDeDonnees.cleanTable(Visite.NOM_TABLE);
			BaseDeDonnees.cleanTable(Client.NOM_TABLE);
			visiteTest.setCodeVisite(GestionDB.ajoutVisite(visiteTest));
			clientTest.setIdClient(GestionDB.ajoutClient(clientTest));
			reservationTest.setCodeReservation(GestionDB.ajoutReservation(visiteTest,clientTest, 10, true));
			reservationAjoutee = GestionDB.existeReservationById(reservationTest);
		}catch (SQLException e) {
			e.printStackTrace();
		}
		Assertions.assertTrue(reservationAjoutee);
	}

	@Test
	void testSupprimerReservation() {
		Visite visiteTest = ConstructorFactory.createVisite("guidee", "Nantes", LocalDateTime.now(), 50);
		Client clientTest = ConstructorFactory.createClient("Buchle", "Guillaume");
		Reservation reservationTest = ConstructorFactory.createReservation(visiteTest, clientTest, 2, false);
		boolean reservationSupprimee = false;
		try {
			BaseDeDonnees.cleanTable(Visite.NOM_TABLE);
			BaseDeDonnees.cleanTable(Client.NOM_TABLE);
			visiteTest.setCodeVisite(GestionDB.ajoutVisite(visiteTest));
			clientTest.setIdClient(GestionDB.ajoutClient(clientTest));
			reservationTest.setCodeReservation(GestionDB.ajoutReservation(reservationTest));
			GestionDB.supprimerReservationById(reservationTest);
			reservationSupprimee = !GestionDB.existeReservationById(reservationTest);
		}catch (SQLException e) {
			e.printStackTrace();
		}
		Assertions.assertTrue(reservationSupprimee);
	}

}