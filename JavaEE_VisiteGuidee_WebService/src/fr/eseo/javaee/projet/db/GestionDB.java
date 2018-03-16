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

	public static void cleanTable(String table) throws SQLException {
		initConnection();
		String sql = "DELETE FROM client";
		bdd.executeSQL(sql, false);
		bdd.closeResulSet();
		bdd.closeStatement();
		closeConnection();
	}

	//METHODES CLIENT
	public static boolean existeClient(String prenom, String nom) throws SQLException {
		boolean existeClient = false;
		initConnection();
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

	public static void updateClient(String prenom, String nom, String dateNaissance, String adresse, int codePostal, int num_tel, String mail) throws SQLException {
		if(existeClient(prenom, nom)) {
			initConnection();
			String sql = "UPDATE client SET dateNaissance='"+dateNaissance+"', adresse='"+adresse+"', codePostal='"+codePostal+"', numTelephone='"+num_tel+"', mail='"+mail+"' WHERE prenom='"+prenom+"' AND nom='"+nom+"'";
			bdd.executeSQL(sql, false);
			closeConnection();
		}
	}
	
	
	
	
	public static boolean existeVisite(String typeVisite, String ville, String date) throws SQLException {
		boolean existeVisite = false;
		initConnection();
		String sql = "SELECT * FROM visite";
		ResultSet rs = bdd.executeSQL(sql, true);
		existeVisite = rs.next();
		bdd.closeResulSet();
		bdd.closeStatement();
		closeConnection();
		return existeVisite;
	}

	public static void ajoutVisite(String type, String ville, String date, int prix) throws SQLException {
		if(!existeVisite(type, ville, date)) {
			initConnection();
			String sql = "INSERT INTO visite (typeVisite, ville, date, prix) VALUES ('"+type+"','"+ville+"','"+date+"','"+prix+"')";
			bdd.executeSQL(sql, false);
			closeConnection();
		}
	}


	public static void supprimerVisite(String type, String ville, String date) throws SQLException {
		if(!existeVisite(type, ville, date)) {
			initConnection();
			String sql = "DELETE FROM visite WHERE typeVisite='"+type+"' AND ville='"+ville+"' AND date='"+date+"')";
			bdd.executeSQL(sql, false);
			closeConnection();
		}
	}
	
	
}
