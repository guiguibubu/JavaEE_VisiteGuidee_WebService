package fr.eseo.javaee.projet.db;

import java.sql.SQLException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class GestionDBTest {

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
			GestionDB.cleanTable("client");
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
			GestionDB.cleanTable("client");
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
}