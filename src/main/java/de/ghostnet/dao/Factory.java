// Eigene Java erstellt, um nur einmal eine EntityManagerFacotry zu erstellen (Best practice)

package de.ghostnet.dao;
import javax.persistence.*;

public class Factory {
	
	private static final EntityManagerFactory emf = 
	        Persistence.createEntityManagerFactory("ghostnetWebapp");
	
	public static EntityManagerFactory getEntityManagerFactory() {
        return emf;
	}
}

