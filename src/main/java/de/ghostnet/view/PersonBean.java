package de.ghostnet.view;

import java.io.Serializable;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;

@Named("personBean")
@SessionScoped
public class PersonBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;
    private String telefonnr;
    private Long id;



    // Getter und Setter für Name
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Getter und Setter für Telefonnr
    public String getTelefonnr() {
        return telefonnr;
    }

    public void setTelefonnr(String telefonnr) {
        this.telefonnr = telefonnr;
    }
    
    public Long getId() {
    	return id;
    }

	public void setId(Long id) {
		this.id = id;
	}
}