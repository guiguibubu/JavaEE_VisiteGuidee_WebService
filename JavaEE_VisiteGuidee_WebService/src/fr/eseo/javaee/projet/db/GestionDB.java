package fr.eseo.javaee.projet.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import fr.eseo.javaee.projet.db.objet.Client;
import fr.eseo.javaee.projet.db.objet.Coordonnee;
import fr.eseo.javaee.projet.db.objet.Reservation;
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
	public static Client searchClient(String prenom, String nom) throws SQLException {
		Client client = new Client(nom, prenom);
		// Elements de recherche
		List<String> listeNomArgs = new ArrayList<>();
		List<String> listeArgs = new ArrayList<>();
		listeNomArgs.add(Client.NOM_COL_NOM);
		listeNomArgs.add(Client.NOM_COL_PRENOM);
		listeArgs.add(nom);
		listeArgs.add(prenom);
		// Element de résultat
		List<String> listeNouveauAttributs = new ArrayList<>();

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
		supprimeClient(searchClient(prenom, nom).getIdClient());
	}

	public static void supprimeClient(int idClient) throws SQLException {
		if(idClient != 0) {
			initConnection();
			String sql = SQLTools.deleteSQL(Client.NOM_TABLE, Client.NOM_COL_ID, BaseDeDonnees.convertForDB(idClient));
			BaseDeDonnees.executeSQL(sql, false);
			closeConnection();
		}
	}

	public static void updateClient(String prenom, String nom, LocalDate dateNaissance, Coordonnee coordonnee) throws SQLException {
		Client client = searchClient(prenom, nom);
		if(client.getIdClient() != 0) {
			initConnection();
			client.setDateNaissance(dateNaissance);
			client.setCoordonnee(coordonnee);
			String sql = SQLTools.updateSQL(Client.NOM_TABLE, client.getListeNomAttributs(), client.getListeAttributs(), Client.NOM_COL_ID, BaseDeDonnees.convertForDB(client.getIdClient()));
			BaseDeDonnees.executeSQL(sql, false);
			closeConnection();
		}
	}

	//METHODES VISITES

	public static List<Visite> searchVisite(String typeVisite, String ville, LocalDateTime dateMin, LocalDateTime dateMax) throws SQLException {
		List<Visite> listVisite = new ArrayList<>();
		initConnection();
		List<String> listClausesWhere = new ArrayList<>();
		if(typeVisite != null && !typeVisite.trim().isEmpty()) {
			listClausesWhere.add(SQLTools.convertIntoWhereClause(Visite.NOM_COL_TYPE, typeVisite));
		}
		if(ville != null && !ville.trim().isEmpty()) {
			listClausesWhere.add(SQLTools.convertIntoWhereClause(Visite.NOM_COL_VILLE, ville));
		}
		if(dateMin != null) {
			listClausesWhere.add(SQLTools.stickElementWithLinkAndGuillemet(Visite.NOM_COL_DATE, BaseDeDonnees.convertForDB(dateMin), " >= ", "'"));
		}
		if(dateMax != null) {
			listClausesWhere.add(SQLTools.stickElementWithLinkAndGuillemet(Visite.NOM_COL_DATE, BaseDeDonnees.convertForDB(dateMax), " <= ", "'"));
		}
		String sql = SQLTools.selectSQL(Visite.NOM_TABLE, listClausesWhere);
		ResultSet rs = BaseDeDonnees.executeSQL(sql, true);
		while (rs.next()) {
			listVisite.add(new Visite(rs.getInt(Visite.NOM_COL_ID),
					rs.getString(Visite.NOM_COL_TYPE),
					rs.getString(Visite.NOM_COL_VILLE),
					BaseDeDonnees.convertDateTimeFromDB(rs.getString(Visite.NOM_COL_DATE)) ,
					Float.parseFloat(rs.getString(Visite.NOM_COL_PRIX))));
		}
		BaseDeDonnees.closeResulSet();
		BaseDeDonnees.closeStatement();
		closeConnection();
		return listVisite;
	}

	public static List<Visite> searchVisite(String typeVisite, String ville, LocalDateTime date) throws SQLException{
		return searchVisite(typeVisite, ville, date, date);
	}

	public static List<Visite> searchVisite(Visite visite) throws SQLException{
		List<Visite> listVisite = new ArrayList<>();
		if(visite != null) {
			listVisite = searchVisite(visite.getTypeDeVisite(), visite.getVille(), visite.getDateVisite());
		}
		return listVisite;
	}

	public static Visite searchVisiteById(int idVisite) throws SQLException {
		Visite visite = new Visite();
		initConnection();
		String sql = SQLTools.selectSQL(Visite.NOM_TABLE, Visite.NOM_COL_ID, BaseDeDonnees.convertForDB(idVisite));
		ResultSet rs = BaseDeDonnees.executeSQL(sql, true);
		while (rs.next()) {
			visite = new Visite(rs.getInt(Visite.NOM_COL_ID),
					rs.getString(Visite.NOM_COL_TYPE),
					rs.getString(Visite.NOM_COL_VILLE),
					BaseDeDonnees.convertDateTimeFromDB(rs.getString(Visite.NOM_COL_DATE)) ,
					Float.parseFloat(rs.getString(Visite.NOM_COL_PRIX)));
		}
		BaseDeDonnees.closeResulSet();
		BaseDeDonnees.closeStatement();
		closeConnection();
		return visite;
	}

	public static boolean existeVisite(String typeVisite, String ville, LocalDateTime date) throws SQLException {
		return !searchVisite(typeVisite, ville, date, date).isEmpty();
	}

	public static boolean existeVisite(Visite visite) throws SQLException {
		return !searchVisite(visite).isEmpty();
	}

	public static boolean existeVisiteById(int idVisite) throws SQLException {
		return searchVisiteById(idVisite).getCodeVisite() != 0;
	}

	public static void ajoutVisite(String type, String ville, LocalDateTime date, float prix) throws SQLException {
		if(!existeVisite(type, ville, date)) {
			initConnection();
			Visite visite = new Visite(type, ville, date, prix);
			String sql = SQLTools.insertSQL(visite.getNomTable(), visite.getListeNomAttributs(), visite.getListeAttributs());
			BaseDeDonnees.executeSQL(sql, false);
			closeConnection();
		}
	}

	public static void ajoutVisite(Visite visite) throws SQLException {
		ajoutVisite(visite.getTypeDeVisite(), visite.getVille(), visite.getDateVisite(), visite.getPrix());
	}

	public static void supprimerVisiteById(int idVisite) throws SQLException {
		initConnection();
		String sql = SQLTools.deleteSQL(Visite.NOM_TABLE, Visite.NOM_COL_ID, BaseDeDonnees.convertForDB(idVisite));
		BaseDeDonnees.executeSQL(sql, false);
		closeConnection();
	}

	public static void supprimerVisiteById(Visite visite) throws SQLException {
		supprimerVisiteById(visite.getCodeVisite());
	}

	public static void updateVisiteById(int idVisite, String type, String ville, LocalDateTime date, float prix) throws SQLException {
		Visite visite = new Visite(idVisite);
		if(idVisite != 0) {
			initConnection();
			visite.setTypeDeVisite(type);
			visite.setVille(ville);
			visite.setDateVisite(date);
			visite.setPrix(prix);
			String sql = SQLTools.updateSQL(Visite.NOM_TABLE, visite.getListeNomAttributs(), visite.getListeAttributs(), Visite.NOM_COL_ID, BaseDeDonnees.convertForDB(visite.getCodeVisite()));
			BaseDeDonnees.executeSQL(sql, false);
			closeConnection();
		}
	}

	public static void updateVisiteById(Visite visite) throws SQLException {
		if(visite != null) {
			updateVisiteById(visite.getCodeVisite(), visite.getTypeDeVisite(), visite.getVille(), visite.getDateVisite(), visite.getPrix());
		}
	}

	//METHODES RESERVATION

	public static List<Reservation> searchReservation(Visite visite, Client client, int nombrePersonnes, boolean paiementEffectue) throws SQLException {
		List<Reservation> listReservation = new ArrayList<>();
		initConnection();
		Reservation reservation = new Reservation(visite, client, nombrePersonnes, paiementEffectue);
		String sql = SQLTools.selectSQL(Visite.NOM_TABLE, reservation.getListeNomAttributs(), reservation.getListeAttributs());
		ResultSet rs = BaseDeDonnees.executeSQL(sql, true);
		while (rs.next()) {
			listReservation.add(new Reservation(rs.getInt(Reservation.NOM_COL_ID),
					new Visite(rs.getInt(Reservation.NOM_COL_VISITE)),
					new Client(rs.getInt(Reservation.NOM_COL_CLIENT)),
					rs.getInt(Reservation.NOM_COL_PLACE),
					BaseDeDonnees.convertBooleanFromDB(rs.getString(Reservation.NOM_COL_PAIEMENT))));
		}
		BaseDeDonnees.closeResulSet();
		BaseDeDonnees.closeStatement();
		closeConnection();
		return listReservation;
	}

	public static List<Reservation> searchReservation(Reservation reservation) throws SQLException {
		return searchReservation(reservation.getVisite(), reservation.getClient(), reservation.getNombrePersonnes(), reservation.isPaiementEffectue());
	}

	public static Reservation searchReservationById(int idReservation) throws SQLException {
		Reservation reservation = new Reservation();
		initConnection();
		String sql = SQLTools.selectSQL(Visite.NOM_TABLE, Reservation.NOM_COL_ID, BaseDeDonnees.convertForDB(idReservation));
		ResultSet rs = BaseDeDonnees.executeSQL(sql, true);
		while (rs.next()) {
			reservation = new Reservation(rs.getInt(Reservation.NOM_COL_ID),
					new Visite(rs.getInt(Reservation.NOM_COL_VISITE)),
					new Client(rs.getInt(Reservation.NOM_COL_CLIENT)),
					rs.getInt(Reservation.NOM_COL_PLACE),
					BaseDeDonnees.convertBooleanFromDB(rs.getString(Reservation.NOM_COL_PAIEMENT)));
		}
		BaseDeDonnees.closeResulSet();
		BaseDeDonnees.closeStatement();
		closeConnection();
		return reservation;
	}

	public static void ajoutReservation(Visite visite, Client client, int nombrePersonnes, boolean paiementEffectue) throws SQLException {
		if(!existeReservation(visite, client, nombrePersonnes, paiementEffectue)) {
			initConnection();
			Reservation reservation = new Reservation(visite, client, nombrePersonnes, paiementEffectue);
			String sql = SQLTools.insertSQL(reservation.getNomTable(), reservation.getListeNomAttributs(), reservation.getListeAttributs());
			BaseDeDonnees.executeSQL(sql, false);
			closeConnection();
		}
	}

	public static void ajoutReservation(Reservation reservation) throws SQLException {
		if(reservation != null) {
			ajoutReservation(reservation.getVisite(), reservation.getClient(), reservation.getNombrePersonnes(), reservation.isPaiementEffectue());
		}
	}

	public static void supprimerReservationById(int idReservation) throws SQLException {
		initConnection();
		String sql = SQLTools.deleteSQL(Reservation.NOM_TABLE, Reservation.NOM_COL_ID, BaseDeDonnees.convertForDB(idReservation));
		BaseDeDonnees.executeSQL(sql, false);
		closeConnection();
	}

	public static void supprimerReservationById(Reservation reservation) throws SQLException {
		if(reservation != null) {
			supprimerReservationById(reservation.getCodeReservation());
		}
	}

	public static void updateReservationById(int idReservation, Visite visite, Client client, int nombrePersonnes, boolean paiementEffectue) throws SQLException {
		Reservation reservation = new Reservation(idReservation);
		if(idReservation != 0) {
			initConnection();
			reservation.setVisite(visite);
			reservation.setClient(client);
			reservation.setNombrePersonnes(nombrePersonnes);
			reservation.setPaiementEffectue(paiementEffectue);
			String sql = SQLTools.updateSQL(Reservation.NOM_TABLE, reservation.getListeNomAttributs(), reservation.getListeAttributs(), Reservation.NOM_COL_ID, BaseDeDonnees.convertForDB(reservation.getCodeReservation()));
			BaseDeDonnees.executeSQL(sql, false);
			closeConnection();
		}
	}

	public static void updateReservationById(Reservation reservation) throws SQLException {
		updateReservationById(reservation.getCodeReservation(), reservation.getVisite(), reservation.getClient(), reservation.getNombrePersonnes(), reservation.isPaiementEffectue());
	}

	public static boolean existeReservation(Visite visite, Client client, int nombrePersonnes, boolean paiementEffectue) throws SQLException {
		return !searchReservation(visite, client, nombrePersonnes, paiementEffectue).isEmpty();
	}

	public static boolean existeReservation(Reservation reservation) throws SQLException {
		return (reservation == null) ? false : existeReservation(reservation.getVisite(), reservation.getClient(), reservation.getNombrePersonnes(), reservation.isPaiementEffectue());
	}

	public static boolean existeReservationById(int idReservation) throws SQLException {
		return searchReservationById(idReservation).getCodeReservation() != 0;
	}

	public static boolean existeReservationById(Reservation reservation) throws SQLException {
		return (reservation == null) ? false : existeReservationById(reservation.getCodeReservation());
	}
}