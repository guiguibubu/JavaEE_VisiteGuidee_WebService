package fr.eseo.javaee.projet.db.tools;

import java.util.ArrayList;
import java.util.List;

public class SQLTools {

	private SQLTools() {super();}

	// METHODES SERVANT DE CONVERTISSEURS UTILES
	/**
	 * Renvoie le chaine de caractères en enlevant les espace et le préfix spécifié DEVANT le le text entré
	 * @param text, le texte a traiter
	 * @param prefix, le préfix à enlever
	 * @return Le texte entré sans espaces devant le préfix, sans le préfix, et sans espace jusqu'à la première lettre
	 */
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

	/**
	 * Enlève tous les espaces devant le texte entré
	 * @param text, le texte à traiter
	 * @return Le texte entré sans les espaces devant
	 */
	public static String deleteSpaceBefore(String text) {
		if(text != null) {
			while (text.startsWith(" ")) {
				text = text.substring(1, text.length());
			}
		}
		return text;
	}

	/**
	 * Colle les deux textes avec un lien entre les deux
	 * @param textLeft, le texte à mettre en premier (à gauche)
	 * @param textRight, le texte à mettre en dernier (à droite)
	 * @param link, la chaîne de caractères à mettre entre les deux textes
	 * @return Le texte contenant les texte collés avec le lien au millieu (textLeft + link + textRight)
	 */
	public static String stickElementWithLink(String textLeft, String textRight, String link) {
		String text = "";
		if(textLeft != null && textRight != null && link != null) {
			text += textLeft + link + textRight;
		}
		return text;
	}

	/**
	 * Colle les éléments d'une liste avec un lien entre chacun
	 * @param list, La liste dont il faut coller les éléments
	 * @param link, Le lien à mettre entre chaque éléments à coller
	 * @param keepEmptyData, Indique s'il faut prendre en compte les chaînes de caractères vides. true : On fait apparaître les chaînes de caractères vides dans le résultat
	 * @return Le texte avec les éléments de liste collés avec le lient entre chaque (text + link +text + link + ... + text + link + text)
	 */
	public static String stickElementWithLink(List<String> list, String link, boolean keepEmptyData) {
		return stickElementWithLinkAndGuillemet(list, link, "", keepEmptyData);
	}

	/**
	 * Colle les éléments d'une liste avec un lien entre chacun et un encadrement autour de chaque élément
	 * @param list, La liste dont il faut coller les éléments
	 * @param link, Le lien à mettre entre chaque éléments à coller
	 * @param guillemet, Chaîne de caractère à mettre de chaque coté de chacun des éléments
	 * @param keepEmptyData, Indique s'il faut prendre en compte les chaînes de caractères vides. true : On fait apparaître les chaînes de caractères vides dans le résultat
	 * @return Le texte avec les éléments de liste collés avec le lient entre chaque (guillemet + text + guillemet + link + guillemet + text + guillemet + link + ... )
	 */
	public static String stickElementWithLinkAndGuillemet(List<String> list, String link, String guillemet, boolean keepEmptyData) {
		StringBuilder stringBuilder = new StringBuilder();
		if(list != null && link != null && !list.isEmpty()) {
			for(int i = 0; i<list.size(); i++) {
				if(list.get(i) != null && (keepEmptyData || !list.get(i).trim().isEmpty())) {
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

	/**
	 * Colle deux textes avec un lien entre les deux et un encadrement autour du membre de droite
	 * @param textLeft, Le texte à mettre en premier (à gauche)
	 * @param textRight, Le texte à mettre en dernier (à droite)
	 * @param link, La chaîne de caractères à mettre entre les deux textes
	 * @param guillemet, Chaîne de caractère à mettre de chaque coté de chacun des éléments
	 * @param upperCase, Indique si l'on doit mettre le texte de droite en majuscule et ajouter la requête UPPERCASE au membre de gauche. true :  "UPPER("+textLeft+")"+link+guillemet+textRight.toUpperCase()+guillemet
	 * @return Le texte avec les deux textes collés
	 */
	public static String stickElementWithLinkAndGuillemet(String textLeft, String textRight, String link, String guillemet, boolean upperCase) {
		String text = "";
		if(link != null && textLeft != null && textRight != null) {
			if(upperCase) {
				text += "UPPER("+textLeft+")"+link+guillemet+textRight.toUpperCase()+guillemet;
			} else {
				text += textLeft+link+guillemet+textRight+guillemet;
			}
		}
		return text;
	}

	/**
	 * Colle les élément d'une première liste avec ceux d'une seconde avec un lien entre chaque et un encadrement pour les éléments de la seconde.
	 * @param listLeft, Liste avec les éléments à mettre en premier (à gauche)
	 * @param listRight, Liste avec les éléments à mettre en second (à droite)
	 * @param link, La chaîne de caractères à mettre entre les deux textes
	 * @param guillemet, Chaîne de caractère à mettre de chaque coté de chacun des éléments
	 * @param upperCase, Indique si l'on doit mettre le texte de droite en majuscule et ajouter la requête UPPERCASE au membre de gauche. true :  "UPPER("+textLeft+")"+link+guillemet+textRight.toUpperCase()+guillemet
	 * @return Le texte avec les deux textes collés
	 */
	public static List<String> stickElementWithLinkAndGuillemet(List<String> listLeft, List<String> listRight, String link, String guillemet, boolean upperString) {
		List<String> list = new ArrayList<>();
		boolean listesNotNull = listLeft != null && listRight != null;
		boolean listesSameSize = (listesNotNull) ? listLeft.size() == listRight.size() : false;
		if(link != null && listesSameSize && !listLeft.isEmpty()) {
			for(int i = 0; i<listLeft.size(); i++) {
				boolean elementsNotNull = listLeft.get(i) != null && listRight.get(i) != null;
				boolean elementsNotEmpty = (elementsNotNull) ? !listLeft.get(i).trim().isEmpty() && !listRight.get(i).trim().isEmpty() : false;
				boolean elementRechercheContainsNumber = (elementsNotNull) ? listRight.get(i).matches("[0-9]") : true;
				if(elementsNotEmpty) {
					boolean compareWithUpper = upperString && !elementRechercheContainsNumber;
					list.add(stickElementWithLinkAndGuillemet(listLeft.get(i), listRight.get(i), link, guillemet,compareWithUpper));
				}
			}
		}
		return list;
	}

	/**
	 * Formatte les éléments d'une liste de noms de colonnes et d'une autres avec les valeurs pour correspondre à une la partie WHERE d'une requête SQL
	 * @param listColNames, Liste des noms des colonnes
	 * @param listArgs, Liste des valeurs correspondant aux colonnes
	 * @return La liste des clauses WHERE pour une requête SQL
	 */
	public static List<String> convertIntoListOfWhereClauses(List<String> listColNames, List<String> listArgs){
		return stickElementWithLinkAndGuillemet(listColNames, listArgs, " = ", "'", true);
	}

	/**
	 * Lie les éléments d'une clause WHERE SQL dans une seule clause WHERE d'une requête SQL
	 * @param listClauses, L'ensemble des clauses WHERE à mettre ensemble
	 * @return La clause WHERE formée avec les clauses en entré
	 */
	public static String convertIntoWhereClauses(List<String> listClauses){
		return stickElementWithLink(listClauses, " AND ", false);
	}

	/**
	 * Lie deux textes au format d'une clause WHERE SQL
	 * @param textLeft, Texte à mettre à gauche
	 * @param textRight, Texte à mettre à droite
	 * @return La clause WHERE formée par les textes en entré
	 */
	public static String convertIntoWhereClause(String textLeft, String textRight){
		return stickElementWithLinkAndGuillemet(textLeft, textRight, " = ", "'", true);
	}

	/**
	 * Formatte les éléments d'une liste de noms de colonnes et d'une autres avec les valeurs pour correspondre à une la partie SET d'une requête SQL
	 * @param listColNames, Liste des noms des colonnes
	 * @param listArgs, Liste des valeurs correspondant aux colonnes
	 * @return La liste des clauses SET pour une requête SQL
	 */
	public static List<String> convertIntoListOfSetClauses(List<String> listColNames, List<String> listArgs){
		return stickElementWithLinkAndGuillemet(listColNames, listArgs, " = ", "'", false);
	}

	/**
	 * Lie les éléments d'une clause SET SQL dans une seule clause SET d'une requête SQL
	 * @param listClauses, L'ensemble des clauses SET à mettre ensemble
	 * @return La clause SET formée avec les clauses en entré
	 */
	public static String convertIntoSetClauses(List<String> listClauses){
		return stickElementWithLink(listClauses, ", ", false);
	}

	/**
	 * Lie deux textes au format d'une clause SET SQL
	 * @param textLeft, Texte à mettre à gauche
	 * @param textRight, Texte à mettre à droite
	 * @return La clause SET formée par les textes en entré
	 */
	public static String convertIntoSetClause(String textLeft, String textRight){
		return stickElementWithLinkAndGuillemet(textLeft, textRight, " = ", "'",false);
	}

	//METHODES SELECT
	/**
	 * Créé une requête SQL de sélection
	 * @param table, La table à laquelle s'adresse la requête
	 * @param listColNames, La liste des noms de colonnes à prendre en compte pour la clause WHERE de la requête
	 * @param listArgs, La liste des valeurs à prendre en compte pour la clause WHERE de la requête
	 * @return La requête SQL de sélection
	 */
	public static String selectSQL(String table, List<String> listColNames, List<String> listArgs) {
		return selectSQL(table, convertIntoListOfWhereClauses(listColNames, listArgs));
	}
	/**
	 * Créé une requête SQL de sélection
	 * @param table, La table à laquelle s'adresse la requête
	 * @param colName, Le nom de colonne à prendre en compte pour la clause WHERE de la requête
	 * @param arg, La valeur à prendre en compte pour la clause WHERE de la requête
	 * @return La requête SQL de sélection
	 */
	public static String selectSQL(String table, String colName, String arg) {
		List<String> listColNames = new ArrayList<>();
		List<String> listArgs = new ArrayList<>();
		listColNames.add(colName);
		listArgs.add(arg);
		return selectSQL(table, listColNames, listArgs);
	}

	/**
	 * Créé une requête SQL de sélection
	 * @param table, La table à laquelle s'adresse la requête
	 * @param listClausesWhere, Liste des éléments à mettre dans la clause WHERE de la requête
	 * @return La requête SQL de sélection
	 */
	public static String selectSQL(String table, List<String> listClausesWhere) {
		return selectSQL(table, convertIntoWhereClauses(listClausesWhere));
	}

	/**
	 * Créé une requête SQL de sélection
	 * @param table, La table à laquelle s'adresse la requête
	 * @param clausesWhere, L'élément de la clause WHERE de la requête
	 * @return La requête SQL de sélection
	 */
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
	/**
	 * Créé une requête SQL de d'insertion
	 * @param table, La table à laquelle s'adresse la requête
	 * @param listColNames, La liste des noms de colonnes de la table
	 * @param listArgs, La liste des valeurs à prendre en compte pour l'insertion
	 * @return La requête SQL d'insertion
	 */
	public static String insertSQL(String table, List<String> listColNames, List<String> listArgs) {
		String sql = "";
		if(listColNames != null && listArgs != null && listColNames.size() == listArgs.size() && !listColNames.isEmpty()) {
			//Insert et nom colonnes
			sql = "INSERT INTO "+table+" ("+stickElementWithLink(listColNames, ",", false)+") ";
			sql += "VALUES ("+stickElementWithLinkAndGuillemet(listArgs, ",", "'", true)+")";
		}
		return sql;
	}

	//METHODE DELETE

	/**
	 * Créé une requête SQL de suppression
	 * @param table, La table à laquelle s'adresse la requête
	 * @param listColNames, La liste des noms de colonnes à prendre en compte pour la clause WHERE de la requête
	 * @param listArgs, La liste des valeurs à prendre en compte pour la clause WHERE de la requête
	 * @return La requête SQL de suppression
	 */
	public static String deleteSQL(String table, List<String> listColNames, List<String> listArgs) {
		return deleteSQL(table, convertIntoListOfWhereClauses(listColNames, listArgs));
	}

	/**
	 * Créé une requête SQL de suppression
	 * @param table, La table à laquelle s'adresse la requête
	 * @param colName, Le nom de colonne à prendre en compte pour la clause WHERE de la requête
	 * @param arg, La valeur à prendre en compte pour la clause WHERE de la requête
	 * @return La requête SQL de suppression
	 */
	public static String deleteSQL(String table, String colName, String arg) {
		return deleteSQL(table, convertIntoWhereClause(colName, arg));
	}

	/**
	 * Créé une requête SQL de suppression
	 * @param table, La table à laquelle s'adresse la requête
	 * @param listClausesWhere, Liste des éléments de la clause WHERE de la requête
	 * @return La requête SQL de suppression
	 */
	public static String deleteSQL(String table, List<String> listClausesWhere) {
		return deleteSQL(table, convertIntoWhereClauses(listClausesWhere));
	}

	/**
	 * Créé une requête SQL de suppression
	 * @param table, La table à laquelle s'adresse la requête
	 * @param clausesWhere, L'élément de la clause WHERE de la requête
	 * @return La requête SQL de suppression
	 */
	public static String deleteSQL(String table, String clausesWhere) {
		String sql = "";
		//Sécurité pour ne pas supprimer toutes les valeurs en une seule fois
		if(clausesWhere != null && !clausesWhere.trim().isEmpty()) {
			sql = "DELETE FROM "+table;
			sql += " WHERE "+clausesWhere;
		}
		return sql;
	}

	//METHODE UPDATE

	/**
	 * Créé une requête SQL de modification d'enregistrement
	 * @param table, La table à laquelle s'adresse la requête
	 * @param listColNames, La liste des noms de colonnes à prendre en compte pour la clause SET de la requête
	 * @param listArgs, La liste des valeurs à prendre en compte pour la clause SET de la requête
	 * @param listColNamesWhere, La liste des noms de colonnes à prendre en compte pour la clause WHERE de la requête
	 * @param listArgsWhere, La liste des valeurs à prendre en compte pour la clause WHERE de la requête
	 * @return La requête SQL de modification d'enregistrement
	 */
	public static String updateSQL(String table, List<String> listColNames, List<String> listArgs, List<String> listColNamesWhere, List<String> listArgsWhere) {
		return updateSQL(table, listColNames, listArgs, convertIntoListOfWhereClauses(listColNamesWhere, listArgsWhere));
	}

	/**
	 * Créé une requête SQL de modification d'enregistrement
	 * @param table, La table à laquelle s'adresse la requête
	 * @param listColNames, La liste des noms de colonnes à prendre en compte pour la clause SET de la requête
	 * @param listArgs, La liste des valeurs à prendre en compte pour la clause SET de la requête
	 * @param colNameWhere, Le nom de colonne à prendre en compte pour la clause WHERE de la requête
	 * @param argWhere, La valeur à prendre en compte pour la clause WHERE de la requête
	 * @return La requête SQL de modification d'enregistrement
	 */
	public static String updateSQL(String table, List<String> listColNames, List<String> listArgs, String colNameWhere, String argWhere) {
		return updateSQL(table, listColNames, listArgs, convertIntoWhereClause(colNameWhere, argWhere));
	}

	/**
	 * Créé une requête SQL de modification d'enregistrement
	 * @param table, La table à laquelle s'adresse la requête
	 * @param listColNames, La liste des noms de colonnes à prendre en compte pour la clause SET de la requête
	 * @param listArgs, La liste des valeurs à prendre en compte pour la clause SET de la requête
	 * @param listClausesWhere, Liste des éléments de la clause WHERE de la requête
	 * @return La requête SQL de modification d'enregistrement
	 */
	public static String updateSQL(String table, List<String> listColNames, List<String> listArgs, List<String> listClausesWhere) {
		return updateSQL(table, listColNames, listArgs, convertIntoWhereClauses(listClausesWhere));
	}

	/**
	 * Créé une requête SQL de modification d'enregistrement
	 * @param table, La table à laquelle s'adresse la requête
	 * @param listColNames, La liste des noms de colonnes à prendre en compte pour la clause SET de la requête
	 * @param listArgs, La liste des valeurs à prendre en compte pour la clause SET de la requête
	 * @param clausesWhere, L'élément de la clause WHERE de la requête
	 * @return La requête SQL de modification d'enregistrement
	 */
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

	/**
	 * Créé une requête SQL de modification d'enregistrement
	 * @param table, La table à laquelle s'adresse la requête
	 * @param listColNames, La liste des noms de colonnes à prendre en compte pour la clause SET de la requête
	 * @param listArgs, La liste des valeurs à prendre en compte pour la clause SET de la requête
	 * @return La requête SQL de modification d'enregistrement
	 */
	public static String updateSQL(String table, List<String> listColNames, List<String> listArgs) {
		return updateSQL(table, listColNames, listArgs, "");
	}
}
