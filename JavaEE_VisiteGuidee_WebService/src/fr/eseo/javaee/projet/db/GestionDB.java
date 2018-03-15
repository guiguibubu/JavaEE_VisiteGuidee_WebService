package fr.eseo.javaee.projet.db;

import java.sql.SQLException;

public class GestionDB {

	public static void ajoutClient(String prenom, String nom) throws SQLException {
		BaseDeDonnees bdd = BaseDeDonnees.getInstance();
		String table = "client";
		bdd.openConnection(table);
		String sql = "INSERT INTO "+table+" (prenom, nom) VALUES ("+prenom+","+nom+")";
		bdd.executeSQL(sql, false);
		bdd.closeConnection();

	}

}
