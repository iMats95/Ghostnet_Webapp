package de.ghostnet.model;

import java.io.Serializable;
import javax.persistence.*;


@Entity
public class Geisternetz  implements Serializable
{
	
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private double breitengrad;
	private double laengengrad;
	private float groesse;

	// Damit die Status-Werte in der Datenbank als String gespeichert werden
	@Enumerated(EnumType.STRING)
	private Status status;
	
	public Geisternetz() { }
	
// Getter & Setter
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public double getBreitengrad() {
		return breitengrad;
	}
	
	public void setBreitengrad(double breitengrad) {
		this.breitengrad = breitengrad;
	}
	
	public double getLaengengrad() {
		return laengengrad;
	}
	
	public void setLaengengrad(double laengengrad) {
		this.laengengrad = laengengrad;
	}
	
	public float getGroesse() {
		return groesse;
	}
	
	public void setGroesse(float groesse) {
		this.groesse = groesse;
	}
	
	public Status getStatus() {
		return status;
	}
	
	public void setStatus(Status status) {
		this.status = status;
	}
	
    public static enum Status {
        GEMELDET,
        BERGUNG_BEVORSTEHEND,
        GEBORGEN,
        VERSCHOLLEN
    }

	// Beziehung zu Person.java
	@ManyToOne
	@JoinColumn(name = "melder_id")
	private Person melder;
	
	@ManyToOne
	@JoinColumn(name = "berger_id")
	private Person berger;
	
	// Getter & Setter für Beziehungs-Attribute:
	public Person getMelder() {
	    return melder;
	}

	public void setMelder(Person melder) {
	    this.melder = melder;
	}

	public Person getBerger() {
	    return berger;
	}

	public void setBerger(Person berger) {
	    this.berger = berger;
	}
}




