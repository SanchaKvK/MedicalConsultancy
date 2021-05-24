package db.pojos;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import medicalconsultancyxml.utils.*;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;


@Entity
@Table(name="videoconsultation")
//@XmlAccessorType(XmlAccessType.FIELD)
//@XmlRootElement(name = "Video_consultation")
public class Video_consultation implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1367040608239901293L;
	@Id
	@GeneratedValue(generator = "videoconsultation")
	@TableGenerator(name = "videoconsultation", table = "sqlite_sequence", pkColumnName = "name", valueColumnName = "seq", pkColumnValue = "videoconsultation")
	//@XmlAttribute
	private Integer id_video;
	//@XmlElement
	//@XmlJavaTypeAdapter(SQLDateAdapter.class)
	private Date consultation_date;
	
	private Time consultatiton_time;
	private Integer duration;
	private String type_of_call;
	private String notes;
	@OneToMany(mappedBy="vd")
	private List<Prescription>prescription;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="id_doctor")
	private Doctor doc;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="id_patient")
	private Patient pat;
	
	public Video_consultation() {
		super();
		this.prescription=new ArrayList<Prescription>();
		
	}
	

	public Video_consultation(Date consultation_date, Time consultatiton_time, String type) {
		super();
		this.consultation_date = consultation_date;
		this.consultatiton_time = consultatiton_time;
		this.type_of_call = type;
		this.prescription=new ArrayList<Prescription>();
	}






	public Video_consultation(Integer id_video, Date consultation_date, Time consultatiton_time, int duration,
			String type, String notes) {
		super();
		this.id_video = id_video;
		this.consultation_date = consultation_date;
		this.consultatiton_time = consultatiton_time;
		this.duration = duration;
		this.type_of_call = type;
		this.notes = notes;
		this.prescription=new ArrayList<Prescription>();
	}






	public Video_consultation(Date consultation_date, Time consultatiton_time, String type, Doctor doc, Patient pat) {
		super();
		this.consultation_date = consultation_date;
		this.consultatiton_time = consultatiton_time;
		this.type_of_call = type;
		this.doc = doc;
		this.pat = pat;
		this.prescription=new ArrayList<Prescription>();
	}






	public Video_consultation(Integer id_video, Date consultation_date, Time consultatiton_time, int duration,
			String type, String notes, Doctor doc, Patient pat) {
		super();
		this.id_video = id_video;
		this.consultation_date = consultation_date;
		this.consultatiton_time = consultatiton_time;
		this.duration = duration;
		this.type_of_call = type;
		this.notes = notes;
		this.doc = doc;
		this.pat = pat;
		this.prescription=new ArrayList<Prescription>();
	}






	public Video_consultation(Integer id_video, Date consultation_date, Time consultatiton_time, Integer duration,
			String type, String notes, List<Prescription> prescription, Doctor doc, Patient pat) {
		super();
		this.id_video = id_video;
		this.consultation_date = consultation_date;
		this.consultatiton_time = consultatiton_time;
		this.duration = duration;
		this.type_of_call = type;
		this.notes = notes;
		this.prescription = prescription;
		this.doc = doc;
		this.pat = pat;
	}






	public Video_consultation(Integer id_video, Date consultation_date, Time consultatiton_time, int duration,
			String type, String notes, List<Prescription> prescription, Doctor doc, Patient pat) {
		super();
		this.id_video = id_video;
		this.consultation_date = consultation_date;
		this.consultatiton_time = consultatiton_time;
		this.duration = duration;
		this.type_of_call = type;
		this.notes = notes;
		this.prescription = prescription;
		this.doc= doc;
		this.pat=pat;
	}

	
	








//Video_consultation prints doc,pat and prescription

	@Override
	public String toString() {
		return "Video_consultation [id_video=" + id_video + ", consultation_date=" + consultation_date
				+ ", consultatiton_time=" + consultatiton_time + ", duration=" + duration + ", type=" + type_of_call
				+ ", notes=" + notes + ", prescription=" + prescription + ", doctor=" + doc.getName() + ", patient=" + pat.getName() + "]";
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id_video == null) ? 0 : id_video.hashCode());
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
		Video_consultation other = (Video_consultation) obj;
		if (id_video == null) {
			if (other.id_video != null)
				return false;
		} else if (!id_video.equals(other.id_video))
			return false;
		return true;
	}

	public int getId_video() {
		return id_video;
	}

	public void setId_video(int id_video) {
		this.id_video = id_video;
	}

	public Date getConsultation_date() {
		return consultation_date;
	}

	public void setConsultation_date(Date consultation_date) {
		this.consultation_date = consultation_date;
	}

	public Time getConsultatiton_time() {
		return consultatiton_time;
	}

	public void setConsultatiton_time(Time consultatiton_time) {
		this.consultatiton_time = consultatiton_time;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public String getType() {
		return type_of_call;
	}

	public void setType(String type) {
		this.type_of_call = type;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public List<Prescription> getPrescription() {
		return prescription;
	}

	public void setPrescription(List<Prescription> prescription) {
		this.prescription = prescription;
	}

	// Additional method to add to a list
	public void addPrescription(Prescription p) {
		if (!this.prescription.contains(p)) {
			this.prescription.add(p);
		}
	}

	public Doctor getDoc() {
		return doc;
	}



	public void setDoc(Doctor doc) {
		this.doc = doc;
	}



	public Patient getPat() {
		return pat;
	}



	public void setPat(Patient pat) {
		this.pat = pat;
	}
	
	
	
	
	
	
}
