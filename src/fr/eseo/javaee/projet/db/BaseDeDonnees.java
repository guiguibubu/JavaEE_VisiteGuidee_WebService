package fr.eseo.javaee.projet.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import fr.eseo.javaee.projet.db.objet.Reservation;
import fr.eseo.javaee.projet.db.objet.Visite;

public class BaseDeDonnees {

	private static BaseDeDonnees instance;

	private static final String DATE_FORMATTER_STRING = "yyyy-MM-dd";
	private static final String DATE_TIME_FORMATTER_STRING = "yyyy-MM-dd HH:mm:ss";
	private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(DATE_FORMATTER_STRING);
	private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DATE_TIME_FORMATTER_STRING);
	private static final String REGEX_MAIL = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
	private static final String TRUE = "1";
	private static final String FALSE = "0";
	private static final String FORMAT_FLOAT = "%.2f";

	private static final ResourceBundle bundle = ResourceBundle.getBundle("baseDeDonne");
	private static final String PARAM_IP 	= bundle.getString("ip");
	private static final String PARAM_PORT 	= bundle.getString("port");
	private static final String PARAM_NOM 	= bundle.getString("nomBDD");
	private static final String PARAM_LOGIN = bundle.getString("login");
	private static final String PARAM_MDP 	= bundle.getString("mdp");

	private Connection connect;
	private Statement stat;
	private ResultSet rs;

	// INIT LA BASE DE DONNEES

	private BaseDeDonnees() {
		try {
			DriverManager.registerDriver(new com.mysql.jdbc.Driver());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static BaseDeDonnees getInstance() throws SQLException {
		initBDD();
		return instance;
	}

	public static void initBDD() {
		if(!isInitialized()) {
			instance = new BaseDeDonnees();
		}
	}

	//GESTION DE LA CONNEXION
	public static void openConnection() throws SQLException {
		if(instance == null) {
			initBDD();
		}
		if(instance.connect != null && !instance.connect.isClosed()) {
			closeConnection();
		}
		String url = "jdbc:mysql://"+PARAM_IP+"/"+PARAM_NOM+"?user="+PARAM_LOGIN+"&password="+PARAM_MDP;
		instance.connect = DriverManager.getConnection(url);
	}

	public static void closeConnection() throws SQLException {
		if(instance.connect != null){instance.connect.close();}
	}

	//GESTION REQUETE SQL
	public static ResultSet executeSQL(String sql, boolean withReturn) throws SQLException {
		try{
			instance.stat = instance.connect.createStatement();
			if(sql != null && sql.trim().startsWith("INSERT")) {
				instance.stat.execute(sql,Statement.RETURN_GENERATED_KEYS);
				instance.rs = instance.stat.getGeneratedKeys();
			}
			else {
				instance.stat.execute(sql);
				instance.rs = instance.stat.getResultSet();
			}
		} finally {
			if(!(instance.stat == null || instance.rs == null || withReturn)) {
				closeResulSet();
				closeStatement();
			}
		}
		return instance.rs;
	}

	public static void cleanTable(String table) throws SQLException {
		openConnection();
		String sql = "DELETE FROM "+table;
		executeSQL(sql, false);
		closeResulSet();
		closeStatement();
		closeConnection();
	}

	public static void closeResulSet() throws SQLException {
		if(instance.rs != null) {instance.rs.close();}
	}

	public static void closeStatement() throws SQLException {
		if(instance.stat != null) {instance.stat.close();}
	}

	//CONVERTISSEURS POUR INTRODUIRE DES DONNEES
	public static String convertForDB(LocalDate date) {
		String dateSQL = date.format(dateFormatter);
		return dateSQL.substring(dateSQL.length() - DATE_FORMATTER_STRING.length());
	}

	public static String convertForDB(LocalDateTime date) {
		return date.format(dateTimeFormatter);
	}

	public static String convertForDB(boolean bool) {
		return (bool) ? TRUE : FALSE;
	}

	public static String convertForDB(float num) {
		return String.format(FORMAT_FLOAT, num).replace(",", ".");
	}

	public static String convertForDB(int num) {
		return String.valueOf(num);
	}

	public static String convertForDB(Visite visite) {
		if(visite == null) {visite = new Visite();}
		return convertForDB(visite.getCodeVisite());
	}

	public static String convertForDB(Reservation reservation) {
		if(reservation == null) {reservation = new Reservation();}
		return convertForDB(reservation.getCodeReservation());
	}

	// CONVERTISSEURS POUR EXTRAIRE DES DONNEES
	public static LocalDate convertDateFromDB(String dateDB) {
		return LocalDate.parse(dateDB, dateFormatter);
	}

	public static LocalDateTime convertDateTimeFromDB(String dateDB) {
		return LocalDateTime.parse(dateDB.substring(0, DATE_TIME_FORMATTER_STRING.length()), dateTimeFormatter);
	}

	public static boolean convertBooleanFromDB(String boolDB) {
		return TRUE.equals(boolDB);
	}

	public static float convertFloatFromDB(String numDB) {
		return Float.parseFloat(numDB);
	}

	public static int convertIntFromDB(String numDB) {
		return Integer.parseInt(numDB);
	}

	//

	public static boolean isEmailGoodFormat(String email) {
		return email.matches(REGEX_MAIL);
	}

	//GETTER-SETTER
	public static boolean isInitialized() {return instance != null;}

	public static Connection getConnect() {return instance.connect;}

	public static Statement getStat() {return instance.stat;}

	public static ResultSet getRs() {return instance.rs;}

}