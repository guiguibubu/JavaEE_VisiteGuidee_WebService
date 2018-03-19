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

	private static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	private static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	private static String regexEmail = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";

	private static ResourceBundle bundle = ResourceBundle.getBundle("baseDeDonne");
	private static String ip 		= bundle.getString("ip");
	private static String port 		= bundle.getString("port");
	private static String nomBDD 	= bundle.getString("nomBDD");
	private static String login 	= bundle.getString("login");
	private static String mdp 		= bundle.getString("mdp");

	private Connection connect;
	private Statement stat;
	private ResultSet rs;

	// INIT LA BASE DE DONNEES

	private BaseDeDonnees() throws SQLException {
		DriverManager.registerDriver(new com.mysql.jdbc.Driver());
	}

	public static BaseDeDonnees getInstance() throws SQLException {
		if(instance == null) {
			instance = new BaseDeDonnees();
		}
		return instance;
	}

	//GESTION DE LA CONNEXION
	protected void openConnection() throws SQLException {
		if(this.connect != null && !this.connect.isClosed()) {
			this.closeConnection();
		}
		String url = "jdbc:mysql://"+ip+"/"+nomBDD+"?user="+login+"&password="+mdp;
		this.connect = DriverManager.getConnection(url);
	}

	protected void closeConnection() throws SQLException {
		if(this.connect != null){this.connect.close();}
	}

	//GESTION REQUETE SQL
	protected ResultSet executeSQL(String sql, boolean withReturn) throws SQLException {
		try{
			this.stat = this.connect.createStatement();
			this.stat.execute(sql);
			this.rs = this.stat.getResultSet();
		} finally {
			if(!(this.stat == null || this.rs == null || withReturn)) {
				this.closeResulSet();
				this.closeStatement();
			}
		}
		return this.rs;
	}

	protected void closeResulSet() throws SQLException {
		if(this.rs != null) {this.rs.close();}
	}

	protected void closeStatement() throws SQLException {
		if(this.stat != null) {this.stat.close();}
	}

	//GESTION DATE
	public String convertDateForDB(LocalDate date) {
		return date.format(dateFormatter);
	}

	public String convertDateTimeForDB(LocalDateTime date) {
		return date.format(dateTimeFormatter);
	}

	public LocalDate convertDateFromDB(String dateDB) {
		return LocalDate.parse(dateDB, dateFormatter);
	}

	public LocalDateTime convertDateTimeFromDB(String dateDB) {
		return LocalDateTime.parse(dateDB, dateFormatter);
	}

	public boolean isEmailGoodFormat(String email) {
		return email.matches(regexEmail);
	}

	//GETTER-SETTER
	public Connection getConnect() {return this.connect;}

	public Statement getStat() {return this.stat;}

	public ResultSet getRs() {return this.rs;}

}