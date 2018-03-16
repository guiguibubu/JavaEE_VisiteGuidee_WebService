package fr.eseo.javaee.projet.db.objet;

import java.time.LocalDate;

public class Visite {

	private int codeVisite;
	private String typeDeVisite;
	private String ville;
	private LocalDate dateVisite;
	private float prix;

	public Visite() {
		this(0,"","",LocalDate.now(),0);
	}

	public Visite(int codeVisite, String typeDeVisite, String ville, LocalDate dateVisite, float prix) {
		this.codeVisite = codeVisite;
		this.typeDeVisite = typeDeVisite;
		this.ville = ville;
		this.dateVisite = dateVisite;
		this.prix = prix;
	}

	public int getCodeVisite() {return this.codeVisite;}
	public void setCodeVisite(int codeVisite) {this.codeVisite = codeVisite;}

	public String getTypeDeVisite() {return this.typeDeVisite;}
	public void setTypeDeVisite(String typeDeVisite) {this.typeDeVisite = typeDeVisite;}

	public String getVille() {return this.ville;}
	public void setVille(String ville) {this.ville = ville;}

	public LocalDate getDateVisite() {return this.dateVisite;}
	public void setDateVisite(LocalDate dateVisite) {this.dateVisite = dateVisite;}

	public float getPrix() {return this.prix;}
	public void setPrix(float prix) {this.prix = prix;}

}
