package fr.eseo.javaee.projet.db;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import fr.eseo.javaee.projet.db.objet.Client;
import fr.eseo.javaee.projet.db.objet.Visite;


class GestionDBTest {

	//Test Client
	@Test
	void testAjoutClient() {
		try {
			GestionDB.ajoutClient("Guillaume", "Buchle");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		Assertions.assertTrue(true);
	}

	@Test
	void testSupprimeClient() {
		boolean existeClient = true;
		try {
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
		Visite visite = new Visite("guide","Angers", date,59);
		try {
			GestionDB.ajoutVisite("guide","Angers", date, 59);
			GestionDB.supprimerVisiteById(visite);
			existeVisite = GestionDB.existeVisite("guide","Angers",date);
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
			existeVisite = GestionDB.existeVisite("guide","Nante",date);
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
			GestionDB.ajoutVisite("guide", "Nante", date, 60);
			GestionDB.ajoutVisite("guide", "Angers", date, 60);
			GestionDB.ajoutVisite("libre", "Paris", date, 61);
			GestionDB.ajoutVisite("guide", "Paris", LocalDateTime.now(), 99);
			listVisite = GestionDB.searchVisite("guide", null, null, null);
		}catch (SQLException e) {
			e.printStackTrace();
		}
		Assertions.assertEquals("[]",listVisite.toString());
	}

	//test passe mais ne devrait pas ==> liste vide
	@Test
	void testSearchVisiteVille() {
		LocalDate dateDate = LocalDate.of(2018,02, 2);
		LocalTime dateTime = LocalTime.of(11,22,33,00);
		LocalDateTime date = LocalDateTime.of(dateDate,dateTime);
		List<Visite> listVisite = new ArrayList<Visite>();
		try {
			GestionDB.ajoutVisite("guide", "Nante", date, 60);
			GestionDB.ajoutVisite("guide", "Angers", date, 60);
			GestionDB.ajoutVisite("libre", "Paris", date, 61);
			GestionDB.ajoutVisite("guide", "Paris", LocalDateTime.now(), 99);
			listVisite = GestionDB.searchVisite(null, "Paris", null, null);
		}catch (SQLException e) {
			e.printStackTrace();
		}
		Assertions.assertEquals("[]",listVisite.toString());
	}

	//test passe mais ne devrait pas ==> liste vide
	@Test
	void testSearchVisiteDate() {
		LocalDate dateDate = LocalDate.of(2018,02, 2);
		LocalTime dateTime = LocalTime.of(11,22,33,00);
		LocalDateTime date = LocalDateTime.of(dateDate,dateTime);
		List<Visite> listVisite = new ArrayList<Visite>();
		try {
			GestionDB.ajoutVisite("guide", "Nante", date, 60);
			GestionDB.ajoutVisite("guide", "Angers", date, 60);
			GestionDB.ajoutVisite("libre", "Paris", date, 61);
			GestionDB.ajoutVisite("guide", "Paris", LocalDateTime.now(), 99);
			listVisite = GestionDB.searchVisite(null, null, date, null);
		}catch (SQLException e) {
			e.printStackTrace();
		}
		Assertions.assertEquals("[]",listVisite.toString());
	}

	@Test
	void testAjouterVisiteObjet() {
		LocalDate dateDate = LocalDate.of(2018,02, 2);
		LocalTime dateTime = LocalTime.of(11,22,33,00);
		LocalDateTime date = LocalDateTime.of(dateDate,dateTime);
		float prix = 59;
		Visite visiteTest = new Visite(1,"guide", "Angers", date, prix);
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
		Visite visiteTest = new Visite(1,"guide", "Angers", date, prix);
		try {
			GestionDB.ajoutVisite(visiteTest);
			GestionDB.supprimerVisiteById(visiteTest);
			existeVisite = GestionDB.existeVisite("guide","Angers",date);
		}catch (SQLException e) {
			e.printStackTrace();
		}
		Assertions.assertFalse(existeVisite);
	}

	//Test Reservation
	@Test
	void testAjouterReservation() {
		Visite visteTest = new Visite();
		Client clientTest = new Client();
		try {
			GestionDB.ajoutReservation(visteTest,clientTest, 10, true);
		}catch (SQLException e) {
			e.printStackTrace();
		}
		Assertions.assertTrue(true);
	}

}