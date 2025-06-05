package de.ghostnet.view;

import java.io.Serializable;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;


@Named("geisternetzBean")
@ViewScoped
public class GeisternetzBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private double breitengrad;
    private double laengengrad;
    private float groesse;
	
 // Getter & Setter
    
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
    
}
