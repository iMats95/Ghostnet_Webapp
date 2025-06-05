package de.ghostnet.controller;

import de.ghostnet.model.Geisternetz;
import de.ghostnet.model.Person;
import de.ghostnet.view.GeisternetzBean;
import de.ghostnet.view.PersonBean;
import de.ghostnet.dao.GeisternetzDAO;
import de.ghostnet.dao.PersonDAO;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import java.io.Serializable;
import jakarta.inject.Named;



@Named
@RequestScoped
public class MeldeNetz implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
    private GeisternetzDAO geisternetzDAO;

    @Inject
    private PersonDAO personDAO;

    @Inject
    private GeisternetzBean geisternetzBean;

    @Inject
    private PersonBean personBean;  
    
    private boolean anonym;

    public boolean isAnonym() {
        return anonym;
    }

    public void setAnonym(boolean anonym) {
        this.anonym = anonym;
    }
    
    public void resetForm() {
        geisternetzBean.setBreitengrad(0);
        geisternetzBean.setLaengengrad(0);
        geisternetzBean.setGroesse(0f);
    }    
    
    public void absenden() {
        Geisternetz netz = new Geisternetz();
        netz.setBreitengrad(geisternetzBean.getBreitengrad());
        netz.setLaengengrad(geisternetzBean.getLaengengrad());
        netz.setGroesse(geisternetzBean.getGroesse());
        netz.setStatus(Geisternetz.Status.GEMELDET);

        if (anonym) {
            Person anonymPerson = geisternetzDAO.findeAnonymePerson();
            netz.setMelder(anonymPerson);
        } else {
            Person person = personDAO.findePerson(personBean.getName(), personBean.getTelefonnr());
            if (person == null) {
                person = new Person();
                person.setName(personBean.getName());
                person.setTelefonnr(personBean.getTelefonnr());
                personDAO.speicherePerson(person);
            }
            netz.setMelder(person);
        }

        geisternetzDAO.speichereNetz(netz);
        
        resetForm();
        
     // FacesMessage hinzufügen
        FacesContext.getCurrentInstance().addMessage(null, 
            new FacesMessage(FacesMessage.SEVERITY_INFO, "Erfolg", "Das Netz wurde erfolgreich erstellt."));
        
    }
}
