package de.ghostnet.controller;

import de.ghostnet.dao.Factory;
import de.ghostnet.dao.GeisternetzDAO;
import de.ghostnet.dao.PersonDAO;
import de.ghostnet.model.Geisternetz;
import de.ghostnet.model.Geisternetz.Status;
import de.ghostnet.model.Person;
import de.ghostnet.view.PersonBean;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.faces.FacesException;
import jakarta.faces.view.ViewScoped;
import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.primefaces.PrimeFaces;

@Named("dashboardVerwalten")
@ViewScoped
public class DashboardVerwalten implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private GeisternetzDAO dao;
    
    @Inject
    private PersonBean personBean;
    
    @Inject
    private PersonDAO personDao;
    
    

    private Status statusFilter;

    // Gibt alle Netze zurück
    public List<Geisternetz> getAlleNetze() {
        return dao.findeAlle();
    }

    // Gibt gefilterte Netze zurück
    public List<Geisternetz> getGefilterteNetze() {
        if (statusFilter == null) {
            return dao.findeAlle();
        } else {
            return dao.findeNachStatus(statusFilter);
        }
    }

    // Getter und Setter für den Status-Filter
    public Status getStatusFilter() {
        return statusFilter;
    }

    public void setStatusFilter(Status statusFilter) {
        this.statusFilter = statusFilter;
    }

    // Für Dropdown-Auswahl (optional)
    public Status[] getStatusWerte() {
        return Status.values();
    }
    
    // Für Dropdown-Auswahl in der View
    public Status[] getStatusListe() {
        return Status.values();
    }

    // Wird vom p:ajax aufgerufen
    public void filterGeisternetze() {
    }
    
    public Geisternetz.Status getStatusGEMELDET() {
        return Geisternetz.Status.GEMELDET;
    }
    
    
    public void bergungUebernehmen(Geisternetz netz) {
        // Aktuelle eingeloggte Person holen
        String name = personBean.getName();
        String telefonnr = personBean.getTelefonnr();
        Person person = personDao.findePerson(name, telefonnr);

        if (netz == null || person == null) {
            System.out.println("Netz oder Person nicht gefunden.");
            return;
        }

        EntityManager em = Factory.getEntityManagerFactory().createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            // Netz aus DB neu laden
            Geisternetz managedNetz = em.find(Geisternetz.class, netz.getId());

            // Berger setzen + Status aktualisieren
            managedNetz.setBerger(person);
            managedNetz.setStatus(Geisternetz.Status.BERGUNG_BEVORSTEHEND);

            em.merge(managedNetz);
            tx.commit();

            // UI aktualisieren
            PrimeFaces.current().ajax().update("dashboardForm:netzTabelle");

        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw new FacesException("Bergung übernehmen fehlgeschlagen", e);
        } finally {
            em.close();
        }
    }

    
    private Long ausgewähltesNetzId;
    private Status neuerStatus;
    
    // Getter und Setter 
    public Long getAusgewähltesNetzId() { 
    	return ausgewähltesNetzId; }
    
    public void setAusgewähltesNetzId(Long id) { 
    	this.ausgewähltesNetzId = id; }

    public Status getNeuerStatus() { 
    	return neuerStatus; }
    
    public void setNeuerStatus(Status status) { 
    	this.neuerStatus = status; }

    public void statusAendern(Geisternetz netz) {
        EntityManager em = Factory.getEntityManagerFactory().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        
        try {
            tx.begin();
            
            Geisternetz managedNetz = em.find(Geisternetz.class, netz.getId());
            
            // Prüfung der Berechtigung
            if(managedNetz.getBerger() == null 
               || !managedNetz.getBerger().getId().equals(personBean.getId())) {
                return;
            }
            
            // Status setzen
            managedNetz.setStatus(neuerStatus);
            
            // Explizites Merge
            em.merge(managedNetz);
            tx.commit();
            
            // Benutzeroberfläche aktualisieren
            PrimeFaces.current().ajax().update("dashboardForm:netzTabelle");
            
        } catch (Exception e) {
            if(tx.isActive()) tx.rollback();
            throw new FacesException("Statusänderung fehlgeschlagen", e);
        } finally {
            em.close();
        }
    }

}