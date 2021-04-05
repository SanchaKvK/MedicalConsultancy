package db.pojos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Doctor implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1691412521236621262L;
	private Integer id_doctor;
	private String specialization;
	private String name;
	private String hospital;
	private List<Video_consultation>videos;
	private List<Rating>ratings;
	public Doctor() {
		super();
		this.videos=new ArrayList<Video_consultation>();
		this.ratings=new ArrayList<Rating>();
		
	}
	
	
	
	
	public Doctor(String specialization, String name, String hospital) {
		super();
		this.specialization = specialization;
		this.name = name;
		this.hospital = hospital;
		this.videos=new ArrayList<Video_consultation>();
		this.ratings=new ArrayList<Rating>();
	}




	public Doctor(String specialization, String name) {
		super();
		this.specialization = specialization;
		this.name = name;
		this.videos=new ArrayList<Video_consultation>();
		this.ratings=new ArrayList<Rating>();
	}




	public Doctor(Integer id_doctor, String specialization, String name, String hospital, List<Rating> ratings) {
		super();
		this.id_doctor = id_doctor;
		this.specialization = specialization;
		this.name = name;
		this.hospital = hospital;
		this.ratings = ratings;
	}




	public Doctor(Integer id_doctor, String specialization, String name, String hospital) {
		super();
		this.id_doctor = id_doctor;
		this.specialization = specialization;
		this.name = name;
		this.hospital = hospital;
		this.videos=new ArrayList<Video_consultation>();
		this.ratings=new ArrayList<Rating>();
	}




	public Doctor(Integer id_doctor, String specialization, String name, String hospital,
			List<Video_consultation> videos, List<Rating> ratings) {
		super();
		this.id_doctor = id_doctor;
		this.specialization = specialization;
		this.name = name;
		this.hospital = hospital;
		this.videos = videos;
		this.ratings = ratings;
	}



	

	










//Doctor prints ratings but not videos

	@Override
	public String toString() {
		return "Doctor [id_doctor=" + id_doctor + ", specialization=" + specialization + ", name=" + name
				+ ", hospital=" + hospital + ", ratings=" + ratings + "]";
	}




	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id_doctor== null) ? 0 : id_doctor.hashCode());
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
		Doctor other = (Doctor) obj;
		if (id_doctor == null) {
			if (other.id_doctor != null)
				return false;
		} else if (!id_doctor.equals(other.id_doctor))
			return false;
		return true;
	}




	
	public List<Rating> getRatings() {
		return ratings;
	}




	public void setRatings(List<Rating> ratings) {
		this.ratings = ratings;
	}




	public void setId_doctor(Integer id_doctor) {
		this.id_doctor = id_doctor;
	}




	public List<Video_consultation> getVideos() {
		return videos;
	}



	public void setVideos(List<Video_consultation> videos) {
		this.videos = videos;
	}



	public int getId_doctor() {
		return id_doctor;
	}
	public void setId_doctor(int id_doctor) {
		this.id_doctor = id_doctor;
	}
	public String getSpecialization() {
		return specialization;
	}
	public void setSpecialization(String specialization) {
		this.specialization = specialization;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getHospital() {
		return hospital;
	}
	public void setHospital(String hospital) {
		this.hospital = hospital;
	}
	
	
	
	

}
