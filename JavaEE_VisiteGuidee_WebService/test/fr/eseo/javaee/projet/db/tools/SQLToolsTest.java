package fr.eseo.javaee.projet.db.tools;

import java.util.ArrayList;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class SQLToolsTest {

	// Cas Nominal
	@Test
	void testSelectSQL() {
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
	void testDeleteSQL() {
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
	void testUpdateSQL() {
		String sqlAttendu = "UPDATE client SET prenom = 'Guillaume', nom = 'Buchle' WHERE ecole = 'ESEO'";
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
		Assertions.assertEquals(sqlAttendu, SQLTools.updateSQL(table, listColNames, listArgs, listColNamesWhere, listArgsWhere));
	}

	// Cas taille liste non concordantes
	@Test
	void testSelectSQLTailleListe() {
		String table = "client";
		ArrayList<String> listColNames = new ArrayList<String>();
		ArrayList<String> listArgs = new ArrayList<String>();
		listColNames.add("prenom");
		listColNames.add("nom");
		listArgs.add("Guillaume");
		Assertions.assertNull(SQLTools.selectSQL(table, listColNames, listArgs));
	}

	@Test
	void testInsertSQLTailleListe() {
		String table = "client";
		ArrayList<String> listColNames = new ArrayList<String>();
		ArrayList<String> listArgs = new ArrayList<String>();
		listColNames.add("prenom");
		listColNames.add("nom");
		listArgs.add("Guillaume");
		Assertions.assertNull(SQLTools.insertSQL(table, listColNames, listArgs));
	}

	@Test
	void testDeleteSQLTailleListe() {
		String table = "client";
		ArrayList<String> listColNames = new ArrayList<String>();
		ArrayList<String> listArgs = new ArrayList<String>();
		listColNames.add("prenom");
		listColNames.add("nom");
		listArgs.add("Guillaume");
		Assertions.assertNull(SQLTools.deleteSQL(table, listColNames, listArgs));
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
		Assertions.assertNull(SQLTools.updateSQL(table, listColNames, listArgs, listColNamesWhere, listArgsWhere));
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
		Assertions.assertNull(SQLTools.updateSQL(table, listColNames, listArgs, listColNamesWhere, listArgsWhere));
	}

	@Test
	void testUpdateSQLWhereVide_Null() {
		String sqlAttendu = "UPDATE client SET prenom = 'Guillaume', nom = 'Buchle'";
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
		String table = "client";
		ArrayList<String> listColNames = new ArrayList<String>();
		ArrayList<String> listArgs = new ArrayList<String>();
		listColNames.add("prenom");
		listColNames.add("nom");
		listArgs.add("Guillaume");
		listArgs.add("Buchle");
		Assertions.assertNull(SQLTools.selectSQL(table, null, listArgs));
		Assertions.assertNull(SQLTools.selectSQL(table, listColNames, null));
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
		Assertions.assertNull(SQLTools.insertSQL(table, null, listArgs));
		Assertions.assertNull(SQLTools.insertSQL(table, listColNames, null));
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
		Assertions.assertNull(SQLTools.deleteSQL(table, null, listArgs));
		Assertions.assertNull(SQLTools.deleteSQL(table, listColNames, null));
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
		Assertions.assertNull(SQLTools.updateSQL(table, null, listArgs, listColNamesWhere, listArgsWhere));
		Assertions.assertNull(SQLTools.updateSQL(table, listColNames, null, listColNamesWhere, listArgsWhere));
	}

	@Test
	void testUpdateSQLListeNullWhere() {
		String sqlAttendu = "UPDATE client SET prenom = 'Guillaume', nom = 'Buchle'";
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
