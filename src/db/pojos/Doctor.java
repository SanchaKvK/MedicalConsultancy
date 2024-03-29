package db.pojos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import db.pojos.users.User;

@Entity
@DiscriminatorValue("d")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Doctor")
@XmlType(propOrder = {"specialization", "hospital"})
public class Doctor extends User implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1691412521236621262L;
	@XmlElement
	private String specialization;
	@XmlAttribute
	private String name;
	@XmlElement
	private String hospital;
	@XmlTransient
	@OneToMany(mappedBy = "doc")
	private List<Video_consultation> videos;
	@OneToMany(mappedBy = "doc")
	@XmlTransient
	private List<Rating> ratings;
	@XmlTransient
	@Basic(fetch = FetchType.LAZY)
	@Lob
	private byte[] photo;

	public Doctor() {
		super();
		this.videos = new ArrayList<Video_consultation>();
		this.ratings = new ArrayList<Rating>();

	}

	public Doctor(Integer id, String email, byte[] password, String role_name, String specialization, String name,
			String hospital, byte[] photo) {
		super(id, email, password, role_name);
		this.specialization = specialization;
		this.name = name;
		this.hospital = hospital;
		this.videos = new ArrayList<Video_consultation>();
		this.ratings = new ArrayList<Rating>();
		this.photo = photo;
	}

	public Doctor(Integer id, String specialization, String name, String hospital, byte[] photo) {
		// removed from this constructor the attribute photo because it was causing
		// problems
		// TODO correct photo issues and reintroduce it if needed
		super(id);
		this.specialization = specialization;
		this.name = name;
		this.hospital = hospital;
		this.videos = new ArrayList<Video_consultation>();
		this.ratings = new ArrayList<Rating>();
		this.photo = photo;
	}

	public Doctor(String email, byte[] password, String role_name, String specialization, String name, String hospital,
			byte[] photo) {
		super(email, password, role_name);
		this.specialization = specialization;
		this.name = name;
		this.hospital = hospital;
		this.photo = photo;
	}

	public Doctor(String specialization, String name, String hospital, List<Video_consultation> videos,
			List<Rating> ratings, byte[] photo) {
		super();
		this.specialization = specialization;
		this.name = name;
		this.hospital = hospital;
		this.videos = videos;
		this.ratings = ratings;
		this.photo = photo;
	}

	public Doctor(String specialization, String name, String hospital, byte[] photo) {
		super();
		this.specialization = specialization;
		this.hospital = hospital;
		this.name = name;
		this.videos = new ArrayList<Video_consultation>();
		this.ratings = new ArrayList<Rating>();
		this.photo = photo;
	}

//Doctor prints ratings but not videos

	@Override
	public String toString() {
		return "Doctor [id_doctor=" + super.getId() + ", specialization=" + specialization + ", name=" + name
				+ ", hospital=" + hospital + ", ]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((super.getId() == null) ? 0 : super.getId().hashCode());
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
		if (super.getId() == null) {
			if (other.getId() != null)
				return false;
		} else if (!super.getId().equals(other.getId()))
			return false;
		return true;
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

	public byte[] getPhoto() {
		return photo;
	}

	public void setPhoto(byte[] photo) {
		this.photo = photo;
	}

}
