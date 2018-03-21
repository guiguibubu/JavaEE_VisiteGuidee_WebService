package fr.eseo.javaee.projet.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import fr.eseo.javaee.projet.db.objet.Client;
import fr.eseo.javaee.projet.db.objet.Visite;
import fr.eseo.javaee.projet.db.tools.SQLTools;

public class GestionDB {

	private GestionDB() {}

	private static void initConnection() throws SQLException {
		BaseDeDonnees.initBDD();
		BaseDeDonnees.openConnection();
	}

	private static void closeConnection() throws SQLException {
		BaseDeDonnees.closeConnection();
	}

	//METHODES CLIENT
	// TODO : méthodes avec objet Client + search
	public static Client searchClient(String prenom, String nom) throws SQLException {
		Client client = new Client(nom, prenom);
		// Elements de recherche
		List<String> listeNomArgs = new ArrayList<String>();
		List<String> listeArgs = new ArrayList<String>();
		listeNomArgs.add(Client.NOM_COL_NOM);
		listeNomArgs.add(Client.NOM_COL_PRENOM);
		listeArgs.add(nom);
		listeArgs.add(prenom);
		// Element de résultat
		List<String> listeNouveauAttributs = new ArrayList<String>();

		initConnection();
		String sql = SQLTools.selectSQL(client.getNomTable(), listeNomArgs, listeArgs);
		ResultSet rs = BaseDeDonnees.executeSQL(sql, true);
		while(rs.next()) {
			for(String nomCol : client.getListeNomAttributs()) {
				listeNouveauAttributs.add(rs.getString(nomCol));
			}
			client.setListeAttributs(listeNouveauAttributs);
		}
		BaseDeDonnees.closeResulSet();
		BaseDeDonnees.closeStatement();
		closeConnection();
		return client;
	}

	public static boolean existeClient(String prenom, String nom) throws SQLException {
		return searchClient(prenom, nom).getIdClient() != 0;
	}

	public static void ajoutClient(String prenom, String nom) throws SQLException {
		if(!existeClient(prenom, nom)) {
			initConnection();
			Client client = new Client(nom, prenom);
			String sql = SQLTools.insertSQL(client.getNomTable(), client.getListeNomAttributs(), client.getListeAttributs());
			BaseDeDonnees.executeSQL(sql, false);
			closeConnection();
		}
	}

	public static void supprimeClient(String prenom, String nom) throws SQLException {
		Client client = searchClient(prenom, nom);
		// Elements de recherche
		List<String> listeNomArgs = new ArrayList<String>();
		List<String> listeArgs = new ArrayList<String>();
		listeNomArgs.add(Client.NOM_COL_ID);
		listeArgs.add(String.valueOf(client.getIdClient()));
		if(client.getIdClient() != 0) {
			initConnection();
			String sql = SQLTools.deleteSQL(Client.NOM_TABLE, listeNomArgs, listeArgs);
			BaseDeDonnees.executeSQL(sql, false);
			closeConnection();
		}
	}

	public static void updateClient(String prenom, String nom, LocalDate dateNaissance, String adresse, int codePostal, String pays, int numTel, String mail) throws SQLException {
		Client client = searchClient(prenom, nom);
		// Elements de recherche
		List<String> listColNamesWhere = new ArrayList<String>();
		List<String> listArgsWhere = new ArrayList<String>();
		listColNamesWhere.add(Client.NOM_COL_ID);
		listArgsWhere.add(String.valueOf(client.getIdClient()));
		if(client.getIdClient() != 0) {
			initConnection();
			client.setDateNaissance(dateNaissance);
			client.setAdresse(adresse);
			client.setCodePostal(codePostal);
			client.setPays(pays);
			client.setNumTelephone(numTel);
			client.setMail(mail);
			String sql = SQLTools.updateSQL(Client.NOM_TABLE, client.getListeNomAttributs(), client.getListeAttributs(), listColNamesWhere, listArgsWhere);
			BaseDeDonnees.executeSQL(sql, false);
			closeConnection();
		}
	}

	//METHODES VISITES

	public static List<Visite> searchVisite(String typeVisite, String ville, LocalDateTime dateMin, LocalDateTime dateMax) throws SQLException {
		ArrayList<Visite> listVisite = new ArrayList<>();
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
		return !searchVisite(typeVisite, ville, date, date).isEmpty();
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
