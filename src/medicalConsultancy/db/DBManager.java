package medicalConsultancy.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import db.pojos.Doctor;
import db.pojos.Pathology;
import db.pojos.Patient;
import db.pojos.Prescription;
import db.pojos.Rating;
import db.pojos.Video_consultation;

public class DBManager {

	private Connection c;

	public void connect() {
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:./db/MedicalConsultancy.db");
			c.createStatement().execute("PRAGMA foreign_keys=ON");
			System.out.println("Database connection opened");
			this.createTables();
			

		} catch (SQLException sqlE) {
			System.out.println("There was a database exception");

		} catch (Exception e) {
			System.out.println("There was a general exception");
			e.printStackTrace();
		}
	}
	
	

	private void createTables() {
		Statement stmt1;
		try {
			stmt1 = c.createStatement();

			String sql1 = " CREATE TABLE patient " + "(id_patient INTEGER PRIMARY KEY AUTOINCREMENT, "
					+ "name TEXT NOT NULL, " + "gender TEXT, " + "date_of_birth DATE NOT NULL, "
					+ "id TEXT NOT NULL UNIQUE, " + "phone_number TEXT UNIQUE, " + "postcode TEXT)";

			stmt1.executeUpdate(sql1);
			sql1 = "CREATE TABLE doctor " + "(id_doctor INTEGER PRIMARY KEY AUTOINCREMENT, "
					+ "specialization TEXT NOT NULL, " + "name TEXT NOT NULL, " + "hospital TEXT)";
			stmt1.executeUpdate(sql1);

			sql1 = "CREATE TABLE videoconsultation " + "(id_video INTEGER PRIMARY KEY AUTOINCREMENT, "
					+ "consultation_date DATE NOT NULL, " + "consultation_time TIME NOT NULL, " + "duration INTEGER, "
					+ "type_of_call TEXT, " + "notes TEXT, "
					+ "id_doctor INTEGER REFERENCES doctor(id_doctor) ON DELETE SET NULL, "
					+ "id_patient INTEGER REFERENCES patient(id_patient) ON DELETE SET NULL)";
			

			stmt1.executeUpdate(sql1);

			sql1 = "CREATE TABLE prescription " + "(id_prescription INTEGER PRIMARY KEY AUTOINCREMENT, "
					+ "doses INTEGER NOT NULL, " + "notes TEXT, " + "duration INTEGER NOT NULL, "
					+ "name TEXT NOT NULL, "
					+ "id_video INTEGER REFERENCES videoconsultation(id_video) ON DELETE SET NULL)";

			stmt1.executeUpdate(sql1);

			sql1 = "CREATE TABLE pathology " + "(id_pathology INTEGER PRIMARY KEY AUTOINCREMENT, "
					+ "name TEXT NOT NULL, " + "type TEXT)";

			stmt1.executeUpdate(sql1);

			sql1 = "CREATE TABLE patient_pathology "
					+ "(id_patient INTEGER REFERENCES patient(id_patient) ON DELETE SET NULL, "
					+ "id_pathology INTEGER REFERENCES pathology(id_pathology) ON DELETE SET NULL, "
					+ "PRIMARY KEY(id_patient,id_pathology))";

			stmt1.executeUpdate(sql1);

			sql1 = "CREATE TABLE rating " + "(id_patient INTEGER REFERENCES patient(id_patient) ON DELETE SET NULL, "
					+ "id_doctor INTEGER REFERENCES doctor(id_doctor) ON DELETE SET NULL, " + "score INTEGER, "
					+ "review TEXT, " + "PRIMARY KEY(id_patient,id_doctor))";

			stmt1.executeUpdate(sql1);
			stmt1.close();

		} catch (SQLException e) {
			if (!e.getMessage().contains("already exists")) {
				e.printStackTrace();
				}
			
			
		
		}	
		
		
	}
	
	
	

	public void disconnect() {
		try {
			c.close();
		} catch (SQLException e) {
			System.out.println("There was a problem whilst closing the database connection");
			e.printStackTrace();
		}
	}

	public void addPatient(Patient p) {
		try {
			String sql="INSERT INTO patient (name , gender, date_of_birth, id, phone_number, postcode) VALUES(?,?,?,?,?,?)";
			PreparedStatement ps=c.prepareStatement(sql);
			ps.setString(1,p.getName());
			ps.setString(2, p.getGender());
			ps.setDate(3, p.getBirth());
			ps.setString(4, p.getId());
			ps.setString(5, p.getPhone_number());
			ps.setString(6, p.getPostcode());
			
			ps.executeUpdate();
			ps.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void addDoctor(Doctor d) {

		try {
			Statement stmt = c.createStatement();
			String sql = "INSERT INTO doctor (name, specialization, hospital) VALUES ('" + d.getName() + "', '"
					+ d.getSpecialization() + "','" + d.getHospital() + "')";
			stmt.executeUpdate(sql);
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public void addPathology(Pathology p) {

		try {
			Statement stmt = c.createStatement();
			String sql = "INSERT INTO pathology (name, type) VALUES ('" + p.getName() + "', '"
				 + p.getType() + "')";
			stmt.executeUpdate(sql);
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	


	public void addRating(Rating r) {

		try {
			String sql="INSERT INTO rating (id_doctor,id_patient,score,review) VALUES(?,?,?,?)";
			PreparedStatement ps=c.prepareStatement(sql);
			ps.setInt(1,r.getDoc().getId_doctor());
			ps.setInt(2, r.getPat().getId_patient());
			ps.setInt(3, r.getScore());
			ps.setString(4, r.getReview());
			ps.executeUpdate();
			ps.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	


	public void addVideo_consultation(Video_consultation v) {

		try {
			
			String sql="INSERT INTO videoconsultation (consultation_date, consultation_time,type_of_call, id_doctor, id_patient) VALUES(?,?,?,?,?)";
			PreparedStatement ps=c.prepareStatement(sql);
			ps.setDate(1, v.getConsultation_date());
			ps.setTime(2, v.getConsultatiton_time());
			ps.setString(3, v.getType());
			ps.setInt(4, v.getDoc().getId_doctor());
			ps.setInt(5, v.getPat().getId_patient());
			ps.executeUpdate();
			ps.close();
		}
		
		
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	

	public void addPrescription(Prescription p) {

		try {
			Statement stmt = c.createStatement();
			String sql = "INSERT INTO prescription(doses, notes, duration, name, id_video) VALUES (" + p.getDoses()
					+ ",'" + p.getNotes() + "'," + p.getDuration() + ",'" + p.getName() + "'," + p.getVd().getId_video()
					+ ")";
			stmt.executeUpdate(sql);
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	
	
	public void diagnosePathology(int patient_id,int pathology_id) {
		try {
			
		String sql="INSERT INTO patient_pathology(id_patient,id_pathology) VALUES(?,?)";
		PreparedStatement ps=c.prepareStatement(sql);
		ps.setInt(1, patient_id);
		ps.setInt(2, pathology_id);
		ps.executeUpdate();
		ps.close();
		
		
			}
		
		catch(Exception e) {
			e.printStackTrace();
		}
		
		
	}

	


	public List<Doctor> searchDoctorByName(String name) {
		// TODO ratings?
		List<Doctor> doctors = new ArrayList<>();

		try {
			String sql = "SELECT * FROM doctor WHERE name LIKE ?";
			PreparedStatement stmt = c.prepareStatement(sql);
			stmt.setString(1, "%" + name + "%");
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {

				Doctor doctor = new Doctor(rs.getInt("id_doctor"), rs.getString("specialization"), rs.getString("name"),
						rs.getString("hospital"));
				doctor.setRatings(this.getRatingOfDoctors(doctor.getId_doctor()));
				doctors.add(doctor);
			}
			rs.close();
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return doctors;
	}
	
	public List<Rating> getRatingOfDoctors(int id_doctor){
		List<Rating>ratings=new ArrayList<Rating>();
		try {
		String sql="SELECT*FROM rating WHERE id_doctor=?";
		PreparedStatement ps=c.prepareStatement(sql);
		ps.setInt(1, id_doctor);
		ResultSet rs=ps.executeQuery();
		
		while(rs.next()) {
			
			ratings.add(new Rating(rs.getInt("score"),rs.getString("review")));
		}
		
		
		}catch(Exception e) {
			e.printStackTrace();
			
		}
		
		return ratings;
		
	}

	
	

	public List<Patient> searchPatientByName(String name) {
		List<Patient> patients = new ArrayList<Patient>();

		try {
			String sql = "SELECT * FROM patient WHERE name LIKE ?";
			PreparedStatement stmt = c.prepareStatement(sql);
			stmt.setString(1, "%" + name + "%");
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {

				Patient patient = new Patient(rs.getInt("id_patient"), rs.getString("name"), rs.getString("gender"),
						rs.getDate("date_of_birth"), rs.getString("id"), rs.getString("phone_number"),
						rs.getString("postcode"));
				patient.setPathologies(getPathologiesOfPatient(patient.getId_patient()));
				patients.add(patient);
			}
			rs.close();
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return patients;
	}
	 
	public List<Video_consultation> getVideosOfPatient(int id_patient) {
		List<Video_consultation> videos=new ArrayList<Video_consultation>();
		try {
			
			String sql="SELECT * FROM videoconsultation WHERE id_patient=?";
			PreparedStatement ps=c.prepareStatement(sql);
			ps.setInt(1,id_patient);
			ResultSet rs=ps.executeQuery();
			
			while(rs.next()) {
				
				Video_consultation v=new Video_consultation(rs.getInt("id_video"),rs.getDate("consultation_date"),
						rs.getTime("consultation_time"),rs.getInt("duration"),rs.getString("type_of_call"),
						rs.getString("notes"),this.getDoctor(rs.getInt("id_doctor")),this.getPatient(rs.getInt("id_patient")));
				videos.add(v);
				
			}
			
			
		}
		
		catch(Exception e) {
			e.printStackTrace();
		}
		
		
		return videos;
	}
	
	

	

	public List<Pathology> getPathologiesOfPatient(int id_patient) {

		List<Pathology> pathologies = new ArrayList<Pathology>();

		try {
			String sql = "SELECT*FROM patient_pathology WHERE id_patient=?";
			PreparedStatement ps = c.prepareStatement(sql);
			ps.setInt(1, id_patient);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {

				int id_pathology = rs.getInt("id_pathology");
				pathologies.add(getPathology(id_pathology));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return pathologies;
	}

	public Pathology getPathology(int id_pathology) {
		try {	
			
			String sql="SELECT*FROM pathology WHERE id_pathology=?";
			PreparedStatement ps=c.prepareStatement(sql);
			ps.setInt(1,id_pathology );
			
			ResultSet rs=ps.executeQuery();
			
			if(rs.next()) {
				
				String name=rs.getString("name");
				String type=rs.getString("type");
				return new Pathology(id_pathology,name,type);
				
			}
				
				rs.close();
				ps.close();}
				
			
			catch(Exception e) {
				
				e.printStackTrace();
			}
			
			
	
			return null;}
	
	//Doctor method will return a Doctor with id,specialization,name and hospital.
	
	public Doctor getDoctor(int id_doctor) {
		
		try {
			
			String sql="SELECT*FROM doctor WHERE id_doctor=?";
			PreparedStatement ps=c.prepareStatement(sql);
			ps.setInt(1, id_doctor);
			ResultSet rs=ps.executeQuery();
			
			if(rs.next()) {
				
				return new Doctor(id_doctor,rs.getString("specialization"),rs.getString("name"),rs.getString("hospital"));
				
				
			}
			
			rs.close();
			ps.close();
			}
		
		
		
		catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	
	
	public Rating getRating(int id_doctor, int id_patient) {
		
		try {
			
			String sql="SELECT*FROM rating WHERE id_doctor=? AND id_patient=?";
			PreparedStatement ps=c.prepareStatement(sql);
			ps.setInt(1, id_doctor);
			ps.setInt(2, id_patient);
			ResultSet rs=ps.executeQuery();
			
			if(rs.next()) {
				
				return new Rating(this.getDoctor(rs.getInt("id_doctor")),this.getPatient(rs.getInt("id_patient")),rs.getInt("score"),rs.getString("review"));
				
				
			}
			
			rs.close();
			ps.close();
			}
		
		
		
		catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public Video_consultation getVideo(int id_video) {
		
		
		try {
			String sql="SELECT*FROM videoconsultation WHERE id_video=?";
			PreparedStatement ps=c.prepareStatement(sql);
			ps.setInt(1, id_video);
			ResultSet rs=ps.executeQuery();
			
			
			if(rs.next()) {
				
				Video_consultation v= new Video_consultation(rs.getInt("id_video"),rs.getDate("consultation_date"),rs.getTime("consultation_time"),rs.getInt("duration"),rs.getString("type_of_call"),
						rs.getString("notes"),this.getDoctor(rs.getInt("id_doctor")),this.getPatient(rs.getInt("id_patient")));
			v.setPrescription(this.getPrescriptionOfVideos(v.getId_video()));
			return v;
			
			}
			
			rs.close();
			ps.close();
		}
		
		catch(Exception e) {e.printStackTrace();}
		
		
		
		
		
		return null;
	}
	
	
	public List<Prescription> getPrescriptionOfVideos(int id_video){
		
		List<Prescription> prescriptions=new ArrayList<Prescription>();
		try {
			String sql="SELECT*FROM prescription WHERE id_video=?";
			PreparedStatement ps=c.prepareStatement(sql);
			ps.setInt(1, id_video);
			ResultSet rs=ps.executeQuery();
			while(rs.next()) {
				
				prescriptions.add(this.getPrescription(rs.getInt("id_prescription")));
				
			}
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return prescriptions;
	}
	
public Prescription getPrescription(int id_prescription) {
		
		try {
			
			String sql="SELECT*FROM prescription WHERE id_prescription=?";
			PreparedStatement ps=c.prepareStatement(sql);
			ps.setInt(1, id_prescription);
			ResultSet rs=ps.executeQuery();
			
			if(rs.next()) {
				
				return new Prescription(rs.getInt("id_prescription"),rs.getString("name"),rs.getInt("doses"),
						rs.getInt("duration"),rs.getString("notes"));
				
			}
			
			rs.close();
			ps.close();
			}
		
		
		
		catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	//Patient method will return a Patient with id,name,gender,birth,id,phone,postcode.
	
	public Patient getPatient(int id_patient) {
		
		try {
			
			String sql="SELECT*FROM patient WHERE id_patient=?";
			PreparedStatement ps=c.prepareStatement(sql);
			ps.setInt(1, id_patient);
			ResultSet rs=ps.executeQuery();
			
			if(rs.next()) {
				
				return new Patient(id_patient,rs.getString("name"),rs.getString("gender"),
						rs.getDate("date_of_birth"),rs.getString("id"),rs.getString("phone_number"),rs.getString("postcode"));
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
		
	}
	

	public void fireDoctor(int id) {
		try {
			String sql = "DELETE FROM doctor WHERE id_doctor = ?";
			PreparedStatement prep = c.prepareStatement(sql);
			prep.setInt(1, id);
			prep.executeUpdate();
			prep.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void deletePatient(int id) {
		try {
			String sql = "DELETE FROM patient WHERE id_patient = ?";
			PreparedStatement prep = c.prepareStatement(sql);
			prep.setInt(1, id);
			prep.executeUpdate();
			prep.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void deleteVideo(int id) {
		try {
			String sql = "DELETE FROM videoconsultation WHERE id_video = ?";
			PreparedStatement prep = c.prepareStatement(sql);
			prep.setInt(1, id);
			prep.executeUpdate();
			prep.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	


	

	
	

	
	
	public List<Pathology> searchPathologyByName(String name) {
		List<Pathology> p=new ArrayList<Pathology>();
		try {
			String sql="SELECT*FROM pathology WHERE name LIKE ?";
			PreparedStatement stmt = c.prepareStatement(sql);
			stmt.setString(1, "%" + name + "%");
			ResultSet rs=stmt.executeQuery();
			
			while(rs.next()) {
				
				Pathology py=new Pathology(rs.getInt("id_pathology"),rs.getString("name"),rs.getString("type"));
				p.add(py);
				
			}	
			rs.close();
			stmt.close();
			
		}
		
		catch(Exception e) {
			e.printStackTrace();
		}
		
		return p;
	}
}
