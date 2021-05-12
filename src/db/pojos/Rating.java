package db.pojos;

import java.io.Serializable;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.*;

@Entity

@Table(name = "rating")
public class Rating implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3167341965153243574L;

	@EmbeddedId
    ratingKey id;
	private Integer score;
	private String review;


	@ManyToOne(fetch = FetchType.LAZY)
	@MapsId("id_doctor")
	@JoinColumn(name = "id_doctor")
	private Doctor doc;

	@ManyToOne(fetch = FetchType.LAZY)
	@MapsId("id_patient")
	@JoinColumn(name = "id_patient")
	private Patient pat;

	public Rating() {
		super();

	}

	public Rating(Doctor doc, Patient pat, Integer score, String review) {
		super();
		this.score = score;
		this.review = review;
		this.doc = doc;
		this.pat = pat;
	}

	public Rating(Integer score, String review) {
		super();
		this.score = score;
		this.review = review;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((review == null) ? 0 : review.hashCode());
		result = prime * result + ((score == null) ? 0 : score.hashCode());
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
		Rating other = (Rating) obj;
		if (review == null) {
			if (other.review != null)
				return false;
		} else if (!review.equals(other.review))
			return false;
		if (score == null) {
			if (other.score != null)
				return false;
		} else if (!score.equals(other.score))
			return false;
		return true;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public String getReview() {
		return review;
	}

	public void setReview(String review) {
		this.review = review;
	}
	
	

//Rating doesnt print doctor and patient
	@Override
	public String toString() {
		return "Rating [score=" + score + ", review=" + review + "]";
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

@Embeddable

class ratingKey implements Serializable {
	@JoinColumn(name = "id_patient")
	Integer id_patient;
	@JoinColumn(name = "id_doctor")
	Integer id_doctor;
	
	
	
	
	
	public ratingKey() {
		super();
	}



	public ratingKey(Integer id_patient, Integer id_doctor) {
		super();
		this.id_patient = id_patient;
		this.id_doctor = id_doctor;
	}
	
	
	
	public Integer getId_patient() {
		return id_patient;
	}



	public void setId_patient(Integer id_patient) {
		this.id_patient = id_patient;
	}



	public Integer getId_doctor() {
		return id_doctor;
	}



	public void setId_doctor(Integer id_doctor) {
		this.id_doctor = id_doctor;
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id_doctor == null) ? 0 : id_doctor.hashCode());
		result = prime * result + ((id_patient == null) ? 0 : id_patient.hashCode());
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
		ratingKey other = (ratingKey) obj;
		if (id_doctor == null) {
			if (other.id_doctor != null)
				return false;
		} else if (!id_doctor.equals(other.id_doctor))
			return false;
		if (id_patient == null) {
			if (other.id_patient != null)
				return false;
		} else if (!id_patient.equals(other.id_patient))
			return false;
		return true;
	}
	
	

}
