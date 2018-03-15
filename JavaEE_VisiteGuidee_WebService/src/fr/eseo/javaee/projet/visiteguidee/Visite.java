package fr.eseo.javaee.projet.visiteguidee;

import java.time.LocalDate;

public class Visite {

	int codeVisite;
	String typeDeVisite;
	String ville;
	LocalDate dateVisite;
	float prix;

	public Visite() {
		this.codeVisite = 0;
		this.typeDeVisite = "";
		this.ville = "";
		this.dateVisite = LocalDate.now();
		this.prix = 0;
	}

	public Visite(int codeVisite, String typeDeVisite, String ville, LocalDate dateVisite, float prix) {
		this.codeVisite = codeVisite;
		this.typeDeVisite = typeDeVisite;
		this.ville = ville;
		this.dateVisite = dateVisite;
		this.prix = prix;
	}





}
