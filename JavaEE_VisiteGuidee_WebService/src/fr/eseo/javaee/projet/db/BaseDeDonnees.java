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

	//GETTER-SETTER
	public Connection getConnect() {return this.connect;}

	public Statement getStat() {return this.stat;}

	public ResultSet getRs() {return this.rs;}

}