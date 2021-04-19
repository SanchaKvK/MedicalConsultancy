package db.pojos;

import java.io.Serializable;

public class Prescription implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -9167640245820196360L;
	private Integer id_prescription;
	private String name;
	private Integer doses;
	private Integer duration;
	private String notes;
	private Video_consultation vd; 
	
	
	public Integer getId_prescription() {
		return id_prescription;
	}
	
	
	
	
	public Prescription() {
		super();
		
	}
	
	


	public Video_consultation getVd() {
		return vd;
	}




	public void setVd(Video_consultation vd) {
		this.vd = vd;
	}




	public Prescription(Integer id_prescription, String name, Integer doses, Integer duration, String notes,
			Video_consultation vd) {
		super();
		this.id_prescription = id_prescription;
		this.name = name;
		this.doses = doses;
		this.duration = duration;
		this.notes = notes;
		this.vd = vd;
	}
	
	



public Prescription(String name, Integer doses, Integer duration, String notes, Video_consultation vd) {
		super();
		this.name = name;
		this.doses = doses;
		this.duration = duration;
		this.notes = notes;
		this.vd = vd;
	}






public Prescription(Integer id_prescription, String name, Integer doses, Integer duration, String notes) {
	super();
	this.id_prescription = id_prescription;
	this.name = name;
	this.doses = doses;
	this.duration = duration;
	this.notes = notes;
}




	//Prescription does not print vd,id_prescription
	@Override
	public String toString() {
		return "Prescription [ name=" + name + ", doses=" + doses
				+ ", duration=" + duration + ", notes=" + notes + "]";
	}




	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id_prescription == null) ? 0 : id_prescription.hashCode());
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
		Prescription other = (Prescription) obj;
		if (id_prescription == null) {
			if (other.id_prescription != null)
				return false;
		} else if (!id_prescription.equals(other.id_prescription))
			return false;
		return true;
	}




	public void setId_prescription(Integer id_prescription) {
		this.id_prescription = id_prescription;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getDoses() {
		return doses;
	}
	public void setDoses(Integer doses) {
		this.doses = doses;
	}
	public Integer getDuration() {
		return duration;
	}
	public void setDuration(Integer duration) {
		this.duration = duration;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	
	
	
	
}
