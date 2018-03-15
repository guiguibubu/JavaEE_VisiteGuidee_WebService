package fr.eseo.javaee.projet.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class BaseDeDonnees {

	private static BaseDeDonnees instance;

	private static ResourceBundle bundle = ResourceBundle.getBundle("baseDeDonne");
	private static String ip 	= bundle.getString("ip");
	private static String port 	= bundle.getString("port");
	private static String site 	= bundle.getString("site");
	private static String login = bundle.getString("login");
	private static String mdp 	= bundle.getString("mdp");

	private Connection connect;

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
	protected void openConnection(String table) throws SQLException {
		if(!this.connect.isClosed()) {
			this.closeConnection();
		}
		String url = "jdbc:mysql://"+ip+"/"+table+"?user="+login+"&password="+mdp;
		this.connect = DriverManager.getConnection(url);
	}

	protected void closeConnection() throws SQLException {
		this.connect.close();
	}

	//GESTION REQUETE SQL
	protected ResultSet executeSQL(String sql, boolean withReturn) throws SQLException {
		ResultSet rs;
		try(Statement stat = this.connect.createStatement()){
			stat.execute(sql);
			rs = stat.getResultSet();
		}
		if(!withReturn) {
			this.closeResulSet(rs);
		}
		return rs;
	}

	protected void closeResulSet(ResultSet rs) throws SQLException {
		rs.close();
	}

	//GETTER-SETTER
	public Connection getConnect() {return this.connect;}

}