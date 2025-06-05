package de.ghostnet.model;
import java.util.List;
import javax.persistence.*;



// Entity: Damit das Objekt in der Datenbank gespeichert wird
// uniqueConstraints: Damit keine doppelten Name/Tel.-Kombinationen gespeichert werden 
@Entity
@Table(
		name = "Person",
		uniqueConstraints = @UniqueConstraint(columnNames = {"name","telefonnr"})
)


public class Person {

	
// Attribute der Klasse Person
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String name;
	private String telefonnr;
		
	public Person() {
		}
	
// Getter und Setter
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getTelefonnr() {
		return telefonnr;
	}
	
	public void setTelefonnr(String telefonnr) {
		this.telefonnr = telefonnr;
	}

// Beziehung zu Geisternetz.java 
	@OneToMany(mappedBy = "melder")
	private List<Geisternetz> gemeldeteNetze;

	@OneToMany(mappedBy = "berger")
	private List<Geisternetz> geborgeneNetze;
	
// Getter & Setter für Beziehungs-Attribute
	
	public List<Geisternetz> getGemeldeteNetze() {
	    return gemeldeteNetze;
	}

	public void setGemeldeteNetze(List<Geisternetz> gemeldeteNetze) {
	    this.gemeldeteNetze = gemeldeteNetze;
	}

	public List<Geisternetz> getGeborgeneNetze() {
	    return geborgeneNetze;
	}

	public void setGeborgeneNetze(List<Geisternetz> geborgeneNetze) {
	    this.geborgeneNetze = geborgeneNetze;
	}
}


