package fr.eseo.javaee.projet.db.tools;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class SQLToolsTest {

	@Test
	void testDeleteSpaceBefore() {
		String textAttendu = "toto";
		String text = "   toto";
		Assertions.assertEquals(textAttendu, SQLTools.deleteSpaceBefore(text));
	}

	@Test
	void testDeleteSpaceAndPrefixBefore() {
		String textAttendu = "toto";
		String text = "    tata   toto";
		Assertions.assertEquals(textAttendu, SQLTools.deleteSpaceAndPrefixBefore(text, "tata"));
	}

	@Test
	void testStickElementWithLinkStringStringString() {
		String textAttendu = "Roméo et Juliette";
		String textLeft = "Roméo";
		String textRight = "Juliette";
		String link = " et ";
		Assertions.assertEquals(textAttendu, SQLTools.stickElementWithLink(textLeft, textRight, link));
	}

	@Test
	void testStickElementWithLinkListOfStringString() {
		String textAttendu = "Roméo et Juliette et Léonardo et Kate";
		List<String> list = new ArrayList<String>();
		list.add("Roméo");
		list.add("Juliette");
		list.add("Léonardo");
		list.add(" ");
		list.add(null);
		list.add("Kate");
		String link = " et ";
		Assertions.assertEquals(textAttendu, SQLTools.stickElementWithLink(list, link));
	}

	@Test
	void testStickElementWithLinkAndGuillemetStringStringStringString() {
		String textAttendu = "Roméo et \"Juliette\"";
		String textLeft = "Roméo";
		String textRight = "Juliette";
		String link = " et ";
		String guillemet ="\"";
		Assertions.assertEquals(textAttendu, SQLTools.stickElementWithLinkAndGuillemet(textLeft, textRight, link, guillemet));
	}

	@Test
	void testStickElementWithLinkAndGuillemetListOfStringListOfStringStringString() {
		List<String> listTextAttendu = new ArrayList<String>();
		listTextAttendu.add("Roméo et \"Juliette\"");
		listTextAttendu.add("Léonardo et \"Kate\"");
		List<String> listLeft = new ArrayList<String>();
		List<String> listRight = new ArrayList<String>();
		listLeft.add("Roméo");
		listLeft.add("Léonardo");
		listRight.add("Juliette");
		listRight.add("Kate");
		String link = " et ";
		String guillemet ="\"";
		Assertions.assertEquals(listTextAttendu, SQLTools.stickElementWithLinkAndGuillemet(listLeft, listRight, link, guillemet));
	}

	@Test
	void testConvertIntoListOfWhereClauses() {
		List<String> listAttendu = new ArrayList<>();
		listAttendu.add("prenom = 'Guillaume'");
		listAttendu.add("nom = 'Buchle'");
		List<String> listColNames = new ArrayList<String>();
		List<String> listArgs = new ArrayList<String>();
		listColNames.add("prenom");
		listColNames.add(" ");
		listColNames.add(null);
		listColNames.add("nom");
		listArgs.add("Guillaume");
		listArgs.add(" ");
		listArgs.add(null);
		listArgs.add("Buchle");
		Assertions.assertEquals(listAttendu, SQLTools.convertIntoListOfWhereClauses(listColNames, listArgs));
	}

	@Test
	void testConvertIntoWhereClauses() {
		String textAttendu = "prenom = 'Guillaume' AND nom = 'Buchle'";
		List<String> listClauses = new ArrayList<String>();
		listClauses.add("prenom = 'Guillaume'");
		listClauses.add(" ");
		listClauses.add(null);
		listClauses.add("nom = 'Buchle'");
		Assertions.assertEquals(textAttendu, SQLTools.convertIntoWhereClauses(listClauses));
	}

	@Test
	void testConvertIntoWhereClause() {
		String textAttendu = "prenom = 'Guillaume'";
		String textLeft = "prenom";
		String textRight = "Guillaume";
		Assertions.assertEquals(textAttendu, SQLTools.convertIntoWhereClause(textLeft, textRight));
	}

	@Test
	void testConvertIntoListOfSetClauses() {
		ArrayList<String> listAttendu = new ArrayList<>();
		listAttendu.add("prenom = 'Guillaume'");
		listAttendu.add("nom = 'Buchle'");
		List<String> listColNames = new ArrayList<String>();
		List<String> listArgs = new ArrayList<String>();
		listColNames.add("prenom");
		listColNames.add(" ");
		listColNames.add(null);
		listColNames.add("nom");
		listArgs.add("Guillaume");
		listArgs.add(" ");
		listArgs.add(null);
		listArgs.add("Buchle");
		Assertions.assertEquals(listAttendu, SQLTools.convertIntoListOfSetClauses(listColNames, listArgs));
	}

	@Test
	void testConvertIntoSetClauses() {
		String textAttendu = "prenom = 'Guillaume', nom = 'Buchle'";
		List<String> listClauses = new ArrayList<String>();
		listClauses.add("prenom = 'Guillaume'");
		listClauses.add(" ");
		listClauses.add(null);
		listClauses.add("nom = 'Buchle'");
		Assertions.assertEquals(textAttendu, SQLTools.convertIntoSetClauses(listClauses));
	}

	@Test
	void testConvertIntoSetClause() {
		String textAttendu = "prenom = 'Guillaume'";
		String textLeft = "prenom";
		String textRight = "Guillaume";
		Assertions.assertEquals(textAttendu, SQLTools.convertIntoSetClause(textLeft, textRight));
	}

	@Test
	void testSelectSQLStringListOfStringListOfString() {
		String sqlAttendu = "SELECT * FROM client WHERE prenom = 'Guillaume' AND nom = 'Buchle'";
		String table = "client";
		ArrayList<String> listColNames = new ArrayList<String>();
		ArrayList<String> listArgs = new ArrayList<String>();
		listColNames.add("prenom");
		listColNames.add("nom");
		listArgs.add("Guillaume");
		listArgs.add("Buchle");
		Assertions.assertEquals(sqlAttendu, SQLTools.selectSQL(table, listColNames, listArgs));
	}

	@Test
	void testSelectSQLStringStringString() {
		String sqlAttendu = "SELECT * FROM client WHERE prenom = 'Guillaume'";
		String table = "client";
		String colName = "prenom";
		String arg = "Guillaume";
		Assertions.assertEquals(sqlAttendu, SQLTools.selectSQL(table, colName, arg));
	}

	@Test
	void testSelectSQLStringListOfString() {
		String sqlAttendu = "SELECT * FROM client WHERE prenom = 'Guillaume' AND nom = 'Buchle'";
		String table = "client";
		ArrayList<String> listClausesWhere = new ArrayList<String>();
		listClausesWhere.add("prenom = 'Guillaume'");
		listClausesWhere.add(" ");
		listClausesWhere.add(null);
		listClausesWhere.add("nom = 'Buchle'");
		Assertions.assertEquals(sqlAttendu, SQLTools.selectSQL(table, listClausesWhere));
	}

	@Test
	void testSelectSQLStringString() {
		String sqlAttendu = "SELECT * FROM client WHERE prenom = 'Guillaume' AND nom = 'Buchle'";
		String table = "client";
		String clausesWhere = "WHERE prenom = 'Guillaume' AND nom = 'Buchle'";
		String clausesWhereSansWhere = " prenom = 'Guillaume' AND nom = 'Buchle'";
		Assertions.assertEquals(sqlAttendu, SQLTools.selectSQL(table, clausesWhere));
		Assertions.assertEquals(sqlAttendu, SQLTools.selectSQL(table, clausesWhereSansWhere));
	}

	@Test
	void testInsertSQL() {
		String sqlAttendu = "INSERT INTO client (prenom,nom) VALUES ('Guillaume','Buchle')";
		String table = "client";
		ArrayList<String> listColNames = new ArrayList<String>();
		ArrayList<String> listArgs = new ArrayList<String>();
		listColNames.add("prenom");
		listColNames.add("nom");
		listArgs.add("Guillaume");
		listArgs.add("Buchle");
		Assertions.assertEquals(sqlAttendu, SQLTools.insertSQL(table, listColNames, listArgs));
	}

	@Test
	void testDeleteSQLStringListOfStringListOfString() {
		String sqlAttendu = "DELETE FROM client WHERE prenom = 'Guillaume' AND nom = 'Buchle'";
		String table = "client";
		ArrayList<String> listColNames = new ArrayList<String>();
		ArrayList<String> listArgs = new ArrayList<String>();
		listColNames.add("prenom");
		listColNames.add("nom");
		listArgs.add("Guillaume");
		listArgs.add("Buchle");
		Assertions.assertEquals(sqlAttendu, SQLTools.deleteSQL(table, listColNames, listArgs));
	}

	@Test
	void testDeleteSQLStringStringString() {
		String sqlAttendu = "DELETE FROM client WHERE prenom = 'Guillaume'";
		String table = "client";
		String colName = "prenom";
		String arg = "Guillaume";
		Assertions.assertEquals(sqlAttendu, SQLTools.deleteSQL(table, colName, arg));
	}

	@Test
	void testDeleteSQLStringListOfString() {
		String sqlAttendu = "DELETE FROM client WHERE prenom = 'Guillaume' AND nom = 'Buchle'";
		String table = "client";
		ArrayList<String> listClausesWhere = new ArrayList<String>();
		listClausesWhere.add("prenom = 'Guillaume'");
		listClausesWhere.add(" ");
		listClausesWhere.add(null);
		listClausesWhere.add("nom = 'Buchle'");
		Assertions.assertEquals(sqlAttendu, SQLTools.deleteSQL(table, listClausesWhere));
	}

	@Test
	void testDeleteSQLStringString() {
		String sqlAttendu = "DELETE FROM client WHERE prenom = 'Guillaume' AND nom = 'Buchle'";
		String table = "client";
		String clausesWhere = "prenom = 'Guillaume' AND nom = 'Buchle'";
		Assertions.assertEquals(sqlAttendu, SQLTools.deleteSQL(table, clausesWhere));
	}

	@Test
	void testUpdateSQLStringListOfStringListOfStringListOfStringListOfString() {
		String sqlAttendu = "UPDATE client SET prenom = 'Guillaume', nom = 'Buchle' WHERE ecole = 'ESEO' AND ville = 'Nantes'";
		String table = "client";
		ArrayList<String> listColNames = new ArrayList<String>();
		ArrayList<String> listArgs = new ArrayList<String>();
		ArrayList<String> listColNamesWhere = new ArrayList<String>();
		ArrayList<String> listArgsWhere = new ArrayList<String>();
		listColNames.add("prenom");
		listColNames.add("nom");
		listArgs.add("Guillaume");
		listArgs.add("Buchle");
		listColNamesWhere.add("ecole");
		listColNamesWhere.add("ville");
		listArgsWhere.add("ESEO");
		listArgsWhere.add("Nantes");
		Assertions.assertEquals(sqlAttendu, SQLTools.updateSQL(table, listColNames, listArgs, listColNamesWhere, listArgsWhere));
	}

	@Test
	void testUpdateSQLStringListOfStringListOfStringStringString() {
		String sqlAttendu = "UPDATE client SET prenom = 'Guillaume', nom = 'Buchle' WHERE ecole = 'ESEO'";
		String table = "client";
		ArrayList<String> listColNames = new ArrayList<String>();
		ArrayList<String> listArgs = new ArrayList<String>();
		listColNames.add("prenom");
		listColNames.add("nom");
		listArgs.add("Guillaume");
		listArgs.add("Buchle");
		String colNameWhere = "ecole";
		String argWhere = "ESEO";
		Assertions.assertEquals(sqlAttendu, SQLTools.updateSQL(table, listColNames, listArgs, colNameWhere, argWhere));
	}

	@Test
	void testUpdateSQLStringListOfStringListOfStringListOfString() {
		String sqlAttendu = "UPDATE client SET prenom = 'Guillaume', nom = 'Buchle' WHERE ecole = 'ESEO' AND ville = 'Nantes'";
		String table = "client";
		ArrayList<String> listColNames = new ArrayList<String>();
		ArrayList<String> listArgs = new ArrayList<String>();
		ArrayList<String> listClausesWhere = new ArrayList<String>();
		listColNames.add("prenom");
		listColNames.add("nom");
		listArgs.add("Guillaume");
		listArgs.add("Buchle");
		listClausesWhere.add("ecole = 'ESEO'");
		listClausesWhere.add(" ");
		listClausesWhere.add(null);
		listClausesWhere.add("ville = 'Nantes'");
		Assertions.assertEquals(sqlAttendu, SQLTools.updateSQL(table, listColNames, listArgs, listClausesWhere));
	}

	@Test
	void testUpdateSQLStringListOfStringListOfStringString() {
		String sqlAttendu = "UPDATE client SET prenom = 'Guillaume', nom = 'Buchle' WHERE ecole = 'ESEO' AND ville = 'Nantes'";
		String table = "client";
		ArrayList<String> listColNames = new ArrayList<String>();
		ArrayList<String> listArgs = new ArrayList<String>();
		String clausesWhere = "ecole = 'ESEO' AND ville = 'Nantes'";
		listColNames.add("prenom");
		listColNames.add("nom");
		listArgs.add("Guillaume");
		listArgs.add("Buchle");
		Assertions.assertEquals(sqlAttendu, SQLTools.updateSQL(table, listColNames, listArgs, clausesWhere));
	}

	@Test
	void testUpdateSQLStringListOfStringListOfString() {
		String sqlAttendu = "";
		String table = "client";
		ArrayList<String> listColNames = new ArrayList<String>();
		ArrayList<String> listArgs = new ArrayList<String>();
		listColNames.add("prenom");
		listColNames.add("nom");
		listArgs.add("Guillaume");
		listArgs.add("Buchle");
		Assertions.assertEquals(sqlAttendu, SQLTools.updateSQL(table, listColNames, listArgs));
	}

	// Cas taille liste non concordantes
	@Test
	void testSelectSQLTailleListe() {
		String table = "client";
		String sql = "SELECT * FROM client";
		ArrayList<String> listColNames = new ArrayList<String>();
		ArrayList<String> listArgs = new ArrayList<String>();
		listColNames.add("prenom");
		listColNames.add("nom");
		listArgs.add("Guillaume");
		Assertions.assertEquals(sql, SQLTools.selectSQL(table, listColNames, listArgs));
	}

	@Test
	void testInsertSQLTailleListe() {
		String table = "client";
		ArrayList<String> listColNames = new ArrayList<String>();
		ArrayList<String> listArgs = new ArrayList<String>();
		listColNames.add("prenom");
		listColNames.add("nom");
		listArgs.add("Guillaume");
		Assertions.assertEquals("", SQLTools.insertSQL(table, listColNames, listArgs));
	}

	@Test
	void testDeleteSQLTailleListe() {
		String table = "client";
		ArrayList<String> listColNames = new ArrayList<String>();
		ArrayList<String> listArgs = new ArrayList<String>();
		listColNames.add("prenom");
		listColNames.add("nom");
		listArgs.add("Guillaume");
		Assertions.assertEquals("", SQLTools.deleteSQL(table, listColNames, listArgs));
	}

	@Test
	void testUpdateSQLTailleListeValue() {
		String table = "client";
		ArrayList<String> listColNames = new ArrayList<String>();
		ArrayList<String> listArgs = new ArrayList<String>();
		ArrayList<String> listColNamesWhere = new ArrayList<String>();
		ArrayList<String> listArgsWhere = new ArrayList<String>();
		listColNames.add("prenom");
		listColNames.add("nom");
		listArgs.add("Guillaume");
		listColNamesWhere.add("ecole");
		listArgsWhere.add("ESEO");
		Assertions.assertEquals("", SQLTools.updateSQL(table, listColNames, listArgs, listColNamesWhere, listArgsWhere));
	}

	@Test
	void testUpdateSQLTailleListeWhere() {
		String table = "client";
		ArrayList<String> listColNames = new ArrayList<String>();
		ArrayList<String> listArgs = new ArrayList<String>();
		ArrayList<String> listColNamesWhere = new ArrayList<String>();
		ArrayList<String> listArgsWhere = new ArrayList<String>();
		listColNames.add("prenom");
		listColNames.add("nom");
		listArgs.add("Guillaume");
		listArgs.add("Buchle");
		listColNamesWhere.add("ecole");
		Assertions.assertEquals("", SQLTools.updateSQL(table, listColNames, listArgs, listColNamesWhere, listArgsWhere));
	}

	@Test
	void testUpdateSQLWhereVide_Null() {
		String sqlAttendu = "";
		String table = "client";
		ArrayList<String> listColNames = new ArrayList<String>();
		ArrayList<String> listArgs = new ArrayList<String>();
		ArrayList<String> listColNamesWhere = new ArrayList<String>();
		ArrayList<String> listArgsWhere = new ArrayList<String>();
		listColNames.add("prenom");
		listColNames.add("nom");
		listArgs.add("Guillaume");
		listArgs.add("Buchle");
		Assertions.assertEquals(sqlAttendu, SQLTools.updateSQL(table, listColNames, listArgs, listColNamesWhere, listArgsWhere));
		Assertions.assertEquals(sqlAttendu, SQLTools.updateSQL(table, listColNames, listArgs, null, listArgsWhere));
		Assertions.assertEquals(sqlAttendu, SQLTools.updateSQL(table, listColNames, listArgs, listColNamesWhere, null));
	}

	//Cas liste null
	@Test
	void testSelectSQLListeNull() {
		String textAttendu = "SELECT * FROM client";
		String table = "client";
		ArrayList<String> listColNames = new ArrayList<String>();
		ArrayList<String> listArgs = new ArrayList<String>();
		listColNames.add("prenom");
		listColNames.add("nom");
		listArgs.add("Guillaume");
		listArgs.add("Buchle");
		Assertions.assertEquals(textAttendu, SQLTools.selectSQL(table, null, listArgs));
		Assertions.assertEquals(textAttendu, SQLTools.selectSQL(table, listColNames, null));
	}

	@Test
	void testInsertSQLListeNull() {
		String table = "client";
		ArrayList<String> listColNames = new ArrayList<String>();
		ArrayList<String> listArgs = new ArrayList<String>();
		listColNames.add("prenom");
		listColNames.add("nom");
		listArgs.add("Guillaume");
		listArgs.add("Buchle");
		Assertions.assertEquals("", SQLTools.insertSQL(table, null, listArgs));
		Assertions.assertEquals("", SQLTools.insertSQL(table, listColNames, null));
	}

	@Test
	void testDeleteSQLListeNull() {
		String table = "client";
		ArrayList<String> listColNames = new ArrayList<String>();
		ArrayList<String> listArgs = new ArrayList<String>();
		listColNames.add("prenom");
		listColNames.add("nom");
		listArgs.add("Guillaume");
		listArgs.add("Buchle");
		Assertions.assertEquals("", SQLTools.deleteSQL(table, null, listArgs));
		Assertions.assertEquals("", SQLTools.deleteSQL(table, listColNames, null));
	}

	@Test
	void testUpdateSQLListeNullValue() {
		String table = "client";
		ArrayList<String> listColNames = new ArrayList<String>();
		ArrayList<String> listArgs = new ArrayList<String>();
		ArrayList<String> listColNamesWhere = new ArrayList<String>();
		ArrayList<String> listArgsWhere = new ArrayList<String>();
		listColNames.add("prenom");
		listColNames.add("nom");
		listArgs.add("Guillaume");
		listArgs.add("Buchle");
		listColNamesWhere.add("ecole");
		listArgsWhere.add("ESEO");
		Assertions.assertEquals("", SQLTools.updateSQL(table, null, listArgs, listColNamesWhere, listArgsWhere));
		Assertions.assertEquals("", SQLTools.updateSQL(table, listColNames, null, listColNamesWhere, listArgsWhere));
	}

	@Test
	void testUpdateSQLListeNullWhere() {
		String sqlAttendu = "";
		String table = "client";
		ArrayList<String> listColNames = new ArrayList<String>();
		ArrayList<String> listArgs = new ArrayList<String>();
		ArrayList<String> listColNamesWhere = new ArrayList<String>();
		ArrayList<String> listArgsWhere = new ArrayList<String>();
		listColNames.add("prenom");
		listColNames.add("nom");
		listArgs.add("Guillaume");
		listArgs.add("Buchle");
		listColNamesWhere.add("ecole");
		listArgsWhere.add("ESEO");
		Assertions.assertEquals(sqlAttendu, SQLTools.updateSQL(table, listColNames, listArgs, null, listArgsWhere));
		Assertions.assertEquals(sqlAttendu, SQLTools.updateSQL(table, listColNames, listArgs, listColNamesWhere, null));
		Assertions.assertEquals(sqlAttendu, SQLTools.updateSQL(table, listColNames, listArgs));
	}
}
