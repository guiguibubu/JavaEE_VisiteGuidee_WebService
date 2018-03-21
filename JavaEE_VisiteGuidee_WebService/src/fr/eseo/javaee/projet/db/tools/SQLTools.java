package fr.eseo.javaee.projet.db.tools;

import java.util.ArrayList;
import java.util.List;

public class SQLTools {

	// METHODES SERVANT DE CONVERTISSEURS UTILES
	public static String deleteSpaceAndPrefixBefore(String text, String prefix) {
		if(text != null) {
			deleteSpaceBefore(text);
			if(prefix != null && text.startsWith(prefix)) {
				text = text.substring(prefix.length(), text.length());
			}
			deleteSpaceBefore(text);
		}
		return text;
	}

	public static String deleteSpaceBefore(String text) {
		if(text != null) {
			while (text.startsWith(" ")) {
				text = text.substring(1, text.length());
			}
		}
		return text;
	}

	public static String stickElementWithLink(String textLeft, String textRight, String link) {
		String text = "";
		if(textLeft != null && textLeft != null && link != null) {
			text += textLeft + link + textRight;
		}
		return text;
	}

	public static String stickElementWithLink(List<String> list, String link) {
		String text = "";
		if(list != null && link != null && list.size()>1) {
			for(int i = 0; i<list.size(); i++) {
				if(list.get(i) != null && !list.get(i).trim().isEmpty()) {
					text += list.get(i);
					if(i<list.size()-1) {
						text += link;
					}
				}
			}
			if(text.endsWith(link)) {
				text = text.substring(0, text.length()-link.length());
			}
		}
		return text;
	}

	public static String stickElementWithLinkAndGuillemet(String textLeft, String textRight, String link, String guillemet) {
		String text = "";
		if(link != null && textLeft != null && textRight != null) {
			text += textLeft+link+guillemet+textRight+guillemet;
		}
		return text;
	}

	public static List<String> stickElementWithLinkAndGuillemet(List<String> listLeft, List<String> listRight, String link, String guillement) {
		List<String> list = new ArrayList<String>();
		boolean listesNotNull = listLeft != null && listRight != null;
		boolean listesSameSize = (listesNotNull) ? listLeft.size() == listRight.size() : false;
		if(link != null && listesSameSize && listLeft.size()>0) {
			for(int i = 0; i<listLeft.size(); i++) {
				boolean elementsNotNull = listLeft.get(i) != null && listRight.get(i) != null;
				boolean elementsNotEmpty = (elementsNotNull) ? !listLeft.get(i).trim().isEmpty() && !listRight.get(i).trim().isEmpty() : false;
				if(elementsNotEmpty) {
					list.add(stickElementWithLinkAndGuillemet(listLeft.get(i), listRight.get(i), link, "'"));
				}
			}
		}
		return list;
	}

	public static List<String> convertIntoListOfWhereOrSetClauses(List<String> listColNames, List<String> listArgs){
		return stickElementWithLinkAndGuillemet(listColNames, listArgs, " = ", "'");
	}

	public static String convertIntoWhereClauses(List<String> listClausesWhere){
		return stickElementWithLink(listClausesWhere, " AND ");
	}

	public static String convertIntoWhereClause(String textLeft, String textRight){
		return stickElementWithLinkAndGuillemet(textLeft, textRight, " = ", "'");
	}

	//METHODES SELECT
	public static String selectSQL(String table, List<String> listColNames, List<String> listArgs) {
		return selectSQL(table, convertIntoListOfWhereOrSetClauses(listColNames, listArgs));
	}

	public static String selectSQL(String table, String colName, String arg) {
		List<String> listColNames = new ArrayList<String>();
		List<String> listArgs = new ArrayList<String>();
		listColNames.add(colName);
		listArgs.add(arg);
		return selectSQL(table, listColNames, listArgs);
	}

	public static String selectSQL(String table, List<String> listClausesWhere) {
		return selectSQL(table, convertIntoWhereClauses(listClausesWhere));
	}

	public static String selectSQL(String table, String clausesWhere) {
		String sql = "";
		if(table != null) {
			sql = "SELECT * FROM "+table;
			if(!clausesWhere.trim().isEmpty()) {
				sql += " WHERE "+deleteSpaceAndPrefixBefore(clausesWhere, "WHERE");
			}
		}
		return sql;
	}

	// METHODES INSERT
	public static String insertSQL(String table, List<String> listColNames, List<String> listArgs) {
		String sql = "";
		if(listColNames != null && listArgs != null && listColNames.size() == listArgs.size() && listColNames.size() > 0) {
			//Insert et nom colonnes
			sql = "INSERT INTO "+table+" ("+stickElementWithLink(listColNames, ",")+") ";
			sql += "VALUES ("+stickElementWithLink(listArgs, ",")+")";
		}
		return sql;
	}

	//METHODE DELETE
	public static String deleteSQL(String table, List<String> listColNames, List<String> listArgs) {
		return deleteSQL(table, convertIntoListOfWhereOrSetClauses(listColNames, listArgs));
	}

	public static String deleteSQL(String table, String colName, String arg) {
		return deleteSQL(table, convertIntoWhereClause(colName, arg));
	}

	public static String deleteSQL(String table, List<String> listClausesWhere) {
		return deleteSQL(table, convertIntoWhereClauses(listClausesWhere));
	}

	public static String deleteSQL(String table, String clausesWhere) {
		String sql = "";
		//Sécurité pour ne pas supprimer toutes les valeurs en une seule fois
		if(!clausesWhere.trim().isEmpty()) {
			sql = "DELETE FROM "+table;
			sql += " WHERE"+clausesWhere;
		}
		return sql;
	}

	//METHODE UPDATE
	public static String updateSQL(String table, List<String> listColNames, List<String> listArgs, List<String> listColNamesWhere, List<String> listArgsWhere) {
		return updateSQL(table, listColNames, listArgs, convertIntoListOfWhereOrSetClauses(listColNamesWhere, listArgsWhere));
	}

	public static String updateSQL(String table, List<String> listColNames, List<String> listArgs, String colNameWhere, String argWhere) {
		return updateSQL(table, listColNames, listArgs, convertIntoWhereClause(colNameWhere, argWhere));
	}

	public static String updateSQL(String table, List<String> listColNames, List<String> listArgs, List<String> listClausesWhere) {
		return updateSQL(table, listColNames, listArgs, convertIntoWhereClauses(listClausesWhere));
	}

	public static String updateSQL(String table, List<String> listColNames, List<String> listArgs, String clausesWhere) {
		String sql = null;
		boolean listeValuesOK = listColNames != null && listArgs != null && listColNames.size() == listArgs.size() && listColNames.size() > 0;
		if(listeValuesOK) {
			// valeur d'update
			sql = "UPDATE "+table+" SET "+stickElementWithLink(stickElementWithLinkAndGuillemet(listColNames, listArgs, " = ", "'"), ", ");
			// clause where
			if(clausesWhere != null && !clausesWhere.trim().isEmpty()) {
				sql += " WHERE "+deleteSpaceAndPrefixBefore(clausesWhere, "WHERE");
			}
		}
		return sql;
	}

	public static String updateSQL(String table, List<String> listColNames, List<String> listArgs) {
		return updateSQL(table, listColNames, listArgs, "");
	}
}
