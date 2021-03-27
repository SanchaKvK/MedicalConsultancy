import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.*;
import db.pojos.*;

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
			
		}catch(SQLException sqlE) {
			System.out.println("There was a database exception");
			
			
		}catch(Exception e) {
			System.out.println("There was a general exception");
			e.printStackTrace();
		}
	}
	
	private void createTables() {
		Statement stmt1;
		try {
			stmt1 = c.createStatement();
		
			String sql1 = " CREATE TABLE patient "
					+"(id_patient INTEGER PRIMARY KEY AUTOINCREMENT, "
					+"name TEXT NOT NULL, "
					+"gender TEXT, "
					+"date of birth DATE NOT NULL, "
					+"id TEXT NOT NULL UNIQUE, "
					+"phone number TEXT UNIQUE, "
					+"postcode TEXT)";
			
			stmt1.executeUpdate(sql1);
			sql1 = "CREATE TABLE doctor "
					+"(id_doctor INTEGER PRIMARY KEY AUTOINCREMENT, "
					+"specialization TEXT NOT NULL, "
					+"name TEXT NOT NULL, "
					+"hospital TEXT)";
			stmt1.executeUpdate(sql1);
			
			
			sql1 = "CREATE TABLE videoconsultation "
					+"(id_video INTEGER PRIMARY KEY AUTOINCREMENT, "
					+"consultation_date DATE NOT NULL, "
					+"consultation_time TIME NOT NULL, "
					+"duration INTEGER, "
					+"type of call TEXT, "
					+"notes TEXT, "
					+"id_doctor INTEGER REFERENCES doctor(id_doctor) ON DELETE SET NULL, "
					+"id_patient INTEGER REFERENCES patient(id_patient) ON DELETE SET NULL)";	

			stmt1.executeUpdate(sql1);
			
			
			sql1 = "CREATE TABLE prescription "
					+"(id_prescription INTEGER PRIMARY KEY AUTOINCREMENT, "
					+"doses INTEGER NOT NULL, "
					+"notes TEXT, "
					+"duration INTEGER NOT NULL, "
					+"name TEXT NOT NULL, "
					+"id_video INTEGER REFERENCES video_consultation(id_video) ON DELETE SET NULL)";
			
			stmt1.executeUpdate(sql1);
			
			sql1 = "CREATE TABLE pathology "
					+ "id_pathology INTEGER PRIMARY KEY AUTOINCREMENT, "
					+ "name TEXT NOT NULL, "
					+ "type TEXT)";
					
			stmt1.executeUpdate(sql1);
			
			sql1= "CREATE TABLE patient_pathology "
					+"id_patient INTEGER REFERENCES patient(id_patient) ON DELETE SET NULL, "
					+"id_pathology INTEGER REFERENCES pathology(id_pathology) ON DELETE SET NULL, "
					+"PRIMARY KEY(id_patient,id_pathology))";
			
			stmt1.executeUpdate(sql1);
			
			sql1 = "CREATE TABLE rating "
					+"id_patient INTEGER REFERENCES patient(id_patient) ON DELETE SET NULL, "
					+"id_doctor INTEGER REFERENCES doctor(id_doctor) ON DELETE SET NULL, "
					+"score INTEGER, "
					+"review TEXT, "
					+"PRIMARY KEY(id_patient,id_doctor))";
			
			
			stmt1.executeUpdate(sql1);
			stmt1.close();
					
		}catch(SQLException e) {
			if(!e.getMessage().contains("already exists")) {
				e.printStackTrace();
			}
		}
		
	}

	public void disconnect() {
		try {
			c.close();
		}catch(SQLException e) {
			System.out.println("There was a problem whilst closing the database connection");
			e.printStackTrace();
		}
	}
	
	
	public void addPatient(Patient p){
		try {
			Statement stmt = c.createStatement();
			String sql = "INSERT INTO Patient (name , gender, date of birth, id, phone number, postcode) VALUES('"+p.getName()+"', '"+ p.getGender()+"', "+ p.getBirth() + ",'" +p.getId()+"', '"+ 
					p.getPhone_number()+"', '"+p.getPostcode()+"'";
			stmt.executeUpdate(sql);
			stmt.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void addDoctor(Doctor d) {
		
		try {
			Statement stmt = c.createStatement();
			String sql = "INSERT INTO Doctor (name, specialization, hospital) VALUES ('"+d.getName()+"', '"+d.getSpecialization()+"','"+d.getHospital()+"')";
			stmt.executeUpdate(sql);
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
		

	public void addRating (Rating r) { 
	 
		try {
			Statement stmt = c.createStatement();
			String sql = "INSERT INTO Rating (id_Doctor,id_Patient,score,review) VALUES ('"+r.getDoc().getId_doctor()+ "','"+r.getPat().getId_patient()+"','"+r.getReview()+"'+'"+r.getScore()+"')";
			stmt.executeUpdate(sql);
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
		
	public void addVideo_consultation (Video_consultation v) {
		
		try {
			Statement stmt = c.createStatement();
			String sql = "INSERT INTO Video_consultation(id_video, consultation_date,consultation_time,duration,type,notes,prescription,id_doctor,id_patient) VALUES ('"+v.getId_video()+"', '"+v.getConsultation_date()+"','"+v.getConsultatiton_time()+"','"+v.getDuration()+"', '"+v.getDoc().getId_doctor()+"','"+v.getPat().getId_patient()+"')";
			stmt.executeUpdate(sql);
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}

	
	public void addPrescription(Prescription p) {
		
		try {
			Statement stmt = c.createStatement();
			String sql = "INSERT INTO prescription (doses, notes, duration, name, id_video) VALUES ("+p.getDoses()+",'"+p.getNotes()+"',"+p.getDuration()+",'"+p.getName()+"',"+p.getVd().getId_video()+")";
			stmt.executeUpdate(sql);
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	
	

	}
