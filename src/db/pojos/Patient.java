package db.pojos;


import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;


public class Patient implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2012426771680446727L;
	private Integer id_patient;
	private String name;
	private String gender;
	private Date birth;
	private int id;
	private String phone_number;
	private String postcode;
	private List<Video_consultation> videos;
	private List<Rating>ratings;
	private List<Pathology>pathologies;
	
	public Patient() {
		
		super();
		this.videos=new ArrayList<Video_consultation>();
		this.ratings=new ArrayList<Rating>();
		this.pathologies=new ArrayList<Pathology>();
	}
	

	

	




	public Patient(Integer id_patient, String name, String gender, Date birth, int id, String phone_number,
			String postcode) {
		super();
		this.id_patient = id_patient;
		this.name = name;
		this.gender = gender;
		this.birth = birth;
		this.id = id;
		this.phone_number = phone_number;
		this.postcode = postcode;
	}









	public Patient(Integer id_patient, String name, String gender, Date birth, int id, String phone_number,
			String postcode, List<Video_consultation> videos, List<Rating> ratings, List<Pathology> pathologies) {
		super();
		this.id_patient = id_patient;
		this.name = name;
		this.gender = gender;
		this.birth = birth;
		this.id = id;
		this.phone_number = phone_number;
		this.postcode = postcode;
		this.videos = videos;
		this.ratings = ratings;
		this.pathologies = pathologies;
	}














	@Override
	public String toString() {
		return "Patient [id_patient=" + id_patient + ", name=" + name + ", gender=" + gender + ", birth=" + birth
				+ ", id=" + id + ", phone_number=" + phone_number + ", postcode=" + postcode + "]";
	}









	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
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
		Patient other = (Patient) obj;
		if (id_patient == null) {
			if (other.id_patient != null)
				return false;
		} else if (!id_patient.equals(other.id_patient))
			return false;
		return true;
	}

	
	

	
	public List<Pathology> getPathologies() {
		return pathologies;
	}









	public void setPathologies(List<Pathology> pathologies) {
		this.pathologies = pathologies;
	}









	public void setId_patient(Integer id_patient) {
		this.id_patient = id_patient;
	}




	public List<Rating> getRatings() {
		return ratings;
	}




	public void setRatings(List<Rating> ratings) {
		this.ratings = ratings;
	}




	public List<Video_consultation> getVideos() {
		return videos;
	}



	public void setVideos(List<Video_consultation> videos) {
		this.videos = videos;
	}



	public int getId_patient() {
		return id_patient;
	}


	public void setId_patient(int id_patient) {
		this.id_patient = id_patient;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getGender() {
		return gender;
	}


	public void setGender(String gender) {
		this.gender = gender;
	}


	public Date getBirth() {
		return birth;
	}


	public void setBirth(Date birth) {
		this.birth = birth;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getPhone_number() {
		return phone_number;
	}


	public void setPhone_number(String phone_number) {
		this.phone_number = phone_number;
	}


	public String getPostcode() {
		return postcode;
	}


	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}
	
	
	

}
