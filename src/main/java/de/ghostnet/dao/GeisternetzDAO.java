
package de.ghostnet.dao;

import java.util.List;
import javax.persistence.*;
import de.ghostnet.model.Geisternetz;
import de.ghostnet.model.Geisternetz.Status;
import de.ghostnet.model.Person;
import jakarta.enterprise.context.RequestScoped;


@RequestScoped
public class GeisternetzDAO  {

	public Person findeAnonymePerson() {
	    EntityManager em = Factory.getEntityManagerFactory().createEntityManager();
	    try {
	        TypedQuery<Person> query = em.createQuery(
	            "SELECT p FROM Person p WHERE p.name = :name AND p.telefonnr IS NULL", Person.class);
	        query.setParameter("name", "Anonym");
	        Person anonym = query.getResultStream().findFirst().orElse(null);

	        if (anonym == null) {
	            anonym = new Person();
	            anonym.setName("Anonym");
	            anonym.setTelefonnr(null);
	            em.getTransaction().begin();
	            em.persist(anonym);
	            em.getTransaction().commit();
	        }
	        return anonym;
	    } finally {
	        em.close();
	    }
	}
	
	public void speichereNetz(Geisternetz geisternetz) {
	    EntityManager em = Factory.getEntityManagerFactory().createEntityManager();
	    try {
	        em.getTransaction().begin();
	        em.persist(geisternetz);
	        em.getTransaction().commit();
	    } catch (Exception e) {
	        em.getTransaction().rollback();
	        System.out.println("Fehler beim Speichern: " + e.getMessage());
	    } finally {
	        em.close();
	    }
	}
	
		
	public void aktualisiere(Geisternetz geisternetz) {
	    // Leer lassen, wird jetzt direkt im Controller gehandhabt
	}
	
	public List<Geisternetz> findeAlle() {
	    EntityManager em = Factory.getEntityManagerFactory().createEntityManager();
	    try {
	        return em.createQuery("SELECT g FROM Geisternetz g", Geisternetz.class).getResultList();
	    } finally {
	        em.close();
	    }
	}

	public List<Geisternetz> findeNachStatus(Status status) {
	    EntityManager em = Factory.getEntityManagerFactory().createEntityManager();
	    try {
	        return em.createQuery("SELECT g FROM Geisternetz g WHERE g.status = :status", Geisternetz.class)
	                 .setParameter("status", status)
	                 .getResultList();
	    } finally {
	        em.close();
	    }
	}
	
	public Geisternetz findeNachId(Long id) {
	    EntityManager em = Factory.getEntityManagerFactory().createEntityManager();
	    try {
	        return em.find(Geisternetz.class, id);
	    } finally {
	        em.close();
	    }
	}

	
	
}