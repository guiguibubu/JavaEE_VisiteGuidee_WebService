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

public class BaseDeDonnees {

	private static BaseDeDonnees instance;

	private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	private static final String regexEmail = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
	private static final String TRUE = "1";
	private static final String FALSE = "0";
	private static final String FORMAT_FLOAT = "%.2f";

	private static final ResourceBundle bundle = ResourceBundle.getBundle("baseDeDonne");
	private static final String ip 			= bundle.getString("ip");
	private static final String port 		= bundle.getString("port");
	private static final String nomBDD 		= bundle.getString("nomBDD");
	private static final String login 		= bundle.getString("login");
	private static final String mdp 		= bundle.getString("mdp");

	private Connection connect;
	private Statement stat;
	private ResultSet rs;

	// INIT LA BASE DE DONNEES

	private BaseDeDonnees() throws SQLException {
		DriverManager.registerDriver(new com.mysql.jdbc.Driver());
	}

	public static BaseDeDonnees getInstance() throws SQLException {
		initBDD();
		return instance;
	}

	public static void initBDD() throws SQLException {
		if(!isInitialized()) {
			instance = new BaseDeDonnees();
		}
	}

	//GESTION DE LA CONNEXION
	protected static void openConnection() throws SQLException {
		if(instance.connect != null && !instance.connect.isClosed()) {
			closeConnection();
		}
		String url = "jdbc:mysql://"+ip+"/"+nomBDD+"?user="+login+"&password="+mdp;
		instance.connect = DriverManager.getConnection(url);
	}

	protected static void closeConnection() throws SQLException {
		if(instance.connect != null){instance.connect.close();}
	}

	//GESTION REQUETE SQL
	protected static ResultSet executeSQL(String sql, boolean withReturn) throws SQLException {
		try{
			instance.stat = instance.connect.createStatement();
			instance.stat.execute(sql);
			instance.rs = instance.stat.getResultSet();
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
	protected static void closeResulSet() throws SQLException {
		if(instance.rs != null) {instance.rs.close();}
	}

	protected static void closeStatement() throws SQLException {
		if(instance.stat != null) {instance.stat.close();}
	}

	//CONVERTISSEURS
	public static String convertDateForDB(LocalDate date) {
		return date.format(dateFormatter);
	}

	public static String convertDateTimeForDB(LocalDateTime date) {
		return date.format(dateTimeFormatter);
	}

	public static String convertBooleanForDB(boolean bool) {
		return (bool) ? TRUE : FALSE;
	}

	public static String convertFloatForDB(float num) {
		return String.format(FORMAT_FLOAT, num);
	}

	public static LocalDate convertDateFromDB(String dateDB) {
		return LocalDate.parse(dateDB, dateFormatter);
	}

	public static LocalDateTime convertDateTimeFromDB(String dateDB) {
		return LocalDateTime.parse(dateDB, dateFormatter);
	}

	public static boolean convertBooleanFromDB(String boolDB) {
		return TRUE.equals(boolDB);
	}

	public static float convertFloatFromDB(String numDB) {
		return Float.parseFloat(numDB);
	}

	public static boolean isEmailGoodFormat(String email) {
		return email.matches(regexEmail);
	}

	//GETTER-SETTER
	public static boolean isInitialized() {return instance != null;}

	public static Connection getConnect() {return instance.connect;}

	public static Statement getStat() {return instance.stat;}

	public static ResultSet getRs() {return instance.rs;}

}