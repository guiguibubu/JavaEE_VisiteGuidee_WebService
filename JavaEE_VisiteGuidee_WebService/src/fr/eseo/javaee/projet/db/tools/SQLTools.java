package fr.eseo.javaee.projet.db.tools;

import java.util.ArrayList;
import java.util.List;

public class SQLTools {

	private SQLTools() {super();}

	// METHODES SERVANT DE CONVERTISSEURS UTILES
	public static String deleteSpaceAndPrefixBefore(String text, String prefix) {
		if(text != null) {
			text = deleteSpaceBefore(text);
			if(prefix != null && text.startsWith(prefix)) {
				text = text.substring(prefix.length(), text.length());
			}
			text = deleteSpaceBefore(text);
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
		if(textLeft != null && textRight != null && link != null) {
			text += textLeft + link + textRight;
		}
		return text;
	}

	public static String stickElementWithLink(List<String> list, String link) {
		return stickElementWithLinkAndGuillemet(list, link, "");
	}

	public static String stickElementWithLinkAndGuillemet(List<String> list, String link, String guillemet) {
		StringBuilder stringBuilder = new StringBuilder();
		if(list != null && link != null && !list.isEmpty()) {
			for(int i = 0; i<list.size(); i++) {
				if(list.get(i) != null && !list.get(i).trim().isEmpty()) {
					stringBuilder.append(guillemet);
					stringBuilder.append(list.get(i));
					stringBuilder.append(guillemet);
					if(i<list.size()-1) {
						stringBuilder.append(link);
					}
				}
			}
			if(stringBuilder.toString().endsWith(link)) {
				stringBuilder.delete(stringBuilder.length()-link.length(), stringBuilder.length());
			}
		}
		return stringBuilder.toString();
	}

	public static String stickElementWithLinkAndGuillemet(String textLeft, String textRight, String link, String guillemet) {
		String text = "";
		if(link != null && textLeft != null && textRight != null) {
			text += textLeft+link+guillemet+textRight+guillemet;
		}
		return text;
	}

	public static List<String> stickElementWithLinkAndGuillemet(List<String> listLeft, List<String> listRight, String link, String guillemet) {
		List<String> list = new ArrayList<>();
		boolean listesNotNull = listLeft != null && listRight != null;
		boolean listesSameSize = (listesNotNull) ? listLeft.size() == listRight.size() : false;
		if(link != null && listesSameSize && !listLeft.isEmpty()) {
			for(int i = 0; i<listLeft.size(); i++) {
				boolean elementsNotNull = listLeft.get(i) != null && listRight.get(i) != null;
				boolean elementsNotEmpty = (elementsNotNull) ? !listLeft.get(i).trim().isEmpty() && !listRight.get(i).trim().isEmpty() : false;
				if(elementsNotEmpty) {
					list.add(stickElementWithLinkAndGuillemet(listLeft.get(i), listRight.get(i), link, guillemet));
				}
			}
		}
		return list;
	}

	public static List<String> convertIntoListOfWhereClauses(List<String> listColNames, List<String> listArgs){
		return stickElementWithLinkAndGuillemet(listColNames, listArgs, " = ", "'");
	}

	public static String convertIntoWhereClauses(List<String> listClauses){
		return stickElementWithLink(listClauses, " AND ");
	}

	public static String convertIntoWhereClause(String textLeft, String textRight){
		return stickElementWithLinkAndGuillemet(textLeft, textRight, " = ", "'");
	}

	public static List<String> convertIntoListOfSetClauses(List<String> listColNames, List<String> listArgs){
		return stickElementWithLinkAndGuillemet(listColNames, listArgs, " = ", "'");
	}

	public static String convertIntoSetClauses(List<String> listClauses){
		return stickElementWithLink(listClauses, ", ");
	}

	public static String convertIntoSetClause(String textLeft, String textRight){
		return stickElementWithLinkAndGuillemet(textLeft, textRight, " = ", "'");
	}

	//METHODES SELECT
	public static String selectSQL(String table, List<String> listColNames, List<String> listArgs) {
		return selectSQL(table, convertIntoListOfWhereClauses(listColNames, listArgs));
	}

	public static String selectSQL(String table, String colName, String arg) {
		List<String> listColNames = new ArrayList<>();
		List<String> listArgs = new ArrayList<>();
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
		if(listColNames != null && listArgs != null && listColNames.size() == listArgs.size() && !listColNames.isEmpty()) {
			//Insert et nom colonnes
			sql = "INSERT INTO "+table+" ("+stickElementWithLink(listColNames, ",")+") ";
			sql += "VALUES ("+stickElementWithLinkAndGuillemet(listArgs, ",", "'")+")";
		}
		return sql;
	}

	//METHODE DELETE
	public static String deleteSQL(String table, List<String> listColNames, List<String> listArgs) {
		return deleteSQL(table, convertIntoListOfWhereClauses(listColNames, listArgs));
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
			sql += " WHERE "+clausesWhere;
		}
		return sql;
	}

	//METHODE UPDATE
	public static String updateSQL(String table, List<String> listColNames, List<String> listArgs, List<String> listColNamesWhere, List<String> listArgsWhere) {
		return updateSQL(table, listColNames, listArgs, convertIntoListOfWhereClauses(listColNamesWhere, listArgsWhere));
	}

	public static String updateSQL(String table, List<String> listColNames, List<String> listArgs, String colNameWhere, String argWhere) {
		return updateSQL(table, listColNames, listArgs, convertIntoWhereClause(colNameWhere, argWhere));
	}

	public static String updateSQL(String table, List<String> listColNames, List<String> listArgs, List<String> listClausesWhere) {
		return updateSQL(table, listColNames, listArgs, convertIntoWhereClauses(listClausesWhere));
	}

	public static String updateSQL(String table, List<String> listColNames, List<String> listArgs, String clausesWhere) {
		String sql = "";
		boolean listeValuesOK = listColNames != null && listArgs != null && listColNames.size() == listArgs.size() && !listColNames.isEmpty();
		boolean clausesWhereOK = clausesWhere != null && !clausesWhere.trim().isEmpty(); // Sécurité pour éviter d'avoir un update général non voulu
		if(listeValuesOK && clausesWhereOK) {
			// valeur d'update
			sql = "UPDATE "+table+" SET "+convertIntoSetClauses(convertIntoListOfSetClauses(listColNames, listArgs));
			// clause where
			sql += " WHERE "+deleteSpaceAndPrefixBefore(clausesWhere, "WHERE");
		}
		return sql;
	}

	public static String updateSQL(String table, List<String> listColNames, List<String> listArgs) {
		return updateSQL(table, listColNames, listArgs, "");
	}
}
