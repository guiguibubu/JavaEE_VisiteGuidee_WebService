package fr.eseo.javaee.projet.db.objet;

import java.time.LocalDateTime;

public class Visite {

	private int codeVisite;
	private String typeDeVisite;
	private String ville;
	private LocalDateTime dateVisite;
	private float prix;

	public Visite() {
		this(0,"","",LocalDateTime.now(),0);
	}

	public Visite(int codeVisite, String typeDeVisite, String ville, LocalDateTime dateVisite, float prix) {
		this.codeVisite = codeVisite;
		this.typeDeVisite = typeDeVisite;
		this.ville = ville;
		this.dateVisite = dateVisite;
		this.prix = prix;
	}

	public boolean isOuverte() {
		return LocalDateTime.now().isBefore(this.dateVisite);
	}

	public int getCodeVisite() {return this.codeVisite;}
	public void setCodeVisite(int codeVisite) {this.codeVisite = codeVisite;}

	public String getTypeDeVisite() {return this.typeDeVisite;}
	public void setTypeDeVisite(String typeDeVisite) {this.typeDeVisite = typeDeVisite;}

	public String getVille() {return this.ville;}
	public void setVille(String ville) {this.ville = ville;}

	public LocalDateTime getDateVisite() {return this.dateVisite;}
	public void setDateVisite(LocalDateTime dateVisite) {this.dateVisite = dateVisite;}

	public float getPrix() {return this.prix;}
	public void setPrix(float prix) {this.prix = prix;}

}
