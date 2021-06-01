package mconsultancy.db.ifaces;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

import db.pojos.Doctor;
import db.pojos.Pathology;
import db.pojos.Patient;
import db.pojos.Prescription;
import db.pojos.Rating;
import db.pojos.Video_consultation;

public interface DBinterface {
	// Connects with the database and, if needed, performs necessary setup
	public void connect();
	
	// Closes the connection with the database
	public void disconnect();

	
	//adds a pathology to the database
	public void addPathology(Pathology p);
	
	//adds a rating to the database
	public void addRating(Rating r);
	
	//adds a videoconsultation to the database
	public void addVideo_consultation(Video_consultation v);
	
	//adds a prescription to the database
	public void addPrescription(Prescription p);
	
	//adds a pathology to a specific patient
	public void diagnosePathology(int patient_id, int pathology_id);
	
	//Shows doctors with a given name
	public List<Doctor> searchDoctorByName(String name);
	
	//Shows the rating of doctors with a given name
	public List<Rating> getRatingOfDoctor(int id_doctor);
	
	//Shows patients with a given name
	public List<Patient> searchPatientByName(String name);
	
	//Shows the pathologies of patients with a given name
	public List<Pathology> getPathologiesOfPatient(int id_patient);
	
	//Lists the pathologies with a given name
	public List<Pathology> searchPathologyByName(String name);
	
	// Shows a specific patient
	public Patient getPatient(int id_patient);
	
	//Shows a specific doctor
	public Doctor getDoctor(int id_doctor);
	
	//Shows a specific pathology
	public Pathology getPathology(int id_pathology);
	
	// Shows a specific videconsultation
	public Video_consultation getVideo(int id_video);
	
	// Shows the prescriptions that have been set in a specific videoconsultation
	public List<Prescription> getPrescriptionOfVideos(int id_video);
	
	// Shows the videconsultations that a specific patient has had and has pending in the future 
	public List<Video_consultation> getVideosOfPatient(int id_patient);
	
	//Shows the future videoconsultations that a specific patient has
	public List<Video_consultation> getPatientFutureVideos(int id_patient);
	
	//Shows the past videconsultations of a specific patient
	public List<Video_consultation> getPatientPreviousVideos(int id_patient);
	
	// Shows the videconsultations that a specific doctor has had and has pending in the future 
	public List<Video_consultation> getVideosOfDoctor(int id_doctor);
	
	//Shows the future videoconsultations that a specific doctor has
	public List<Video_consultation> getDoctorFutureVideos(int id_doctor);
	
	////Shows the past videconsultations of a specific doctor
	public List<Video_consultation> getDoctorPreviousVideos(int id_doctor);
	
	//fire a doctor
	public void fireDoctor(int id);
	
	//delete a patient
	public void deletePatient(int id);
	
	//delete an appointment
	public void deleteAppointment(int id);
	
	//change an appointment by date 
	public void changeAppointmentDate(Date d, int id);
	
	//change appointment by time 
	public void changeAppointmentTime(Time t, int id);
	
	//Lists the availability of a specific doctor on a given date
	public List<Time> availableHours(int id_doctor, Date consultation_date);
	
	//updates the notes in a specific video consultation
	public void changeVideoconsultationNotes(String notes, int id);
	
	//updates the duration in a specific videoconsultation
	public void changeVideoconsultationDuration(int duration, int id);
	
	public List<Doctor> searchDoctorType(String name);
	

}
