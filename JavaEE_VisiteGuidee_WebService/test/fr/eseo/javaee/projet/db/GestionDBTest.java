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
}
