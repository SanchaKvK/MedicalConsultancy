package db.pojos;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class Video_consultation implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1367040608239901293L;
	private Integer id_video;
	private Date consultation_date;
	private Time consultatiton_time;
	private Integer duration;
	private String type;
	private String notes;
	private List<Prescription>prescription;
	private Doctor doc;
	private Patient pat;
	
	public Video_consultation() {
		super();
		this.prescription=new ArrayList<Prescription>();
		
	}
	
	
	
	


	public Video_consultation(Date consultation_date, Time consultatiton_time, String type) {
		super();
		this.consultation_date = consultation_date;
		this.consultatiton_time = consultatiton_time;
		this.type = type;
		this.prescription=new ArrayList<Prescription>();
	}






	public Video_consultation(Integer id_video, Date consultation_date, Time consultatiton_time, int duration,
			String type, String notes) {
		super();
		this.id_video = id_video;
		this.consultation_date = consultation_date;
		this.consultatiton_time = consultatiton_time;
		this.duration = duration;
		this.type = type;
		this.notes = notes;
		this.prescription=new ArrayList<Prescription>();
	}






	public Video_consultation(Date consultation_date, Time consultatiton_time, String type, Doctor doc, Patient pat) {
		super();
		this.consultation_date = consultation_date;
		this.consultatiton_time = consultatiton_time;
		this.type = type;
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
		this.type = type;
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
		this.type = type;
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
		this.type = type;
		this.notes = notes;
		this.prescription = prescription;
		this.doc= doc;
		this.pat=pat;
	}

	
	








//Video_consultation prints doc,pat and prescription

	@Override
	public String toString() {
		return "Video_consultation [id_video=" + id_video + ", consultation_date=" + consultation_date
				+ ", consultatiton_time=" + consultatiton_time + ", duration=" + duration + ", type=" + type
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
		return type;
	}

	public void setType(String type) {
		this.type = type;
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
