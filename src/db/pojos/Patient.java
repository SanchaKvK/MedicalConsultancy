package db.pojos;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import db.pojos.users.User;

@Entity
@DiscriminatorValue("p")

public class Patient extends User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2012426771680446727L;

	private String name;
	private String gender;
	private Date birth;
	private String DNI;
	private String phone_number;
	private String postcode;

	@OneToMany(mappedBy = "pat")
	private List<Video_consultation> videos;
	@OneToMany(mappedBy = "pat")
	private List<Rating> ratings;

	@ManyToMany
	@JoinTable(name = "patient_pathology", joinColumns = {
			@JoinColumn(name = "id_patient", referencedColumnName = "id") }, inverseJoinColumns = {
					@JoinColumn(name = "id_pathology", referencedColumnName = "id_pathology") })
	private List<Pathology> pathologies;

	public Patient() {

		super();
		this.videos = new ArrayList<Video_consultation>();
		this.ratings = new ArrayList<Rating>();
		this.pathologies = new ArrayList<Pathology>();
	}

	public Patient(String email, byte[] password, String role, String name, String gender, Date birth, String iD,
			String phone_number, String postcode) {
		super(email, password, role);
		this.name = name;
		this.gender = gender;
		this.birth = birth;
		this.DNI = iD;
		this.phone_number = phone_number;
		this.postcode = postcode;
	}

	public Patient(Integer id, String email, byte[] password, String role, String name, String gender, Date birth,
			String iD, String phone_number, String postcode) {
		super(id, email, password, role);
		this.name = name;
		this.gender = gender;
		this.birth = birth;
		this.DNI = iD;
		this.phone_number = phone_number;
		this.postcode = postcode;
		this.videos = new ArrayList<Video_consultation>();
		this.ratings = new ArrayList<Rating>();
		this.pathologies = new ArrayList<Pathology>();
	}

	public Patient(Integer id, String name, String gender, Date birth, String iD2, String phone_number,
			String postcode) {
		super(id);
		this.name = name;
		this.gender = gender;
		this.birth = birth;
		this.DNI = iD2;
		this.phone_number = phone_number;
		this.postcode = postcode;
	}

	public Patient(String name, String gender, Date birth, String ID, String phone_number, String postcode) {
		super();
		this.name = name;
		this.gender = gender;
		this.birth = birth;
		this.DNI = ID;
		this.phone_number = phone_number;
		this.postcode = postcode;
		this.videos = new ArrayList<Video_consultation>();
		this.ratings = new ArrayList<Rating>();
		this.pathologies = new ArrayList<Pathology>();
	}

//Patient print pathologies but not videos and ratings

	@Override
	public String toString() {
		return "Patient [id_patient=" + super.getId() + ", name=" + name + ", gender=" + gender + ", birth=" + birth
				+ ", id=" + DNI + ", phone_number=" + phone_number + ", postcode=" + postcode + ", pathologies="
				+ pathologies + "]";
	}

	public List<Pathology> getPathologies() {
		return pathologies;
	}

	public void setPathologies(List<Pathology> pathologies) {
		this.pathologies = pathologies;
	}

	public List<Rating> getRatings() {
		return ratings;
	}

	public void addPathology(Pathology p) {
		if (!this.pathologies.contains(p))
			this.pathologies.add(p);
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

	public String getID() {
		return DNI;

	}

	public void setID(String id) {
		this.DNI = id;
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
