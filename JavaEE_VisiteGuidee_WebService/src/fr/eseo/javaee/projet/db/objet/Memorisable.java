/**
 *
 */
package fr.eseo.javaee.projet.db.objet;

import java.util.List;

/**
 *	Interface traduisant le comportement que doit pouvoir faire un objet mémorisable dans une base de données
 *
 */

public interface Memorisable {

	/**
	 * Renvoie le nom de la table DANS la Base de Donnee
	 * @return le nom de la table DANS la Base de Donnee
	 */
	public String getNomTable();

	/**
	 * Renvoie la liste des noms des attributs DANS la Base de Donnee
	 * @return la liste des noms des attributs DANS la Base de Donnee
	 */
	public List<String> getListeNomAttributs();

	/**
	 * Renvoie la liste des valeurs des attributs actuels formatés pour la base de données
	 * @return la liste des valeurs des attributs actuels formatés pour la base de données
	 */
	public List<String> getListeAttributs();

	/**
	 * Valorise les attributs de l'objet avec les valeurs dans la liste
	 */
	public void setListeAttributs(List<String> listeNouvellesValeurs);
}
