package de.ghostnet.dao;

import javax.persistence.*;
import de.ghostnet.model.Person;
import jakarta.enterprise.context.RequestScoped;


@RequestScoped
public class PersonDAO   {

	// Person in der Datenbank speichern
	public void speicherePerson(Person person) {
	    EntityManager em = Factory.getEntityManagerFactory().createEntityManager();
	    try {
	        em.getTransaction().begin();
	        em.persist(person);
	        em.getTransaction().commit();
	    } catch (Exception e) {
	        em.getTransaction().rollback();
	        System.out.println("Fehler beim Speichern: " + e.getMessage());
	    } finally {
	        em.close();
	    }
	}
	
	// Person in der Datenbank suchen
	public Person findePerson(String name, String telefonnr) {
	    EntityManager em = Factory.getEntityManagerFactory().createEntityManager();
	    try {
	        TypedQuery<Person> query = em.createQuery(
	            "SELECT p FROM Person p WHERE p.name = :name AND p.telefonnr = :telefonnr", Person.class);
	        query.setParameter("name", name);
	        query.setParameter("telefonnr", telefonnr);
	        return query.getResultStream().findFirst().orElse(null);
	    } finally {
	        em.close();
	    }
	}
}