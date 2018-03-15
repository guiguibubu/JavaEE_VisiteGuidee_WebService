package fr.eseo.javaee.projet.db;

import java.sql.SQLException;

import org.junit.jupiter.api.Test;

class GestionDBTest {

	@Test
	void testAjoutClient() {
		try {
			GestionDB.ajoutClient("Guillaume", "Buchle");
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
}
