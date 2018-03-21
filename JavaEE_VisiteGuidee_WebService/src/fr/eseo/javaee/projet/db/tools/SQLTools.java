package fr.eseo.javaee.projet.db.tools;

import java.util.List;

public class SQLTools {

	public static String selectSQL(String table, List<String> listColNames, List<String> listArgs) {
		String sql = null;
		if(listColNames != null && listArgs != null && listColNames.size() == listArgs.size()) {
			sql = "SELECT * FROM "+table;
			if(listColNames.size() > 0) {
				sql += " WHERE";
				for (int i = 0; i<listColNames.size()-1; i++) {
					sql += " "+listColNames.get(i)+" = '"+listArgs.get(i)+"' AND";
				}
				sql += " "+listColNames.get(listColNames.size()-1)+" = '"+listArgs.get(listColNames.size()-1)+"'";
			}
		}
		return sql;
	}

	public static String insertSQL(String table, List<String> listColNames, List<String> listArgs) {
		String sql = null;
		if(listColNames != null && listArgs != null && listColNames.size() == listArgs.size() && listColNames.size() > 0) {
			//Insert et nom colonnes
			sql = "INSERT INTO "+table+" (";
			for (int i = 0; i<listColNames.size()-1; i++) {
				sql += listColNames.get(i)+",";
			}
			sql += listColNames.get(listColNames.size()-1)+") ";
			// values et valeurs
			sql += "VALUES (";
			for (int i = 0; i<listArgs.size()-1; i++) {
				sql += "'"+listArgs.get(i)+"',";
			}
			sql += "'"+listArgs.get(listArgs.size()-1)+"')";
		}
		return sql;
	}

	public static String deleteSQL(String table, List<String> listColNames, List<String> listArgs) {
		String sql = null;
		if(listColNames != null && listArgs != null && listColNames.size() == listArgs.size()) {
			sql = "DELETE FROM "+table;
			if(listColNames.size() > 0) {
				sql += " WHERE";
				for (int i = 0; i<listColNames.size()-1; i++) {
					sql += " "+listColNames.get(i)+" = '"+listArgs.get(i)+"' AND";
				}
				sql += " "+listColNames.get(listColNames.size()-1)+" = '"+listArgs.get(listColNames.size()-1)+"'";
			}
		}
		return sql;
	}

	public static String updateSQL(String table, List<String> listColNames, List<String> listArgs, List<String> listColNamesWhere, List<String> listArgsWhere) {
		String sql = null;
		boolean listeValuesOK = listColNames != null && listArgs != null && listColNames.size() == listArgs.size() && listColNames.size() > 0;
		boolean listeWhereTailleOK = (listColNamesWhere != null && listArgsWhere != null && listColNamesWhere.size() == listArgsWhere.size());
		boolean listeWhereNull = listColNamesWhere == null || listArgsWhere ==null;
		if(listeValuesOK && (listeWhereTailleOK || listeWhereNull)) {
			// valeur d'update
			sql = "UPDATE "+table+" SET";
			for (int i = 0; i<listColNames.size()-1; i++) {
				sql += " "+listColNames.get(i)+" = '"+listArgs.get(i)+"',";
			}
			sql += " "+listColNames.get(listColNames.size()-1)+" = '"+listArgs.get(listColNames.size()-1)+"'";
			// clause where
			if(!listeWhereNull && listArgsWhere.size() > 0) {
				sql += " WHERE";
				for (int i = 0; i<listColNamesWhere.size()-1; i++) {
					sql += " "+listColNamesWhere.get(i)+" = '"+listArgsWhere.get(i)+"' AND";
				}
				sql += " "+listColNamesWhere.get(listColNamesWhere.size()-1)+" = '"+listArgsWhere.get(listArgsWhere.size()-1)+"'";
			}
		}
		return sql;
	}

	public static String updateSQL(String table, List<String> listColNames, List<String> listArgs) {
		return updateSQL(table, listColNames, listArgs, null, null);
	}
}
