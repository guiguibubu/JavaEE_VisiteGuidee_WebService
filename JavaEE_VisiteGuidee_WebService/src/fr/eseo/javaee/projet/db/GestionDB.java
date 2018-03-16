package fr.eseo.javaee.projet.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class GestionDB {

	static BaseDeDonnees bdd;

	private GestionDB() {};

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
		String sql = "SELECT * FROM client WHERE prenom=\""+prenom+"\" AND nom=\""+nom+"\"";
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

	public static void updateClient(String prenom, String nom, LocalDate dateNaissance, String adresse, int codePostal, int numTel, String mail) throws SQLException {
		if(existeClient(prenom, nom)) {
			initConnection();
			String sql = "UPDATE client SET dateNaissance='"+bdd.convertDateForDB(dateNaissance)+"', adresse='"+adresse+"', codePostal='"+codePostal+"', numTelephone='"+numTel+"', mail='"+mail+"' WHERE prenom='"+prenom+"' AND nom='"+nom+"'";
			bdd.executeSQL(sql, false);
			closeConnection();
		}
	}

	//METHODES VISITES

	public static boolean searchVisite(String typeVisite, String ville, LocalDateTime date) throws SQLException {
		// TODO : changer sortie en objet arraylist de visite
		boolean existeVisite = false;
		boolean whereClause = false;
		initConnection();
		String sql = "SELECT * FROM visite";

		if(!"".equals(typeVisite)) {
			if(!whereClause) {
				sql += " WHERE";
				whereClause = true;
			}
			sql += " typeVisite = \""+typeVisite+"\"";
		}

		ResultSet rs = bdd.executeSQL(sql, true);
		existeVisite = rs.next();
		bdd.closeResulSet();
		bdd.closeStatement();
		closeConnection();
		return existeVisite;
	}

	public static boolean existeVisite(String typeVisite, String ville, LocalDateTime date) throws SQLException {
		return searchVisite(typeVisite, ville, date);
	}

	public static void ajoutVisite(String type, String ville, LocalDateTime date, int prix) throws SQLException {
		if(!existeVisite(type, ville, date)) {
			initConnection();
			String sql = "INSERT INTO visite (typeVisite, ville, date, prix) VALUES ('"+type+"','"+ville+"','"+bdd.convertDateTimeForDB(date)+"','"+prix+"')";
			bdd.executeSQL(sql, false);
			closeConnection();
		}
	}


	public static void supprimerVisite(String type, String ville, LocalDateTime date) throws SQLException {
		if(!existeVisite(type, ville, date)) {
			initConnection();
			String sql = "DELETE FROM visite WHERE typeVisite='"+type+"' AND ville='"+ville+"' AND date='"+date+"')";
			bdd.executeSQL(sql, false);
			closeConnection();
		}
	}
}
