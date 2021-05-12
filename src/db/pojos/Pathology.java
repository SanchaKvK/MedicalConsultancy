package db.pojos;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name="pathology")

public class Pathology implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7155856944921964272L;
	@Id
	@GeneratedValue(generator="pathology")
	@TableGenerator(name="pathology", table="sqlite_sequence",
	    pkColumnName="name", valueColumnName="seq", pkColumnValue="pathology")
	private Integer id_pathology;
	private String name;
	private String type;
	@ManyToMany(mappedBy="pathologies")
	private List<Patient>patients;
	public Pathology() {
		super();
		
	}
	
	
	//used in the application
	public Pathology(Integer id_pathology, String name, String type) {
		super();
		this.id_pathology = id_pathology;
		this.name = name;
		this.type = type;
		this.patients= new ArrayList<Patient>();
	}



	public Pathology(String name, String type) {
		super();
		this.name = name;
		this.type = type;
	}


	public Pathology(Integer id_pathology, String name, String type, List<Patient> patients) {
		super();
		this.id_pathology = id_pathology;
		this.name = name;
		this.type = type;
		this.patients = patients;
	}
	
	
	//Pathology doesnt print patients
	@Override
	public String toString() {
		return "Pathology [id_pathology=" + id_pathology + ", name=" + name + ", type=" + type + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id_pathology == null) ? 0 : id_pathology.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pathology other = (Pathology) obj;
		if (id_pathology == null) {
			if (other.id_pathology != null)
				return false;
		} else if (!id_pathology.equals(other.id_pathology))
			return false;
		return true;
	}
	public Integer getId_pathology() {
		return id_pathology;
	}
	public void setId_pathology(Integer id_pathology) {
		this.id_pathology = id_pathology;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public List<Patient> getPatients() {
		return patients;
	}
	public void setPatients(List<Patient> patients) {
		this.patients = patients;
	}
	
	
	
}
