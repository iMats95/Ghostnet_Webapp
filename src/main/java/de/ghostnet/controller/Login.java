package de.ghostnet.controller;

import de.ghostnet.dao.PersonDAO;

import de.ghostnet.view.PersonBean;
import de.ghostnet.model.Person;
import jakarta.faces.view.ViewScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;

@Named
@ViewScoped
public class Login implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
    private PersonBean personBean;

	// Zum Anmelden (Speichern des Namens/Telefonnr, wenn sich jemand zum Bergen einträgt)
    public String anmelden() {
        PersonDAO dao = new PersonDAO();

        Person vorhandenePerson = dao.findePerson(
            personBean.getName(), 
            personBean.getTelefonnr()
        );

        if (vorhandenePerson == null) {
            Person neuePerson = new Person();
            neuePerson.setName(personBean.getName());
            neuePerson.setTelefonnr(personBean.getTelefonnr());
            dao.speicherePerson(neuePerson);
            vorhandenePerson = neuePerson;
        }
        
        personBean.setId(vorhandenePerson.getId());

        FacesContext.getCurrentInstance().getExternalContext()
            .getSessionMap().put("benutzer", vorhandenePerson);

        return "dashboard.xhtml?faces-redirect=true";
    }
}