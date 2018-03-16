package fr.eseo.javaee.projet.db;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GestionDB {

	static BaseDeDonnees bdd;

	private static void initConnection() throws SQLException {
		bdd = BaseDeDonnees.getInstance();
		bdd.openConnection();
	}

	private static void closeConnection() throws SQLException {
		bdd.closeConnection();
	}

	public static boolean existeClient(String prenom, String nom) throws SQLException {
		boolean existeClient = false;
		initConnection();
		//		String sql = "SELECT * FROM client WHERE prenom='"+prenom+"' AND nom='"+nom+"'";
		String sql = "SELECT * FROM client";
		ResultSet rs = bdd.executeSQL(sql, true);
		existeClient = rs.next();
		bdd.closeResulSet();
		bdd.closeStatement();
		closeConnection();
		return existeClient;
	}

	public static void ajoutClient(String prenom, String nom) throws SQLException {
		if(!existeClient(prenom, nom)) {
			initConnection();
			String sql = "INSERT INTO client (prenom, nom) VALUES ('"+prenom+"','"+nom+"')";
			bdd.executeSQL(sql, false);
			closeConnection();
		}
	}

	public static void supprimeClient(String prenom, String nom) throws SQLException {
		if(existeClient(prenom, nom)) {
			initConnection();
			String sql = "DELETE FROM client WHERE prenom='"+prenom+"' AND nom='"+nom+"'";
			bdd.executeSQL(sql, false);
			closeConnection();
		}
	}

}
