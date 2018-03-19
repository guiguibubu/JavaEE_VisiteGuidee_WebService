package fr.eseo.javaee.projet.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import fr.eseo.javaee.projet.db.objet.Visite;

public class GestionDB {

	private GestionDB() {};

	private static void initConnection() throws SQLException {
		BaseDeDonnees.initBDD();
		BaseDeDonnees.openConnection();
	}

	private static void closeConnection() throws SQLException {
		BaseDeDonnees.closeConnection();
	}

	//METHODES CLIENT
	// TODO : méthodes avec objet Client
	public static boolean existeClient(String prenom, String nom) throws SQLException {
		boolean existeClient = false;
		initConnection();
		String sql = "SELECT * FROM client WHERE prenom=\""+prenom+"\" AND nom=\""+nom+"\"";
		ResultSet rs = BaseDeDonnees.executeSQL(sql, true);
		existeClient = rs.next();
		BaseDeDonnees.closeResulSet();
		BaseDeDonnees.closeStatement();
		closeConnection();
		return existeClient;
	}

	public static void ajoutClient(String prenom, String nom) throws SQLException {
		if(!existeClient(prenom, nom)) {
			initConnection();
			String sql = "INSERT INTO client (prenom, nom) VALUES ('"+prenom+"','"+nom+"')";
			BaseDeDonnees.executeSQL(sql, false);
			closeConnection();
		}
	}

	public static void supprimeClient(String prenom, String nom) throws SQLException {
		if(existeClient(prenom, nom)) {
			initConnection();
			String sql = "DELETE FROM client WHERE prenom='"+prenom+"' AND nom='"+nom+"'";
			BaseDeDonnees.executeSQL(sql, false);
			closeConnection();
		}
	}

	public static void updateClient(String prenom, String nom, LocalDate dateNaissance, String adresse, int codePostal, int numTel, String mail) throws SQLException {
		if(existeClient(prenom, nom)) {
			initConnection();
			String sql = "UPDATE client SET dateNaissance='"+BaseDeDonnees.convertDateForDB(dateNaissance)+"', adresse='"+adresse+"', codePostal='"+codePostal+"', numTelephone='"+numTel+"', mail='"+mail+"' WHERE prenom='"+prenom+"' AND nom='"+nom+"'";
			BaseDeDonnees.executeSQL(sql, false);
			closeConnection();
		}
	}

	//METHODES VISITES

	public static List<Visite> searchVisite(String typeVisite, String ville, LocalDateTime dateMin, LocalDateTime dateMax) throws SQLException {
		ArrayList<Visite> listVisite = new ArrayList<Visite>();
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
		if(!"".equals(ville)) {
			if(!whereClause) {
				sql += " WHERE";
				whereClause = true;
			}
			sql += " ville = \""+ville+"\"";
		}
		if(dateMin != null) {
			if(!whereClause) {
				sql += " WHERE";
				whereClause = true;
			}
			sql += " date >= \""+BaseDeDonnees.convertDateTimeForDB(dateMin)+"\"";
		}
		if(dateMax != null) {
			if(!whereClause) {
				sql += " WHERE";
				whereClause = true;
			}
			sql += " date <= \""+BaseDeDonnees.convertDateTimeForDB(dateMax)+"\"";
		}

		ResultSet rs = BaseDeDonnees.executeSQL(sql, true);
		while (rs.next()) {
			listVisite.add(new Visite(rs.getInt("idVisite"),
					rs.getString("typeVisite"),
					rs.getString("ville"),
					BaseDeDonnees.convertDateTimeFromDB(rs.getString("dateVisite")) ,
					Float.parseFloat(rs.getString("prixVisite"))));
		}
		BaseDeDonnees.closeResulSet();
		BaseDeDonnees.closeStatement();
		closeConnection();
		return listVisite;
	}

	public static void ajoutVisite(String type, String ville, LocalDateTime date, float prix) throws SQLException {
		if(!existeVisite(type, ville, date)) {
			initConnection();
			String sql = "INSERT INTO visite (typeVisite, ville, date, prix) VALUES ('"+type+"','"+ville+"','"+BaseDeDonnees.convertDateTimeForDB(date)+"','"+prix+"')";
			BaseDeDonnees.executeSQL(sql, false);
			closeConnection();
		}
	}

	public static void supprimerVisite(String type, String ville, LocalDateTime date) throws SQLException {
		if(!existeVisite(type, ville, date)) {
			initConnection();
			String sql = "DELETE FROM visite WHERE typeVisite='"+type+"' AND ville='"+ville+"' AND date='"+date+"')";
			BaseDeDonnees.executeSQL(sql, false);
			closeConnection();
		}
	}

	public static boolean existeVisite(String typeVisite, String ville, LocalDateTime date) throws SQLException {
		return searchVisite(typeVisite, ville, date, date).size() > 0;
	}

	public static void ajoutVisite(Visite visite) throws SQLException {
		ajoutVisite(visite.getTypeDeVisite(), visite.getVille(), visite.getDateVisite(), visite.getPrix());
	}

	public static void supprimerVisite(Visite visite) throws SQLException {
		supprimerVisite(visite.getTypeDeVisite(), visite.getVille(), visite.getDateVisite());
	}

	//METHODES RESERVATION
	// TODO : Implémenter search, exist, insert, delete, update + version avec objet Reservation
}
