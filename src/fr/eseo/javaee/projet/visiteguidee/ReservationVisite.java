package fr.eseo.javaee.projet.visiteguidee;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.jws.WebService;

import fr.eseo.javaee.projet.db.GestionDB;
import fr.eseo.javaee.projet.db.objet.Client;
import fr.eseo.javaee.projet.db.objet.ConstructorFactory;
import fr.eseo.javaee.projet.db.objet.Reservation;
import fr.eseo.javaee.projet.db.objet.Visite;

@WebService(targetNamespace = "http://visiteguidee.projet.javaee.eseo.fr/", endpointInterface = "fr.eseo.javaee.projet.visiteguidee.ReservationVisiteSEI", portName = "ReservationVisitePort", serviceName = "ReservationVisiteService")
public class ReservationVisite implements ReservationVisiteSEI {

	/**
	 * Renvoie la liste des réservations faites par le client avec idClient comme ID
	 * @param idClient l'ID du client pour lequel on recherche les réservations
	 * @return la liste des réservations faites par le client avec idClient comme ID
	 */
	@Override
	public List<Reservation> trouverReservationByIdClient(int idClient) {
		List<Reservation> listeReservation = new ArrayList<>();
		try {
			if(idClient > 0) {
				Client client = GestionDB.searchClientById(idClient);
				if(client != null && client.getIdClient() > 0) {
					listeReservation = GestionDB.searchReservation(null, client, -1, false);
					listeReservation.addAll(GestionDB.searchReservation(null, client, -1, true));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return listeReservation;
	}

	/**
	 * Renvoie le client avec le nom et prénom spécifié, et le créé s'il n'existe pas
	 * @param nom Le nom du client que l'on recherche
	 * @param prenom Le prenom du client que l'on recherche
	 * @return Le client que l'on recherche, ou le créé s'il n'existe pas. L'ID du client est égal à -1 en cas de problème
	 */
	@Override
	public Client trouverClient(String nom, String prenom) {
		Client client = ConstructorFactory.createClient();
		try {
			if(GestionDB.existeClient(prenom, nom)) {
				client = GestionDB.searchClient(prenom, nom);
			} else {
				client.setIdClient(GestionDB.ajoutClient(prenom, prenom));
				client.setNom(nom);
				client.setPrenom(prenom);
			}
		} catch (SQLException e) {
			client.setIdClient(-1);
			e.printStackTrace();
		}
		return client;
	}

	/**
	 * Renvoie la liste des visites ayant les attributs en commun avec la visite recherché
	 * @param la visite dont les attributs sont les critères de recherche. Les attributs null ne sont pas considérés dans la recherche
	 * @return La liste des visites ayant les attributs en commun avec la visite recherché
	 */
	@Override
	public List<Visite> trouverVisite (Visite maVisite) {
		List<Visite> listVisite = new ArrayList<>();
		try {
			listVisite = GestionDB.searchVisite(maVisite);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return listVisite;
	}

	/**
	 * Enregistre la réservation et renvoie le code de réservation généré
	 * @param La réservation que l'on souhaite enregistrer
	 * @return le code de réservation généré, ou 0 en cas de problème
	 */
	@Override
	public int reserverVisite (Reservation maReservation) {
		int codeReservation = 0;
		try {
			codeReservation = GestionDB.ajoutReservation(maReservation);
		} catch (SQLException e) {
			codeReservation = -1;
			e.printStackTrace();
		}
		return codeReservation;
	}

	/**
	 * Enregistre le paiement de la reservation du code saisie
	 * @param Le code de la réservation dont l'on veut enregistrer le paiement
	 * @return Le paiement est enregistré
	 */
	@Override
	public boolean payerVisite (int monCodeReservation) {
		Reservation reservation = ConstructorFactory.createReservation();
		try {
			reservation = GestionDB.searchReservationById(monCodeReservation);
			if(reservation.getCodeReservation() != 0) {
				reservation.setPaiementEffectue(true);
				GestionDB.updateReservationById(reservation);
				reservation = GestionDB.searchReservationById(monCodeReservation);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return reservation.isPaiementEffectue();
	}

	/**
	 * Annule la réservation précisée par le code
	 * @return L'annulation est bien enregistrée
	 */
	@Override
	public boolean annulerVisite (int monCodeReservation) {
		boolean annulationReusie = false;
		try {
			GestionDB.supprimerReservationById(monCodeReservation);
			annulationReusie = !GestionDB.existeReservationById(monCodeReservation);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return annulationReusie;
	}
}
