package fr.eseo.javaee.projet.db.objet;

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

}
