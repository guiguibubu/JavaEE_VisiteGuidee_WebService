package fr.eseo.javaee.projet.db.objet;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class ConstructorFactory {

	//RESERVATION

	public static Reservation createReservation(int codeReservation, Visite visite, Client client, int nombrePersonnes, boolean paiementEffectue) {
		Reservation reservation = new Reservation();
		reservation.setCodeReservation(codeReservation);
		reservation.setVisite(visite);
		reservation.setClient(client);
		reservation.setNombrePersonnes(nombrePersonnes);
		reservation.setPaiementEffectue(paiementEffectue);
		return reservation;
	}

	public static Reservation createReservation(Visite visite, Client client, int nombrePersonnes, boolean paiementEffectue) {
		return createReservation(0, visite, client, nombrePersonnes, paiementEffectue);
	}

	public static Reservation createReservation(int idReservation) {
		return createReservation(idReservation, null,null,0,false);
	}

	public static Reservation createReservation() {
		return createReservation(0);
	}

	//VISITE

	public static Visite createVisite(int codeVisite, String typeDeVisite, String ville, LocalDateTime dateVisite, float prix) {
		Visite visite = new Visite();
		visite.setCodeVisite(codeVisite);
		visite.setTypeDeVisite(typeDeVisite);
		visite.setVille(ville);
		visite.setDateVisite(dateVisite);
		visite.setPrix(prix);
		return visite;
	}

	public static Visite createVisite(String typeDeVisite, String ville, LocalDateTime dateVisite, float prix) {
		return createVisite(0, typeDeVisite, ville, dateVisite, prix);
	}

	public static Visite createVisite(int codeVisite) {
		return createVisite(codeVisite,"","",LocalDateTime.now(),0);
	}

	public static Visite createVisite() {
		return createVisite(0);
	}

	//CLIENT
	public static Client createClient() {
		return createClient(0);
	}

	public static Client createClient(int idClient) {
		return createClient(idClient, "", "");
	}

	public static Client createClient (String nom, String prenom) {
		return createClient(0, nom, prenom);
	}

	public static Client createClient (int idClient, String nom, String prenom) {
		return createClient(idClient, nom, prenom, LocalDate.MIN, "", 0,"", 0,"");
	}

	public static Client createClient (int idClient, String nom, String prenom, LocalDate dateNaissance, String adresse, int codePostal, String pays, int numTelephone, String mail) {
		Client client = new Client();
		client.setIdClient(idClient);
		client.setNom(nom);
		client.setPrenom(prenom);
		client.setDateNaissance(dateNaissance);
		client.setAdresse(adresse);
		client.setCodePostal(codePostal);
		client.setPays(pays);
		client.setNumTelephone(numTelephone);
		client.setMail(mail);
		return client;
	}
}
